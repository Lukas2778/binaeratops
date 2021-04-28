package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.groups.RepositoryGroup;
import de.dhbw.binaeratops.model.api.RaceI;
import de.dhbw.binaeratops.model.entitys.Race;
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
 * Test zum Prüfen der Funktionalität des Rassen Repositories.
 * In diesem Test wird das Management von Rassen getestet.
 *
 * Funktionalitäten, die getestet werden:
 * <ul>
 *     <li>Alle Einträge finden</li>
 *     <li>Einen bestimmten Eintrag mittels Rassen ID finden</li>
 *     <li>Einen bestimmten Eintrag aktualisieren</li>
 *     <li>Einen bestimmten Eintrag löschen</li>
 * </ul>
 *
 * @see RaceRepositoryI
 * @see RaceI
 * @see Race
 *
 * @author Nicolas Haug
 */
@Category({RepositoryGroup.class})
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class RaceRepositoryTest {

    @Autowired
    RaceRepositoryI raceRepo;

    private RaceI race1;
    private RaceI race2;

    /**
     * Initialisierungsmethode, zum Befüllen der In-Memory-Datenbank.
     */
    @Before
    public void init() {
        RaceI race = new Race();
        race.setRaceName("Magier");
        race.setDescription("Test");
        raceRepo.save((Race) race);

        race1 = new Race();
        race1.setRaceName("Wolf");
        raceRepo.save((Race) race1);

        race2 = new Race();
        race2.setRaceName("Tiger");
        race2.setDescription("lol");
        raceRepo.save((Race) race2);
    }

    /**
     * Testet die Funktionalität 'findAll()'.
     * Erwartet als Ergebnis alle Einträge, die bereits in der Datenbank sind.
     */
    @Test
    public void testFindAll() {
        List<Race> races = raceRepo.findAll();
        Assert.assertEquals(3, races.size());
    }

    /**
     * Testet die Funktionalität 'findByRaceId()'.
     * Erwartet als Ergebnis die richtige Rasse zu holen.
     */
    @Test
    public void testFindByRaceId() {
        RaceI race = raceRepo.findByRaceId(race1.getRaceId());
        Assert.assertEquals("Wolf", race.getRaceName());
    }

    /**
     * Testet das Aktualisieren einer Rasse.
     * Erwartet den aktualisierten Eintrag aus der Datenbank.
     */
    @Test
    public void testUpdateRace() {
        RaceI race = raceRepo.findByRaceId(race1.getRaceId());
        race.setRaceName("Werwolf");
        RaceI raceModified = raceRepo.findByRaceId(race1.getRaceId());
        Assert.assertEquals("Werwolf", raceModified.getRaceName());
    }

    /**
     * Testet das Löschen einer Rasse.
     * Erwartet dass die Anzahl der Datenbankeinträge um 1 geringer ist.
     */
    @Test
    public void testDeleteRace() {
        raceRepo.delete((Race) race2);
        List<Race> races = raceRepo.findAll();
        Assert.assertEquals(2, races.size());
    }
}