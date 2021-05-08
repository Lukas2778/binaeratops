package de.dhbw.binaeratops.service.events;


/**
 * Event zur Aktualisierung der Chats.
 */
public class UpdateChatEvent {
    private String message;

    /**
     * Konstruktor für das Event.
     * @param AMessage Die neue Nachricht.
     */
    public UpdateChatEvent(String AMessage){
        this.message = AMessage;
    }

    /**
     * Gibt die Nachricht zurück.
     * @return Nachricht.
     */
    public String getMessage(){
        return message;
    }
}
