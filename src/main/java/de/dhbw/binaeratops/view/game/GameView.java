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
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.actions.KickUserAction;
import de.dhbw.binaeratops.model.api.RoomI;
import de.dhbw.binaeratops.model.chat.ChatMessage;
import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.model.enums.Gender;
import de.dhbw.binaeratops.model.enums.Status;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
import de.dhbw.binaeratops.model.map.Tile;
import de.dhbw.binaeratops.model.repository.DungeonRepositoryI;
import de.dhbw.binaeratops.model.repository.RoomRepositoryI;
import de.dhbw.binaeratops.service.api.game.GameServiceI;
import de.dhbw.binaeratops.service.api.map.MapServiceI;
import de.dhbw.binaeratops.service.api.parser.ParserServiceI;
import de.dhbw.binaeratops.service.exceptions.parser.*;
import de.dhbw.binaeratops.service.impl.parser.UserMessage;
import de.dhbw.binaeratops.view.TranslationProvider;
import de.dhbw.binaeratops.view.chat.ChatView;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Oberfl??che f??r die Komponente "Spieler Ansicht".
 * <p>
 * Diese Ansicht stellt alle View-Komponenten f??r das Spielen des Dungeons bereit.
 * <p>
 * Daf??r sendet sie die Benutzerangaben direkt an den entsprechenden Service.
 *
 * @author Lukas G??pel, Nicolas Haug, Timon Gartung, Matthias Rall, Lars R??sel
 */
@CssImport("./views/game/game-view.css")
@PreserveOnRefresh
public class GameView extends VerticalLayout implements HasDynamicTitle, HasUrlParameter<Long>, BeforeLeaveObserver {
    BeforeLeaveEvent.ContinueNavigationAction action;

    ParserServiceI myParserService;
    MapServiceI mapServiceI;
    RoomRepositoryI myRoomRepo;//@TODO nur zu Testzwecken, dann entfernen
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
    private final Flux<ChatMessage> messages;
    private final UnicastProcessor<ChatMessage> messagesPublisher;
    private final Flux<KickUserAction> kickUsers;

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

    Button filterChatButton;
    Button filterActionButton;
    Button resetFilterButton;

    private List<ItemInstance> inventoryList;
    Grid<ItemInstance> inventoryGrid;
    private List<ItemInstance> armorList;
    Grid<ItemInstance> armorGrid;

    private Avatar myAvatar;
    private Room currentRoom;
    private List<Room> visitedRooms;
    private Avatar selectedInDialogAvatar;

    private Timer myTimer;

    /**
     * Konstruktor zum Erzeugen der View f??r den Tab '??ber uns'.
     *
     * @param messages          Wird f??r den Nachrichtenaustausch zwischen Spielern und Dungeon-Master ben??tigt.
     * @param AParserService    Wird f??r die Interaktion mit dem Dungeon ben??tigt.
     * @param AMapService       Wird zur Erstellung der Karte ben??tigt.
     * @param ARoomRepo         Wird f??r das Auffinden von R??umen ben??tigt.
     * @param ADungeonRepo      Wird f??r das Auffinden des Dungeon Objekts anhand der ??bergebenen Dungeon ID ben??tigt.
     * @param AGameService      Wird f??r die Interaktion mit der Datenbank ben??tigt.
     * @param AMessagePublisher Wird zum Empfangen von Nachrichten ben??tigt.
     * @param kickUsers         Wird zum Kicken der Benutzer ben??tigt.
     */
    public GameView(Flux<ChatMessage> messages, @Autowired ParserServiceI AParserService,
                    @Autowired MapServiceI AMapService, @Autowired RoomRepositoryI ARoomRepo,
                    @Autowired DungeonRepositoryI ADungeonRepo, @Autowired GameServiceI AGameService,
                    UnicastProcessor<ChatMessage> AMessagePublisher, Flux<KickUserAction> kickUsers) {
        this.messages = messages;
        this.messagesPublisher = AMessagePublisher;
        myParserService = AParserService;
        mapServiceI = AMapService;
        myRoomRepo = ARoomRepo;
        myDungeonRepo = ADungeonRepo;
        myGameService = AGameService;
        this.kickUsers = kickUsers;

        currentUser = VaadinSession.getCurrent().getAttribute(User.class);

        //Timer
        myTimer = new Timer();
    }

