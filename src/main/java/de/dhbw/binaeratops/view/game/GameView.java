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
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.api.AvatarI;
import de.dhbw.binaeratops.model.chat.ChatMessage;
import de.dhbw.binaeratops.model.entitys.Avatar;
import de.dhbw.binaeratops.model.entitys.Item;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
import de.dhbw.binaeratops.service.api.parser.ParserServiceI;
import de.dhbw.binaeratops.service.api.map.MapServiceI;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerSyntaxMissingException;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerSyntaxUnexpectedException;
import de.dhbw.binaeratops.service.impl.parser.UserMessage;
import de.dhbw.binaeratops.view.chat.ChatView;
import de.dhbw.binaeratops.view.map.MapView;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Oberfläche des Tabs 'Über uns'
 */
//@Route(value = "game")
@CssImport("./views/game/game-view.css")
@PageTitle("Dungeon - Spiel")
public class GameView extends VerticalLayout implements HasUrlParameter<Long> {
    ParserServiceI myParserService;
    MapServiceI mapServiceI;
    MapView mapView;
    Image[][] tiles;
    private final ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());

    Long dungeonId;
    private final Flux<ChatMessage> messages;

    H2 binTitle;
    String aboutText;
    Html html;
    HorizontalLayout gameLayout;
    SplitLayout gameSplitLayout;

    VerticalLayout gameFirstLayout;
    VerticalLayout gameSecondLayout;

    ChatView myDungeonChatView;

    HorizontalLayout insertInputLayout;
    TextField textField;
    Button confirmButt;

    private List<Item> inventoryList;
    private List<Item> armorList;

    /**
     * Konstruktor zum Erzeugen der View für den Tab 'Über uns'.
     * @param messages Nachrichten.
     * @param AParserService ParserService.
     */
    public GameView(Flux<ChatMessage> messages, @Autowired ParserServiceI AParserService, @Autowired MapServiceI AMapService) {
        myParserService=AParserService;
        mapServiceI=AMapService;

        this.messages = messages;

        binTitle=new H2("Du bist in der Spieloberfläche!");
        aboutText= "<div>Du hast auf einen aktiven Dungeon geklickt und kannst hier Teile des Chats und des Parsers" +
                " testen.<br>Schau dir zuerst die 'Help' an, indem du /help eingibst.</div>";
        html=new Html(aboutText);

        myDungeonChatView =new ChatView(messages);
        gameLayout=new HorizontalLayout();
        gameSplitLayout=new SplitLayout();

        gameFirstLayout = new VerticalLayout();
        gameSecondLayout = new VerticalLayout();

        textField=new TextField();
        textField.focus();
        textField.setWidthFull();

        confirmButt=new Button("Eingabe");
        confirmButt.addClickShortcut(Key.ENTER);
        AvatarI myAvatar=new Avatar();
        confirmButt.addClickListener(e->{
            //Parser wird mit Texteingabe aufgerufen
            try {
                UserMessage um = myParserService.parseCommand(textField.getValue(), dungeonId, myAvatar, VaadinSession.getCurrent().getAttribute(User.class));
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
            } catch (CmdScannerException cmdScannerException) {
                cmdScannerException.printStackTrace();
            } catch (InvalidImplementationException invalidImplementationException) {
                invalidImplementationException.printStackTrace();
            }
        });
        confirmButt.setWidth("120px");

        insertInputLayout=new HorizontalLayout();
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
        add(binTitle, html, gameLayout);
        expand(myDungeonChatView);
        setSizeFull();
    }

    void createMap(Long ALong) {
        mapView=new MapView();
        VerticalLayout mapLayout=new VerticalLayout();
        mapLayout.add(mapView.initMap(mapServiceI,dungeonId, tiles));
        gameSecondLayout.add(mapLayout);
        createInventory();
    }

    void createInventory(){
        HorizontalLayout gridLayout =new HorizontalLayout();

        VerticalLayout inventoryLayout = new VerticalLayout();
        Text inventoryTitle = new Text("Inventar");
        inventoryList=new ArrayList<>();
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
        armorList=new ArrayList<>();
        Grid<Item> armorGrid = new Grid<>();
        armorGrid.setItems(armorList);
        armorGrid.setVerticalScrollingEnabled(true);
        armorGrid.addColumn(Item::getItemName).setHeader("Item");
        armorGrid.addColumn(Item::getType).setHeader("Typ");
        armorGrid.addColumn(Item::getSize).setHeader("Größe");
        armorGrid.getStyle().set("background", "grey");
        armorGrid.setSizeFull();
        armorLayout.add(armorTitle,armorGrid);

        gridLayout.add(inventoryLayout, armorLayout);
        gridLayout.setSizeFull();

        Button leftDungeonButt=new Button("Dungeon verlassen");
        leftDungeonButt.getStyle().set("color" , "red");

        gameSecondLayout.add(gridLayout, leftDungeonButt);
    }

    @Override
    public void setParameter(BeforeEvent ABeforeEvent, Long ALong) {
        dungeonId=ALong;
        createMap(dungeonId);
    }
}