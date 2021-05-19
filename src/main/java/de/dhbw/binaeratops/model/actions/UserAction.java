package de.dhbw.binaeratops.model.actions;

import de.dhbw.binaeratops.model.entitys.Avatar;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.Permission;
import de.dhbw.binaeratops.model.entitys.User;

/**
 * Transportklasse für die UserAction.
 *
 * @author Timon Gartung
 */
public class UserAction {
    private Dungeon dungeon;
    private Avatar avatar;
    private String actionType;
    private String userActionMessage;
    private User user;
    private Permission permission;

    /**
     * Standardkonstruktor mit Dungeon, Avatar, AktionsTyp und Benutzernachricht.
     *
     * @param ADungeon           Dungeon.
     * @param AAvatar            Avatar.
     * @param AActionType        Aktionstyp.
     * @param AUserActionMessage Aktionsnachricht
     */
    public UserAction(Dungeon ADungeon, Avatar AAvatar, String AActionType, String AUserActionMessage) {
        this.dungeon = ADungeon;
        this.avatar = AAvatar;
        this.actionType = AActionType;
        this.userActionMessage = AUserActionMessage;
    }

    public UserAction(Dungeon ADungeon, User AUser, Permission APermission, String AActionType, String AUserActionMessage) {
        this.dungeon = ADungeon;
        this.user = AUser;
        this.permission = APermission;
        this.actionType = AActionType;
        this.userActionMessage = AUserActionMessage;
    }

    /**
     * Testkonstruktor.
     *
     * @param AUserActionMessage Testnachricht.
     */
    public UserAction(String AUserActionMessage) {
        this.userActionMessage = AUserActionMessage;
    }

    public User getUser() {
        return user;
    }

    /**
     * Gibt die Aktionsnachricht zurück.
     *
     * @return Aktionsnachricht.
     */
    public String getUserActionMessage() {
        return userActionMessage;
    }

    /**
     * Gibt den Dungeon der Aktion zurück.
     *
     * @return Dungeon der Aktion.
     */
    public Dungeon getDungeon() {
        return dungeon;
    }

    /**
     * Gibt den Avatar der Aktion zurück.
     *
     * @return Avatar der Aktion.
     */
    public Avatar getAvatar() {
        return avatar;
    }

    /**
     * Gibt den Aktionstyp der Aktion zurück.
     *
     * @return Aktionstyp der Aktion.
     */
    public String getActionType() {
        return actionType;
    }

    public Permission getPermission() {
        return permission;
    }
}