    private boolean isVirgin = true;

    @Override
    public void setParameter(BeforeEvent ABeforeEvent, Long ALong) {
        dungeonId = ALong;
        myDungeon = myDungeonRepo.findByDungeonId(dungeonId);

        if (isVirgin) {
            initiateGameView();
            //Avatarauswahl ??ffnen
            createAvatarDialog();
            initializeKickSubscriber();
            takeVirginity();
        } else {
            if (!myAvatarDialog.isOpened()) {
                gridLayout.remove(armorLayout);
                gridLayout.remove(inventoryLayout);
                createInventory();
                loadChat();
            }
        }
    }

    private void takeVirginity() {
        isVirgin = false;
    }

    // TODO Kommentare schreiben
    private void initializeKickSubscriber() {
        kickUsers.subscribe(message -> getUI().ifPresent(ui -> ui.access(() -> {
            if (message.getUser().getUserId().equals(VaadinSession.getCurrent().getAttribute(User.class).getUserId())) {
                if ("KICK".equals(message.getKick())) {
                    myAvatar = null;
                    Span label = new Span(res.getString("view.game.notification.kicked"));
                    showErrorNotification(label);
                    myAvatarDialog.close();
                    UI.getCurrent().navigate("aboutUs");
                }
            }
        })));
    }


