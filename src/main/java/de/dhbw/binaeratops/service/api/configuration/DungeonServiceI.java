package de.dhbw.binaeratops.service.api.configuration;

import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;

import java.util.List;

public interface DungeonServiceI {
    /**
     * Sucht in der Datenbank nach allen Dungeons, die ein Benutzer erstellt hat.
     * @param AUser Benutzer.
     * @return Liste von Dungeons, die der übergebene Benutzer erstellt hat.
     */
    List<Dungeon> getAllDungeonsFromUser(User AUser);

    /**
     * Sucht nach Dungeons, die dem Benutzer in der Lobby-Ansicht angezeigt werden sollen.
     * @param AUser Benutzer.
     * @return Liste der Dungeons, die dem übergebenen Benutzer angezeigt werden sollen.
     */
    List<Dungeon> getDungeonsLobby(User AUser);

    /**
     * setzt einen Dungeon auf aktiv.
     * @param ADungeonId Id des Dungeon.
     */
    void activateDungeon(long ADungeonId);

    /**
     * setzt einen Dungeon auf inaktiv.
     * @param ADungeonId Id des Dungeon.
     */
    void deactivateDungeon(long ADungeonId);
}

