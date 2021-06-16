package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.model.entitys.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository für einen Raumaufenthalt.
 * <p>
 * Es stellt alle Funktionalitäten zum Speichern, Löschen und Holen einem Raumaufenthalt aus der Datenbank bereit.
 *
 * @author Lukas Göpel
 * @see Attendance
 */
@Repository
public interface AttendanceRepositoryI extends JpaRepository<Attendance, Long> {

    /**
     * Sucht alle Aufenthaltseinträge aus der Datenbank.
     *
     * @return Alle Aufenthaltseinträge aus der Datenbank.
     */
    @Override
    @NonNull
    List<Attendance> findAll();

    /**
     * Sucht alle Aufenthaltseinträge zu einem bestimmten Avatar in einem Dungeon.
     *
     * @param AAvatar  Avatar, zu dem die Aufenthaltseinträge gesucht werden sollen.
     * @param ADungeon Dungeon, in dem sich der Avatar aufhält.
     * @return Aufenthaltsorte.
     */
    List<Attendance> findByAvatarAndDungeon(Avatar AAvatar, Dungeon ADungeon);

    /**
     * Sucht alle Aufenthaltseinträge zu einem Dungeon.
     *
     * @param ADungeon Dungeon, zu dem alle Aufenthalseinträge gesucht werden sollen.
     * @return Aufenthaltseinträge.
     */
    List<Attendance> findByDungeon(Dungeon ADungeon);
}
