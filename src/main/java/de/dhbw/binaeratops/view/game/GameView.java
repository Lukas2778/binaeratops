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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.api.RoomI;
import de.dhbw.binaeratops.model.chat.ChatMessage;
import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.model.enums.Gender;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
import de.dhbw.binaeratops.model.map.Tile;
import de.dhbw.binaeratops.model.repository.DungeonRepositoryI;
import de.dhbw.binaeratops.model.repository.RoomRepositoryI;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import de.dhbw.binaeratops.service.api.configuration.DungeonServiceI;
import de.dhbw.binaeratops.service.api.game.GameServiceI;
import de.dhbw.binaeratops.service.api.map.MapServiceI;
import de.dhbw.binaeratops.service.api.parser.ParserServiceI;
import de.dhbw.binaeratops.service.exceptions.parser.*;
import de.dhbw.binaeratops.service.impl.parser.UserMessage;
import de.dhbw.binaeratops.view.TranslationProvider;
import de.dhbw.binaeratops.view.chat.ChatView;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Oberfläche des Tabs 'Über uns'
 */
//@Route(value = "gameView")
@CssImport("./views/game/game-view.css")
@PageTitle("Dungeon - Spiel")
public class GameView extends VerticalLayout implements HasUrlParameter<Long>, BeforeLeaveObserver {
    BeforeLeaveEvent.ContinueNavigationAction action;

    ParserServiceI myParserService;
    MapServiceI mapServiceI;
    RoomRepositoryI myRoomRepo;
    DungeonRepositoryI myDungeonRepo;
    GameServiceI myGameService;

    User currentUser;
    Long dungeonId;
    Dungeon myDungeon;
    Image[][] myTiles;
    Dialog myAvatarDialog;
    Dialog myCreateAvatarDialog;
    Dialog myConfirmLeavingDialog;

