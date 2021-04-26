package de.dhbw.binaeratops.model.repository;


import de.dhbw.binaeratops.model.api.UserI;
import de.dhbw.binaeratops.model.entitys.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Test zum Prüfen der Funktionalität des Benutzer Repositories.
 * In diesem Test wird das Management von Benutzern getestet.
 *
 * Funktionalitäten, die getestet werden:
 * <ul>
 *     <li>Alle Einträge finden</li>
 *     <li>Einen bestimmten Eintrag mittels Benutzername finden</li>
 *     <li>Einen bestimmten Eintrag aktualisieren</li>
 *     <li>Einen bestimmten Eintrag löschen</li>
 * </ul>
 *
 * @see UserRepository
 * @see UserI
 * @see User
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepo;

    private User user2;

    /**
     * Initialisierungsmethode, zum Befüllen der In-Memory-Datenbank.
     */
    @Before
    public void init() {
        User user1 = new User("Hans", "i19036@hb.dhbw-stuttgart.de", "12345", 345433, true);
        userRepo.save(user1);

        user2 = new User("avh", "a.vanhoof@hb.dhbw-stuttgart.de", "36353", 345433, true);
        userRepo.save(user2);
    }


    /**
     * Testet die Funktionalität 'findAll()'.
     * Erwartet als Ergebnis alle Einträge, die bereits in der Datenbank sind.
     */
    @Test
    public void testFindAll() {
        List<User> users = userRepo.findAll();
        Assert.assertEquals(2, users.size());
    }

    /**
     * Testet die Funktionalität 'findByName()'.
     * Erwartet als Ergebnis den richtigen Benutzer zu holen.
     */
    @Test
    public void testFindByUsername() {
        UserI avh = userRepo.findByName("avh");
        Assert.assertEquals(user2.getEmail(), avh.getEmail());
    }

    /**
     * Testet das Aktualisieren eines Benutzers.
     * Erwartet den aktualisierten Eintrag aus der Datenbank.
     */
    @Test
    public void testUpdateUser() {
        UserI avh = userRepo.findByName("avh");
        avh.setCode(134234);
        UserI avhModified = userRepo.findByName("avh");
        Assert.assertEquals(avh.getCode(), avhModified.getCode());
    }

    /**
     * Testet das Löschen eines Benutzers.
     * Erwartet dass die Anzahl der Datenbankeinträge um 1 geringer ist.
     */
    @Test
    public void testDeleteAvatar() {
        userRepo.delete(user2);
        List<User> avatars = userRepo.findAll();
        Assert.assertEquals(1, avatars.size());
    }

    /**
     * Testet nichts. Hintergrund: GitHub kann keine dynamischen Strukturen im Maven build nutzen, daher vereinfacht für GitHub.
     * Bei Veränderung müssen diese Tests einkommentiert werden und beim Builden der Serverapplikation
     */
    @Test
    public void testNothing() {
        Assert.assertTrue(true);
    }
}