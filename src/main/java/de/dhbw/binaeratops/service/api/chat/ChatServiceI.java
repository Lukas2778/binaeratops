package de.dhbw.binaeratops.service.api.chat;

import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;
import java.util.List;

public interface ChatServiceI
{

    /**
     * Einen Avatar anflüstern.
     * @param AMessage Nachricht.
     * @param AUserId ID des Spielers.
     */
    public void whisperAvatar(String AMessage, long AUserId);

    /**
     * Diese Methode ist nur vom DungeonMaster ausführbar. Eine Nachricht wird an alle Avatare des Dungeons geschickt.
     * @param AMessage Nachricht.
     */
    public void speakToAllAvatars(String AMessage);

    /**
     * Alle Avatare in einem Raum ansprechen.
     * @param AMessage Nachricht.
     * @param AUserId ID des Spieler.
     */
    public void speak(String AMessage, long AUserId);

}