    private final ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());
    private TranslationProvider transProv = new TranslationProvider();
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
    List<Avatar> avatarList;
    Grid<Avatar> avatarGrid;

    private List<ItemInstance> inventoryList;
    Grid<ItemInstance> inventoryGrid;
    private List<ItemInstance> armorList;
    Grid<ItemInstance> armorGrid;

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
                    @Autowired DungeonRepositoryI ADungeonRepo, @Autowired GameServiceI AGameService) {
        myParserService = AParserService;
        mapServiceI = AMapService;
        myRoomRepo = ARoomRepo;
        myDungeonRepo = ADungeonRepo;
        myGameService=AGameService;

        currentUser=VaadinSession.getCurrent().getAttribute(User.class);

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
        textField.setWidthFull();

        confirmButt = new Button("Eingabe");
        confirmButt.addClickShortcut(Key.ENTER);
        confirmButt.addClickListener(e -> {
            //Parser wird mit Texteingabe aufgerufen
            try {
                UserMessage um = myParserService.parseCommand(textField.getValue(), dungeonId, myAvatar, currentUser);
                String message = transProv.getUserMessage(um, VaadinSession.getCurrent().getLocale());
                myDungeonChatView.messageList.add(new Paragraph(new Html(message)));
                switch (um.getKey()){
                    case "view.game.ctrl.cmd.move.north":
                        changeRoom(currentRoom.getNorthRoomId());
                        break;
                    case "view.game.ctrl.cmd.move.east":
                        changeRoom(currentRoom.getEastRoomId());
                        break;
                    case "view.game.ctrl.cmd.move.south":
                        changeRoom(currentRoom.getSouthRoomId());
                        break;
                    case "view.game.ctrl.cmd.move.west":
                        changeRoom(currentRoom.getWestRoomId());
                        break;
                    default:
                        break;
                }
            } catch (CmdScannerInsufficientPermissionException insufficientPermissions) {
                Notification.show(transProv.getUserMessage(insufficientPermissions.getUserMessage(), VaadinSession.getCurrent().getLocale()))
                        .setPosition(Notification.Position.BOTTOM_CENTER);
            } catch (CmdScannerInvalidItemTypeException invalidItemType) {
                Notification.show(transProv.getUserMessage(invalidItemType.getUserMessage(), VaadinSession.getCurrent().getLocale()))
                        .setPosition(Notification.Position.BOTTOM_CENTER);
            } catch (CmdScannerInvalidParameterException invalidParameter) {
                Notification.show(transProv.getUserMessage(invalidParameter.getUserMessage(), VaadinSession.getCurrent().getLocale()))
                        .setPosition(Notification.Position.BOTTOM_CENTER);
            } catch (CmdScannerSyntaxMissingException syntaxMissing) {
                Notification.show(transProv.getUserMessage(syntaxMissing.getUserMessage(), VaadinSession.getCurrent().getLocale()))
                        .setPosition(Notification.Position.BOTTOM_CENTER);
            } catch (CmdScannerSyntaxUnexpectedException syntaxUnexpected) {
                Notification.show(transProv.getUserMessage(syntaxUnexpected.getUserMessage(), VaadinSession.getCurrent().getLocale()))
                        .setPosition(Notification.Position.BOTTOM_CENTER);
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

        gameFirstLayout.add(binTitle, html, myDungeonChatView, insertInputLayout);
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

        myAvatarDialog= new Dialog();
        myAvatarDialog.open();

        myAvatarDialog.setCloseOnEsc(false);
        myAvatarDialog.setCloseOnOutsideClick(false);
        buttLayout = new HorizontalLayout();
        gridLayoutVert=new VerticalLayout();

        // Header
        title = new H2("Avatarauswahl");
        Header header = new Header(title);

        avatarGrid = new Grid<>();
        refreshAvatarGrid();
        avatarGrid.setVerticalScrollingEnabled(true);
        avatarGrid.addColumn(Avatar::getName).setHeader("Avatarname");
        avatarGrid.addColumn(avatar -> {
            if(avatar.getRace()!=null)
                return avatar.getRace().getRaceName();
            return null;
        }).setHeader("Rasse");
        avatarGrid.addColumn(avatar -> {
            if(avatar.getRole()!=null)
                return avatar.getRole().getRoleName();
            return null;
        }).setHeader("Rolle");
        avatarGrid.addColumn(avatar -> {
            if(avatar.getCurrentRoom()!=null)
                return avatar.getCurrentRoom().getRoomName();
            return null;
        }).setHeader("letzter Raum");
        //avatarGrid.setSizeFull();
        //expand(avatarGrid);
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
                textField.focus();
                loadAvatarProgress(myDungeon.getAvatarById(((Avatar) selectedAvatar.toArray()[0]).getAvatarId()));
                createMap();
                changeRoom(currentRoom.getRoomId());
            }
            else {
                Notification.show("Wähle zuerst einen Avatar aus!");
            }
        });
        enterDungeon.addClickShortcut(Key.ENTER);

        buttLayout.add(cancel, createAvatar, enterDungeon);
        buttLayout.setWidthFull();

        // Add it all together
        myAvatarDialog.add(header, gridLayoutVert, buttLayout);
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

        List<Gender> avatarGenderList = new ArrayList<>();
        avatarGenderList.add(Gender.MALE);
        avatarGenderList.add(Gender.FEMALE);
        avatarGenderList.add(Gender.DIVERSE);
        ComboBox<Gender> avatarGenderField = new ComboBox<>("Geschlecht");
        avatarGenderField.setItems(avatarGenderList);

        List<Role> avatarRoleList = myDungeon.getRoles();
        ComboBox<Role> avatarRoleField = new ComboBox("Rolle");
        avatarRoleField.setItems(avatarRoleList);
        avatarRoleField.setItemLabelGenerator(Role::getRoleName);

        List<Race> avatarRaceList=myDungeon.getRaces();
        ComboBox<Race> avatarRaceField = new ComboBox("Rasse");
        avatarRaceField.setItems(avatarRaceList);
        avatarRaceField.setItemLabelGenerator(Race::getRaceName);

        Button cancelButt =new Button("Abbrechen", e->myCreateAvatarDialog.close());
        cancelButt.getStyle().set("color", "red");
        Button createAvatarButt=new Button("Speichern");
        createAvatarButt.addClickListener(e->{
           //Neuen Avatar speichern
            myGameService.createNewAvatar(myDungeon,currentUser, myDungeon.getStartRoomId(),avatarNameFiled.getValue(),
                    avatarGenderField.getValue(),avatarRoleField.getValue(),avatarRaceField.getValue());
            refreshAvatarGrid();
            myCreateAvatarDialog.close();
        });
        createAvatarButt.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        createAvatarButt.focus();
        createAvatarButt.addClickShortcut(Key.ENTER);

        contentLayout.add(header, description, avatarNameFiled, avatarGenderField, avatarRoleField, avatarRaceField);
        buttCreateLayout.add(cancelButt, createAvatarButt);
        myCreateAvatarDialog.add(contentLayout, buttCreateLayout);
    }

    void refreshAvatarGrid(){
        avatarList = currentUser.getAvatars();
        avatarGrid.setItems(avatarList);
    }

    void loadAvatarProgress(Avatar AAvatar) {
        this.myAvatar = AAvatar;
        this.currentRoom = myAvatar.getCurrentRoom();
        this.visitedRooms = myAvatar.getVisitedRooms();
        if (currentRoom == null || myRoomRepo.findByRoomId(currentRoom.getRoomId())==null) {
            currentRoom = myRoomRepo.findByRoomId(myDungeon.getStartRoomId());
        }
        myGameService.addActivePlayer(myDungeon,currentUser);
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

        inventoryGrid = new Grid<>();
        inventoryGrid.setVerticalScrollingEnabled(true);
        inventoryGrid.addColumn(item -> item.getItem().getItemName()).setHeader("Item");
        inventoryGrid.addColumn(item -> item.getItem().getType().toString()).setHeader("Typ");
        inventoryGrid.addColumn(item -> item.getItem().getSize().toString()).setHeader("Größe");
        inventoryGrid.getStyle().set("background", "grey");
        inventoryGrid.setSizeFull();
        inventoryLayout.add(inventoryTitle, inventoryGrid);

        VerticalLayout armorLayout = new VerticalLayout();
        Text armorTitle = new Text("Rüstung");

        armorGrid = new Grid<>();
        armorGrid.setVerticalScrollingEnabled(true);
        armorGrid.addColumn(item -> item.getItem().getItemName()).setHeader("Item");
        armorGrid.addColumn(item -> {
            if (item.getItem().getType()!=null)
                return item.getItem().getType().toString();
            return null;
        }).setHeader("Typ");
        armorGrid.addColumn(item -> {
            if(item.getItem().getSize()!=null)
                return item.getItem().getSize().toString();
            return null;
        }).setHeader("Größe");
        armorGrid.getStyle().set("background", "grey");
        armorGrid.setSizeFull();
        armorLayout.add(armorTitle, armorGrid);

        gridLayout.add(inventoryLayout, armorLayout);
        gridLayout.setSizeFull();
        gridLayoutVert.add(gridLayout);
        refreshInventory();
    }

    void refreshInventory(){
        inventoryList = myAvatar.getInventory();
        inventoryGrid.setItems(inventoryList);

        armorList = myAvatar.getEquipment();
        armorGrid.setItems(armorList);
    }

    void changeRoom(Long ARoomId) {
        if (ARoomId == null) {
            Notification.show("Room null");
            return;
        }
        currentRoom = myRoomRepo.findByRoomId(ARoomId);
        //Avatar Fortschritt speichern
        visitedRooms = myGameService.saveAvatarProgress(myAvatar, currentRoom);//Liste updaten
        //Kartenanzeige aktualisieren
        updateMap();
        if(inventoryGrid!=null&&armorGrid!=null)
            refreshInventory();
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
            myTiles[room.getXcoordinate()][room.getYcoordinate()].getStyle().set("opacity", "1.0");
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
        int x = currentRoom.getXcoordinate();
        int y = currentRoom.getYcoordinate();
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

    @Override
    public void beforeLeave(BeforeLeaveEvent event) {
        if (this.hasChanges()) {
            action =
                    event.postpone();
            confirmLeaveDialog();
            Notification.show("Du willst jetzt schon gehen?");
        }
    }
    private boolean hasChanges() {
        if (myAvatar!=null)
            return true;
        return false;
    }

    void confirmLeaveDialog(){
        myConfirmLeavingDialog=new Dialog();
        myConfirmLeavingDialog.open();

        myConfirmLeavingDialog.setCloseOnEsc(false);
        myConfirmLeavingDialog.setCloseOnOutsideClick(false);

        // Header
        H4 title = new H4("Willst du das Spiel wirklich verlassen?");

        HorizontalLayout buttLayout=new HorizontalLayout();

        Button leaveButt =new Button("Dungeon verlassen");
        leaveButt.getStyle().set("color", "red");
        leaveButt.addClickListener(e->{
            myGameService.removeActivePlayer(myDungeon, currentUser);
            myConfirmLeavingDialog.close();
            action.proceed();
        });

        Button stayButt=new Button("Zurück");
        stayButt.addClickListener(e->myConfirmLeavingDialog.close());
        stayButt.focus();
        stayButt.addClickShortcut(Key.ENTER);
        buttLayout.add(leaveButt, stayButt);

        myConfirmLeavingDialog.add(title,buttLayout);
    }

}