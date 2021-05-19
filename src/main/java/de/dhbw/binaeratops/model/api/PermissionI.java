package de.dhbw.binaeratops.model.api;

import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;

/**
 * Schnittstelle für eine Berechtigung.
 * <p>
 * Sie stellt alle Funktionalitäten zum Umgang mit einer Berechtigung bereit.
 * <p>
 * Für Datenbankoperationen siehe {@link de.dhbw.binaeratops.model.repository.PermissionRepositoryI}.
 * <p>
 * Für die Implementierung siehe {@link de.dhbw.binaeratops.model.entitys.Permission}.
 *
 * @author Nicolas Haug
 */
public interface PermissionI {

    /**
     * Gibt die ID der Spielberechtigung zurück.
     *
     * @return ID er Spielberechtigung.
     */
    Long getPermissionId();

    /**
     * Setzt die ID der Spielberechtigung.
     *
     * @param APermissionId ID der Spielberechtigung.
     */
    void setPermissionId(Long APermissionId);

    /**
     * Gibt den Benutzer, für den die Spielberechtigung erteilt wurde, zurück.
     *
     * @return Benutzer der Spielberechtigung.
     */
    User getUser();

    /**
     * Setzt den Benutzer, für den die Spielberechtigung erteilt werden soll.
     *
     * @param AUser Benutzer der Spielberechtigung.
     */
    void setUser(User AUser);

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

    /**
     * Gibt den angefragten Dungeon zurück.
     *
     * @return Angefragter Dungeon.
     */
    Dungeon getRequestedDungeon();

    /**
     * Setzt den angefragten Dungeon.
     *
     * @param ARequestedDungeon Zu setzender angefragter Dungeon.
     */
    public void setRequestedDungeon(Dungeon ARequestedDungeon);
}
