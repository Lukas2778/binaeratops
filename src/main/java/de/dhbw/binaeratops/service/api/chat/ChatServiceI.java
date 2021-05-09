package de.dhbw.binaeratops.service.api.chat;

import de.dhbw.binaeratops.model.chat.ChatMessage;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;
import java.util.List;

/**
 * Interface zum versenden von Chatnachrichten.
 */
public interface ChatServiceI
{
    /**
     * Nachricht an eine bestimmte Gruppe von Benutzern.
     * @param AMessage Nachricht.
     * @param AReceiverList Liste von Empfängern der Nachricht.
     */
    public void sendMessage(String AMessage, List<User> AReceiverList);

    /**
     * Nachricht an einen Benutzer.
     * @param AMessage TextNachricht.
     * @param AReceiver Empfänger der Nachricht.
     */
    public void sendMessage(String AMessage, User AReceiver);

}