    void initiateGameView() {
        binTitle = new H2(res.getString("view.game.headline"));
        aboutText = MessageFormat.format(res.getString("view.game.text"), myDungeon.getDungeonName(), myDungeon.getCommandSymbol());
        html = new Html(aboutText);

        myDungeonChatView = new ChatView(messages);
        gameLayout = new HorizontalLayout();
        gameSplitLayout = new SplitLayout();
        gameFirstLayout = new VerticalLayout();
        gameSecondLayout = new VerticalLayout();
        mapLayout = new VerticalLayout();
        gridLayout = new HorizontalLayout();
        gridLayoutVert = new VerticalLayout();

        //FilterButtons
        HorizontalLayout filterButtonsLayout = new HorizontalLayout();
        Label s = new Label(res.getString("view.game.label.filter"));
        s.getStyle().set("padding-top", "8px");
        filterActionButton = new Button(res.getString("view.game.button.filter.actions"));
        filterActionButton.addClickListener(e -> {
            myDungeonChatView.setFilterModeAction();
            filterActionButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
            filterChatButton.removeThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
            resetFilterButton.removeThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
        });

        filterChatButton = new Button(res.getString("view.game.button.filter.messages"));
        filterChatButton.addClickListener(e -> {
            myDungeonChatView.setFilterModeChat();
            filterChatButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
            resetFilterButton.removeThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
            filterActionButton.removeThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
        });
        resetFilterButton = new Button(res.getString("view.game.button.filter.all"));
        resetFilterButton.addClickListener(e -> {
            myDungeonChatView.setFilterModeAll();
            resetFilterButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
            filterActionButton.removeThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
            filterChatButton.removeThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
        });

        filterButtonsLayout.add(s, resetFilterButton, filterChatButton, filterActionButton);


        leftDungeonButt = new Button(res.getString("view.game.button.leave.dungeon"));
        leftDungeonButt.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        leftDungeonButt.addClickListener(e -> {
            UI.getCurrent().navigate("lobby");
        });

        textField = new TextField();
        textField.setWidthFull();

        confirmButt = new Button(res.getString("view.game.button.submit"));
        confirmButt.addClickShortcut(Key.ENTER);
        confirmButt.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        confirmButt.addClickListener(e -> {
            //Parser wird mit Texteingabe aufgerufen
            try {
                if (textField.getValue() != "") {
                    UserMessage um = myParserService.parseCommand(textField.getValue(), dungeonId, myAvatar, currentUser);
                    String message = transProv.getUserMessage(um, VaadinSession.getCurrent().getLocale());
                    myDungeonChatView.addMessage(new Paragraph(new Html(message)));
                    if (um.getKey() != null) {
                        switch (um.getKey()) {
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
                            case "view.game.ctrl.cmd.take":
                            case "view.game.ctrl.cmd.drop":
                            case "view.game.ctrl.cmd.consume":
                            case "view.game.ctrl.cmd.equip.already.equipped":
                            case "view.game.ctrl.cmd.equip":
                            case "view.game.ctrl.cmd.laydown":
                                refreshInventory();
                            default:
                                break;
                        }
                    }
                }
                textField.setValue("");
                textField.focus();
            } catch (CmdScannerAlreadyRequestedException alreadyRequested) {
                showParseErrorNotification(new Span(transProv.getUserMessage(alreadyRequested.getUserMessage(), VaadinSession.getCurrent().getLocale())));
            } catch (CmdScannerRecipientOfflineException recipientOffline) {
                showParseErrorNotification(new Span(transProv.getUserMessage(recipientOffline.getUserMessage(), VaadinSession.getCurrent().getLocale())));
            } catch (CmdScannerInvalidRecipientException invalidRecipient) {
                showParseErrorNotification(new Span(transProv.getUserMessage(invalidRecipient.getUserMessage(), VaadinSession.getCurrent().getLocale())));
            } catch (CmdScannerInsufficientPermissionException insufficientPermissions) {
                showParseErrorNotification(new Span(transProv.getUserMessage(insufficientPermissions.getUserMessage(), VaadinSession.getCurrent().getLocale())));
            } catch (CmdScannerInvalidItemTypeException invalidItemType) {
                showParseErrorNotification(new Span(transProv.getUserMessage(invalidItemType.getUserMessage(), VaadinSession.getCurrent().getLocale())));
            } catch (CmdScannerInvalidParameterException invalidParameter) {
                showParseErrorNotification(new Span(transProv.getUserMessage(invalidParameter.getUserMessage(), VaadinSession.getCurrent().getLocale())));
            } catch (CmdScannerSyntaxMissingException syntaxMissing) {
                showParseErrorNotification(new Span(transProv.getUserMessage(syntaxMissing.getUserMessage(), VaadinSession.getCurrent().getLocale())));
            } catch (CmdScannerSyntaxUnexpectedException syntaxUnexpected) {
                showParseErrorNotification(new Span(transProv.getUserMessage(syntaxUnexpected.getUserMessage(), VaadinSession.getCurrent().getLocale())));
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

        gameFirstLayout.add(binTitle, html, filterButtonsLayout, myDungeonChatView, insertInputLayout);
        gameSecondLayout.add(mapLayout, gridLayoutVert, leftDungeonButt);
        mapLayout.setClassName("map-layout");
        gridLayoutVert.setClassName("grid-layout");
        expand(mapLayout);
        expand(myDungeonChatView);
        add(gameLayout);

        setSizeFull();
    }

    void createAvatarDialog() {
        HorizontalLayout buttLayout;
        VerticalLayout gridLayoutVert;
        H2 title;

        myAvatarDialog = new Dialog();
        myAvatarDialog.open();

        myAvatarDialog.setCloseOnEsc(false);
        myAvatarDialog.setCloseOnOutsideClick(false);
        buttLayout = new HorizontalLayout();
        gridLayoutVert = new VerticalLayout();

        // Header
        title = new H2(res.getString("view.game.headline.avatar.selection"));
        Header header = new Header(title);

        avatarGrid = new Grid<>();
        refreshAvatarGrid();
        avatarGrid.setVerticalScrollingEnabled(true);
        avatarGrid.addColumn(Avatar::getName).setHeader(res.getString("view.game.grid.avatar"));
        avatarGrid.addColumn(avatar -> {
            if (avatar.getRace() != null)
                return avatar.getRace().getRaceName();
            return null;
        }).setHeader(res.getString("view.game.grid.race"));
        avatarGrid.addColumn(avatar -> {
            if (avatar.getRole() != null)
                return avatar.getRole().getRoleName();
            return null;
        }).setHeader(res.getString("view.game.grid.role"));
        avatarGrid.addColumn(avatar -> {
            if (avatar.getCurrentRoom() != null)
                return avatar.getCurrentRoom().getRoomName();
            return null;
        }).setHeader(res.getString("view.game.grid.room"));
        avatarGrid.addComponentColumn(item -> createDeleteAvatarButton(item)).setHeader(res.getString("view.game.grid.button.delete"));
        gridLayoutVert.add(avatarGrid);

        // Footer
        Button cancel = new Button(res.getString("view.game.grid.button.leave.dungeon.confirm"), e -> {
            myAvatarDialog.close();
            UI.getCurrent().navigate("lobby");
        });
        cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

        Button createAvatar = new Button(res.getString("view.game.grid.button.new.avatar"), e -> {
            createNewAvatarDialog();
        });
        createAvatar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        createAvatar.focus();

        Button enterDungeon = new Button(res.getString("view.game.grid.button.enter.dungeon"), e -> {
            currentUser = myGameService.getUser(VaadinSession.getCurrent().getAttribute(User.class).getUserId());
            Set<Avatar> selectedAvatar = avatarGrid.getSelectedItems();
            if (selectedAvatar.size() > 0) {
                selectedInDialogAvatar = myGameService.getAvatarById(((Avatar) selectedAvatar.toArray()[0]).getAvatarId());
                //myGameService.addActivePlayer();
                myAvatarDialog.close();
                textField.focus();
                loadAvatarProgress(selectedInDialogAvatar);
                createMap();
                changeRoom(currentRoom.getRoomId());
                loadChat();
            } else {
                Span label = new Span(res.getString("view.game.notification.select.avatar"));
                showErrorNotification(label);
            }
        });

        buttLayout.add(cancel, createAvatar, enterDungeon);
        buttLayout.setWidthFull();

        // Add it all together
        myAvatarDialog.add(header, gridLayoutVert, buttLayout);
        myAvatarDialog.setWidth("50%");
    }

    private Button createDeleteAvatarButton(Avatar AAvatar) {
        Button deleteAvatarButt = new Button("", clickEvent -> {

            Dialog confirmDeleteDialog = new Dialog();
            H4 title = new H4(res.getString("view.game.headline.delete.avatar"));
            Text text = new Text(MessageFormat.format(res.getString("view.game.text.delete.avatar"), AAvatar.getName()));
            Button deleteAnywayButt = new Button(res.getString("view.game.button.delete.avatar"));
            deleteAnywayButt.getStyle().set("color", "red");
            deleteAnywayButt.addClickListener(e -> {
                myGameService.deleteAvatar(myDungeon.getDungeonId(), currentUser.getUserId(), AAvatar.getAvatarId());
                refreshAvatarGrid();
                confirmDeleteDialog.close();
            });

            Button cancelButt = new Button(res.getString("view.game.button.cancel"));
            cancelButt.addClickShortcut(Key.ENTER);
            cancelButt.addClickListener(e -> {
                confirmDeleteDialog.close();
            });

            confirmDeleteDialog.add(title, text, new HorizontalLayout(deleteAnywayButt, cancelButt));
            confirmDeleteDialog.open();
        });

        Icon iconDeleteAvatarButton = new Icon(VaadinIcon.CLOSE_BIG);
        deleteAvatarButt.setIcon(iconDeleteAvatarButton);
        deleteAvatarButt.getStyle().set("color", "red");

        return deleteAvatarButt;
    }

    private void createNewAvatarDialog() {
        VerticalLayout contentLayout;
        HorizontalLayout buttCreateLayout;
        H2 title;

        myCreateAvatarDialog = new Dialog();
        myCreateAvatarDialog.open();

        myCreateAvatarDialog.setCloseOnEsc(false);
        myCreateAvatarDialog.setCloseOnOutsideClick(false);

        contentLayout = new VerticalLayout();
        buttCreateLayout = new HorizontalLayout();

        // Header
        title = new H2(res.getString("view.game.headline.create.avatar"));
        Header header = new Header(title);
        Text description = new Text(res.getString("view.game.text.create.avatar"));

        // Avatar Felder
        TextField avatarNameFiled = new TextField(res.getString("view.game.textfield.avatarname"));
        avatarNameFiled.addValueChangeListener(e -> avatarNameFiled.setInvalid(false));
        avatarNameFiled.focus();

        List<Gender> avatarGenderList = new ArrayList<>(Arrays.asList(Gender.values()));
        ComboBox<Gender> avatarGenderField = new ComboBox<>(res.getString("view.game.combobox.gender"));
        avatarGenderField.setItems(avatarGenderList);
        avatarGenderField.addValueChangeListener(e -> avatarGenderField.setInvalid(false));

        List<Role> avatarRoleList = myDungeon.getRoles();
        ComboBox<Role> avatarRoleField = new ComboBox(res.getString("view.game.combobox.role"));
        avatarRoleField.setItems(avatarRoleList);
        avatarRoleField.setItemLabelGenerator(Role::getRoleName);
        avatarRoleField.addValueChangeListener(e -> avatarRoleField.setInvalid(false));

        List<Race> avatarRaceList = myDungeon.getRaces();
        ComboBox<Race> avatarRaceField = new ComboBox(res.getString("view.game.combobox.race"));
        avatarRaceField.setItems(avatarRaceList);
        avatarRaceField.setItemLabelGenerator(Race::getRaceName);
        avatarRaceField.addValueChangeListener(e -> avatarRaceField.setInvalid(false));

        Button cancelButt = new Button(res.getString("view.game.button.cancel"), e -> myCreateAvatarDialog.close());
        cancelButt.getStyle().set("color", "red");
        Button createAvatarButt = new Button(res.getString("view.game.button.save"));

        createAvatarButt.addClickListener(e -> {
            if (!myGameService.avatarNameIsValid(myDungeon.getDungeonId(), avatarNameFiled.getValue())) {
                avatarNameFiled.setInvalid(true);
                showErrorNotification(new Span(res.getString("view.game.notification.forgot.name")));
            } else {
                if (!myGameService.avatarGenderIsValid(avatarGenderField.getValue())) {
                    avatarGenderField.setInvalid(true);
                    showErrorNotification(new Span(res.getString("view.game.notification.forgot.gender")));
                } else {
                    if (!myGameService.avatarRoleIsValid(avatarRoleField.getValue())) {
                        avatarRoleField.setInvalid(true);
                        showErrorNotification(new Span(res.getString("view.game.notification.forgot.role")));
                    } else {
                        if (!myGameService.avatarRaceIsValid(avatarRaceField.getValue())) {
                            avatarRaceField.setInvalid(true);
                            showErrorNotification(new Span(res.getString("view.game.notification.forgot.race")));
                        } else {
                            Avatar currentAvatar = new Avatar();
                            //Lebenspunkte berechnen also Standartlebenspunkte + Rollenbonus + Rassenbonus
                            currentAvatar.setLifepoints(myDungeon.getStandardAvatarLifepoints(), avatarRaceField.getValue().getLifepointsBonus(), avatarRoleField.getValue().getLifepointsBonus());

                            //Neuen Avatar speichern
                            myGameService.createNewAvatar(myDungeon.getDungeonId(), currentUser.getUserId(), myDungeon.getStartRoomId(),
                                    avatarNameFiled.getValue(), avatarGenderField.getValue(),
                                    avatarRoleField.getValue().getRoleId(), avatarRaceField.getValue().getRaceId(),
                                    currentAvatar.getLifepoints());
                            refreshAvatarGrid();
                            myCreateAvatarDialog.close();
                            Span label = new Span(res.getString("view.game.notification.avatar.saved"));
                            Notification notification = new Notification();
                            Button closeButton = new Button("", es -> {
                                notification.close();
                            });
                            closeButton.setIcon(new Icon(VaadinIcon.CLOSE));
                            closeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

                            notification.add(label, closeButton);
                            label.getStyle().set("margin-right", "0.3rem");
                            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                            notification.setDuration(10000);
                            notification.setPosition(Notification.Position.TOP_END);
                            notification.open();
                        }
                    }
                }
            }
            refreshAvatarGrid();
        });
        createAvatarButt.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        createAvatarButt.focus();
        createAvatarButt.addClickShortcut(Key.ENTER);

        contentLayout.add(header, description, avatarNameFiled, avatarGenderField, avatarRoleField, avatarRaceField);
        buttCreateLayout.add(cancelButt, createAvatarButt);
        myCreateAvatarDialog.add(contentLayout, buttCreateLayout);
    }

    void refreshAvatarGrid() {
        avatarList = new ArrayList<>();
        currentUser = myGameService.getUser(currentUser.getUserId());
        List<Avatar> tempAvatarList = currentUser.getAvatars();
        for (Avatar canAddAvatar : tempAvatarList) {
            try {
                if (canAddAvatar.getDungeon().getDungeonId().equals(myDungeon.getDungeonId())) {
                    avatarList.add(canAddAvatar);
                }
            } catch (Exception e) {
            }
        }
        avatarGrid.setItems(avatarList);
    }

    void loadAvatarProgress(Avatar AAvatar) {
        this.myAvatar = AAvatar;
        this.currentRoom = myAvatar.getCurrentRoom();
        this.visitedRooms = myGameService.attendanceToRooms(myDungeon.getDungeonId(), AAvatar.getAvatarId());
        if (currentRoom == null || myRoomRepo.findByRoomId(currentRoom.getRoomId()) == null) {
            currentRoom = myRoomRepo.findByRoomId(myDungeon.getStartRoomId());
        }
        myGameService.addActivePlayer(myDungeon.getDungeonId(), currentUser.getUserId(), myAvatar.getAvatarId());
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


    VerticalLayout inventoryLayout;
    VerticalLayout armorLayout;

    void createInventory() {
        inventoryLayout = new VerticalLayout();
        Text inventoryTitle = new Text(res.getString("view.game.text.inventory"));

        inventoryGrid = new Grid<>();
        inventoryGrid.setVerticalScrollingEnabled(true);
        inventoryGrid.addColumn(item -> item.getItem().getItemName()).setHeader(res.getString("view.game.grid.itemname"));
        inventoryGrid.addColumn(item -> item.getItem().getType().toString()).setHeader(res.getString("view.game.grid.itemtype"));
        inventoryGrid.addColumn(item -> item.getItem().getSize().toString()).setHeader(res.getString("view.game.grid.itemsize"));
        inventoryGrid.getStyle().set("background", "grey");
        inventoryGrid.setSizeFull();
        inventoryLayout.add(inventoryTitle, inventoryGrid);

        armorLayout = new VerticalLayout();
        Text armorTitle = new Text(res.getString("view.game.text.equipment"));

        armorGrid = new Grid<>();
        armorGrid.setVerticalScrollingEnabled(true);
        armorGrid.addColumn(item -> item.getItem().getItemName()).setHeader(res.getString("view.game.grid.itemname"));
        armorGrid.addColumn(item -> {
            if (item.getItem().getType() != null)
                return item.getItem().getType().toString();
            return null;
        }).setHeader(res.getString("view.game.grid.itemtype"));
        armorGrid.addColumn(item -> {
            if (item.getItem().getSize() != null)
                return item.getItem().getSize().toString();
            return null;
        }).setHeader(res.getString("view.game.grid.itemsize"));
        armorGrid.getStyle().set("background", "grey");
        armorGrid.setSizeFull();
        armorLayout.add(armorTitle, armorGrid);

        gridLayout.add(inventoryLayout, armorLayout);
        gridLayout.setSizeFull();
        gridLayoutVert.add(gridLayout);
        refreshInventory();

        //Timer setzen
        myTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                refreshView();
            }
        }, 0, 2000);//eine Sekunde delay
    }

    void refreshInventory() {
        myAvatar = myGameService.getAvatarById(myAvatar.getAvatarId());
        inventoryList = myAvatar.getInventory();
        inventoryGrid.setItems(inventoryList);

        armorList = myAvatar.getEquipment();
        armorGrid.setItems(armorList);
    }

    void changeRoom(Long ARoomId) {
        currentRoom = myRoomRepo.findByRoomId(ARoomId);
        myAvatar = myGameService.getAvatarById(myAvatar.getAvatarId());
        //Avatar Fortschritt speichern
        visitedRooms = myGameService.saveAvatarProgress(myDungeon.getDungeonId(), myAvatar.getAvatarId(), currentRoom.getRoomId());//Liste updaten
        //Kartenanzeige aktualisieren
        updateMap();
        if (inventoryGrid != null && armorGrid != null)
            refreshInventory();
    }

    void updateMap() {
        Lock l=new ReentrantLock();
        l.lock();
        try {
            //alle Stellen des Dungeons schwarz f??rben, die noch nicht durch den Avatar erforscht wurden
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

            //alle anderen R??ume roten Rand abw??hlen

            for (Image[] tL : myTiles) {
                for (Image t : tL) {
                    t.getStyle().set("width", "100px");
                    t.getStyle().set("height", "100px");
                    t.getStyle().set("border-style", "none");
                    t.getStyle().set("border-color", "inherit");
                }
            }

            //aktuellen Raum roten Rand anw??hlen
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
        finally {
            l.unlock();
        }
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent event) {
        if (this.hasChanges()) {
            action =
                    event.postpone();
            confirmLeaveDialog();
            Span label = new Span(res.getString("view.game.notification.already.leaving"));
            Notification notification = new Notification();
            Button closeButton = new Button("", e -> {
                notification.close();
            });
            closeButton.setIcon(new Icon(VaadinIcon.CLOSE));
            closeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

            notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
            notification.add(label, closeButton);
            label.getStyle().set("margin-right", "0.3rem");
            notification.setDuration(10000);
            notification.setPosition(Notification.Position.TOP_END);
            notification.open();
        }
    }

    private boolean hasChanges() {
        return myAvatar != null;
    }

    void confirmLeaveDialog() {
        myConfirmLeavingDialog = new Dialog();
        myConfirmLeavingDialog.open();

        myConfirmLeavingDialog.setCloseOnEsc(false);
        myConfirmLeavingDialog.setCloseOnOutsideClick(false);

        // Header
        H4 title = new H4(res.getString("view.game.headline.already.leaving"));

        HorizontalLayout buttLayout = new HorizontalLayout();

        Button leaveButt = new Button(res.getString("view.game.button.leave.dungeon"));
        leaveButt.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        leaveButt.addClickListener(e -> {
            myGameService.removeActivePlayer(myDungeon.getDungeonId(), currentUser.getUserId(), myAvatar.getAvatarId(), false);
            myConfirmLeavingDialog.close();
            action.proceed();
        });

        Button stayButt = new Button(res.getString("view.game.button.back"));
        stayButt.addClickListener(e -> myConfirmLeavingDialog.close());
        stayButt.focus();
        stayButt.addClickShortcut(Key.ENTER);
        buttLayout.add(leaveButt, stayButt);

        myConfirmLeavingDialog.add(title, buttLayout);
    }

    private void showParseErrorNotification(Span ALabel) {
        Notification notification = new Notification();
        Button closeButton = new Button("", e -> {
            notification.close();
        });
        closeButton.setIcon(new Icon(VaadinIcon.CLOSE));
        closeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        notification.add(ALabel, closeButton);
        ALabel.getStyle().set("margin-right", "0.3rem");
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.setDuration(10000);
        notification.setPosition(Notification.Position.BOTTOM_CENTER);
        notification.open();
    }

    private void showErrorNotification(Span ALabel) {
        Notification notification = new Notification();
        Button closeButton = new Button("", e -> {
            notification.close();
        });
        closeButton.setIcon(new Icon(VaadinIcon.CLOSE));
        closeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        notification.add(ALabel, closeButton);
        ALabel.getStyle().set("margin-right", "0.3rem");
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.setDuration(10000);
        notification.setPosition(Notification.Position.TOP_END);
        notification.open();
    }

    @Override
    public String getPageTitle() {
        return res.getString("view.game.pagetitle");
    }

    /**
     * Der Chat wird aktiviert. Ohne diese Methode w??rde der Chat nicht direkt automatisch Nachrichten laden.
     */
    public void loadChat() {
        String greetingMessage = MessageFormat.format(res.getString("view.game.greeting"), selectedInDialogAvatar.getName());
        messagesPublisher.onNext(new ChatMessage(new Paragraph(new Html(greetingMessage)), greetingMessage, currentUser.getUserId()));
        confirmButt.clickInClient();
        resetFilterButton.clickInClient();
    }

    private void refreshView() {
        //wird dem Timer nach aufgerufen, sodass der DungeonMaster das Inventar des Spielers aktualisieren kann
        if (myGameService.getDungeonStatus(dungeonId).equals(Status.INACTIVE)) {
            Dialog dialog = new Dialog();
            VerticalLayout vl = new VerticalLayout();
            Label dungeonMasterLeft = new Label(res.getString("view.game.label.dm.left"));
            Button closeButton = new Button(res.getString("view.game.button.close"), es -> dialog.close());
            vl.add(dungeonMasterLeft, closeButton);
            closeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            myAvatar = null;
            getUI().ifPresent(ui -> ui.access(() -> {
                ui.navigate("lobby");
                dialog.add(vl);
                dialog.open();
            }));
            return;
        }
        try {
            if (myAvatar != null) {
                getUI().ifPresent(ui -> ui.access(() ->
                        {
                            refreshInventory();
                        }
                ));
            }
        } catch (Exception e) {
        }
    }
}