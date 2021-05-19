package de.dhbw.binaeratops.model.entitys;

import de.dhbw.binaeratops.model.api.PermissionI;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Entity Objekt für eine Berechtigung.
 * <p>
 * Es repräsentiert die Entity "Berechtigung" der Datenbank in der Programmlogik.
 * <p>
 * Es implementiert dazu alle Funktionalitäten der Berechtigung Schnittstelle.
 *
 * @author Nicolas Haug
 * @see PermissionI
 */
@Entity
public class Permission implements PermissionI {

    @Id
    @GeneratedValue
    private Long permissionId;

    @ManyToOne
    private User user;

    @ManyToOne
    private Dungeon allowedDungeon;

    @ManyToOne
    private Dungeon blockedDungeon;

    @ManyToOne
    private Dungeon requestedDungeon;

    /**
     * Konstruktor zum Erzeugen einer Berechtigung mit dem zugehörigen Benutzer.
     *
     * @param AUser Benutzer, dem die Berechtigung erteilt werden soll.
     */
    public Permission(User AUser) {
        this.user = AUser;
    }

    /**
     * Standardkonstruktor zum Erzeugen einer Berechtigung.
     */
    public Permission() {

    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long APermissionId) {
        this.permissionId = APermissionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User AUser) {
        this.user = AUser;
    }

    public Dungeon getAllowedDungeon() {
        return allowedDungeon;
    }

    public void setAllowedDungeon(Dungeon AAllowedDungeon) {
        this.allowedDungeon = AAllowedDungeon;
    }

    public Dungeon getBlockedDungeon() {
        return blockedDungeon;
    }

    public void setBlockedDungeon(Dungeon ABlockedDungeon) {
        this.blockedDungeon = ABlockedDungeon;
    }

    public Dungeon getRequestedDungeon() {
        return requestedDungeon;
    }

    public void setRequestedDungeon(Dungeon ARequestedDungeon) {
        this.requestedDungeon = ARequestedDungeon;
    }
}
