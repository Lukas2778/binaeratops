package de.dhbw.binaeratops.model.streammessages;

import de.dhbw.binaeratops.model.entitys.Avatar;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;

/**
 * Klasse für eine Berechtigung.
 *
 * @author Timon Gartung
 */
public class UserAction {

    private Dungeon dungeon;
    private User user;
    private Avatar avatar;
    private String actionMessage;

    /**
     *
     * @param dungeon Dungeon.
     * @param user Benutzer.
     * @param avatar  Avatar.
     * @param actionMessage Aktionstext.
     */
    public UserAction(Dungeon dungeon, User user, Avatar avatar, String actionMessage) {
        this.dungeon = dungeon;
        this.user = user;
        this.avatar = avatar;
        this.actionMessage = actionMessage;
    }

    /**
     * Konstruktor für die Berechtigungsmeldung.
     */
    public UserAction() {
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
     * Gibt die Actionsnachricht der Spilers zurück.
     * @return Ationsnachricht.
     */
    public String getActionMessage() {
        return actionMessage;
    }

    /**
     * Gibt den Avatar des Spielers zurück
     * @return Avatar.
     */
    public Avatar getAvatar() {
        return avatar;
    }
}
