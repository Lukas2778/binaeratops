package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.model.entitys.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository für einen Raum.
 *
 * Es stellt alle Funktionalitäten zum Speichern, Löschen und Holen eines Raumes aus der Datenbank bereit.
 *
 * @see Room
 *
 * @author Nicolas Haug
 */
@Repository
public interface RoomRepositoryI extends JpaRepository<Room, Long> {

    /**
     * Sucht alle Raumeinträge aus der Datenbank.
     * @return Alle Raumeinträge aus der Datenbank.
     */
    @Override
    @NonNull
    List<Room> findAll();

    /**
     * Sucht den Raum mit der übergebenen ID in der Datenbank.
     * @param ARoomId ID des gesuchten Raumes.
     * @return Gesuchter Raum.
     */
    Room findByRoomId(Long ARoomId);

    /**
     * Sucht alle Räume eines bestimmten Dungeons.
     * @param ADungeonId Dungeon, für den alle Räume gesucht werden sollen.
     * @return Alle Räume des Dungeons.
     */
    @Query(value = "SELECT room.* FROM room LEFT JOIN dungeon_rooms ON room.room_id = dungeon_rooms.rooms_room_id WHERE dungeon_rooms.dungeon_dungeon_id = :dungeonId", nativeQuery = true)
    List<Room> findByDungeon(@Param("dungeonId")Long ADungeonId);
}