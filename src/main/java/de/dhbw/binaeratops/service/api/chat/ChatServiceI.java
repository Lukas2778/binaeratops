package de.dhbw.binaeratops.service.api.chat;

import de.dhbw.binaeratops.model.entitys.User;

import java.util.List;

/**
 * Interface für die Komponente "ChatService".
 * <p>
 * Dieser Service stellt alle Funktionalitäten zum Versenden von Chatnachrichten bereit.
 * </p>
 * <p>
 * Für Implementierung dieser Komponente siehe @{@link de.dhbw.binaeratops.service.impl.chat.ChatService}.
 * </p>
 *
 * @author Timon Gartung, Pedro Treuer
 */
public interface ChatServiceI {
    /**
     * Nachricht an eine bestimmte Gruppe von Benutzern.
     *
     * @param AMessage      Nachricht.
     * @param AReceiverList Liste von Empfängern der Nachricht.
     */
    public void sendMessage(String AMessage, List<User> AReceiverList);

    /**
     * Nachricht an einen Benutzer.
     *
     * @param AMessage  TextNachricht.
     * @param AReceiver Empfänger der Nachricht.
     */
    public void sendMessage(String AMessage, User AReceiver);

}
