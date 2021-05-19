package de.dhbw.binaeratops.service.api.chat;

import de.dhbw.binaeratops.model.entitys.Avatar;
import de.dhbw.binaeratops.model.entitys.Room;
import de.dhbw.binaeratops.model.entitys.User;

import java.util.List;

/**
 * Interface für die Komponente "ChatService".
 * <p>
 * Dieser Service stellt alle Funktionalitäten zum Versenden von Chatnachrichten bereit.
 * </p>
 * <p>
 * Für Implementierung dieser Komponente siehe {@link de.dhbw.binaeratops.service.impl.chat.ChatService}.
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
    void sendMessage(String AMessage, List<User> AReceiverList);

    /**
     * Nachricht an einen Benutzer.
     *
     * @param AMessage  TextNachricht.
     * @param AReceiver Empfänger der Nachricht.
     */
    void sendActionMessage(String AMessage, User AReceiver);

    /**
     * Erstellt die Dungeon-Master-Nachricht und verschickt sie an die angegebenen Benutzern.
     *
     * @param AMessage       Nachricht vom Dungeon-Master.
     * @param AReceiverList  Liste der Empfänger der Nachricht.
     * @param ADungeonMaster Dungeon-Master, der die Nachricht verschickt hat.
     */
    void notifyAll(String AMessage, List<User> AReceiverList, User ADungeonMaster);

    /**
     * Erstellt eine Nachricht vom Dungeon-Master an einen Spieler und verschickt sie.
     *
     * @param AMessage       Nachricht.
     * @param AReceiver      Empfänger der nachricht.
     * @param ADungeonMaster Dungeon-Master, der die Nachricht verschickt hat.
     */
    void whisperDungeonMaster(String AMessage, User AReceiver, User ADungeonMaster);

    /**
     * Erstellt eine Nachricht vom Spieler an einen anderen Spieler oder Dungeon-Master und verschickt sie.
     *
     * @param AMessage  Nachricht.
     * @param AReceiver Empfänger der Nachricht.
     * @param AAvatar   Absender der Nachricht.
     */
    void whisper(String AMessage, User AReceiver, Avatar AAvatar);

    /**
     * Nachricht für den Raumchat wird erstellt und verschickt.
     *
     * @param AMessage      Nachricht.
     * @param AReceiverList Benutzer, die die Nachricht erhalten sollen.
     * @param AAvatar       Absender der Nachricht.
     * @param ARoom         Raum in dem die Nachricht abgeschickt wurde.
     */
    void sendRoomMessage(String AMessage, List<User> AReceiverList, Avatar AAvatar, Room ARoom);

    /**
     * Nachricht vom Dungeon-Master für den Raumchat wird erstellt und verschickt.
     * @param AMessage Nachricht.
     * @param AReceiverList Liste von Benutzern, die die Nachricht erhalten sollen.
     * @param ADungeonMaster Dungeon-Master, der die Nachricht abschickt.
     * @param ARoom Raum an dem die Nachricht geschickt wird.
     */
    void sendRoomMessage(String AMessage, List<User> AReceiverList, User ADungeonMaster, Room ARoom);


    void whisperFromNpc(String AMessage, User AReceiver, String AAvatarName);
}
