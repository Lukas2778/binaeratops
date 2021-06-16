package de.dhbw.binaeratops.model.api;

import de.dhbw.binaeratops.model.entitys.Avatar;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.Room;

/**
 * Schnittstelle für einen Raumaufenthalt.
 * <p>
 * Sie stellt alle Funktionalitäten zum Umgang mit einem Raumaufenthalt bereit.
 * <p>
 * Für Datenbankoperationen siehe {@link de.dhbw.binaeratops.model.repository.AttendanceRepositoryI}.
 * <p>
 * Für Implementierung siehe {@link de.dhbw.binaeratops.model.entitys.Attendance}
 *
 * @author Lukas Göpel, Nicolas Haug
 */
public interface AttendanceI {

    /**
     * Gibt ID des Raumaufenthalts zurück.
     *
     * @return ID des Raumaufenthalts.
     */
    Long getAttendanceId();

    /**
     * Setze Raumaufenthalts ID
     *
     * @param AAttendanceId Raumaufenthalts ID.
     */
    void setAttendanceId(Long AAttendanceId);

    /**
     * Gibt den Dungeon des Raumaufenthalts zurück.
     *
     * @return Dungeon.
     */
    Dungeon getDungeon();

    /**
     * Setzt den Dungeon des Raumaufenthalts.
     *
     * @param ADungeon Dungeon.
     */
    void setDungeon(Dungeon ADungeon);

    /**
     * Gibt den Avatar des Raumaufenthalts zurück.
     *
     * @return Avatar.
     */
    Avatar getAvatar();

    /**
     * Setzt den Avatar des Raumaufenthalts.
     *
     * @param AAvatar Avatar.
     */
    void setAvatar(Avatar AAvatar);

    /**
     * Gibt den Raum des Raumaufenthalts zurück.
     *
     * @return Raum.
     */
    Room getRoom();

    /**
     * Setzt den Raum des Raumaufenthalts.
     *
     * @param ARoom Raum.
     */
    void setRoom(Room ARoom);
}
