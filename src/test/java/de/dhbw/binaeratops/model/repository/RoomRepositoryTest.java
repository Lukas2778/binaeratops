package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.groups.Logger;
import de.dhbw.binaeratops.groups.RepositoryGroup;
import de.dhbw.binaeratops.model.api.RoomI;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.Room;
import de.dhbw.binaeratops.model.enums.Status;
import de.dhbw.binaeratops.model.enums.Visibility;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Test zum Prüfen der Funktionalität des Raum Repositories.
 * In diesem Test wird das Management von Räumen getestet.
 *
 * Funktionalitäten, die getestet werden:
 * <ul>
 *     <li>Alle Einträge finden</li>
 *     <li>Einen bestimmten Eintrag mittels Raum ID finden</li>
 *     <li>Einen bestimmten Eintrag aktualisieren</li>
 *     <li>Einen bestimmten Eintrag löschen</li>
 * </ul>
 *
 * @see RoomRepositoryI
 * @see RoomI
 * @see Room
 *
 * @author Nicolas Haug
 */
@Category({RepositoryGroup.class})
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class RoomRepositoryTest extends Logger {

    @Autowired
    RoomRepositoryI roomRepo;

    @Autowired
    DungeonRepositoryI dungeonRepo;

    private Dungeon dungeon1;
    private RoomI room1;
    private RoomI room2;

    /**
     * Initialisierungsmethode, zum Befüllen der In-Memory-Datenbank.
     */
    @Before
    public void init() {
        RoomI room = new Room();
        room.setRoomName("Bar");
        room.setDescription("Alkis");
        roomRepo.save((Room) room);


        room1 = new Room();
        room1.setRoomName("Unterschlupf");
        roomRepo.save((Room) room1);

        room2 = new Room();
        room2.setRoomName("Marktplatz");
        room2.setDescription("Handel");
        roomRepo.save((Room) room2);

        dungeon1 = new Dungeon();
        dungeon1.setDungeonName("test");
        dungeon1.setDungeonVisibility(Visibility.PUBLIC);
        dungeon1.setDefaultInventoryCapacity(4l);
        dungeon1.setDungeonStatus(Status.ACTIVE);
        dungeon1.setDungeonMasterId(4l);
        dungeon1.setPlayerMaxSize(4l);
        dungeon1.setStartRoomId(4l);
        dungeon1.setCommandSymbol('/');
        dungeon1.getRooms().add((Room) room);
        dungeon1.getRooms().add((Room) room1);
        dungeon1.getRooms().add((Room) room2);
        dungeonRepo.save(dungeon1);
    }

    /**
     * Testet die Funktionalität 'findAll()'.
     * Erwartet als Ergebnis alle Einträge, die bereits in der Datenbank sind.
     */
    @Test
    public void testFindAll() {
        List<Room> rooms = roomRepo.findAll();
        Assert.assertEquals(3, rooms.size());
    }

    /**
     * Testet die Funktionalität 'findByRoomId()'.
     * Erwartet als Ergebnis den richtigen Raum zu holen.
     */
    @Test
    public void testFindByRoomId() {
        RoomI roomActual = roomRepo.findByRoomId(room1.getRoomId());
        Assert.assertEquals("Unterschlupf", roomActual.getRoomName());
    }

    /**
     * Testet das Aktualisieren eines Raumes.
     * Erwartet den aktualisierten Eintrag aus der Datenbank.
     */
    @Test
    public void testUpdateRoom() {
        RoomI room = roomRepo.findByRoomId(room1.getRoomId());
        room.setEastRoomId(4l);
        RoomI roomModified = roomRepo.findByRoomId(room1.getRoomId());
        Assert.assertEquals((Long) 4l, roomModified.getEastRoomId());
    }

    /**
     * Testet das Löschen eines Raumes.
     * Erwartet dass die Anzahl der Datenbankeinträge um 1 geringer ist.
     */
    @Test
    public void testDeleteRoom() {
        dungeon1.getRooms().remove(room2);
        roomRepo.delete((Room) room2);
        List<Room> rooms = roomRepo.findAll();
        Assert.assertEquals(2, rooms.size());
    }
}