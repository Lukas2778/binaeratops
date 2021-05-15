package de.dhbw.binaeratops.model;

import de.dhbw.binaeratops.model.entitys.Avatar;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.service.api.chat.ChatServiceI;

/**
 * Transportkklasse für die UserAction
 *
 * @author Timon Gartung
 */
public class UserAction {
    private Dungeon dungeon;
    private Avatar user;
    private String actionType;
    private String userActionMessage;

    /**
     *
     * @param dungeon Dungeon.
     * @param user Spiler.
     * @param actionType AKtionstyp.
     * @param userActionMessage Spe
     */
    public UserAction(Dungeon dungeon, Avatar user, String actionType, String userActionMessage) {
        this.dungeon = dungeon;
        this.user = user;
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

    public Avatar getUser() {
        return user;
    }

    public String getActionType() {
        return actionType;
    }

}
