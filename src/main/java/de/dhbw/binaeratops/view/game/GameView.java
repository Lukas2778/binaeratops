package de.dhbw.binaeratops.view.game;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.api.RoomI;
import de.dhbw.binaeratops.model.chat.ChatMessage;
import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
import de.dhbw.binaeratops.model.map.Tile;
import de.dhbw.binaeratops.model.repository.DungeonRepositoryI;
import de.dhbw.binaeratops.model.repository.RoomRepositoryI;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import de.dhbw.binaeratops.service.api.configuration.DungeonServiceI;
import de.dhbw.binaeratops.service.api.parser.ParserServiceI;
import de.dhbw.binaeratops.service.api.map.MapServiceI;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerSyntaxMissingException;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerSyntaxUnexpectedException;
import de.dhbw.binaeratops.service.impl.parser.UserMessage;
import de.dhbw.binaeratops.view.chat.ChatView;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Oberfläche des Tabs 'Über uns'
 */
@Route(value = "gameView")
@CssImport("./views/game/game-view.css")
@PageTitle("Dungeon - Spiel")
public class GameView extends VerticalLayout implements HasUrlParameter<Long> {
    ParserServiceI myParserService;
    MapServiceI mapServiceI;
    RoomRepositoryI myRoomRepo;//@TODO nur zu Testzwecken, dann entfernen
    DungeonRepositoryI myDungeonRepo;
    ConfiguratorServiceI myConfiguratorService;
    DungeonServiceI myDungeonService;

    Long dungeonId;
    Dungeon myDungeon;
    Image[][] myTiles;
    Dialog myAvatarDialog;
    Dialog myCreateAvatarDialog;

