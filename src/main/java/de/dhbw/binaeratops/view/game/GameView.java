package de.dhbw.binaeratops.view.game;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
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

    Long dungeonId;
    Dungeon myDungeon;
    Image[][] myTiles;

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

    ChatView myDungeonChatView;
    TextField textField;
    Button confirmButt;

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
                    @Autowired DungeonRepositoryI ADungeonRepo) {
        myParserService = AParserService;
        mapServiceI = AMapService;
        myRoomRepo = ARoomRepo;//@TODO remove
        myDungeonRepo = ADungeonRepo;

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

        gameFirstLayout.add(myDungeonChatView, insertInputLayout);

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

        add(binTitle, html, northButt, eastButt, southButt, westButt, gameLayout);
        expand(myDungeonChatView);
        setSizeFull();
    }

    @Override
    public void setParameter(BeforeEvent ABeforeEvent, Long ALong) {
        dungeonId = ALong;
        myDungeon = myDungeonRepo.findByDungeonId(dungeonId);
        Avatar test = new Avatar();//@TODO remove
        loadAvatarProgress(test);
        createMap();
        changeRoom(currentRoom.getRoomId());
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
        VerticalLayout mapLayout = new VerticalLayout();
        mapLayout.add(initMap());
        gameSecondLayout.add(mapLayout);
        createInventory();
        //changeRoom(myRoomRepo.findByRoomId(602L));//@TODO remove
    }

    VerticalLayout initMap() {
        Tile[][] newTiles = mapServiceI.getMapGame(dungeonId,true);
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
        HorizontalLayout gridLayout = new HorizontalLayout();

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

        Button leftDungeonButt = new Button("Dungeon verlassen");
        leftDungeonButt.getStyle().set("color", "red");

        gameSecondLayout.add(gridLayout, leftDungeonButt);
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
        visitedRooms=myAvatar.getVisitedRooms();//Liste updaten
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
        myTiles[x][y].getStyle().set("width", "99px");
        myTiles[x][y].getStyle().set("height", "99px");
        myTiles[x][y].getStyle().set("border-style", "solid");
        myTiles[x][y].getStyle().set("border-color", "red");
    }

}