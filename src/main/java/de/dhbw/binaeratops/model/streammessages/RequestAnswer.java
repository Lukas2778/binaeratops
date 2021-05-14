package de.dhbw.binaeratops.model.streammessages;

import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;

/**
 * Klasse für eine Berechtigung.
 *
 * @author Timon Gartung
 */
public class RequestAnswer {

    private Dungeon dungeon;
    private User user;
    private boolean isEntitled;

    public RequestAnswer() {
    }

    /**
     * Konstruktor für die Berechtigungsmeldung.
     * @param ADungeon Dungeon.
     * @param AUser Benutzer.
     * @param AIsEntitled Ist der Benutzer berechtigt.
     */
    public RequestAnswer(Dungeon ADungeon, User AUser, boolean AIsEntitled) {
        this.dungeon = ADungeon;
        this.user = AUser;
        this.isEntitled = AIsEntitled;
    }

    /**
     * Konstruktor für die Berechtigungsmeldung.
     * @param dungeon Dungeon.
     * @param user Benutzer.
     */
    public RequestAnswer(Dungeon dungeon, User user) {
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

    /**
     * Gibt die Berechtigung des Benutzers zurück.
     * @return Ist der Benutzer berechtigt.
     */
    public boolean isEntitled() {
        return isEntitled;
    }
}
