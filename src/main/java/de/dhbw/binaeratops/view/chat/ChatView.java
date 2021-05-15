package de.dhbw.binaeratops.view.chat;

import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.VaadinSession;
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

        //Subscriber: Vaadin - Websocketverbindung wird aufgebaut und bei neuen Nachrichten wird der Chat aktualisiert.
        messages.subscribe(message -> getUI().ifPresent(ui -> ui.access(() -> {
            //Prüfung ob der Spieler die Nachricht erhalten darf.
            if (message.getUserIdList().contains(VaadinSession.getCurrent().getAttribute(User.class).getUserId())) {
                messageList.add(new Paragraph(message.getMessage()));
            }
        })));
    }
}
