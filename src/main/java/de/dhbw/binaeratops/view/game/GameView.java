package de.dhbw.binaeratops.view.game;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import de.dhbw.binaeratops.service.api.configuration.DungeonServiceI;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;
import de.dhbw.binaeratops.service.impl.parser.ParserService;
import de.dhbw.binaeratops.service.impl.parser.UserMessage;
import de.dhbw.binaeratops.view.chat.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Oberfläche des Tabs 'Über uns'
 */
@Route(value = "game")
@PageTitle("Dungeon - Spiel")
public class GameView extends VerticalLayout implements HasUrlParameter<Long> {
    ParserService myParserService;
    private final ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());

    Long dungeonId;
    private final Flux<ChatMessage> messages;

    H2 binTitle;
    String aboutText;
    Html html;
    Chat myDungeonChat;

    HorizontalLayout insertInputLayout;
    TextField textField;
    Button confirmButt;

    /**
     * Konstruktor zum Erzeugen der View für den Tab 'Über uns'.
     * @param messages
     */
    public GameView(Flux<ChatMessage> messages, @Autowired ParserService AParserService) {
        myParserService=AParserService;

        this.messages = messages;
        binTitle=new H2("Du bist in der Spieloberfläche!");
        aboutText= "<div>Du hast auf einen aktiven Dungeon geklickt und kannst hier Teile des Chats und des Parsers" +
                " testen.<br>Schau dir zuerst die 'Help' an, indem du /help eingibst.</div>";
        html=new Html(aboutText);

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
                    myDungeonChat.messageList.add(new Paragraph(MessageFormat.format(res.getString(um.getKey()), um.getParams())));
                }
            } catch (CmdScannerException cmdScannerException) {
                cmdScannerException.printStackTrace();
            } catch (InvalidImplementationException invalidImplementationException) {
                invalidImplementationException.printStackTrace();
            }
        });
        insertInputLayout=new HorizontalLayout();
        insertInputLayout.add(textField, confirmButt);

        setSizeFull();
        add(binTitle, html, myDungeonChat, insertInputLayout);
    }

    @Override
    public void setParameter(BeforeEvent ABeforeEvent, Long ALong) {
        this.dungeonId=ALong;
    }
}