package de.dhbw.binaeratops.view.dungeonmaster;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
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
import de.dhbw.binaeratops.model.actions.KickUserAction;
import de.dhbw.binaeratops.model.api.AvatarI;
import de.dhbw.binaeratops.model.chat.ChatMessage;
import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
import de.dhbw.binaeratops.model.map.Tile;
import de.dhbw.binaeratops.service.api.configuration.DungeonServiceI;
import de.dhbw.binaeratops.service.api.map.MapServiceI;
import de.dhbw.binaeratops.service.api.parser.ParserServiceI;
import de.dhbw.binaeratops.service.exceptions.parser.*;
import de.dhbw.binaeratops.service.impl.chat.ChatService;
import de.dhbw.binaeratops.service.impl.game.GameService;
import de.dhbw.binaeratops.service.impl.parser.UserMessage;
import de.dhbw.binaeratops.view.TranslationProvider;
import de.dhbw.binaeratops.view.chat.ChatView;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import java.text.MessageFormat;
import java.util.*;

/**
 * Oberfläche für die Komponente "Dungeon-Master Ansicht".
 * <p>
 * Diese Ansicht stellt alle View-Komponenten für die Verwaltung des Dungeons bereit.
 * <p>
 * Dafür sendet sie die Benutzerangaben direkt an den entsprechenden Service.
 *
 * @author Matthias Rall, Lars Rösel
 */
@CssImport("./views/game/map-master.css")
@PreserveOnRefresh
@Push
public class DungeonMasterView extends Div implements HasDynamicTitle, HasUrlParameter<Long>, RouterLayout, PageConfigurator, BeforeLeaveObserver {
    Image[][] tiles;

    HashMap<Avatar, Button> notificationButtons = new HashMap<>();
    HashMap<Avatar, UserAction> actionMap = new HashMap<>();

    private final SplitLayout splitChatWithRest = new SplitLayout();
    private final SplitLayout splitMapAndRoomWithActions = new SplitLayout();
    private final SplitLayout splitMapWithRoom = new SplitLayout();

    private Grid<Avatar> userGrid = new Grid<>(Avatar.class);

    private final GameService gameService;

    private final MapServiceI mapServiceI;

    private final DungeonServiceI dungeonServiceI;
    private final Flux<ChatMessage> messages;
    private final ParserServiceI myParserService;

