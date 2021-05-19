package de.dhbw.binaeratops.model.api;

import de.dhbw.binaeratops.model.entitys.Dungeon;

public interface PermissionI {

    /***
     * Gibt den erlaubten Dungeons zurück.
     * @return Erlaubten Dungeons.
     */
    Dungeon getAllowedDungeon();

    /**
     * Setzt den erlaubten Dungeon.
     *
     * @param AAllowedDungeon Zu setzender erlaubter Dungeon.
     */
    void setAllowedDungeon(Dungeon AAllowedDungeon);

    /**
     * Gibt den blockierten Dungeon zurück.
     *
     * @return Blockierter Dungeon.
     */
    Dungeon getBlockedDungeon();

    /**
     * Setzt den blockierten Dungeon.
     *
     * @param ABlockedDungeon Zu setzender blockierter Dungeon.
     */
    void setBlockedDungeon(Dungeon ABlockedDungeon);
}
