package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.groups.Logger;
import de.dhbw.binaeratops.groups.RepositoryGroup;
import de.dhbw.binaeratops.model.api.RoleI;
import de.dhbw.binaeratops.model.entitys.Role;
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
 * Test zum Prüfen der Funktionalität des Rollen Repositories.
 * In diesem Test wird das Management von Rollen getestet.
 *
 * Funktionalitäten, die getestet werden:
 * <ul>
 *     <li>Alle Einträge finden</li>
 *     <li>Einen bestimmten Eintrag mittels Rollen ID finden</li>
 *     <li>Einen bestimmten Eintrag aktualisieren</li>
 *     <li>Einen bestimmten Eintrag löschen</li>
 * </ul>
 *
 * @see RoleRepositoryI
 * @see RoleI
 * @see Role
 *
 * @author Nicolas Haug
 */
@Category({RepositoryGroup.class})
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class RoleRepositoryTest extends Logger {

    @Autowired
    RoleRepositoryI roleRepo;

    private RoleI role1;
    private RoleI role2;

    /**
     * Initialisierungsmethode, zum Befüllen der In-Memory-Datenbank.
     */
    @Before
    public void init() {
        RoleI role3 = new Role();
        role3.setRoleName("§21");
        roleRepo.save((Role) role3);

        role1 = new Role();
        role1.setRoleName("Metzger");
        role1.setDescription("DESC");
        roleRepo.save((Role) role1);

        role2 = new Role();
        role2.setRoleName("Timon");
        role2.setDescription("342");
        roleRepo.save((Role) role2);
    }

    /**
     * Testet die Funktionalität 'findAll()'.
     * Erwartet als Ergebnis alle Einträge, die bereits in der Datenbank sind.
     */
    @Test
    public void testFindAll() {
        List<Role> roles = roleRepo.findAll();
        Assert.assertEquals(3, roles.size());
    }

    /**
     * Testet die Funktionalität 'findByRoleId()'.
     * Erwartet als Ergebnis die richtige Rolle zu holen.
     */
    @Test
    public void testFindByRoleId() {
        RoleI role5 = roleRepo.findByRoleId(role1.getRoleId());
        Assert.assertEquals("Metzger", role5.getRoleName());
    }

    /**
     * Testet das Aktualisieren einer Rolle.
     * Erwartet den aktualisierten Eintrag aus der Datenbank.
     */
    @Test
    public void testUpdateRole() {
        RoleI role5 = roleRepo.findByRoleId(role1.getRoleId());
        role5.setDescription("CHANGED");
        RoleI roleModified = roleRepo.findByRoleId(role1.getRoleId());
        Assert.assertEquals("CHANGED", roleModified.getDescription());
    }

    /**
     * Testet das Löschen einer Rolle.
     * Erwartet dass die Anzahl der Datenbankeinträge um 1 geringer ist.
     */
    @Test
    public void testDeleteRole() {
        roleRepo.delete((Role) role1);
        List<Role> roles = roleRepo.findAll();
        Assert.assertEquals(2, roles.size());
    }
}