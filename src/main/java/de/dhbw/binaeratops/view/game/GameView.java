package de.dhbw.binaeratops.view.game;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
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
import de.dhbw.binaeratops.model.api.AvatarI;
import de.dhbw.binaeratops.model.chat.ChatMessage;
import de.dhbw.binaeratops.model.entitys.Avatar;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
import de.dhbw.binaeratops.model.map.Tile;
import de.dhbw.binaeratops.service.api.map.MapServiceI;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;
import de.dhbw.binaeratops.service.impl.parser.ParserService;
import de.dhbw.binaeratops.service.impl.parser.UserMessage;
import de.dhbw.binaeratops.view.chat.Chat;
import de.dhbw.binaeratops.view.map.MapView;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Oberfläche des Tabs 'Über uns'
 */
@Route(value = "gameView")
@CssImport("./views/game/game-view.css")
@PageTitle("Dungeon - Spiel")
public class GameView extends VerticalLayout implements HasUrlParameter<Long> {
    ParserService myParserService;
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

    Chat myDungeonChat;

    HorizontalLayout insertInputLayout;
    TextField textField;
    Button confirmButt;

    /**
     * Konstruktor zum Erzeugen der View für den Tab 'Über uns'.
     * @param messages
     */
    public GameView(Flux<ChatMessage> messages, @Autowired ParserService AParserService, @Autowired MapServiceI AMapService) {
        myParserService=AParserService;
        mapServiceI=AMapService;

        this.messages = messages;

        binTitle=new H2("Du bist in der Spieloberfläche!");
        aboutText= "<div>Du hast auf einen aktiven Dungeon geklickt und kannst hier Teile des Chats und des Parsers" +
                " testen.<br>Schau dir zuerst die 'Help' an, indem du /help eingibst.</div>";
        html=new Html(aboutText);

        gameLayout=new HorizontalLayout();
        gameSplitLayout=new SplitLayout();

        myDungeonChat=new Chat(messages);

        textField=new TextField();
        textField.focus();
        confirmButt=new Button("Eingabe");
        confirmButt.addClickShortcut(Key.ENTER);
        AvatarI myAvatar=new Avatar();
        confirmButt.addClickListener(e->{
            //Parser wird mit Texteingabe aufgerufen
            try {
                UserMessage um=myParserService.parseCommand(textField.getValue(),dungeonId,myAvatar, VaadinSession.getCurrent().getAttribute(User.class));
                if(um.getKey()!=null) {
                    switch (um.getKey()) {
                        case "view.game.ingame.cmd.notify.all":
                            myDungeonChat.messageList.add(new Paragraph(MessageFormat.format(res.getString(um.getKey()), um.getParams().get(0))));
                            break;
                        case "view.game.cmd.help":
                            myDungeonChat.messageList.add(new Paragraph(new Html(MessageFormat.format(res.getString(um.getKey()), um.getParams().get(0)))));
                            break;
                        case "view.game.cmd.help.all":
                            myDungeonChat.messageList.add(new Paragraph(new Html(MessageFormat.format(res.getString(um.getKey()), um.getParams().get(0)))));
                            break;
                        case "view.game.cmd.help.cmds":
                            myDungeonChat.messageList.add(new Paragraph(new Html(MessageFormat.format(res.getString(um.getKey()), um.getParams().get(0)))));
                            break;
                        case "view.game.cmd.help.ctrl":
                            myDungeonChat.messageList.add(new Paragraph(new Html(res.getString(um.getKey()))));
                            break;
                        default:
                            Notification.show("An Error Occured.");
                            break;
                    }
                }
            } catch (CmdScannerException | InvalidImplementationException cmdScannerException) {
                cmdScannerException.printStackTrace();
            }
        });
        insertInputLayout=new HorizontalLayout();
        insertInputLayout.add(textField, confirmButt);
        insertInputLayout.setSizeFull();

        gameFirstLayout = new VerticalLayout();
        //VerticalLayout gameSecondLayout = new VerticalLayout();

        gameFirstLayout.setSizeFull();
        //gameSecondLayout.setSizeFull();

        gameFirstLayout.add(myDungeonChat, insertInputLayout);
        //gameSecondLayout.add(initMap(dungeonId));

        gameLayout.add(gameSplitLayout);
        gameLayout.setSizeFull();
        add(binTitle, html, gameLayout);
        expand(myDungeonChat);
        setSizeFull();
    }

    void createMap(Long ALong) {
        mapView=new MapView();
        gameSplitLayout.addToPrimary(gameFirstLayout);
        gameSplitLayout.addToSecondary(mapView.initMap(mapServiceI,dungeonId, tiles));
        gameSplitLayout.setSizeFull();
    }

    @Override
    public void setParameter(BeforeEvent ABeforeEvent, Long ALong) {
        dungeonId=ALong;
        createMap(dungeonId);
    }
}