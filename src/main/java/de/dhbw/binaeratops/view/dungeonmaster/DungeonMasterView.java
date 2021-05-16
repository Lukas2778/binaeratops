package de.dhbw.binaeratops.view.dungeonmaster;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.KickUser;
import de.dhbw.binaeratops.model.UserAction;
import de.dhbw.binaeratops.model.api.AvatarI;
import de.dhbw.binaeratops.model.chat.ChatMessage;
import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
import de.dhbw.binaeratops.model.map.Tile;
import de.dhbw.binaeratops.service.api.configuration.DungeonServiceI;
import de.dhbw.binaeratops.service.api.map.MapServiceI;
import de.dhbw.binaeratops.service.api.parser.ParserServiceI;
import de.dhbw.binaeratops.service.exceptions.parser.*;
import de.dhbw.binaeratops.service.impl.game.GameService;
import de.dhbw.binaeratops.service.impl.parser.UserMessage;
import de.dhbw.binaeratops.view.TranslationProvider;
import de.dhbw.binaeratops.view.chat.ChatView;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import java.util.*;

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

    HashMap<Avatar, Button> notificationButtons = new HashMap<>();
    HashMap<Avatar, UserAction> actionMap = new HashMap<>();

    private final SplitLayout splitChatWithRest = new SplitLayout();
    private final SplitLayout splitMapAndRoomWithActions = new SplitLayout();
    private final SplitLayout splitMapWithRoom = new SplitLayout();

    private final Grid<Avatar> userGrid = new Grid<>(Avatar.class);

    private final GameService gameService;

    private final MapServiceI mapServiceI;

    private final DungeonServiceI dungeonServiceI;
    private final Flux<ChatMessage> messages;
    private final ParserServiceI myParserService;
    private final ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());
    private TranslationProvider transProv = new TranslationProvider();
    private final UnicastProcessor<UserAction> userActionsPublisher;
    private final Flux<UserAction> userAction;
    private final UnicastProcessor<ChatMessage> messagesPublisher;
    private final UnicastProcessor<KickUser> kickUsersPublisher;

    private Timer timer=new Timer();
    Dungeon dungeon;
    Long dungeonId;
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

    Room currentRoom;

    boolean sureToLeave = false;
    boolean loaded = true;

    public DungeonMasterView(@Autowired MapServiceI mapServiceI, @Autowired GameService gameService, @Autowired DungeonServiceI dungeonServiceI,
                             Flux<ChatMessage> messages, @Autowired ParserServiceI AParserService,
                             UnicastProcessor<UserAction> userActionsPublisher, Flux<UserAction> userAction, UnicastProcessor<ChatMessage> AMessagePublisher,
                             UnicastProcessor<KickUser> AKickUsersPublisher) {
        this.mapServiceI = mapServiceI;
        this.gameService = gameService;
        this.dungeonServiceI = dungeonServiceI;
        this.messages = messages;
        this.myParserService = AParserService;
        this.userActionsPublisher = userActionsPublisher;
        this.userAction = userAction;
        this.messagesPublisher = AMessagePublisher;
        this.kickUsersPublisher = AKickUsersPublisher;
        setId("SomeView");

        userActionsIncoming();
        Thread thread = new Thread();
        thread.start();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                refreshView();
            }
        },0,1000);
    }

    private void refreshView () {
        getUI().ifPresent(ui -> ui.access(() -> {
            userGrid.setItems(dungeonServiceI.getCurrentAvatars(dungeon.getDungeonId()));
            if(currentRoom != null ) {
                Room room = dungeonServiceI.getRoomById(currentRoom.getRoomId());
                fillCurrentRoom(room);
            }
        }));
    }

    private void userActionsIncoming() {
        userAction.subscribe(action -> getUI().ifPresent(ui -> ui.access(() -> {
            if (action.getDungeon().getDungeonMasterId().equals(VaadinSession.getCurrent().getAttribute(User.class).getUserId())) {
                if (action.getActionType().equals("UPDATE")) {
                    //TODO update der view
                    return;
                }
                Notification.show("Message:" + action.getUserActionMessage() + " Avatar: " + action.getUser(), 5000, Notification.Position.TOP_END);
                notificationButtons.get(action.getUser()).getStyle().set("background", "red");
                actionMap.put(action.getUser(), action);
            }
        })));
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        gameService.initialize(dungeon, attachEvent.getUI(), this);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long ALong) {
        dungeon = dungeonServiceI.getDungeon(ALong);
        dungeonId = ALong;
        createLayoutBasic();
    }

    void createLayoutBasic() {
        setSizeFull();
        game();

        splitChatWithRest.setSizeFull();
        splitChatWithRest.addToPrimary(gameLayout);
        splitChatWithRest.setPrimaryStyle("width", "45%");
        splitChatWithRest.addToSecondary(splitMapAndRoomWithActions);

        splitMapAndRoomWithActions.setSizeFull();
        splitMapAndRoomWithActions.setOrientation(SplitLayout.Orientation.VERTICAL);
        splitMapAndRoomWithActions.addToPrimary(splitMapWithRoom);
        splitMapAndRoomWithActions.setSecondaryStyle("height", "700px");
        createLayoutAction();

        splitMapWithRoom.setSizeFull();
        splitMapWithRoom.setOrientation(SplitLayout.Orientation.HORIZONTAL);
        splitMapWithRoom.addToPrimary(initMap(dungeon.getDungeonId()));

        add(splitChatWithRest);
    }

    private void game() {
        aboutText = "<div>Du hast auf einen aktiven Dungeon geklickt und kannst hier Teile des Chats und des Parsers" +
                " testen.<br>Schau dir zuerst die 'Help' an, indem du /help eingibst.</div>";
        html = new Html(aboutText);

        myDungeonChatView = new ChatView(messages);

        textField = new TextField();
        textField.setWidthFull();
        textField.focus();

        confirmButt = new Button("Eingabe");
        confirmButt.setWidth("120px");
        confirmButt.addClickShortcut(Key.ENTER);
        AvatarI myAvatar = new Avatar();
        myAvatar.setUser(VaadinSession.getCurrent().getAttribute(User.class)); // Sonst Fehler in Parser
        confirmButt.addClickListener(e -> {
            //Parser wird mit Texteingabe aufgerufen
            try {
                UserMessage um = myParserService.parseCommand(textField.getValue(), dungeon.getDungeonId(), myAvatar, VaadinSession.getCurrent().getAttribute(
                        User.class));
                String message = transProv.getUserMessage(um, VaadinSession.getCurrent().getLocale());
                myDungeonChatView.messageList.add(new Paragraph(new Html(message)));
            } catch (CmdScannerRecipientOfflineException recipientOffline) {
                Notification.show(transProv.getUserMessage(recipientOffline.getUserMessage(), VaadinSession.getCurrent().getLocale()))
                        .setPosition(Notification.Position.BOTTOM_CENTER);
            } catch (CmdScannerInvalidRecipientException invalidRecipient) {
                Notification.show(transProv.getUserMessage(invalidRecipient.getUserMessage(), VaadinSession.getCurrent().getLocale()))
                        .setPosition(Notification.Position.BOTTOM_CENTER);
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
            textField.clear();
        });
        insertInputLayout = new HorizontalLayout();
        insertInputLayout.add(textField, confirmButt);
        insertInputLayout.setWidthFull();

        gameLayout.add(html, myDungeonChatView, insertInputLayout);
        gameLayout.setSizeFull();
        gameLayout.expand(myDungeonChatView);
    }


    private void createLayoutAction() {
        createAvatarGrid();
        createCurrentRoom();

        HorizontalLayout userGridAndButtLayoutH = new HorizontalLayout();
        VerticalLayout buttLayoutV = new VerticalLayout();
        VerticalLayout userGridLayoutV = new VerticalLayout();
        buttLayoutV.getStyle().set("width", "200px");
        buttLayoutV.setClassName("button-layout");

        userGridAndButtLayoutH.setSizeFull();
        //buttLayoutV.setSizeFull();
        Button actionsButton = new Button("Aktionen");
        Button npcsButton = new Button("NPCs");
        Button authorisationButton = new Button("Spielberechtigungen");
        Button pauseButton = new Button("Dungeon pausieren");
        Button leaveButton = new Button("Dungeon verlassen");
        leaveButton.getStyle().set("color", "red");
        addClickListeners(actionsButton, npcsButton, authorisationButton, pauseButton, leaveButton);
        VerticalLayout leaveAndPause = new VerticalLayout();
        leaveAndPause.add(pauseButton, leaveButton);
        buttLayoutV.add(leaveAndPause);
        //buttLayoutV.add(testActionsButton,actionsButton,  authorisationButton, leaveAndPause);
        //buttLayoutV.setAlignItems(leaveAndPause, FlexComponent.Alignment.END);
        userGridLayoutV.add(userGrid);
        userGridAndButtLayoutH.add(userGridLayoutV, buttLayoutV);
        userGridAndButtLayoutH.setSpacing(true);
        splitMapAndRoomWithActions.addToSecondary(userGridAndButtLayoutH);
    }

    private void addClickListeners(Button actionsButton, Button npcsButton, Button authorisationButton,
                                   Button pauseButton, Button leaveButton) {
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
        pauseButton.addClickListener(e -> Notification.show("Not Implemented"));
        leaveButton.addClickListener(e -> {
            Dialog leaveDialog = createLeaveDialog();
            leaveDialog.open();
        });

    }

    private void createAvatarGrid() {
        userGrid.setSizeFull();
        userGrid.removeAllColumns();

        userGrid.setItems(dungeonServiceI.getCurrentAvatars(dungeon.getDungeonId()));

        userGrid.addColumn(Avatar::getName)
                .setComparator((avater1, avatar2) -> avater1.getName()
                        .compareToIgnoreCase(avatar2.getName()))
                .setHeader("Name");
        userGrid.addColumn(avatar -> dungeonServiceI.getRoomOfAvatar(avatar).getRoomName())
                .setComparator(Comparator.comparing(avatar -> dungeonServiceI.getRoomOfAvatar(avatar).getRoomName()))
                .setHeader("Raum");
        userGrid.addComponentColumn(avatar -> {
            Button requestsButton = new Button("Beantworten");
            notificationButtons.put(avatar, requestsButton);
            requestsButton.addClickListener(e -> {
                if (actionMap.containsKey(avatar)) {
                    UserAction myUserAction = actionMap.get(avatar);
                    switch (myUserAction.getActionType()) {
                        case "CONSUME":
                            Dialog consumeDialog = new Dialog();
                            TextArea consumeActionText = new TextArea();
                            Label consumeUserActionText = new Label("Aktion von " + myUserAction.getUser().getName() + ":" + myUserAction.getUserActionMessage());

                            HorizontalLayout randomLayout = makeDice();
                            Button consumeSendActionButton = new Button("Senden", evfds -> {
                                messagesPublisher.onNext(new ChatMessage(consumeActionText.getValue(), avatar.getUser().getUserId()));
                                actionMap.remove(avatar);
                                dungeonServiceI.setAvatarNotRequested(avatar.getAvatarId());
                                notificationButtons.get(avatar).getStyle().clear();
                                consumeDialog.close();
                            });
                            consumeDialog.add(new VerticalLayout(consumeUserActionText, randomLayout, consumeActionText, consumeSendActionButton));
                            consumeDialog.open();
                            break;
                        case "TALK":
                            Dialog talkDialog = new Dialog();
                            TextArea talkActionText = new TextArea();
                            Label talkUserActionText = new Label("Aktion von " + myUserAction.getUser().getName() + ":" + myUserAction.getUserActionMessage());
                            Button talkSendActionButton = new Button("Test", evfds -> {
                                messagesPublisher.onNext(new ChatMessage(talkActionText.getValue(), avatar.getUser().getUserId()));
                                actionMap.remove(avatar);
                                dungeonServiceI.setAvatarNotRequested(avatar.getAvatarId());
                                notificationButtons.get(avatar).getStyle().clear();
                                talkDialog.close();
                            });
                            talkDialog.add(new VerticalLayout(talkUserActionText, talkActionText, talkSendActionButton));
                            talkDialog.open();
                            break;
                        case "ATTACK":
                            Dialog attackDialog = new Dialog();
                            TextArea attackActionText = new TextArea();
                            Label attackUserActionText = new Label("Aktion von " + myUserAction.getUser().getName() + ":" + myUserAction.getUserActionMessage());
                            Button attackSendActionButton = new Button("Test", evfds -> {
                                messagesPublisher.onNext(new ChatMessage(attackActionText.getValue(), avatar.getUser().getUserId()));
                                actionMap.remove(avatar);
                                dungeonServiceI.setAvatarNotRequested(avatar.getAvatarId());
                                notificationButtons.get(avatar).getStyle().clear();
                                attackDialog.close();
                            });
                            attackDialog.add(new VerticalLayout(attackUserActionText, attackActionText, attackSendActionButton));
                            attackDialog.open();
                            break;
                    }
                }
            });
            return requestsButton;
        }).setHeader("Anfragen");
        userGrid.addComponentColumn(avatar -> {
            Button whisperButton = new Button("Whisper");
            whisperButton.addClickListener(e -> {
                Notification.show("Not Implemented");
            });
            return whisperButton;
        }).setHeader("Anflüstern");
        userGrid.addComponentColumn(avatar -> {
            Button infoButton = new Button("Infos");
            VerticalLayout lay = new VerticalLayout();
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
            infoDialog.setMinHeight("700px");//@TODO
            infoDialog.setMinWidth("1000px");//@TODO
            lay.add(headline, user, room, equipment);
            infoDialog.add(lay, inventoryLabel, inventory);
            infoButton.addClickListener(e -> infoDialog.open());
            return infoButton;
        }).setHeader("Informationen");
        userGrid.addComponentColumn(avatar -> {
            Button kickButton = new Button("Spieler kicken");
            Dialog confirmKickDialog = new Dialog();
            Label confirmLabel = new Label("Bist du dir sicher, dass du den Spieler kicken möchtest?");
            Button confirmButton = new Button("Kicken!");
            Button cancelButton = new Button("Abbrechen");
            confirmKickDialog.add(new VerticalLayout(confirmLabel, new HorizontalLayout(confirmButton, cancelButton)));

            kickButton.addClickListener(e -> {
                confirmKickDialog.open();
            });

            confirmButton.addClickListener(e -> {
                dungeonServiceI.kickPlayer(dungeonId, avatar.getUser().getUserId());
                kickUsersPublisher.onNext(new KickUser(avatar.getUser()));
                confirmKickDialog.close();
            });

            cancelButton.addClickListener(e -> {
                confirmKickDialog.close();
            });
            return kickButton;
        }).setHeader("Kicken");
    }


    HorizontalLayout makeDice() {
        TextField resultLabel = new TextField();
        IntegerField boundField = new IntegerField();
        boundField.setMin(1);
        boundField.setMax(40);
        boundField.setValue(20);

        resultLabel.setReadOnly(true);
        resultLabel.setMaxLength(3);
        resultLabel.setWidth("60px");
        resultLabel.getStyle().set("padding", "0px");

        boundField.setWidth("60px");
        boundField.getStyle().set("padding", "0px");

        Button roll = new Button("Würfeln D:", e -> {
            if(!boundField.isInvalid()) {
                Random r = new Random();
                resultLabel.setValue(String.valueOf(r.nextInt(boundField.getValue()) + 1));
            }
        });
        return new HorizontalLayout(roll, boundField, resultLabel);
    }

    VerticalLayout initMap(Long ADungeonId) {
        int minX = mapServiceI.getMinXY(ADungeonId).getKey();
        int minY = mapServiceI.getMinXY(ADungeonId).getValue();

        Tile[][] newTiles = mapServiceI.getMapGame(ADungeonId, false);
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
                tiles[i][j].addClickListener(e ->
                {
                    Room room = dungeonServiceI.getRoomByPosition(dungeon, finalX, finalY);
                    if (room != null) {
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

        roomNameTextField.setReadOnly(true);
        roomNameTextField.setWidthFull();
        roomDescriptionTextArea.setReadOnly(true);
        roomDescriptionTextArea.setWidthFull();

        itemInRoomGrid.removeAllColumns();
        itemInRoomGrid.addColumn(Item::getItemName).setHeader("Gegenstände");
        itemInRoomGrid.setSizeFull();
        //itemInRoomGrid.setWidth("250px");

        npcInRoomGrid.removeAllColumns();
        npcInRoomGrid.addColumn(NPC::getNpcName).setHeader("NPCs");
        npcInRoomGrid.setSizeFull();
        //npcInRoomGrid.setWidth("250px");

        hl.add(itemInRoomGrid, npcInRoomGrid);
        hl.setSizeFull();
        vl.add(roomNameTextField, roomDescriptionTextArea, hl);
        vl.setSizeFull();
        splitMapWithRoom.addToSecondary(vl);
    }

    private void fillCurrentRoom(Room ARoom) {
        currentRoom = ARoom;
        roomNameTextField.setValue(ARoom.getRoomName());

        if (ARoom.getDescription() != null)
            roomDescriptionTextArea.setValue(ARoom.getDescription());
        else
            roomDescriptionTextArea.setValue("");

        itemInRoomGrid.setItems(ARoom.getItems().stream().map(ItemInstance::getItem).toArray(Item[]::new));
        npcInRoomGrid.setItems(ARoom.getNpcs().stream().map(NpcInstance::getNpc).toArray(NPC[]::new));
    }

    @Override
    public void configurePage(InitialPageSettings settings) {
        String script = "window.onbeforeunload = function (e) { var e = e || window.event; document.getElementById(\"SomeView\").$server.browserIsLeaving(); return; };";
        settings.addInlineWithContents(InitialPageSettings.Position.PREPEND, script, InitialPageSettings.WrapMode.JAVASCRIPT);
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent e) {
        if (!sureToLeave) {
            e.postpone();
            Dialog leaveDialog = createLeaveDialog();
            leaveDialog.open();
        } else {
            loaded = false;
        }
    }

    private Dialog createLeaveDialog() {
        Dialog leaveDialog = new Dialog();
        leaveDialog.setCloseOnEsc(false);
        leaveDialog.setCloseOnOutsideClick(false);
        leaveDialog.setHeight(75, Unit.PERCENTAGE);

        H3 leaveHeadline = new H3("Dungeon verlassen");
        String leaveOrNewDMText = "<div>Willst du den Dungeon wirklich verlassen?<br>" +
                "Ernenne hier einen anderen Spieler zum Dungeon-Master (damit der Laden weiter läuft).<br>" +
                "ODER beende den Dungeon komplett.<br>-- Aber sei gewarnt: " +
                "Alle Spieler werden aus dem Dungeon gekickt (sende davor lieber eine Benachrichtigung!)</div>";

        Button continueButton = new Button("Weiterspielen");
        Button chooseDMButton = new Button("Neuer Dungeon-Master");
        chooseDMButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button leaveForSureButton = new Button("Verlassen");
        leaveForSureButton.getStyle().set("color", "red");

        Grid<User> newDMGrid = new Grid<>(User.class);
        newDMGrid.removeAllColumns();
        newDMGrid.addColumn(User::getName).setHeader("Name");
        newDMGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        continueButton.addClickListener(event -> {
            leaveDialog.close();
        });

        chooseDMButton.addClickListener(event -> {
            if (newDMGrid.getSelectedItems().size() > 0) {
                User newDM = (User) newDMGrid.getSelectedItems().toArray()[0];
                dungeonServiceI.setDungeonMaster(dungeon, newDM.getUserId());
                dungeonServiceI.deactivateDungeon(dungeonId);
                leaveDialog.close();
                UI.getCurrent().navigate("myDungeons");
            } else {
                Notification.show("Bitte wähle einen neuen Dungeon-Master aus!");
            }
        });

        leaveForSureButton.addClickListener(event -> {
            sureToLeave = true;
            dungeonServiceI.deactivateDungeon(dungeonId);
            leaveDialog.close();
            UI.getCurrent().navigate("myDungeons");
        });

        newDMGrid.setItems(dungeonServiceI.getCurrentUsers(dungeon));
        newDMGrid.setVerticalScrollingEnabled(true);
        VerticalLayout myGridLayoutVert = new VerticalLayout(newDMGrid);

        leaveDialog.add(leaveHeadline, new Html(leaveOrNewDMText), myGridLayoutVert,
                new HorizontalLayout(leaveForSureButton, chooseDMButton, continueButton));

        return leaveDialog;
    }
}
