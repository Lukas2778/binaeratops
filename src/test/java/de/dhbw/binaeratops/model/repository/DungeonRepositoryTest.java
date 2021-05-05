package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.groups.Logger;
import de.dhbw.binaeratops.groups.RepositoryGroup;
import de.dhbw.binaeratops.model.api.DungeonI;
import de.dhbw.binaeratops.model.api.UserI;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;
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
 * Test zum Prüfen der Funktionalität des Dungeon Repositories.
 * In diesem Test wird das Management von Dungeons getestet.
 *
 * Funktionalitäten, die getestet werden:
 * <ul>
 *     <li>Alle Einträge finden</li>
 *     <li>Einen bestimmten Eintrag mittels Dungeon ID finden</li>
 *     <li>Einen bestimmten Eintrag aktualisieren</li>
 *     <li>Einen bestimmten Eintrag löschen</li>
 * </ul>
 *
 * @see DungeonRepositoryI
 * @see DungeonI
 * @see Dungeon
 *
 * @author Nicolas Haug
 */
@Category({RepositoryGroup.class})
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class DungeonRepositoryTest extends Logger {

    @Autowired
    DungeonRepositoryI dungeonRepo;

    @Autowired
    UserRepositoryI userRepo;

    private UserI user;
    private DungeonI dungeon1;
    private DungeonI dungeon2;

    /**
     * Initialisierungsmethode, zum Befüllen der In-Memory-Datenbank.
     */
    @Before
    public void init() {
        DungeonI dungeon = new Dungeon();
        dungeon.setDungeonName("Pedro");
        dungeon.setDungeonStatus(Status.ACTIVE);
        dungeon.setDungeonVisibility(Visibility.PUBLIC);
        dungeon.setDungeonMasterId(4L);
        dungeon.setPlayerMaxSize(4L);
        dungeon.setStartRoomId(4L);
        dungeon.setDefaultInventoryCapacity(100L);
        dungeon.setCommandSymbol('#');
        dungeonRepo.save((Dungeon) dungeon);

        dungeon1 = new Dungeon();
        dungeon1.setDungeonName("Pedro");
        dungeon1.setDungeonStatus(Status.INACTIVE);
        dungeon1.setDungeonVisibility(Visibility.PRIVATE);
        dungeon1.setDungeonMasterId(4L);
        dungeon1.setPlayerMaxSize(4L);
        dungeon1.setStartRoomId(4L);
        dungeon1.setDefaultInventoryCapacity(100L);
        dungeon1.setCommandSymbol('#');
        dungeonRepo.save((Dungeon) dungeon1);

        dungeon2 = new Dungeon();
        dungeon2.setDungeonName("Pedro");
        dungeon2.setDungeonStatus(Status.INACTIVE);
        dungeon2.setDungeonVisibility(Visibility.IN_CONFIGURATION);
        dungeon2.setDungeonMasterId(4L);
        dungeon2.setPlayerMaxSize(4L);
        dungeon2.setStartRoomId(4L);
        dungeon2.setDefaultInventoryCapacity(100L);
        dungeon2.setCommandSymbol('#');
        dungeonRepo.save((Dungeon) dungeon2);

        user = new User();
        user.setEmail("g@g.g");
        user.setPassword("efge");
        user.setName("test");
        user.getMyDungeons().add((Dungeon) dungeon);
        user.getMyDungeons().add((Dungeon) dungeon1);
        user.getMyDungeons().add((Dungeon) dungeon2);
        userRepo.save((User) user);
    }

    /**
     * Testet die Funktionalität 'findAll()'.
     * Erwartet als Ergebnis alle Einträge, die bereits in der Datenbank sind.
     */
    @Test
    public void testFindAll() {
        List<Dungeon> dungeons = dungeonRepo.findAll();
        Assert.assertEquals(3, dungeons.size());
    }

    /**
     * Testet die Funktionalität 'findByDungeonId()'.
     * Erwartet als Ergebnis den richtigen Dungeon zu holen.
     */
    @Test
    public void testFindByDungeonId() {
        DungeonI dungeon = dungeonRepo.findByDungeonId(dungeon1.getDungeonId());
        Assert.assertEquals(Visibility.PRIVATE, dungeon.getDungeonVisibility());
    }

    /**
     * Testet das Aktualisieren eines Dungeons.
     * Erwartet den aktualisierten Eintrag aus der Datenbank.
     */
    @Test
    public void testUpdateDungeon() {
        DungeonI dungeon = dungeonRepo.findByDungeonId(dungeon1.getDungeonId());
        dungeon.setDungeonStatus(Status.ACTIVE);
        DungeonI dungeonModified = dungeonRepo.findByDungeonId(dungeon1.getDungeonId());
        Assert.assertEquals(Status.ACTIVE, dungeonModified.getDungeonStatus());
    }

    /**
     * Testet das Löschen eines Dungeons.
     * Erwartet dass die Anzahl der Datenbankeinträge um 1 geringer ist.
     */
    @Test
    public void testDeleteDungeon() {
        user.getMyDungeons().remove(dungeon2);
        dungeonRepo.delete((Dungeon) dungeon2);
        List<Dungeon> dungeons = dungeonRepo.findAll();
        Assert.assertEquals(2, dungeons.size());
    }
}