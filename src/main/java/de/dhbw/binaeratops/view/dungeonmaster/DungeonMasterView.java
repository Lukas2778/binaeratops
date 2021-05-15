package de.dhbw.binaeratops.view.dungeonmaster;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.api.AvatarI;
import de.dhbw.binaeratops.model.chat.ChatMessage;
import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
import de.dhbw.binaeratops.model.map.Tile;
import de.dhbw.binaeratops.model.repository.DungeonRepositoryI;
import de.dhbw.binaeratops.service.api.configuration.DungeonServiceI;
import de.dhbw.binaeratops.service.api.map.MapServiceI;
import de.dhbw.binaeratops.service.api.parser.ParserServiceI;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerSyntaxMissingException;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerSyntaxUnexpectedException;
import de.dhbw.binaeratops.service.impl.game.GameService;
import de.dhbw.binaeratops.service.impl.parser.UserMessage;
import de.dhbw.binaeratops.view.map.MapView;
import de.dhbw.binaeratops.view.chat.ChatView;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;

/**
 * @author Mathias Rall
 * Date: 07.05.2021
 * Time: 16:20
 */

@CssImport("./views/game/map-master.css")
@PageTitle("Title")
@Push
public class DungeonMasterView extends Div implements HasUrlParameter<Long>, RouterLayout, PageConfigurator, BeforeLeaveObserver {
    Image[][] tiles;
    MapView mapView;

    private final SplitLayout splitChatWithRest = new SplitLayout();
    private final SplitLayout splitMapAndRoomWithActions = new SplitLayout();
    private final SplitLayout splitMapWithRoom = new SplitLayout();

    private final Grid<Avatar> grid = new Grid<>(Avatar.class);

    private GameService gameService;

    private MapServiceI mapServiceI;

