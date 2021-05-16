package de.dhbw.binaeratops.view.chat;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.shared.communication.PushMode;
import de.dhbw.binaeratops.model.chat.ChatMessage;
import de.dhbw.binaeratops.model.entitys.User;
import reactor.core.publisher.Flux;


/**
 * Oberfläche für die Komponente "Chat".
 * <p>
 * Diese Ansicht stellt alle View-Komponenten für den Chat bereit.
 * <p>
 * Damit können hier alle Chat-Nachrichten in einer eigenen View dargestellt werden.
 *
 * @author Pedro Treuer, Timon Gartung, Nicolas Haug
 */
public class ChatView extends VerticalLayout {
    {
        addClassName("chat-component");
    }

    private final Flux<ChatMessage> messages;
    public MessageListView messageList;

    /**
     * Konstruktor des Chats.
     *
     * @param AMessage Subscriber der die neuen Nachrichten von der Logik holt.
     */
    public ChatView(Flux<ChatMessage> AMessage) {
        this.messages = AMessage;
        showChat();
    }

    /**
     * Initialisierung des Chats.
     */
    private void showChat() {
        messageList = new MessageListView();
        add(messageList);
        expand(messageList);

        UI.getCurrent().getPushConfiguration().setPushMode(PushMode.AUTOMATIC);

        //Subscriber: Vaadin - Websocketverbindung wird aufgebaut und bei neuen Nachrichten wird der Chat aktualisiert.
        messages.subscribe(message -> getUI().ifPresent(ui -> ui.access(() -> {
            User currentUser = VaadinSession.getCurrent().getAttribute(User.class);
            //Prüfung ob der Spieler die Nachricht erhalten darf.
            if (message.getUserIdList().contains(currentUser.getUserId())) {
                messageList.add(new Paragraph(message.getMessage()));
                UI.getCurrent().getPushConfiguration().setPushMode(PushMode.MANUAL);
                ui.push();
                UI.getCurrent().getPushConfiguration().setPushMode(PushMode.AUTOMATIC);
            }
        })));
    }
}
