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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.api.AvatarI;
import de.dhbw.binaeratops.model.chat.ChatMessage;
import de.dhbw.binaeratops.model.entitys.Avatar;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
import de.dhbw.binaeratops.model.map.Tile;
import de.dhbw.binaeratops.model.repository.DungeonRepositoryI;
import de.dhbw.binaeratops.service.api.configuration.DungeonServiceI;
import de.dhbw.binaeratops.service.api.map.MapServiceI;
import de.dhbw.binaeratops.service.api.parser.ParserServiceI;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;
import de.dhbw.binaeratops.service.impl.game.GameService;
import de.dhbw.binaeratops.service.impl.parser.ParserService;
import de.dhbw.binaeratops.service.impl.parser.UserMessage;
import de.dhbw.binaeratops.view.chat.ChatView;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

import java.text.MessageFormat;
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
public class DungeonMasterView extends Div implements HasUrlParameter<Long>, RouterLayout {
    private final int WIDTH = 8;
    Image[][] tiles;

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

    public DungeonMasterView(@Autowired MapServiceI mapServiceI, @Autowired GameService gameService, @Autowired DungeonServiceI dungeonServiceI,
                             @Autowired DungeonRepositoryI dungeonRepositoryI, Flux<ChatMessage> messages, @Autowired ParserService AParserService) {
        this.mapServiceI = mapServiceI;
        this.gameService = gameService;
        this.dungeonServiceI = dungeonServiceI;
        this.dungeonRepositoryI = dungeonRepositoryI;
        this.messages = messages;
        this. myParserService = AParserService;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        gameService.initialize(dungeon, attachEvent.getUI(), this);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long ALong) {
        dungeon = dungeonRepositoryI.findByDungeonId(ALong);
        dungeonId=ALong;
        createLayoutBasic(ALong);
    }

    void createLayoutBasic(Long ALong) {
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
        splitMapWithRoom.addToPrimary(initMap(ALong));
        splitMapWithRoom.addToSecondary(new Label("AKTUELLER RAUM"));

        add(splitChatWithRest);
    }

    private void game(){
        aboutText= "<div>Du hast auf einen aktiven Dungeon geklickt und kannst hier Teile des Chats und des Parsers" +
                " testen.<br>Schau dir zuerst die 'Help' an, indem du /help eingibst.</div>";
        html=new Html(aboutText);

        myDungeonChatView =new ChatView(messages);

        textField=new TextField();
        textField.focus();
        confirmButt=new Button("Eingabe");
        confirmButt.addClickShortcut(Key.ENTER);
        AvatarI myAvatar=new Avatar();
        confirmButt.addClickListener(e->{
            //Parser wird mit Texteingabe aufgerufen
            try {
                UserMessage um=myParserService.parseCommand(textField.getValue(), dungeon.getDungeonId(), myAvatar, VaadinSession.getCurrent().getAttribute(
                        User.class));
                if(um.getKey()!=null) {
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
            } catch ( CmdScannerException cmdScannerException) {
                cmdScannerException.printStackTrace();
            } catch ( InvalidImplementationException invalidImplementationException) {
                invalidImplementationException.printStackTrace();
            }
            textField.clear();
        });
        insertInputLayout=new HorizontalLayout();
        insertInputLayout.add(textField, confirmButt);

        gameLayout.setSizeFull();
        gameLayout.add(html, myDungeonChatView, insertInputLayout);
        gameLayout.expand(myDungeonChatView);
    }


    private void createLayoutAction() {
        createGrid();

        HorizontalLayout hl = new HorizontalLayout();
        hl.setSizeFull();
        VerticalLayout vl = new VerticalLayout();
        vl.setSizeFull();
        Button actionsButton = new Button("Aktionen");
        Button npcsButton = new Button("NPCs");
        Button authorisationButton = new Button("Spielberechtigungen");
        Button pauseButton = new Button("Dungeon pausieren");
        Button leaveButton = new Button("Dungeon verlassen");
        leaveButton.addClickListener(e->{
            dungeonServiceI.deactivateDungeon(dungeonId);
            UI.getCurrent().navigate("myDungeons");

        });
        addClickListeners(actionsButton, npcsButton, authorisationButton, pauseButton, leaveButton);
        vl.add(actionsButton, npcsButton, authorisationButton, pauseButton, leaveButton);
        hl.add(grid, vl);
        splitMapAndRoomWithActions.addToSecondary(hl);
    }

    private void addClickListeners(Button actionsButton, Button npcsButton, Button authorisationButton, Button pauseButton, Button leaveButton) {
        actionsButton.addClickListener(e -> {
            Dialog actionDialog = new Dialog(new Text("Aktionen zur Auswahl"));
            actionDialog.open();
        });
        npcsButton.addClickListener(e -> {
            Dialog npcDialog = new Dialog(new Text("NPCs zur Auswahl"));
            npcDialog.open();
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

    private void createGrid() {
        grid.setSizeFull();
        grid.removeAllColumns();
        Avatar testAvatar = new Avatar();
        testAvatar.setAvatarId(99999999L);
        testAvatar.setName("Awodur");
        grid.setItems(testAvatar);

        grid.addColumn(Avatar::getName)
                .setComparator((avater1, avatar2) -> avater1.getName()
                        .compareToIgnoreCase(avatar2.getName()))
                .setHeader("Name");
        grid.addColumn(Avatar::getRoomId)
                .setComparator(Comparator.comparing(Avatar::getRoomId))
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
            Dialog infoDialog = new Dialog(new Label("Informationen zum Avatar: " + avatar.getName()));
            infoButton.addClickListener(e -> {
                infoDialog.open();
            });
            return infoButton;
        }).setHeader("Informationen");
    }

    VerticalLayout initMap(Long ADungeonId) {
        Tile[][] newTiles = mapServiceI.getMapGame(ADungeonId);
        tiles = new Image[newTiles.length][newTiles[0].length];
        VerticalLayout columns = new VerticalLayout();
        columns.setSpacing(false);
        for (int i = 0; i < newTiles.length; i++) {
            HorizontalLayout rows = new HorizontalLayout();
            rows.setSpacing(false);
            for (int j = 0; j < newTiles[0].length; j++) {
                tiles[i][j] = new Image("map/" + newTiles[i][j].getPath() + ".png", "Room");
                tiles[i][j].addClassName("room");
                rows.add(tiles[i][j]);
            }
            columns.add(rows);
        }
        return columns;
    }


}
