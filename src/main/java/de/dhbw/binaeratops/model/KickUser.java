package de.dhbw.binaeratops.model;

import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;

/**
 * @author Lars Rösel
 * Date: 16.05.2021
 * Time: 19:26
 */
public class KickUser {
    private User user;
    private Dungeon dungeon;

    public KickUser(User AUser, Dungeon ADungeon) {
        this.user = AUser;
        this.dungeon = ADungeon;
    }

    public User getUser() {
        return user;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }
}
