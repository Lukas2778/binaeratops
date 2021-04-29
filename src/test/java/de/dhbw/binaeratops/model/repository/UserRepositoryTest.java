package de.dhbw.binaeratops.model.repository;


import de.dhbw.binaeratops.groups.RepositoryGroup;
import de.dhbw.binaeratops.model.api.AvatarI;
import de.dhbw.binaeratops.model.api.UserI;
import de.dhbw.binaeratops.model.entitys.Avatar;
import de.dhbw.binaeratops.model.enums.Gender;
import de.dhbw.binaeratops.model.entitys.User;
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
 * Test zum Prüfen der Funktionalität des Benutzer Repositories.
 * In diesem Test wird das Management von Benutzern getestet.
 *
 * Funktionalitäten, die getestet werden:
 * <ul>
 *     <li>Alle Einträge finden</li>
 *     <li>Einen bestimmten Eintrag mittels Benutzername finden</li>
 *     <li>Einen bestimmten Eintrag aktualisieren</li>
 *     <li>Einen bestimmten Eintrag löschen</li>
 *     <li>Einen bestimmten Eintrag mit Avatarliste zu speichern</li>
 *     <li>Einen bestimmten Eintrag mit Avatarliste zu laden</li>
 * </ul>
 *
 * @see UserRepositoryI
 * @see UserI
 * @see User
 *
 * @author Nicolas Haug
 */
@Category({RepositoryGroup.class})
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    UserRepositoryI userRepo;

    @Autowired
    AvatarRepositoryI avatarRepo;

    private User user2;

    // Zum Testen der Listen Funktionalität.
    private AvatarI avatar;
    private AvatarI avatarI;


    /**
     * Initialisierungsmethode, zum Befüllen der In-Memory-Datenbank.
     */
    @Before
    public void init() {
        User user1 = new User("Hans", "i19036@hb.dhbw-stuttgart.de", "12345", 345433, true);
        userRepo.save(user1);

        user2 = new User("avh", "a.vanhoof@hb.dhbw-stuttgart.de", "36353", 345433, true);


        avatar = new Avatar();
        avatar.setName("Timon");
        avatar.setGender(Gender.MALE);
        avatarRepo.save((Avatar) avatar);

        avatarI = new Avatar();
        avatarI.setName("Pumba");
        avatarI.setGender(Gender.DIVERSE);
        avatarRepo.save((Avatar) avatarI);

        user2.getAvatars().add((Avatar) avatar);
        user2.getAvatars().add((Avatar) avatarI);

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
     * Testet die Funktionalität 'findByUsername()'.
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
     * Testet das Speichern von Avataren zu einem Benutzer.
     * Erwartet, dass die Anzahl der in der Datenbank gespeicherten Einträge, der tatsächlichen entspricht.
     */
    @Test
    public void testSaveAvatarList() {
        AvatarI avatar1 = new Avatar();
        avatar1.setName("Test1");
        avatar1.setGender(Gender.MALE);
        avatarRepo.save((Avatar) avatar1);

        AvatarI avatar2 = new Avatar();
        avatar2.setName("Test2");
        avatar2.setGender(Gender.DIVERSE);
        avatarRepo.save((Avatar) avatar2);

        UserI user = new User();
        user.setEmail("g@g.g");
        user.setName("test");
        user.setPasswordHash("435n3rtr3");
        user.getAvatars().add((Avatar) avatar1);
        user.getAvatars().add((Avatar) avatar2);
        userRepo.save((User) user);

        UserI testUser = userRepo.findByName("test");
        Assert.assertEquals(2,testUser.getAvatars().size());
        Assert.assertEquals("Test2", testUser.getAvatars().get(1).getName());
    }

    /**
     * Testet, dass das Laden einer Avatarliste eines Benutzers korrekt funktioniert.
     * Erwartet, dass der tatsächliche Avatarname dem orginalen Avatarnamen entspricht.
     */
    @Test
    public void testLoadAvatarList() {
        UserI u = userRepo.findByName("avh");
        Assert.assertEquals(avatarI.getName(), u.getAvatars().get(1).getName());
    }
}