package de.dhbw.binaeratops.model.streammessages;

import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;

/**
 * Klasse für eine Berechtigung.
 *
 * @author Timon Gartung
 */
public class UserRequest {

    private Dungeon dungeon;
    private User user;

    /**
     * Konstruktor für die Berechtigungsmeldung.
     * @param dungeon Dungeon.
     * @param user Benutzer.
     */
    public UserRequest(Dungeon dungeon, User user) {
        this.dungeon = dungeon;
        this.user = user;
    }

    /**
     * Gibt den Dungeon zurück.
     * @return Dungeon.
     */
    public Dungeon getDungeon() {
        return dungeon;
    }

    /**
     * Gibt den Benutzer zurück.
     * @return Benutzer.
     */
    public User getUser() {
        return user;
    }

}
