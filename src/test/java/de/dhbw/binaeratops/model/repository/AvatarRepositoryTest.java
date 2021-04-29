package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.groups.RepositoryGroup;
import de.dhbw.binaeratops.model.api.AvatarI;
import de.dhbw.binaeratops.model.entitys.Avatar;
import de.dhbw.binaeratops.model.enums.Gender;
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
 * Test zum Prüfen der Funktionalität des Avatar Repositories.
 * In diesem Test wird das Management von Avataren getestet.
 *
 * Funktionalitäten, die getestet werden:
 * <ul>
 *     <li>Alle Einträge finden</li>
 *     <li>Einen bestimmten Eintrag mittels Avatar ID finden</li>
 *     <li>Einen bestimmten Eintrag aktualisieren</li>
 *     <li>Einen bestimmten Eintrag löschen</li>
 * </ul>
 *
 * @see AvatarRepositoryI
 * @see AvatarI
 * @see Avatar
 *
 * @author Nicolas Haug
 */
@Category({RepositoryGroup.class})
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class AvatarRepositoryTest {

    @Autowired
    AvatarRepositoryI avatarRepo;

    private Avatar avatar2;
    private Avatar avatar3;

    /**
     * Initialisierungsmethode, zum Befüllen der In-Memory-Datenbank.
     */
    @Before
    public void init() {
        Avatar avatar1 = new Avatar();
        avatar1.setGender(Gender.MALE);
        avatar1.setName("Test");
        avatarRepo.save(avatar1);

        avatar2 = new Avatar();
        avatar2.setName("Pedro Alfonso");
        avatar2.setGender(Gender.DIVERSE);
        avatarRepo.save(avatar2);

        avatar3 = new Avatar();
        avatar3.setName("Marko Polo");
        avatar3.setGender(Gender.FEMALE);
        avatarRepo.save(avatar3);
    }

    /**
     * Testet die Funktionalität 'findAll()'.
     * Erwartet als Ergebnis alle Einträge, die bereits in der Datenbank sind.
     */
    @Test
    public void testFindAll() {
        List<Avatar> avatars = avatarRepo.findAll();
        Assert.assertEquals(3, avatars.size());
    }

    /**
     * Testet die Funktionalität 'findByAvatarId()'.
     * Erwartet als Ergebnis den richtigen Avatar zu holen.
     */
    @Test
    public void testFindByAvatarId() {
        AvatarI avatar = avatarRepo.findByAvatarId(avatar2.getAvatarId());
        Assert.assertEquals("Pedro Alfonso", avatar.getName());
    }

    /**
     * Testet das Aktualisieren eines Avatars.
     * Erwartet den aktualisierten Eintrag aus der Datenbank.
     */
    @Test
    public void testUpdateAvatar() {
        AvatarI avatar = avatarRepo.findByAvatarId(avatar2.getAvatarId());
        avatar.setGender(Gender.FEMALE);
        AvatarI avatarModified = avatarRepo.findByAvatarId(avatar2.getAvatarId());
        Assert.assertEquals(Gender.FEMALE, avatarModified.getGender());
    }

    /**
     * Testet das Löschen eines Avatars.
     * Erwartet dass die Anzahl der Datenbankeinträge um 1 geringer ist.
     */
    @Test
    public void testDeleteAvatar() {
        avatarRepo.delete(avatar3);
        List<Avatar> avatars = avatarRepo.findAll();
        Assert.assertEquals(2, avatars.size());
    }
}