    private final ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());
    private final Flux<ChatMessage> myMessages;

    H2 binTitle;
    String aboutText;
    Html html;

    HorizontalLayout gameLayout;
    SplitLayout gameSplitLayout;
    VerticalLayout gameFirstLayout;
    VerticalLayout gameSecondLayout;
    HorizontalLayout insertInputLayout;
    VerticalLayout mapLayout;
    HorizontalLayout gridLayout;
    VerticalLayout gridLayoutVert;

    ChatView myDungeonChatView;
    TextField textField;
    Button confirmButt;
    Button leftDungeonButt;

    //@TODO remove test navigation
    Button northButt;
    Button eastButt;
    Button southButt;
    Button westButt;

    private List<Item> inventoryList;
    private List<Item> armorList;

    private Avatar myAvatar;
    private Room currentRoom;
    private List<Room> visitedRooms;

    /**
     * Konstruktor zum Erzeugen der View für den Tab 'Über uns'.
     *
     * @param messages       Nachrichten.
     * @param AParserService ParserService.
     */
    public GameView(Flux<ChatMessage> messages, @Autowired ParserServiceI AParserService,
                    @Autowired MapServiceI AMapService, @Autowired RoomRepositoryI ARoomRepo,
                    @Autowired DungeonRepositoryI ADungeonRepo, @Autowired ConfiguratorServiceI AConfiguratorService, @Autowired DungeonServiceI ADungeonService) {
        myParserService = AParserService;
        mapServiceI = AMapService;
        myRoomRepo = ARoomRepo;//@TODO remove
        myDungeonRepo = ADungeonRepo;
        myConfiguratorService=AConfiguratorService;
        myDungeonService=ADungeonService;


        myMessages = messages;
        binTitle = new H2("Du bist in der Spieloberfläche!");
        aboutText = "<div>Du hast auf einen aktiven Dungeon geklickt und kannst hier Teile des Chats und des Parsers" +
                " testen.<br>Schau dir zuerst die 'Help' an, indem du /help eingibsty.</div>";
        html = new Html(aboutText);

        myDungeonChatView = new ChatView(messages);
        gameLayout = new HorizontalLayout();
        gameSplitLayout = new SplitLayout();
        gameFirstLayout = new VerticalLayout();
        gameSecondLayout = new VerticalLayout();
        mapLayout = new VerticalLayout();
        gridLayout = new HorizontalLayout();
        gridLayoutVert = new VerticalLayout();
        leftDungeonButt = new Button("Dungeon verlassen");
        leftDungeonButt.getStyle().set("color", "red");
        leftDungeonButt.addClickListener(e->{
           UI.getCurrent().navigate("lobby");
        });

        textField = new TextField();
        textField.focus();
        textField.setWidthFull();

        confirmButt = new Button("Eingabe");
        confirmButt.addClickShortcut(Key.ENTER);
        confirmButt.addClickListener(e -> {
            //Parser wird mit Texteingabe aufgerufen
            try {
                UserMessage um = myParserService.parseCommand(textField.getValue(), dungeonId, myAvatar, VaadinSession.getCurrent().getAttribute(User.class));
                if (um.getKey() != null) {
                    switch (um.getKey()) {
                        case "view.game.ingame.cmd.notify.all":
                            myDungeonChatView.messageList.add(new Paragraph(MessageFormat.format(res.getString(um.getKey()), um.getParams().get(0))));
                            break;
                        case "view.game.cmd.help":
                        case "view.game.cmd.help.cmds":
                        case "view.game.cmd.help.all":
                            myDungeonChatView.messageList.add(new Paragraph(new Html(MessageFormat.format(res.getString(um.getKey()), um.getParams().get(0)))));
                            break;
                        case "view.game.cmd.help.ctrl":
                            myDungeonChatView.messageList.add(new Paragraph(new Html(res.getString(um.getKey()))));
                            break;
                        default:
                            Notification.show("An Error Occured.");
                            break;
                    }
                }
            } catch (CmdScannerSyntaxMissingException syntaxMissing) {
                Notification.show(MessageFormat.format(res.getString(syntaxMissing.getUserMessage().getKey()), syntaxMissing.getUserMessage().getParams().get(0))).setPosition(Notification.Position.BOTTOM_CENTER);
            } catch (CmdScannerSyntaxUnexpectedException syntaxUnexpected) {
                Notification.show(MessageFormat.format(res.getString(syntaxUnexpected.getUserMessage().getKey()), syntaxUnexpected.getUserMessage().getParams().get(0), syntaxUnexpected.getUserMessage().getParams().get(1))).setPosition(Notification.Position.BOTTOM_CENTER);
            } catch (CmdScannerException | InvalidImplementationException cmdScannerException) {
                cmdScannerException.printStackTrace();
            }
        });
        confirmButt.setWidth("120px");

        insertInputLayout = new HorizontalLayout();
        insertInputLayout.add(textField, confirmButt);
        insertInputLayout.setWidthFull();

        gameFirstLayout.setSizeFull();
        gameSecondLayout.setSizeFull();

        gameSplitLayout.addToPrimary(gameFirstLayout);
        gameSplitLayout.addToSecondary(gameSecondLayout);

        gameSplitLayout.setSizeFull();
        gameLayout.add(gameSplitLayout);
        gameLayout.setSizeFull();

        //@TODO remove
        northButt = new Button("north");
        northButt.addClickListener(e -> {
            changeRoom(currentRoom.getNorthRoomId());
        });
        eastButt = new Button("east");
        eastButt.addClickListener(e -> {
            changeRoom(currentRoom.getEastRoomId());
        });
        southButt = new Button("south");
        southButt.addClickListener(e -> {
            changeRoom(currentRoom.getSouthRoomId());
        });
        westButt = new Button("west");
        westButt.addClickListener(e -> {
            changeRoom(currentRoom.getWestRoomId());
        });
        //@TODO remove

        gameFirstLayout.add(new HorizontalLayout(northButt, eastButt, southButt, westButt), binTitle, html, myDungeonChatView, insertInputLayout);
        gameSecondLayout.add(mapLayout, gridLayoutVert, leftDungeonButt);
        mapLayout.setClassName("map-layout");
        gridLayoutVert.setClassName("grid-layout");
        expand(mapLayout);
        expand(myDungeonChatView);
        add(gameLayout);

        setSizeFull();
    }

    @Override
    public void setParameter(BeforeEvent ABeforeEvent, Long ALong) {
        dungeonId = ALong;
        myDungeon = myDungeonRepo.findByDungeonId(dungeonId);
        //Avatarauswahl öffnen
        createAvatarDialog();
    }

    void createAvatarDialog() {
        HorizontalLayout buttLayout;
        VerticalLayout gridLayoutVert;
        H2 title;
        List<Avatar> avatarList;
        Grid<Avatar> avatarGrid;

        myAvatarDialog= new Dialog();
        myAvatarDialog.open();

        myAvatarDialog.setCloseOnEsc(false);
        myAvatarDialog.setCloseOnOutsideClick(false);
        buttLayout = new HorizontalLayout();
        gridLayoutVert=new VerticalLayout();

        // Header
        title = new H2("Avatarauswahl");
        Header header = new Header(title);

        avatarList = new ArrayList<>();
        User user = VaadinSession.getCurrent().getAttribute(User.class);
        avatarList.addAll(user.getAvatars());

        avatarGrid = new Grid<>();
        avatarGrid.setItems(avatarList);
        avatarGrid.setVerticalScrollingEnabled(true);
        avatarGrid.addColumn(Avatar::getName).setHeader("Avatarname");
        avatarGrid.addColumn(Avatar::getRace).setHeader("Rasse");
        avatarGrid.addColumn(Avatar::getRole).setHeader("Rolle");
        avatarGrid.addColumn(Avatar::getCurrentRoom).setHeader("letzter Raum");
        //avatarGrid.setSizeFull();
        expand(avatarGrid);
        gridLayoutVert.add(avatarGrid);

        // Footer
        Button cancel = new Button("Verlassen", e -> {
            myAvatarDialog.close();
            UI.getCurrent().navigate("lobby");
        });
        cancel.getStyle().set("color", "red");

        Button createAvatar = new Button("Neuer Avatar", e -> {
            createNewAvatarDialog();
        });
        createAvatar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        createAvatar.focus();

        Button enterDungeon = new Button("Eintreten", e -> {
            Set selectedAvatar=avatarGrid.getSelectedItems();
            if(selectedAvatar.size()>0) {
                //Dungeon betreten
                myAvatarDialog.close();
                loadAvatarProgress(myDungeon.getAvatarById(((Avatar) selectedAvatar.toArray()[0]).getAvatarId()));
                createMap();
                changeRoom(currentRoom.getRoomId());
            }
        });
        enterDungeon.addClickShortcut(Key.ENTER);
        enterDungeon.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        buttLayout.add(cancel, createAvatar, enterDungeon);
        buttLayout.setWidthFull();

        // Add it all together
        myAvatarDialog.add(header, gridLayoutVert, buttLayout);
        myAvatarDialog.setHeight("70%");
        myAvatarDialog.setWidth("50%");
    }

    void createNewAvatarDialog(){
        VerticalLayout contentLayout;
        HorizontalLayout buttCreateLayout;
        H2 title;

        myCreateAvatarDialog=new Dialog();
        myCreateAvatarDialog.open();

        myCreateAvatarDialog.setCloseOnEsc(false);
        myCreateAvatarDialog.setCloseOnOutsideClick(false);

        contentLayout=new VerticalLayout();
        buttCreateLayout=new HorizontalLayout();

        // Header
        title = new H2("Avatar erstellen");
        Header header =new Header(title);
        Text description= new Text("Konfiguriere dir hier deinen Avatar, mit dem du den Dungeon bestreiten willst.");

        // Avatar Felder
        TextField avatarNameFiled = new TextField("Avatarname");

        //myConfiguratorService.setDungeon(myDungeon.getDungeonId());
        List<Role> avatarRoleList = myDungeon.getRoles();
        ComboBox<Role> avatarRoleField = new ComboBox("Rolle");
        avatarRoleField.setItems(avatarRoleList);

        List<Race> avatarRaceList=myDungeon.getRaces();
        ComboBox<Race> avatarRaceField = new ComboBox("Rasse");
        avatarRaceField.setItems(avatarRaceList);

        Button cancelButt =new Button("Abbrechen", e->myCreateAvatarDialog.close());
        cancelButt.getStyle().set("color", "red");
        Button createAvatarButt=new Button("Speichern");
        createAvatarButt.addClickListener(e->{
           //Neuen Avatar speichern
            Avatar myAvatar =new Avatar();
            myAvatar.setUser(VaadinSession.getCurrent().getAttribute(User.class));
            myAvatar.setName(avatarNameFiled.getValue());
            myAvatar.setRole(avatarRoleField.getValue());
            myAvatar.setRace(avatarRaceField.getValue());
            myDungeon.addAvatar(myAvatar);
            myCreateAvatarDialog.close();
        });
        createAvatarButt.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        createAvatarButt.focus();
        createAvatarButt.addClickShortcut(Key.ENTER);

        contentLayout.add(header, description, avatarNameFiled, avatarRoleField, avatarRaceField);
        buttCreateLayout.add(cancelButt, createAvatarButt);
        myCreateAvatarDialog.add(contentLayout, buttCreateLayout);
    }

    void loadAvatarProgress(Avatar AAvatar) {
        this.myAvatar = AAvatar;
        this.currentRoom = myAvatar.getCurrentRoom();
        this.visitedRooms = myAvatar.getVisitedRooms();
        if (currentRoom == null) {
            currentRoom = myRoomRepo.findByRoomId(myDungeon.getStartRoomId());
        }
    }

    void createMap() {
        mapLayout.add(initMap());
        createInventory();
    }

    VerticalLayout initMap() {
        Tile[][] newTiles = mapServiceI.getMapGame(dungeonId, true);
        myTiles = new Image[newTiles.length][newTiles[0].length];
        VerticalLayout columns = new VerticalLayout();
        columns.setSpacing(false);
        for (int i = 0; i < newTiles.length; i++) {
            HorizontalLayout rows = new HorizontalLayout();
            rows.setSpacing(false);
            for (int j = 0; j < newTiles[0].length; j++) {
                myTiles[i][j] = new Image("map/" + newTiles[i][j].getPath() + ".png", "Room");
                myTiles[i][j].getStyle().set("opacity", "0.0");
                myTiles[i][j].addClassName("game-room");
                rows.add(myTiles[i][j]);
            }
            columns.add(rows);
        }
        updateMap();
        return columns;
    }

    void createInventory() {
        VerticalLayout inventoryLayout = new VerticalLayout();
        Text inventoryTitle = new Text("Inventar");
        inventoryList = new ArrayList<>();
        Grid<Item> inventoryGrid = new Grid<>();
        inventoryGrid.setItems(inventoryList);
        inventoryGrid.setVerticalScrollingEnabled(true);
        inventoryGrid.addColumn(Item::getItemName).setHeader("Item");
        inventoryGrid.addColumn(Item::getType).setHeader("Typ");
        inventoryGrid.addColumn(Item::getSize).setHeader("Größe");
        inventoryGrid.getStyle().set("background", "grey");
        inventoryGrid.setSizeFull();
        inventoryLayout.add(inventoryTitle, inventoryGrid);

        VerticalLayout armorLayout = new VerticalLayout();
        Text armorTitle = new Text("Rüstung");
        armorList = new ArrayList<>();
        Grid<Item> armorGrid = new Grid<>();
        armorGrid.setItems(armorList);
        armorGrid.setVerticalScrollingEnabled(true);
        armorGrid.addColumn(Item::getItemName).setHeader("Item");
        armorGrid.addColumn(Item::getType).setHeader("Typ");
        armorGrid.addColumn(Item::getSize).setHeader("Größe");
        armorGrid.getStyle().set("background", "grey");
        armorGrid.setSizeFull();
        armorLayout.add(armorTitle, armorGrid);

        gridLayout.add(inventoryLayout, armorLayout);
        gridLayout.setSizeFull();
        gridLayoutVert.add(gridLayout);
    }

    void changeRoom(Long ARoomId) {
        if (ARoomId == null) {
            Notification.show("Room null");
            return;
        }
        currentRoom = myRoomRepo.findByRoomId(ARoomId);
        myAvatar.setCurrentRoom(currentRoom);
        //Avatar Fortschritt speichern
        myAvatar.addVisitedRoom(currentRoom);
        visitedRooms = myAvatar.getVisitedRooms();//Liste updaten
        //Kartenanzeige aktualisieren
        updateMap();
    }

    void updateMap() {
        //alle Stellen des Dungeons schwarz färben, die noch nicht durch den Avatar erforscht wurden
        for (int i = 0; i < myTiles.length; i++) {
            for (int j = 0; j < myTiles[0].length; j++) {
                //noch nicht besucht -> Raum ausblenden
                myTiles[i][j].getStyle().set("opacity", "0.0");
            }
        }

        for (RoomI room : visitedRooms) {
            //schon besucht
            myTiles[room.getXCoordinate()][room.getYCoordinate()].getStyle().set("opacity", "1.0");
        }

        //alle anderen Räume roten Rand abwählen

        for (Image[] tL : myTiles) {
            for (Image t : tL) {
                t.getStyle().set("width", "100px");
                t.getStyle().set("height", "100px");
                t.getStyle().set("border-style", "none");
                t.getStyle().set("border-color", "inherit");
            }
        }

        //aktuellen Raum roten Rand anwählen
        int x = currentRoom.getXCoordinate();
        int y = currentRoom.getYCoordinate();
        //zum aktuellen Raum mithilfe von JavaScript scrollen
        UI.getCurrent().getPage().executeJs("arguments[0].scrollIntoView({\n" +
                "            behavior: 'auto',\n" +
                "            block: 'center',\n" +
                "            inline: 'center'\n" +
                "        });", myTiles[x][y]);
        myTiles[x][y].getStyle().set("width", "99px");
        myTiles[x][y].getStyle().set("height", "99px");
        myTiles[x][y].getStyle().set("border-style", "solid");
        myTiles[x][y].getStyle().set("border-color", "red");
    }
}