package de.dhbw.binaeratops.model.entitys;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Permission {

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

    public Permission(User AUser) {
        this.user = AUser;
    }

    public Permission() {

    }

    public Long getPermissionId() {
        return permissionId;
    }
// TODO Kommentare + Interface
    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
