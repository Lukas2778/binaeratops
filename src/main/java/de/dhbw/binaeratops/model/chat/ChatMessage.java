package de.dhbw.binaeratops.model.chat;

import java.util.List;

/**
 * Klasse für eine Chat-Nachricht.
 *
 * @author Pedro Treuer, Timon Gartung
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

    /**
     * Gibt die Textnachricht zurück.
     *
     * @return Textnachrciht.
     */
    public String getMessage() {
        return message;
    }

    // TODO Duplikat -> entfernen.
    public String getMessages() {
        return message;
    }

    /**
     * Gibt die Liste der Benutzer zurück, an welche die Chat-Nachricht geschickt werden soll.
     *
     * @return Liste der Benutzer, an welche die Chat-Nachricht geschickt werden soll.
     */
    public List<Long> getUserIdList() {
        return userIdList;
    }
}