    private final ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());
    private final TranslationProvider transProv = new TranslationProvider();

    private final Flux<UserAction> userAction;
    private final UnicastProcessor<ChatMessage> messagesPublisher;
    private final UnicastProcessor<KickUserAction> kickUsersPublisherAction;
    private final ChatService chatService;

    Dungeon dungeon;
    Long dungeonId;
    String aboutText;
    Html html;
    ChatView myDungeonChatView;

    HorizontalLayout insertInputLayout;
    TextField textField;
    Button confirmButt;
    VerticalLayout gameLayout = new VerticalLayout();

    TextField roomNameTextField = new TextField(res.getString("view.dungeon.master.field.room.name"));
    TextArea roomDescriptionTextArea = new TextArea(res.getString("view.dungeon.master.area.description"));
    Grid<Item> itemInRoomGrid = new Grid<>(Item.class);
    Grid<NPC> npcInRoomGrid = new Grid<>(NPC.class);

    Room currentRoom;

    boolean sureToLeave = false;
    boolean loaded = true;

    // TODO Kommentare schreiben
    public DungeonMasterView(@Autowired MapServiceI mapServiceI, @Autowired GameService gameService, @Autowired DungeonServiceI dungeonServiceI,
                             Flux<ChatMessage> messages, @Autowired ParserServiceI AParserService, Flux<UserAction> userAction, UnicastProcessor<ChatMessage> AMessagePublisher,
                             UnicastProcessor<KickUserAction> AKickUsersPublisherAction, @Autowired ChatService chatService) {
        this.mapServiceI = mapServiceI;
        this.gameService = gameService;
        this.dungeonServiceI = dungeonServiceI;
        this.messages = messages;
        this.myParserService = AParserService;
        this.userAction = userAction;
        this.messagesPublisher = AMessagePublisher;
        this.kickUsersPublisherAction = AKickUsersPublisherAction;
        this.chatService = chatService;
        setId("SomeView");

        userActionsIncoming();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                refreshView();
            }
        }, 0, 1000);
    }

    private void refreshView() {
        getUI().ifPresent(ui -> ui.access(() -> {
            userGrid.setItems(dungeonServiceI.getCurrentAvatars(dungeon.getDungeonId()));
            for (Avatar avatar : actionMap.keySet()) { //TODO Farbe wird zurückgesetzt
                notificationButtons.get(avatar).getStyle().set("background", "red");
            }
            if (currentRoom != null) {
                Room room = dungeonServiceI.getRoomById(currentRoom.getRoomId());
                fillCurrentRoom(room);
            }
        }));
    }

    private void userActionsIncoming() {
        userAction.subscribe(action -> getUI().ifPresent(ui -> ui.access(() -> {
            if (action.getDungeon().getDungeonMasterId().equals(VaadinSession.getCurrent().getAttribute(User.class).getUserId())) {
                if (action.getActionType().equals(ActionType.ENTRY_REQUEST)) {
                    Notification notification = new Notification();
                    Span label = new Span(MessageFormat.format(res.getString("view.dungeon.master.label.user.joins"), action.getUser().getName()));
                    Button acceptButton = new Button(res.getString("view.dungeon.master.button.accept"), e -> {
                        dungeonServiceI.allowUser(dungeonId, action.getUser().getUserId(), action.getPermission());
                        notification.close();
                    });
                    acceptButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
                    Button declineButton = new Button(res.getString("view.dungeon.master.button.decline"), e -> {
                        dungeonServiceI.declinePlayer(dungeonId, action.getUser().getUserId(), action.getPermission());
                        notification.close();
                    });
                    declineButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
                    Button closeButton = new Button("", e -> notification.close());
                    closeButton.setIcon(new Icon(VaadinIcon.CLOSE));
                    closeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

                    notification.add(label, acceptButton, declineButton, closeButton);
                    label.getStyle().set("margin-right", "0.3rem");
                    acceptButton.getStyle().set("margin-right", "0.3rem");
                    declineButton.getStyle().set("margin-right", "0.3rem");
                    notification.setDuration(10000);
                    notification.setPosition(Notification.Position.TOP_END);
                    dungeonServiceI.deleteUserAction(action);
                    notification.open();
                    //TODO den user einlassen
                } else if (action.getActionType().equals(ActionType.CONSUME)) {
                    Notification notification = new Notification();
                    Span label = new Span("Avatar " + action.getAvatar().getName() + " möchte den Gegenstand " + action.getInteractedItem().getItemName() + " konsumieren");
                    Button answerButton = new Button("Beantworten", b -> {
                        notification.close();
                        createInteractionsDialog().open();
                    });
                    answerButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
                    Button closeButton = new Button("", e -> notification.close());
                    closeButton.setIcon(new Icon(VaadinIcon.CLOSE));
                    closeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

                    notification.add(label, answerButton, closeButton);
                    label.getStyle().set("margin-right", "0.3rem");
                    answerButton.getStyle().set("margin-right", "0.3rem");
                    notification.setDuration(10000);
                    notification.setPosition(Notification.Position.TOP_END);
                    notification.open();
                } else if (action.getActionType().equals(ActionType.TALK)) { // TODO
                    Notification notification = new Notification();
                    Span label = new Span("Avatar " + action.getAvatar().getName() + " möchte mit dem NPC " + action.getInteractedNpc().getNpcName() + " sprechen");
                    Button answerButton = new Button("Beantworten", b -> {
                        notification.close();
                        createInteractionsDialog().open();
                    });
                    answerButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
                    Button closeButton = new Button("", e -> {
                        notification.close();
                    });
                    closeButton.setIcon(new Icon(VaadinIcon.CLOSE));
                    closeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

                    notification.add(label, answerButton, closeButton);
                    label.getStyle().set("margin-right", "0.3rem");
                    answerButton.getStyle().set("margin-right", "0.3rem");
                    notification.setDuration(10000);
                    notification.setPosition(Notification.Position.TOP_END);
                    notification.open();
                } else {
                    Notification.show(MessageFormat.format(res.getString("view.dungeon.master.notification.action"), action.getAvatar().getName(), action.getActionType().toString()), 5000, Notification.Position.TOP_END);
                    notificationButtons.get(action.getAvatar()).getStyle().set("background", "red");
                    actionMap.put(action.getAvatar(), action);
                    //TODO test
                }
            }
        })));
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        gameService.initialize(dungeon, attachEvent.getUI(), this);
    }

    private boolean isVirgin = true;

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long ALong) {
        dungeon = dungeonServiceI.getDungeon(ALong);
        dungeonId = ALong;
        if (isVirgin){
            createLayoutBasic();
        }
        else{
            userGridLayoutV.remove(userGrid);
            userGrid = new Grid<>(Avatar.class);
            userGridLayoutV.add(userGrid);
            createAvatarGrid();
            splitMapWithRoom.removeAll();
            createCurrentRoom();
            if (currentRoom != null){
                //fillCurrentRoom(currentRoom);
            }
        }
        takeVirginity();
    }

    private void takeVirginity()
    {
        isVirgin = false;
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
        aboutText = MessageFormat.format(res.getString("view.dungeon.master.text.about"), dungeon.getDungeonName(), dungeon.getCommandSymbol());
        html = new Html(aboutText);

        myDungeonChatView = new ChatView(messages);

        textField = new TextField();
        textField.setWidthFull();
        textField.focus();

        confirmButt = new Button(res.getString("view.dungeon.master.button.submit"));
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
                myDungeonChatView.addMessage(new Paragraph(new Html(message)));
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

    VerticalLayout userGridLayoutV;

    private void createLayoutAction() {
        createAvatarGrid();
        createCurrentRoom();

        HorizontalLayout userGridAndButtLayoutH = new HorizontalLayout();
        VerticalLayout buttLayoutV = new VerticalLayout();
        userGridLayoutV = new VerticalLayout();
        buttLayoutV.getStyle().set("width", "200px");
        buttLayoutV.setClassName("button-layout");

        userGridAndButtLayoutH.setSizeFull();
        //buttLayoutV.setSizeFull();
        Button actionsButton = new Button("Aktionen");
        Button npcsButton = new Button("NPCs");
        Button authorisationButton = new Button("Spielberechtigungen"); // TODO entfernen
        Button pauseButton = new Button(res.getString("view.dungeon.master.button.pause.game"));
        Button leaveButton = new Button(res.getString("view.dungeon.master.button.leave.game"));
        Button requestButton = new Button("Beitrittsanfragen");
        Button interactionsButton = new Button("Interaktionsanfragen");
        leaveButton.getStyle().set("color", "red");
        addClickListeners(actionsButton, npcsButton, authorisationButton, pauseButton, leaveButton, requestButton, interactionsButton);
        VerticalLayout leaveAndPause = new VerticalLayout();
        //leaveAndPause.add(pauseButton, leaveButton);
        leaveAndPause.add(leaveButton, requestButton, interactionsButton);
        buttLayoutV.add(leaveAndPause);
        //buttLayoutV.add(testActionsButton,actionsButton,  authorisationButton, leaveAndPause);
        //buttLayoutV.setAlignItems(leaveAndPause, FlexComponent.Alignment.END);
        userGridLayoutV.add(userGrid);
        userGridAndButtLayoutH.add(userGridLayoutV, buttLayoutV);
        userGridAndButtLayoutH.setSpacing(true);
        splitMapAndRoomWithActions.addToSecondary(userGridAndButtLayoutH);
    }

    private void addClickListeners(Button actionsButton, Button npcsButton, Button authorisationButton,
                                   Button pauseButton, Button leaveButton, Button requestButton, Button interactionsButton) {
        actionsButton.addClickListener(e -> {
            Dialog actionDialog = new Dialog(new Text("Aktionen zur Auswahl"));
            actionDialog.open();
        });
        npcsButton.addClickListener(e -> {
            Dialog npcDialog = new Dialog(new Text("NPCs zur Auswahl")); // TODO entfernen
            npcDialog.open();
        });
        authorisationButton.addClickListener(e -> {
            Dialog authorisationDialog = new Dialog(new Text("Neue Anfragen und aktuelle Spieler"));
            authorisationDialog.open();
        });
        pauseButton.addClickListener(e -> Notification.show("Dungeon pausiert"));
        leaveButton.addClickListener(e -> {
            Dialog leaveDialog = createLeaveDialog();
            leaveDialog.open();
        });
        requestButton.addClickListener(e -> {
            Dialog requestDialog = createRequestDialog();
            requestDialog.open();
        });
        interactionsButton.addClickListener(e -> {
            Dialog interactionsDialog = createInteractionsDialog();
            interactionsDialog.open();
        });
    }

    private Dialog createRequestDialog() {
        Dialog requestDialog = new Dialog();
        H1 headline = new H1("Beitrittsanfragen");
        Grid<Permission> perms = new Grid<>();
        perms.addColumn(e -> e.getUser().getName()).setHeader("Benutzer");
        perms.addComponentColumn(permission -> {
            HorizontalLayout hl = new HorizontalLayout();
            Button acceptButton = new Button("", v -> {
                dungeon.removeRequestUser(permission);
                dungeon.addAllowedUser(permission);
                dungeonServiceI.savePermission(permission);
                perms.setItems(dungeonServiceI.getRequestedPermissions(dungeon));
            });
            acceptButton.setIcon(new Icon(VaadinIcon.CHECK));
            Button declineButton = new Button("", v -> {
                dungeon.removeRequestUser(permission);
                dungeon.addBlockedUser(permission);
                dungeonServiceI.savePermission(permission);
                perms.setItems(dungeonServiceI.getRequestedPermissions(dungeon));
            });
            declineButton.setIcon(new Icon(VaadinIcon.CLOSE));
            hl.add(acceptButton, declineButton);
            return hl;
        });
        perms.setItems(dungeonServiceI.getRequestedPermissions(dungeon));

        Button closeButton = new Button("Schließen", b -> requestDialog.close());

        closeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        requestDialog.setWidth(500, Unit.PIXELS);
        requestDialog.setMaxWidth(100, Unit.PERCENTAGE);
        requestDialog.add(headline, perms, closeButton);
        return requestDialog;
    }

    private Dialog createInteractionsDialog() {
        Dialog interactionsDialog = new Dialog();
        H1 headline = new H1("Interaktionsanfragen");
        Grid<UserAction> interactions = new Grid<>();
        interactions.addColumn(userAction -> userAction.getAvatar().getName()).setHeader("Avatarname");
        interactions.addColumn(userAction -> userAction.getActionType().toString().toUpperCase()).setHeader("Aktionstyp");
        interactions.addComponentColumn(userAction -> {
            Button answerButton = new Button("Beantworten", b -> {
                Avatar avatar = userAction.getAvatar();
                switch (userAction.getActionType()) {
                    case CONSUME:
                        Dialog consumeDialog = createConsumeDialog(userAction, interactions);
//                        TextArea consumeActionText = new TextArea();
//                        Label consumeUserActionText = new Label(MessageFormat.format(res.getString("view.dungeon.master.dialog.answer.text"), userAction.getAvatar().getName(), userAction.getInteractedItem()));
//
//                        HorizontalLayout randomLayout = makeDice();
//                        Button consumeSendActionButton = new Button(res.getString("view.dungeon.master.dialog.answer.button.send"), evfds -> {
//                            chatService.sendActionMessage(consumeActionText.getValue(), avatar.getUser());
//                            actionMap.remove(avatar);
//                            dungeonServiceI.setAvatarNotRequested(avatar.getAvatarId());
//                            dungeonServiceI.deleteUserAction(userAction);
//                            interactions.setItems(dungeonServiceI.getUserActions(dungeon));
//                            consumeDialog.close();
//                        });
//                        consumeDialog.add(new VerticalLayout(consumeUserActionText, randomLayout, consumeActionText, consumeSendActionButton));
                        consumeDialog.open();
                        break;
                    case TALK: // TODO testen
                        Dialog talkDialog = createTalkDialog(userAction, interactions);
//                        TextArea talkActionText = new TextArea();
//                        Label talkUserActionText = new Label(MessageFormat.format(res.getString("view.dungeon.master.dialog.answer.talk.text1"), userAction.getAvatar().getName(), userAction.getInteractedNpc().getNpc().getNpcName()));
//                        Label talkUserActionText2 = new Label(MessageFormat.format(res.getString("view.dungeon.master.dialog.answer.talk.label2"), userAction.getMessage(), userAction.getInteractedNpc().getNpc().getNpcName()));
//                        Button talkSendActionButton = new Button(res.getString("view.dungeon.master.dialog.answer.button.send"), evfds -> {
//                            chatService.whisperFromNpc(talkActionText.getValue(), avatar.getUser(), userAction.getInteractedNpc().getNpcName());
//                            actionMap.remove(avatar);
//                            dungeonServiceI.setAvatarNotRequested(avatar.getAvatarId());
//                            dungeonServiceI.deleteUserAction(userAction);
//                            interactions.setItems(dungeonServiceI.getUserActions(dungeon));
//                            talkDialog.close();
//                        });
//                        talkDialog.add(new VerticalLayout(talkUserActionText, talkUserActionText2, talkActionText, talkSendActionButton));
                        talkDialog.open();
                        break;
                    case HIT: // TODO testen
                        Dialog attackDialog = new Dialog();
                        TextArea attackActionText = new TextArea();
                        Label attackUserActionText = new Label("Aktion von " + userAction.getAvatar().getName() + ":" + userAction.getInteractedNpc().getNpc().getNpcName());
                        Button attackSendActionButton = new Button("Test", evfds -> {
                            messagesPublisher.onNext(new ChatMessage(attackActionText.getValue(), avatar.getUser().getUserId()));
                            actionMap.remove(avatar);
                            dungeonServiceI.setAvatarNotRequested(avatar.getAvatarId());
                            dungeonServiceI.deleteUserAction(userAction);
                            interactions.setItems(dungeonServiceI.getUserActions(dungeon));
                            attackDialog.close();
                        });
                        attackDialog.add(new VerticalLayout(attackUserActionText, attackActionText, attackSendActionButton));
                        attackDialog.open();
                        break;
                    default:
                        // TODO Fehlerbehandlung..
                        break;
                }
            });
            return answerButton;
        });
        interactions.setItems(dungeonServiceI.getUserActions(dungeon));

        Button closeButton = new Button("Schließen", b -> {
            interactionsDialog.close();
        });
        closeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        interactionsDialog.setWidth(650, Unit.PIXELS);
        interactionsDialog.setMaxWidth(100, Unit.PERCENTAGE);
        interactionsDialog.add(headline, interactions, closeButton);

        return interactionsDialog;
    }

    private Dialog createTalkDialog(UserAction AUserAction, Grid<UserAction> AUserActionGrid) {
        Dialog talkDialog = new Dialog();
        talkDialog.setWidth(75, Unit.PERCENTAGE);

        SplitLayout avatarAndRequest = new SplitLayout();
        SplitLayout requestAndNpc = new SplitLayout();

        VerticalLayout vlAvatar = new VerticalLayout();
        VerticalLayout vlRequest = new VerticalLayout();
        VerticalLayout vlNpc = new VerticalLayout();

        avatarAndRequest.addToPrimary(vlAvatar);
        avatarAndRequest.addToSecondary(requestAndNpc);

        requestAndNpc.addToPrimary(vlRequest);
        requestAndNpc.addToSecondary(vlNpc);

        vlAvatar.setWidth(40, Unit.PERCENTAGE);
        requestAndNpc.setWidth(60, Unit.PERCENTAGE);
        vlRequest.setWidth(60, Unit.PERCENTAGE);
        vlNpc.setWidth(40, Unit.PERCENTAGE);

        // Avatar
        H2 headlineAvatar = new H2("Avatar des Spielers");

        TextField nameField = new TextField();
        nameField.setLabel("Name: ");
        nameField.setReadOnly(true);
        nameField.setValue(AUserAction.getAvatar().getName());

        TextField raceField = new TextField();
        raceField.setLabel("Rasse: ");
        raceField.setReadOnly(true);
        raceField.setValue(AUserAction.getAvatar().getRace().getRaceName());

        TextField roleField = new TextField();
        roleField.setLabel("Rolle: ");
        roleField.setReadOnly(true);
        roleField.setValue(AUserAction.getAvatar().getRole().getRoleName());

        IntegerField avatarLifePointsField = new IntegerField("Lebenspunkte: ");
        avatarLifePointsField.setStep(1);
        avatarLifePointsField.setHasControls(true);
        avatarLifePointsField.setMin(1);
        avatarLifePointsField.setValue(AUserAction.getAvatar().getLifepoints().intValue());
        avatarLifePointsField.addValueChangeListener(e -> {
            if (!avatarLifePointsField.isInvalid()) {
                gameService.setLifePoints(AUserAction.getAvatar().getAvatarId(), avatarLifePointsField.getValue());
            }
        });


        Grid<ItemInstance> inventory = new Grid<>();
        inventory.addColumn(item -> item.getItem().getItemName()).setHeader("Name");
        inventory.addColumn(item -> item.getItem().getDescription()).setHeader("Beschreibung");
        inventory.addColumn(item -> item.getItem().getType()).setHeader("Typ");
        inventory.addComponentColumn(item -> {
            Button deleteButton = new Button();
            deleteButton.setIcon(new Icon(VaadinIcon.CLOSE));
            deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
            deleteButton.addClickListener(e -> {
                gameService.removeItemFromInventory(AUserAction.getAvatar().getAvatarId(), item.getItemInstanceId());
                inventory.setItems(gameService.getInventory(AUserAction.getAvatar().getAvatarId()));
            });
            return deleteButton;
        }).setHeader("Löschen");
        inventory.setItems(gameService.getInventory(AUserAction.getAvatar().getAvatarId()));

        vlAvatar.add(headlineAvatar, nameField, raceField, roleField, avatarLifePointsField, inventory);

        // REQUEST
        H2 requestHeadline = new H2("Anfragenbehandlung");

        TextArea senderMessageArea = new TextArea();
        senderMessageArea.setReadOnly(true);
        senderMessageArea.setWidthFull();
        senderMessageArea.setValue(AUserAction.getMessage());
        senderMessageArea.setLabel("Nachricht des Avatars: ");

        Label questionLabel = new Label("Was antwortet der NPC \""+ AUserAction.getInteractedNpc().getNpcName() +"\"?");

        TextArea receiverMessageArea = new TextArea();
        receiverMessageArea.setWidthFull();
        receiverMessageArea.focus();
        receiverMessageArea.setLabel("Antwort des NPCs: ");

        Button sendButton = new Button("Senden");
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sendButton.addClickListener(e -> {
            chatService.whisperFromNpc(receiverMessageArea.getValue(), AUserAction.getAvatar().getUser(), AUserAction.getInteractedNpc().getNpcName());
            actionMap.remove(AUserAction.getAvatar());
            dungeonServiceI.setAvatarNotRequested(AUserAction.getAvatar().getAvatarId());
            dungeonServiceI.deleteUserAction(AUserAction);
            AUserActionGrid.setItems(dungeonServiceI.getUserActions(dungeon));
            talkDialog.close();
        });

        Button cancelButton = new Button("Abbrechen");
        cancelButton.addClickListener(e -> talkDialog.close());

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(sendButton, cancelButton);

        vlRequest.add(requestHeadline, senderMessageArea, questionLabel, receiverMessageArea, buttonLayout);

        // NPC
        H2 npcHeadline = new H2("Angesprochener NPC");

        TextField npcNameField = new TextField();
        npcNameField.setLabel("Name des NPCs");
        npcNameField.setReadOnly(true);
        npcNameField.setValue(AUserAction.getInteractedNpc().getNpcName());

        TextField npcRace = new TextField();
        npcRace.setLabel("Rasse des NPCs");
        npcRace.setReadOnly(true);
        npcRace.setValue(AUserAction.getInteractedNpc().getNpc().getRace().getRaceName());

        TextArea npcDescription = new TextArea();
        npcDescription.setReadOnly(true);
        npcDescription.setLabel("Beschreibung des NPCs");
        if (AUserAction.getInteractedNpc().getNpc().getDescription() != null) {
            npcDescription.setValue(AUserAction.getInteractedNpc().getNpc().getDescription());
        } else {
            npcDescription.setValue("");
        }

        vlNpc.add(npcHeadline, npcNameField, npcRace, npcDescription);

        talkDialog.add(avatarAndRequest);

        return talkDialog;
    }

    private Dialog createConsumeDialog(UserAction AUserAction, Grid<UserAction> AUserActionGrid) {
        Dialog consumeDialog = new Dialog();
        consumeDialog.setWidth(75, Unit.PERCENTAGE);

        SplitLayout avatarAndRequest = new SplitLayout();
        SplitLayout requestAndItem = new SplitLayout();

        VerticalLayout vlAvatar = new VerticalLayout();
        VerticalLayout vlRequest = new VerticalLayout();
        VerticalLayout vlItem = new VerticalLayout();

        avatarAndRequest.addToPrimary(vlAvatar);
        avatarAndRequest.addToSecondary(requestAndItem);

        requestAndItem.addToPrimary(vlRequest);
        requestAndItem.addToSecondary(vlItem);

        vlAvatar.setWidth(40, Unit.PERCENTAGE);
        requestAndItem.setWidth(60, Unit.PERCENTAGE);
        vlRequest.setWidth(60, Unit.PERCENTAGE);
        vlItem.setWidth(40, Unit.PERCENTAGE);

        // Avatar
        H2 headlineAvatar = new H2("Avatar des Spielers");

        TextField nameField = new TextField();
        nameField.setLabel("Name: ");
        nameField.setReadOnly(true);
        nameField.setValue(AUserAction.getAvatar().getName());

        TextField raceField = new TextField();
        raceField.setLabel("Rasse: ");
        raceField.setReadOnly(true);
        raceField.setValue(AUserAction.getAvatar().getRace().getRaceName());

        TextField roleField = new TextField();
        roleField.setLabel("Rolle: ");
        roleField.setReadOnly(true);
        roleField.setValue(AUserAction.getAvatar().getRole().getRoleName());

        IntegerField avatarLifePointsField = new IntegerField("Lebenspunkte: ");
        avatarLifePointsField.setStep(1);
        avatarLifePointsField.setHasControls(true);
        avatarLifePointsField.setMin(1);
        avatarLifePointsField.setValue(AUserAction.getAvatar().getLifepoints().intValue());
        avatarLifePointsField.addValueChangeListener(e -> {
            if (!avatarLifePointsField.isInvalid()) {
                gameService.setLifePoints(AUserAction.getAvatar().getAvatarId(), avatarLifePointsField.getValue());
            }
        });


        Grid<ItemInstance> inventory = new Grid<>();
        inventory.addColumn(item -> item.getItem().getItemName()).setHeader("Name");
        inventory.addColumn(item -> item.getItem().getDescription()).setHeader("Beschreibung");
        inventory.addColumn(item -> item.getItem().getType()).setHeader("Typ");
        inventory.addComponentColumn(item -> {
            Button deleteButton = new Button();
            deleteButton.setIcon(new Icon(VaadinIcon.CLOSE));
            deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
            deleteButton.addClickListener(e -> {
                gameService.removeItemFromInventory(AUserAction.getAvatar().getAvatarId(), item.getItemInstanceId());
                inventory.setItems(gameService.getInventory(AUserAction.getAvatar().getAvatarId()));
            });
            return deleteButton;
        }).setHeader("Löschen");
        inventory.setItems(gameService.getInventory(AUserAction.getAvatar().getAvatarId()));

        vlAvatar.add(headlineAvatar, nameField, raceField, roleField, avatarLifePointsField, inventory);

        // REQUEST
        H2 requestHeadline = new H2("Anfragenbehandlung");

        TextArea receiverMessageArea = new TextArea();
        receiverMessageArea.setWidthFull();
        receiverMessageArea.focus();
        receiverMessageArea.setLabel("Effekt des konsumierten Gegenstandes: ");

        Button sendButton = new Button("Senden");
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sendButton.addClickListener(e -> {
            chatService.sendActionMessage(receiverMessageArea.getValue(), AUserAction.getAvatar().getUser());
            actionMap.remove(AUserAction.getAvatar());
            dungeonServiceI.setAvatarNotRequested(AUserAction.getAvatar().getAvatarId());
            dungeonServiceI.deleteUserAction(AUserAction);
            AUserActionGrid.setItems(dungeonServiceI.getUserActions(dungeon));
            consumeDialog.close();
        });

        Button cancelButton = new Button("Abbrechen");
        cancelButton.addClickListener(e -> consumeDialog.close());

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(sendButton, cancelButton);

        vlRequest.add(requestHeadline, receiverMessageArea, buttonLayout);

        // ITEM
        H2 itemHeadline = new H2("Konsumierter Gegenstand");

        TextField itemNameField = new TextField();
        itemNameField.setLabel("Name des Gegenstandes");
        itemNameField.setWidthFull();
        itemNameField.setReadOnly(true);
        itemNameField.setValue(AUserAction.getInteractedItem().getItemName());

        TextField itemType = new TextField();
        itemType.setLabel("Typ des Gegenstandes");
        itemType.setWidthFull();
        itemType.setReadOnly(true);
        itemType.setValue(AUserAction.getInteractedItem().getType().toString());

        IntegerField itemSizeField = new IntegerField("Größe des Gegenstandes");
        itemSizeField.setStep(1);
        itemSizeField.setHasControls(true);
        itemSizeField.setWidthFull();
        itemSizeField.setReadOnly(true);
        itemSizeField.setMin(1);
        itemSizeField.setValue(AUserAction.getInteractedItem().getSize().intValue());

        TextArea itemDescription = new TextArea();
        itemDescription.setReadOnly(true);
        itemDescription.setWidthFull();
        itemDescription.setLabel("Beschreibung des Gegenstandes");
        if (AUserAction.getInteractedItem().getDescription() != null) {
            itemDescription.setValue(AUserAction.getInteractedItem().getDescription());
        } else {
            itemDescription.setValue("");
        }

        vlItem.add(itemHeadline, itemNameField, itemType, itemSizeField, itemDescription);

        consumeDialog.add(avatarAndRequest);

        return consumeDialog;
    }

    private void createAvatarGrid() {
        userGrid.setSizeFull();
        userGrid.removeAllColumns();

        userGrid.setItems(dungeonServiceI.getCurrentAvatars(dungeon.getDungeonId()));

        userGrid.addColumn(Avatar::getName)
                .setComparator((avater1, avatar2) -> avater1.getName()
                        .compareToIgnoreCase(avatar2.getName()))
                .setHeader(res.getString("view.dungeon.master.grid.name"));
        userGrid.addColumn(avatar -> dungeonServiceI.getRoomOfAvatar(avatar).getRoomName())
                .setComparator(Comparator.comparing(avatar -> dungeonServiceI.getRoomOfAvatar(avatar).getRoomName()))
                .setHeader(res.getString("view.dungeon.master.grid.room"));
//        userGrid.addComponentColumn(avatar -> {
//            Button requestsButton = new Button(res.getString("view.dungeon.master.grid.button.answer"));
//            notificationButtons.put(avatar, requestsButton);
//            requestsButton.addClickListener(e -> {
//                if (actionMap.containsKey(avatar)) {
//                    UserAction myUserAction = actionMap.get(avatar);
//                    switch (myUserAction.getActionType()) {
//                        case CONSUME:
//                            Dialog consumeDialog = new Dialog();
//                            TextArea consumeActionText = new TextArea();
//                            Label consumeUserActionText = new Label(MessageFormat.format(res.getString("view.dungeon.master.dialog.answer.text"), myUserAction.getAvatar().getName(), myUserAction.getMessage()));
//
//                            HorizontalLayout randomLayout = makeDice();
//                            Button consumeSendActionButton = new Button(res.getString("view.dungeon.master.dialog.answer.button.send"), evfds -> {
//                                chatService.sendActionMessage(consumeActionText.getValue(), avatar.getUser());
//                                actionMap.remove(avatar);
//                                dungeonServiceI.setAvatarNotRequested(avatar.getAvatarId());
//                                notificationButtons.get(avatar).getStyle().clear();
//                                myUserAction.setRequested(false);
//                                dungeonServiceI.saveUserAction(myUserAction);
//                                consumeDialog.close();
//                            });
//                            consumeDialog.add(new VerticalLayout(consumeUserActionText, randomLayout, consumeActionText, consumeSendActionButton));
//                            consumeDialog.open();
//                            break;
//                        case "TALK":
//                            Dialog talkDialog = new Dialog();
//                            TextArea talkActionText = new TextArea();
//                            Label talkUserActionText = new Label(MessageFormat.format(res.getString("view.dungeon.master.dialog.answer.talk.text1"),myUserAction.getAvatar().getName(),myUserAction.getUserActionMessage()));
//                            Label talkUserActionText2 = new Label(MessageFormat.format(res.getString("view.dungeon.master.dialog.answer.talk.label2"), myUserAction.getTalkMessage(), myUserAction.getUserActionMessage()));
//                            Button talkSendActionButton = new Button(res.getString("view.dungeon.master.dialog.answer.button.send"), evfds -> {
//                                chatService.whisperFromNpc(talkActionText.getValue(),avatar.getUser(),myUserAction.getUserActionMessage());
//                                actionMap.remove(avatar);
//                                dungeonServiceI.setAvatarNotRequested(avatar.getAvatarId());
//                                notificationButtons.get(avatar).getStyle().clear();
//                                talkDialog.close();
//                            });
//                            talkDialog.add(new VerticalLayout(talkUserActionText,talkUserActionText2, talkActionText, talkSendActionButton));
//                            talkDialog.open();
//                            break;
//                        case "HIT":
//                            Dialog attackDialog = new Dialog();
//                            TextArea attackActionText = new TextArea();
//                            Label attackUserActionText = new Label("Aktion von " + myUserAction.getAvatar().getName() + ":" + myUserAction.getUserActionMessage());
//                            Button attackSendActionButton = new Button("Test", evfds -> {
//                                messagesPublisher.onNext(new ChatMessage(attackActionText.getValue(), avatar.getUser().getUserId()));
//                                actionMap.remove(avatar);
//                                dungeonServiceI.setAvatarNotRequested(avatar.getAvatarId());
//                                notificationButtons.get(avatar).getStyle().clear();
//                                attackDialog.close();
//                            });
//                            attackDialog.add(new VerticalLayout(attackUserActionText, attackActionText, attackSendActionButton));
//                            attackDialog.open();
//                            break;
//                    }
//                }
//            });
//            return requestsButton;
//        }).setHeader(res.getString("view.dungeon.master.grid.request"));
        /*userGrid.addComponentColumn(avatar -> {
            Button whisperButton = new Button("Whisper");
            whisperButton.addClickListener(e -> {
                Notification.show("Not Implemented");
            });
            return whisperButton;
        }).setHeader("Anflüstern");

         */
        userGrid.addComponentColumn(avatar -> {
            Button infoButton = new Button(res.getString("view.dungeon.master.grid.button.infos"));
            VerticalLayout lay = new VerticalLayout();
            H4 headline = new H4(MessageFormat.format(res.getString("view.dungeon.master.dialog.infos.h4"), avatar.getName()));
            Label user = new Label(MessageFormat.format(res.getString("view.dungeon.master.dialog.infos.label1"), avatar.getUser().getName()));
            Label room = new Label(MessageFormat.format(res.getString("view.dungeon.master.dialog.infos.label2"), dungeonServiceI.getRoomOfAvatar(avatar).getRoomName()));
            Label equipment = new Label(MessageFormat.format(res.getString("view.dungeon.master.dialog.infos.label3"), Arrays.toString(avatar.getEquipment().stream().map(itemInstance -> itemInstance.getItem().getItemName()).toArray(String[]::new))));
            Label inventoryLabel = new Label(res.getString("view.dungeon.master.dialog.infos.label4"));
            Grid<Item> inventory = new Grid<>(Item.class);
            inventory.removeAllColumns();
            inventory.addColumn(Item::getItemName).setHeader(res.getString("view.dungeon.master.dialog.infos.grid.name")).setAutoWidth(true);
            inventory.addColumn(Item::getDescription).setHeader(res.getString("view.dungeon.master.dialog.infos.grid.description")).setAutoWidth(true);
            inventory.addColumn(Item::getSize).setHeader(res.getString("view.dungeon.master.dialog.infos.grid.size")).setAutoWidth(true);
            inventory.addColumn(Item::getType).setHeader(res.getString("view.dungeon.master.dialog.infos.grid.type")).setAutoWidth(true);
            inventory.setItems(avatar.getInventory().stream().map(ItemInstance::getItem).toArray(Item[]::new));

            Dialog infoDialog = new Dialog();
            infoDialog.setMinHeight("700px");
            infoDialog.setMinWidth("1000px");
            lay.add(headline, user, room, equipment);
            infoDialog.add(lay, inventoryLabel, inventory);
            infoButton.addClickListener(e -> infoDialog.open());
            return infoButton;
        }).setHeader(res.getString("view.dungeon.master.grid.infos"));
        userGrid.addComponentColumn(avatar -> {
            Button kickButton = new Button(res.getString("view.dungeon.master.dialog.kick.grid.button.kick"));
            Dialog confirmKickDialog = new Dialog();
            Label confirmLabel = new Label(res.getString("view.dungeon.master.dialog.kick.grid.label"));
            Button confirmButton = new Button(res.getString("view.dungeon.master.dialog.kick.grid.button.kick.player"));
            Button cancelButton = new Button(res.getString("view.dungeon.master.dialog.kick.grid.button.kick.player.cancel"));
            confirmKickDialog.add(new VerticalLayout(confirmLabel, new HorizontalLayout(confirmButton, cancelButton)));

            kickButton.addClickListener(e -> confirmKickDialog.open());

            confirmButton.addClickListener(e -> {
                dungeonServiceI.kickPlayer(dungeonId, avatar.getUser().getUserId());
                kickUsersPublisherAction.onNext(new KickUserAction(avatar.getUser(), "KICK"));
                confirmKickDialog.close();
            });

            cancelButton.addClickListener(e -> confirmKickDialog.close());
            return kickButton;
        }).setHeader(res.getString("view.dungeon.master.grid.kick"));
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

        Button roll = new Button(res.getString("view.dungeon.master.button.dice"), e -> {
            if (!boundField.isInvalid()) {
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

    VerticalLayout vl;
    HorizontalLayout hl;

    private void createCurrentRoom() {
        vl = new VerticalLayout();
        hl = new HorizontalLayout();

        roomNameTextField.setReadOnly(true);
        roomNameTextField.setWidthFull();
        roomDescriptionTextArea.setReadOnly(true);
        roomDescriptionTextArea.setWidthFull();

        itemInRoomGrid = new Grid<>(Item.class);
        itemInRoomGrid.removeAllColumns();
        itemInRoomGrid.addColumn(Item::getItemName).setHeader(res.getString("view.dungeon.master.current.room.grid.item"));
        itemInRoomGrid.setSizeFull();
        //itemInRoomGrid.setWidth("250px");

        npcInRoomGrid = new Grid<>(NPC.class);
        npcInRoomGrid.removeAllColumns();
        npcInRoomGrid.addColumn(NPC::getNpcName).setHeader(res.getString("view.dungeon.master.current.room.grid.npc"));
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
        npcInRoomGrid.setItems(ARoom.getNpcs().stream().map(NPCInstance::getNpc).toArray(NPC[]::new));
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
        //leaveDialog.setHeight(75, Unit.PERCENTAGE);

        H3 leaveHeadline = new H3(res.getString("view.dungeon.master.dialog.leave.h3"));
        String leaveOrNewDMText = res.getString("view.dungeon.master.dialog.leave.text");

        Button continueButton = new Button(res.getString("view.dungeon.master.dialog.leave.continue"));
        Button chooseDMButton = new Button(res.getString("view.dungeon.master.dialog.leave.new.dm"));
        chooseDMButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button leaveForSureButton = new Button(res.getString("view.dungeon.master.dialog.leave.leave"));
        leaveForSureButton.getStyle().set("color", "red");

        Grid<User> newDMGrid = new Grid<>(User.class);
        newDMGrid.removeAllColumns();
        newDMGrid.addColumn(User::getName).setHeader(res.getString("view.dungeon.master.dialog.leave.grid.name"));
        newDMGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        continueButton.addClickListener(event -> leaveDialog.close());

        chooseDMButton.addClickListener(event -> {
            if (newDMGrid.getSelectedItems().size() > 0) {
                User newDM = dungeonServiceI.getUser(((User) newDMGrid.getSelectedItems().toArray()[0]).getUserId());
                dungeonServiceI.setDungeonMaster(dungeon.getDungeonId(), newDM.getUserId());
                dungeonServiceI.deactivateDungeon(dungeonId);
                sureToLeave = true;
                setPlayersInactive();
                leaveDialog.close();
                UI.getCurrent().navigate("myDungeons");
            } else {
                Notification.show(res.getString("view.dungeon.master.dialog.leave.notification"));
            }
        });

        leaveForSureButton.addClickListener(event -> {
            sureToLeave = true;
            dungeonServiceI.deactivateDungeon(dungeonId);
            setPlayersInactive();
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

    private void setPlayersInactive() {
        List<Avatar> avatars = dungeonServiceI.getCurrentAvatars(dungeonId);
        for (Avatar avatar : avatars) {
            dungeonServiceI.setAvatarInactive(avatar.getAvatarId());
        }
    }

    @Override
    public String getPageTitle() {
        return res.getString("view.dungeon.master.pagetitle");
    }
}
