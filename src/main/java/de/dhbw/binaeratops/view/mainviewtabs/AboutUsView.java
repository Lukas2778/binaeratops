package de.dhbw.binaeratops.view.mainviewtabs;

//import com.awesomecontrols.subwindow.SubWindow;

import com.sun.xml.bind.v2.TODO;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.chat.ChatMessage;
import de.dhbw.binaeratops.model.chat.MessageList;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.service.api.chat.ChatServiceI;
import de.dhbw.binaeratops.service.api.chat.PlayerServiceI;

import java.util.ArrayList;
import java.util.List;

import de.dhbw.binaeratops.service.events.ChatEvent;
import de.dhbw.binaeratops.service.impl.chat.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

/**
 * Oberfläche des Tabs 'Über uns'
 */
//@Route(value = "aboutUs",layout = MainView.class)

//@PreserveOnRefresh

@PageTitle("Über Uns")
public class AboutUsView extends VerticalLayout {
    H1 binTitle;
    String aboutText;
    Html html;

    MessageList messageList;
    PlayerServiceI playerService;
    ChatServiceI chatService;

    private final Flux<ChatMessage> messages;
    //private final PlayerServiceI APlayerService;
    private final UnicastProcessor publisher;

    /**
     * Konstruktor zum Erzeugen der View für den Tab 'Über uns'.
     */
    public AboutUsView(@Autowired PlayerServiceI APlayerService, @Autowired ChatServiceI AChatService, UnicastProcessor<ChatMessage> publisher, Flux<ChatMessage> messages) {

        this.playerService = APlayerService;
        this.publisher = publisher;
        this.messages = messages;
        this.chatService = AChatService;

        binTitle = new H1("Willkommen bei Binäratops");
        add(binTitle);
        showChat();
        setSizeFull();
    }


    private void showChat() {
        createInputLayout();
        messageList = new MessageList();

       /*SubWindow sw = new SubWindow("sw1");

       sw.setHeight("150px");
      sw.setWidth("300px");*/

        add(messageList, createInputLayout());

        expand(messageList);

        //Subscriber: Beim erhalten einer Nachricht wird geprüft ob der Spieler die Nachricht erhalten darf.
        messages.subscribe(message -> getUI().ifPresent(ui -> ui.access(() -> {
            if (message.getUserIdList().contains(VaadinSession.getCurrent().getAttribute(User.class).getUserId())) {
                messageList.add(new Paragraph(/*message.getFrom() + */": " + message.getMessage()));
            }
        })));
    }

    TextField messageField;

    private Component createInputLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidth("100%");
        messageField = new TextField();
        Button sendButton = new Button("Send");
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button flüsternButton = new Button("Flüstern");
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        layout.add(messageField, sendButton, flüsternButton);
        layout.expand(messageField);


        sendButton.addClickListener(click -> {
            playerService.sendInput(messageField.getValue(), VaadinSession.getCurrent().getAttribute(User.class));
            List<Long> myList = new ArrayList<>();
            myList.add(VaadinSession.getCurrent().getAttribute(User.class).getUserId());
            messageField.clear();
            messageField.focus();
        });

        flüsternButton.addClickListener(click -> {
            //TODO: Funktioniert nicht
            messageList.add(new Paragraph(messageField.getValue()));
            messageField.clear();
            messageField.focus();

        });

        messageField.focus();

        return layout;
    }

    /**
     * Wenn der Chat des Spielers eine neue Nachricht erhält wird dieses Event ausgelöst.
     * Der Chat wird mit der Nachricht ergänzt.
     *
     * @param AEvent Die Nachricht.
     */

    @EventListener()
    public void handleNewMessage(ChatEvent AEvent) {
        messageList.add(new Paragraph(AEvent.getMessage()));

    }


}
