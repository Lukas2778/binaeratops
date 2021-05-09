package de.dhbw.binaeratops.model.chat;
import java.util.List;

/**
 * Klasse für eine Nachricht.
 */
public class ChatMessage {
    private String message;
    private List<Long> userIdList;

    /**
     * Konstruktor der Nachricht.
     *
     * @param message     Textnachricht.
     * @param AUserIdList Liste von Empfänger der Nachricht.
     */
    public ChatMessage(String message, List<Long> AUserIdList) {
        this.message = message;
        this.userIdList = AUserIdList;
    }

    public String getMessage() {
        return message;
    }

    public String getMessages() {
        return message;
    }

    public List<Long> getUserIdList() {
        return userIdList;
    }
}
