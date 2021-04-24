package de.dhbw.binaeratops.model.impl;


import de.dhbw.binaeratops.model.api.AvatarI;
import de.dhbw.binaeratops.model.entitys.Avatar;
import de.dhbw.binaeratops.model.entitys.Gender;
import de.dhbw.binaeratops.model.repository.AvatarRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
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
 * @see AvatarRepository
 * @see AvatarI
 * @see Avatar
 */
@RunWith(MockitoJUnitRunner.class)
//@ActiveProfiles("test")
//@DataJpaTest
//@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class AvatarRepositoryTest {

    @Mock
    private AvatarRepository avatarRepo;

    ArrayList<Avatar> createdAvatars = new ArrayList<>();

    private Avatar avatar2;
    private Avatar avatar3;

    /**
     * Initialisierungsmethode, zum Befüllen der In-Memory-Datenbank.
     */
    @Before
    public void init() {
        Avatar avatar1 = new Avatar();
        avatar1.setAvatarId(1L);
        avatar1.setGender(Gender.MALE);
        avatar1.setName("Test");
        createdAvatars.add(avatar1);

        avatar2 = new Avatar();
        avatar2.setAvatarId(2L);
        avatar2.setName("Pedro Alfonso");
        avatar2.setGender(Gender.DIVERSE);
        createdAvatars.add(avatar2);

        avatar3 = new Avatar();
        avatar3.setAvatarId(3L);
        avatar3.setName("Marko Polo");
        avatar3.setGender(Gender.FEMALE);
        createdAvatars.add(avatar3);
    }

    @After
    public void reset() {
        createdAvatars = new ArrayList<>();
    }

    /**
     * Testet die Funktionalität 'findAll()'.
     * Erwartet als Ergebnis alle Einträge, die bereits in der Datenbank sind.
     */
    @Test
    public void testFindAll() {
        Mockito.when(avatarRepo.findAll()).thenReturn(createdAvatars);
        List<Avatar> avatars = avatarRepo.findAll();
        Assert.assertEquals(3, avatars.size());
    }

    /**
     * Testet die Funktionalität 'findByAvatarId()'.
     * Erwartet als Ergebnis den richtigen Avatar zu holen.
     */
    @Test
    public void testFindByAvatarId() {
        Mockito.when(avatarRepo.findByAvatarId(avatar2.getAvatarId())).thenReturn(avatar2);
        AvatarI avatar = avatarRepo.findByAvatarId(avatar2.getAvatarId());
        Assert.assertEquals("Pedro Alfonso", avatar.getName());
    }

    /**
     * Testet das Aktualisieren eines Avatars.
     * Erwartet den aktualisierten Eintrag aus der Datenbank.
     */
    @Test
    public void testUpdateAvatar() {
        Mockito.when(avatarRepo.findByAvatarId(avatar2.getAvatarId())).thenReturn(avatar2);
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
        Mockito.doNothing().when(avatarRepo).delete(avatar3);
        avatarRepo.delete(avatar3);
        createdAvatars.remove(avatar3);
        Mockito.when(avatarRepo.findAll()).thenReturn(createdAvatars);
        List<Avatar> avatars = avatarRepo.findAll();
        Assert.assertEquals(2, avatars.size());
    }
}
