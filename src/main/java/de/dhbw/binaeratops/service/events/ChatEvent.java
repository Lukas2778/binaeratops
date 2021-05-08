package de.dhbw.binaeratops.service.events;

import de.dhbw.binaeratops.model.entitys.User;
import java.util.List;

/**
 * Event zur Aktualisierung der Chats.
 */

public class ChatEvent
{
    private String message;

    private List<User> userList;


    /**
     * Konstruktor für das Event.
     * @param AMessage Die neue Nachricht.
     */
    public ChatEvent(String AMessage, List<User> AUserList){
        this.message = AMessage;
        this.userList = AUserList;
    }


    /**
     * Gibt die Nachricht zurück.
     * @return Nachricht.
     */
    public String getMessage(){
        return message;
    }

    public List<User> getUserList()
    {
        return userList;
    }
}
