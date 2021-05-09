package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.groups.Logger;
import de.dhbw.binaeratops.groups.RepositoryGroup;
import de.dhbw.binaeratops.model.api.ItemI;
import de.dhbw.binaeratops.model.entitys.Item;
import de.dhbw.binaeratops.model.enums.ItemType;
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
 * Test zum Prüfen der Funktionalität des Gegenstand Repositories.
 * In diesem Test wird das Management von Gegenständen getestet.
 *
 * Funktionalitäten, die getestet werden:
 * <ul>
 *     <li>Alle Einträge finden</li>
 *     <li>Einen bestimmten Eintrag mittels Gegenstand ID finden</li>
 *     <li>Einen bestimmten Eintrag aktualisieren</li>
 *     <li>Einen bestimmten Eintrag löschen</li>
 * </ul>
 *
 * @see ItemRepositoryI
 * @see ItemI
 * @see Item
 *
 * @author Nicolas Haug
 */
@Category({RepositoryGroup.class})
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ItemRepositoryTest extends Logger {

    @Autowired
    ItemRepositoryI itemRepo;

    private Item item1;
    private Item item2;

    /**
     * Initialisierungsmethode, zum Befüllen der In-Memory-Datenbank.
     */
    @Before
    public void init() {
        Item item = new Item();
        item.setItemName("Punsch");
        item.setType(ItemType.CONSUMABLE);
        item.setDescription("Trinkbar");
        item.setSize(3L);
        itemRepo.save((Item) item);

        item1 = new Item();
        item1.setItemName("Schwert");
        item1.setType(ItemType.WEAPON);
        item1.setDescription("Ausrüstbar");
        item1.setSize(6L);
        itemRepo.save((Item) item1);

        item2 = new Item();
        item2.setItemName("Kette");
        item2.setType(ItemType.DEFAULT);
        item2.setDescription("Antike Kette");
        item2.setSize(1L);
        itemRepo.save((Item) item2);
    }

    /**
     * Testet die Funktionalität 'findAll()'.
     * Erwartet als Ergebnis alle Einträge, die bereits in der Datenbank sind.
     */
    @Test
    public void testFindAll() {
        List<Item> items = itemRepo.findAll();
        Assert.assertEquals(3, items.size());
    }

    /**
     * Testet die Funktionalität 'findByItemId()'.
     * Erwartet als Ergebnis den richtigen Gegenstand zu holen.
     */
    @Test
    public void testFindByItemId() {
        Item item = itemRepo.findByItemId(item1.getItemId());
        Assert.assertEquals(ItemType.WEAPON, item.getType());
    }

    /**
     * Testet das Aktualisieren eines Gegenstandes.
     * Erwartet den aktualisierten Eintrag aus der Datenbank.
     */
    @Test
    public void testUpdateItem() {
        Item item = itemRepo.findByItemId(item1.getItemId());
        item.setSize(4L);
        Item itemModified = itemRepo.findByItemId(item1.getItemId());
        Assert.assertEquals((Long) 4l, itemModified.getSize());
    }

    /**
     * Testet das Löschen eines Gegenstandes.
     * Erwartet dass die Anzahl der Datenbankeinträge um 1 geringer ist.
     */
    @Test
    public void testDeleteItem() {
        itemRepo.delete((Item) item2);
        List<Item> items = itemRepo.findAll();
        Assert.assertEquals(2, items.size());
    }
}