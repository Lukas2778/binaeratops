package de.dhbw.binaeratops.view.chat;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
@CssImport("./views/game/game-view.css")
public class ChatView extends VerticalLayout {
    enum FilterMode{
        All,
        CHAT,
        ACTIONS
    }

    private final Flux<ChatMessage> messages;

    public MessageListView messageList;
    public MessageListView allMessagesList;
    public MessageListView chatMessageslist;
    public MessageListView actionMessagesList;

    private FilterMode filterMode;

    /**
     * Konstruktor des Chats.
     *
     * @param AMessage Subscriber der die neuen Nachrichten von der Logik holt.
     */
    public ChatView(Flux<ChatMessage> AMessage) {
        this.messages = AMessage;
        filterMode = FilterMode.All;
        addClassName("chat-component");
        showChat();
    }

    /**
     * Initialisierung des Chats.
     */
    private void showChat() {
        messageList = new MessageListView();
        allMessagesList = new MessageListView();
        chatMessageslist = new MessageListView();
        actionMessagesList = new MessageListView();
        add(messageList);
        expand(messageList);

        UI.getCurrent().getPushConfiguration().setPushMode(PushMode.AUTOMATIC);

        //Subscriber: Vaadin - Websocketverbindung wird aufgebaut und bei neuen Nachrichten wird der Chat aktualisiert.
        messages.subscribe(message -> getUI().ifPresent(ui -> ui.access(() -> {
            User currentUser = VaadinSession.getCurrent().getAttribute(User.class);
            //Prüfung ob der Spieler die Nachricht erhalten darf.
            if (message.getUserIdList().contains(currentUser.getUserId())) {
                if (message.isChatMessage()){
                    chatMessageslist.add(message.getParagraph());
                    allMessagesList.add(message.getParagraph());
                    if (filterMode == FilterMode.All || filterMode == FilterMode.CHAT){
                        messageList.add(message.getParagraph());
                    }
                }else {
                    actionMessagesList.add(new Paragraph(message.getText()));
                    allMessagesList.add(new Paragraph(message.getText()));
                    if (filterMode == FilterMode.All || filterMode == FilterMode.ACTIONS){
                        messageList.add(new Paragraph(message.getText()));
                    }
                }
                UI.getCurrent().getPushConfiguration().setPushMode(PushMode.MANUAL);
                ui.push();
                UI.getCurrent().getPushConfiguration().setPushMode(PushMode.AUTOMATIC);
            }
        })));
    }

    /**
     * Schaltet alle Filter aus
     */
    public void setFilterModeAll(){
        getUI().ifPresent(ui ->ui.access(()->{
            filterMode = FilterMode.All;
            messageList = allMessagesList;
        }));
    }

    /**
     * Setzt Filter nach Chatnachrichten.
     */
    public void setFilterModeChat(){
        getUI().ifPresent(ui ->ui.access(()->{
            filterMode = FilterMode.CHAT;
            messageList = chatMessageslist;
        }));
    }

    /**
     * Setzt Filter nach Aktionsnachrichten.
     */
    public void setFilterModeAction(){
        getUI().ifPresent(ui ->ui.access(()->{
            filterMode = FilterMode.ACTIONS;
            messageList = actionMessagesList;
        }));
    }
}
