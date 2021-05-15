package de.dhbw.binaeratops.model.api;

import de.dhbw.binaeratops.model.entitys.Avatar;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.ItemInstance;
import de.dhbw.binaeratops.model.entitys.Race;
import de.dhbw.binaeratops.model.entitys.Role;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.enums.Gender;
import java.util.List;

/**
 * Schnittstelle für eine Permission.
 * <p>
 * Sie stellt alle Funktionalitäten zum Umgang mit einer Permission bereit.
 * <p>
 * Für Datenbankoperationen siehe {@link de.dhbw.binaeratops.model.repository.AvatarRepositoryI}. TODO ändern
 * <p>
 * Für Implementierung siehe {@link de.dhbw.binaeratops.model.entitys.Permission}
 *
 * @author Pedro Treuer
 */
public interface PermissionI
{
    /**
     * Gibt die ID des Dungeons zurück.
     *
     * @return Dungeon-ID
     */
    Dungeon getDungeon();

    /**
     * Setzt die ID eines Dungeons.
     *
     * @param ADungeon, DungeonId  die gesetzt werden soll.
     */
    void setDungeon(Dungeon ADungeon);

    /**
     * Gib die ID des Users zurücl
     *
     * @return UserId
     */
    User getUser();

    /**
     * Setzt die ID eines Users
     *
     * @param AUser, UserId die gesetzt werden soll
     */
    void setUser(User AUser);

    /**
     * Gibt einen Boolean zurück, ob der Spieler die Spielberechtigung eines Dungeons hat
     *
     * @return Access, also Zugang
     */
    Boolean getAccess();

    /**
     * Setzt den Zugang eines Users
     *
     * @param AAccess, Access das gesetzt werden soll
     */
    void setAccess(Boolean AAccess);
}
