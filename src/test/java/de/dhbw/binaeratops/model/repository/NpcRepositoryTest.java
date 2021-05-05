package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.groups.Logger;
import de.dhbw.binaeratops.groups.RepositoryGroup;
import de.dhbw.binaeratops.model.api.NPCI;
import de.dhbw.binaeratops.model.entitys.NPC;
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
 * Test zum Prüfen der Funktionalität des NPC Repositories.
 * In diesem Test wird das Management von NPCs getestet.
 * <p>
 * Funktionalitäten, die getestet werden:
 * <ul>
 *     <li>Alle Einträge finden</li>
 *     <li>Einen bestimmten Eintrag mittels NPC ID finden</li>
 *     <li>Einen bestimmten Eintrag aktualisieren</li>
 *     <li>Einen bestimmten Eintrag löschen</li>
 * </ul>
 *
 * @author Nicolas Haug
 * @see NPCRepositoryI
 * @see NPCI
 * @see NPC
 */
@Category({RepositoryGroup.class})
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NpcRepositoryTest extends Logger {

    @Autowired
    NPCRepositoryI npcRepo;

    private NPCI npc1;
    private NPCI npc2;

    /**
     * Initialisierungsmethode, zum Befüllen der In-Memory-Datenbank.
     */
    @Before
    public void init() {
        NPCI npc = new NPC();
        npc.setNpcName("Olaf Herden");
        npc.setDescription("Studiengangsleiter");
        npcRepo.save((NPC) npc);

        npc1 = new NPC();
        npc1.setNpcName("avh");
        npc1.setDescription("Dozent der Informatik");
        npcRepo.save((NPC) npc1);

        npc2 = new NPC();
        npc2.setNpcName("Martin Plümicke");
        npc2.setDescription("Dozent der Informatik");
        npcRepo.save((NPC) npc2);
    }

    /**
     * Testet die Funktionalität 'findAll()'.
     * Erwartet als Ergebnis alle Einträge, die bereits in der Datenbank sind.
     */
    @Test
    public void testFindAll() {
        List<NPC> npcs = npcRepo.findAll();
        Assert.assertEquals(3, npcs.size());
    }

    /**
     * Testet die Funktionalität 'findByNPCId()'.
     * Erwartet als Ergebnis den richtigen NPC zu holen.
     */
    @Test
    public void testFindByNpcId() {
        NPCI npc = npcRepo.findByNpcId(npc1.getNpcId());
        Assert.assertEquals("avh", npc.getNpcName());
    }

    /**
     * Testet das Aktualisieren eines NPCs.
     * Erwartet den aktualisierten Eintrag aus der Datenbank.
     */
    @Test
    public void testUpdateNpc() {
        NPCI npc = npcRepo.findByNpcId(npc1.getNpcId());
        npc.setDescription("Dozent des Wissenschaftlichen Arbeitens");
        NPCI npcModified = npcRepo.findByNpcId(npc1.getNpcId());
        Assert.assertEquals("Dozent des Wissenschaftlichen Arbeitens", npcModified.getDescription());
    }

    /**
     * Testet das Löschen eines NPC.
     * Erwartet dass die Anzahl der Datenbankeinträge um 1 geringer ist.
     */
    @Test
    public void testDeleteNpc() {
        npcRepo.delete((NPC) npc2);
        List<NPC> npcs = npcRepo.findAll();
        Assert.assertEquals(2, npcs.size());
    }
}