package de.dhbw.binaeratops.service.api.chat;

import de.dhbw.binaeratops.model.chat.ChatMessage;
import de.dhbw.binaeratops.model.entitys.User;
import reactor.core.publisher.Flux;

import java.util.List;

public interface PlayerServiceI
{
    /**
     * Aktualisert den chat des Spielers, falls er die Nachricht erhalten soll.
     * @param AMessage Nachricht.
     * @param AUserList Liste von Spieler, die die Nachricht erhalten sollen.
     */
    public void updateMessageList(String AMessage, List<User> AUserList);

    /**
     * Abschicken der Eingabe des Spielers.
     * @param AMessage Nachricht.
     * @param AUser Absender der Eingabe.
     */
    public void sendInput(String AMessage, User AUser);


}