    private DungeonServiceI dungeonServiceI;
    private DungeonRepositoryI dungeonRepositoryI;
    private Flux<ChatMessage> messages;
    private ParserServiceI myParserService;
    private final ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());

    Dungeon dungeon;
    Long dungeonId;
    H2 binTitle;
    String aboutText;
    Html html;
    ChatView myDungeonChatView;

    HorizontalLayout insertInputLayout;
    TextField textField;
    Button confirmButt;
    VerticalLayout gameLayout = new VerticalLayout();

    TextField roomNameTextField = new TextField("Name: ");
    TextArea roomDescriptionTextArea = new TextArea("Beschreibung: ");
    Grid<Item> itemInRoomGrid = new Grid<>(Item.class);
    Grid<NPC> npcInRoomGrid = new Grid<>(NPC.class);

    public DungeonMasterView(@Autowired MapServiceI mapServiceI, @Autowired GameService gameService, @Autowired DungeonServiceI dungeonServiceI,
                             @Autowired DungeonRepositoryI dungeonRepositoryI, Flux<ChatMessage> messages, @Autowired ParserServiceI AParserService) {
        this.mapServiceI = mapServiceI;
        this.gameService = gameService;
        this.dungeonServiceI = dungeonServiceI;
        this.dungeonRepositoryI = dungeonRepositoryI;
        this.messages = messages;
        this.myParserService = AParserService;
        setId("SomeView");
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        gameService.initialize(dungeon, attachEvent.getUI(), this);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long ALong) {
        dungeon = dungeonRepositoryI.findByDungeonId(ALong);
        dungeonId = ALong;
        createLayoutBasic(ALong);
    }

    void createLayoutBasic(Long ALong) {
        mapView = new MapView();
        setSizeFull();
        game();

        splitChatWithRest.setSizeFull();
        splitChatWithRest.addToPrimary(gameLayout);
        splitChatWithRest.addToSecondary(splitMapAndRoomWithActions);

        splitMapAndRoomWithActions.setSizeFull();
        splitMapAndRoomWithActions.setOrientation(SplitLayout.Orientation.VERTICAL);
        splitMapAndRoomWithActions.addToPrimary(splitMapWithRoom);
        createLayoutAction();

        splitMapWithRoom.setSizeFull();
        splitMapWithRoom.addToPrimary(mapView.initMap(mapServiceI, ALong, tiles));

        add(splitChatWithRest);
    }

    private void game() {
        aboutText = "<div>Du hast auf einen aktiven Dungeon geklickt und kannst hier Teile des Chats und des Parsers" +
                " testen.<br>Schau dir zuerst die 'Help' an, indem du /help eingibst.</div>";
        html = new Html(aboutText);

        myDungeonChatView = new ChatView(messages);

        textField = new TextField();
        textField.focus();
        confirmButt = new Button("Eingabe");
        confirmButt.addClickShortcut(Key.ENTER);
        AvatarI myAvatar = new Avatar();
        confirmButt.addClickListener(e -> {
            //Parser wird mit Texteingabe aufgerufen
            try {
                UserMessage um = myParserService.parseCommand(textField.getValue(), dungeon.getDungeonId(), myAvatar, VaadinSession.getCurrent().getAttribute(
                        User.class));
                if (um.getKey() != null) {
                    switch (um.getKey()) {
                        case "view.game.ingame.cmd.notify.all":
                            myDungeonChatView.messageList.add(new Paragraph(MessageFormat.format(res.getString(um.getKey()), um.getParams().get(0))));
                            break;
                        case "view.game.cmd.help":
                            myDungeonChatView.messageList.add(new Paragraph(new Html(MessageFormat.format(res.getString(um.getKey()), um.getParams().get(0)))));
                            break;
                        case "view.game.cmd.help.all":
                            myDungeonChatView.messageList.add(new Paragraph(new Html(MessageFormat.format(res.getString(um.getKey()), um.getParams().get(0)))));
                            break;
                        case "view.game.cmd.help.cmds":
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
            textField.clear();
        });
        insertInputLayout = new HorizontalLayout();
        insertInputLayout.add(textField, confirmButt);

        gameLayout.setSizeFull();
        gameLayout.add(html, myDungeonChatView, insertInputLayout);
        gameLayout.expand(myDungeonChatView);
    }


    private void createLayoutAction() {
        createAvatarGrid();
        createCurrentRoom();

        HorizontalLayout hl = new HorizontalLayout();
        hl.setSizeFull();
        VerticalLayout vl = new VerticalLayout();
        vl.setSizeFull();
        Button actionsButton = new Button("Aktionen");
        Button npcsButton = new Button("NPCs");
        Button authorisationButton = new Button("Spielberechtigungen");
        Button pauseButton = new Button("Dungeon pausieren");
        Button leaveButton = new Button("Dungeon verlassen");
        leaveButton.addClickListener(e -> {
            dungeonServiceI.deactivateDungeon(dungeonId);
            UI.getCurrent().navigate("myDungeons");

        });
        addClickListeners(actionsButton, npcsButton, authorisationButton, pauseButton, leaveButton);
        HorizontalLayout leaveAndPause=new HorizontalLayout();
        leaveAndPause.add(pauseButton,leaveButton);
        //leaveAndPause.
        vl.add(actionsButton,  authorisationButton, leaveAndPause);
        //vl.setAlignItems(leaveAndPause, FlexComponent.Alignment.END);
        hl.add(grid, vl);
        splitMapAndRoomWithActions.addToSecondary(hl);
    }

    private void addClickListeners(Button actionsButton, Button npcsButton, Button authorisationButton, Button pauseButton, Button leaveButton) {
        actionsButton.addClickListener(e -> {
            Dialog actionDialog = new Dialog(new Text("Aktionen zur Auswahl"));
            actionDialog.open();
        });
        authorisationButton.addClickListener(e -> {
            Dialog authorisationDialog = new Dialog(new Text("Neue Anfragen und aktuelle Spieler"));
            authorisationDialog.open();
        });
        pauseButton.addClickListener(e -> {
            Notification.show("Not Implemented");
        });
        leaveButton.addClickListener(e -> {
            Dialog leaveDialog = new Dialog(new Text("Willst du den Dungeon wirklich verlassen? Willst du einen anderen DM auswählen?"));
            leaveDialog.open();
        });
    }

    private void createAvatarGrid() {
        grid.setSizeFull();
        grid.removeAllColumns();

        grid.setItems(dungeonServiceI.getCurrentAvatars(dungeon.getDungeonId()));

        grid.addColumn(Avatar::getName)
                .setComparator((avater1, avatar2) -> avater1.getName()
                        .compareToIgnoreCase(avatar2.getName()))
                .setHeader("Name");
        grid.addColumn(avatar -> dungeonServiceI.getRoomOfAvatar(avatar).getRoomName())
                .setComparator(Comparator.comparing(avatar -> dungeonServiceI.getRoomOfAvatar(avatar).getRoomName()))
                .setHeader("Raum");
        grid.addComponentColumn(avatar -> {
            Button requestsButton = new Button("Anfragen");

            Dialog requestDialog = new Dialog(new Label("Informationen zur Anfrage von: " + avatar.getName()));
            requestsButton.addClickListener(e -> {
                requestDialog.open();
            });
            return requestsButton;
        }).setHeader("Anfragen");
        grid.addComponentColumn(avatar -> {
            Button whisperButton = new Button("Whisper");
            whisperButton.addClickListener(e -> {
                Notification.show("Not Implemented");
            });
            return whisperButton;
        }).setHeader("Anflüstern");
        grid.addComponentColumn(avatar -> {
            Button infoButton = new Button("Infos");
            VerticalLayout lay = new VerticalLayout();
            //lay.setSizeFull();
            H4 headline = new H4("Informationen zu: " + avatar.getName());
            Label user = new Label("User: " + avatar.getUser().getName());
            Label room = new Label("Raum: " + dungeonServiceI.getRoomOfAvatar(avatar).getRoomName());
            Label equipment = new Label("Ausgerüstet ist: " + Arrays.toString(avatar.getEquipment().stream().map(itemInstance -> itemInstance.getItem().getItemName()).toArray(String[]::new)));
            Label inventoryLabel = new Label("Inventar:");
            Grid<Item> inventory = new Grid<>(Item.class);
            inventory.removeAllColumns();
            inventory.addColumn(Item::getItemName).setHeader("Name").setAutoWidth(true);
            inventory.addColumn(Item::getDescription).setHeader("Beschreibung").setAutoWidth(true);
            inventory.addColumn(Item::getSize).setHeader("Größe").setAutoWidth(true);
            inventory.addColumn(Item::getType).setHeader("Typ").setAutoWidth(true);
            inventory.setItems(avatar.getInventory().stream().map(ItemInstance::getItem).toArray(Item[]::new));

            Dialog infoDialog = new Dialog();
            infoDialog.setMinHeight("700px");
            infoDialog.setMinWidth("1000px");
            lay.add(headline, user, room, equipment);
            infoDialog.add(lay, inventoryLabel, inventory);
            infoButton.addClickListener(e -> {
                infoDialog.open();
            });
            return infoButton;
        }).setHeader("Informationen");
    }

    VerticalLayout initMap(Long ADungeonId) {
        int minX = mapServiceI.getMinXY(ADungeonId).getKey();
        int minY = mapServiceI.getMinXY(ADungeonId).getValue();

        Tile[][] newTiles = mapServiceI.getMapGame(ADungeonId);
        tiles = new Image[newTiles.length][newTiles[0].length];
        VerticalLayout columns = new VerticalLayout();
        columns.setSpacing(false);
        for (int i = 0; i < newTiles.length; i++) {
            HorizontalLayout rows = new HorizontalLayout();
            rows.setSpacing(false);
            for (int j = 0; j < newTiles[0].length; j++) {
                int finalX = minX + i;
                int finalY = minY + j;
                tiles[i][j] = new Image("map/" + newTiles[i][j].getPath() + ".png", "Room");
                tiles[i][j].addClassName("room");
                tiles[i][j].addClickListener(e->
                {
                    Room room = dungeonServiceI.getRoomByPosition(dungeon, finalX,finalY);
                    if(room != null) {
                        fillCurrentRoom(room);
                        for (int k = 0; k < newTiles.length; k++) {
                            for (int l = 0; l < newTiles[0].length; l++) {
                                tiles[k][l].getStyle().set("border-color", "inherit");
                                tiles[k][l].getStyle().set("border-style", "none");
                                tiles[k][l].getStyle().set("width", "100px");
                                tiles[k][l].getStyle().set("height", "100px");
                            }
                        }
                        tiles[finalX - minX][finalY - minY].getStyle().set("width", "95px");
                        tiles[finalX - minX][finalY - minY].getStyle().set("height", "95px");
                        tiles[finalX - minX][finalY - minY].getStyle().set("border-style", "solid");
                        tiles[finalX - minX][finalY - minY].getStyle().set("border-color", "red");
                    }
                });
                rows.add(tiles[i][j]);
            }
            columns.add(rows);
        }
        return columns;
    }

    private void createCurrentRoom() {
        VerticalLayout vl = new VerticalLayout();
        HorizontalLayout hl = new HorizontalLayout();

        roomNameTextField.setEnabled(false);
        roomDescriptionTextArea.setEnabled(false);

        itemInRoomGrid.removeAllColumns();
        itemInRoomGrid.addColumn(Item::getItemName).setHeader("Name");

        npcInRoomGrid.removeAllColumns();
        npcInRoomGrid.addColumn(NPC::getNpcName).setHeader("Name");

        hl.add(itemInRoomGrid, npcInRoomGrid);
        vl.add(roomNameTextField, roomDescriptionTextArea, hl);
        splitMapWithRoom.addToSecondary(vl);
    }

    private void fillCurrentRoom(Room ARoom) {
        roomNameTextField.setValue(ARoom.getRoomName());
        roomDescriptionTextArea.setValue(ARoom.getDescription());
        itemInRoomGrid.setItems(ARoom.getItems().stream().map(ItemInstance::getItem).toArray(Item[]::new));
        //npcInRoomGrid.setItems(ARoom.getNpcs().stream().map(NpcIns));
    }

    @Override
    public void configurePage(InitialPageSettings settings) {
        String script = "window.onbeforeunload = function (e) { var e = e || window.event; document.getElementById(\"SomeView\").$server.browserIsLeaving(); return; };";
        settings.addInlineWithContents(InitialPageSettings.Position.PREPEND, script, InitialPageSettings.WrapMode.JAVASCRIPT);
    }

    @ClientCallable
    public void browserIsLeaving() {
        System.out.println("Called browserIsLeavingDM");
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent e) {
        browserIsLeaving();
    }

}
