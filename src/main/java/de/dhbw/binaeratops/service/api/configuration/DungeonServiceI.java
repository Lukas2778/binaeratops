package de.dhbw.binaeratops.service.api.configuration;

import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;

import java.util.List;

public interface DungeonServiceI {
    List<Dungeon> getAllDungeonsFromUser(User AUser);

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

