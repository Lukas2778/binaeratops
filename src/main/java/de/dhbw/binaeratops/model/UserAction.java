package de.dhbw.binaeratops.model;

import de.dhbw.binaeratops.model.entitys.Avatar;
import de.dhbw.binaeratops.model.entitys.Dungeon;

/**
 * Transportkklasse für die UserAction
 *
 * @author Timon Gartung
 */
public class UserAction {
    private Dungeon dungeon;
    private Avatar avatar;
    private String actionType;
    private String userActionMessage;

    /**
     *
     * @param dungeon Dungeon.
     * @param avatar Spiler.
     * @param actionType AKtionstyp.
     * @param userActionMessage Spe
     */
    public UserAction(Dungeon dungeon, Avatar avatar, String actionType, String userActionMessage) {
        this.dungeon = dungeon;
        this.avatar = avatar;
        this.actionType = actionType;
        this.userActionMessage = userActionMessage;
    }

    /**
     * Testkonstruktor.
     * @param userActionMessage testnachricht.
     */
    public UserAction(String userActionMessage) {
        this.userActionMessage = userActionMessage;
    }
    public String getUserActionMessage() {
        return userActionMessage;
    }
    public Dungeon getDungeon() {
        return dungeon;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public String getActionType() {
        return actionType;
    }

}
