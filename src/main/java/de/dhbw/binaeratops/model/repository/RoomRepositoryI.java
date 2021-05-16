package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository für einen Raum.
 * <p>
 * Es stellt alle Funktionalitäten zum Speichern, Löschen und Holen eines Raumes aus der Datenbank bereit.
 *
 * @author Nicolas Haug
 * @see Room
 */
@Repository
public interface RoomRepositoryI extends JpaRepository<Room, Long> {

    /**
     * Sucht alle Raumeinträge aus der Datenbank.
     *
     * @return Alle Raumeinträge aus der Datenbank.
     */
    @Override
    @NonNull
    List<Room> findAll();

    /**
     * Sucht den Raum mit der übergebenen ID in der Datenbank.
     *
     * @param ARoomId ID des gesuchten Raumes.
     * @return Gesuchter Raum.
     */
    Room findByRoomId(Long ARoomId);

    List<Room> findByXcoordinate (Integer AXCoordinate);

    List<Room> findByYcoordinate (Integer AYCoordinate);

    List<Room> findByDungeon (Dungeon ADungeon);

    List<Room> findByDungeonAndXcoordinateAndYcoordinate(Dungeon ADungeon, Integer AX, Integer AY);
}