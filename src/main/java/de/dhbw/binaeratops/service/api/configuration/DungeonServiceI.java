package de.dhbw.binaeratops.service.api.configuration;

import de.dhbw.binaeratops.model.entitys.Avatar;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.Room;
import de.dhbw.binaeratops.model.entitys.User;

import java.util.List;

/**
 * Interface für die Komponente "DungeonService".
 * <p>
 * Dieser Service stellt alle Funktionalitäten zum Umgang mit einem Dungen bereit.
 * </p>
 * <p>
 * Für Implementierung dieser Komponente siehe @{@link de.dhbw.binaeratops.service.impl.configurator.DungeonService}.
 * </p>
 *
 * @author Timon Gartung, Pedro Treuer, Nicolas Haug, Lukas Göpel, Matthias Rall, Lars Rösel
 */
public interface DungeonServiceI {
    /**
     * Sucht in der Datenbank nach allen Dungeons, die ein Benutzer erstellt hat.
     *
     * @param AUser Benutzer.
     * @return Liste von Dungeons, die der übergebene Benutzer erstellt hat.
     */
    List<Dungeon> getAllDungeonsFromUser(User AUser);

    /**
     * Sucht nach Dungeons, die dem Benutzer in der Lobby-Ansicht angezeigt werden sollen.
     *
     * @param AUser Benutzer.
     * @return Liste der Dungeons, die dem übergebenen Benutzer angezeigt werden sollen.
     */
    List<Dungeon> getDungeonsLobby(User AUser);

    /**
     * setzt einen Dungeon auf aktiv.
     *
     * @param ADungeonId Id des Dungeon.
     */
    void activateDungeon(long ADungeonId);

    /**
     * setzt einen Dungeon auf inaktiv.
     *
     * @param ADungeonId Id des Dungeon.
     */
    void deactivateDungeon(long ADungeonId);

    /**
     * Speichert den übergebenen Dungeon.
     *
     * @param ADungeon Dungeon, der gespeichert werden soll.
     */
    void saveDungeon(Dungeon ADungeon);

    /**
     * Speichert den übergebenen Benutzer.
     *
     * @param AUser Benutzer, der gespeichert werden soll.
     */
    void saveUser(User AUser);

    /**
     *
     * @param ADungeonId  ID des Dungeon.
     * @return Liste der activen Spieleravatare.
     */
    List<Avatar> getCurrentAvatars( long ADungeonId);


    /**
     *
     * @param AAvatar den gesucheten Avatar.
     * @return den aktuellen Raum des Avatars.
     */
    Room getRoomOfAvatar ( Avatar AAvatar);

    Room getRoomByPosition(Dungeon ADungeon ,int AX, int AY);

    void setDungeonMaster(Dungeon ADungeon, Long AUserId);

    List<User> getCurrentUsers(Dungeon ADungeon);

    Dungeon getDungeon(Long ADungeonId);

    void kickPlayer(Long ADungeonId, Long AUserId);
}

