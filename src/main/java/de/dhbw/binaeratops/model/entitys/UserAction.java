package de.dhbw.binaeratops.model.entitys;

import de.dhbw.binaeratops.model.api.UserActionI;
import de.dhbw.binaeratops.model.enums.ActionType;

import javax.persistence.*;

/**
 * Entity Objekt für eine Benutzeraktion.
 * <p>
 * Es repräsentiert die Entity "Benutzeraktion" der Datenbank in der Programmlogik.
 * <p>
 * Es implementiert dazu alle Funktionalitäten der Benutzeraktion Schnittstelle.
 *
 * @author Nicolas Haug, Lars Rösel
 * @see UserActionI
 */
@Entity
public class UserAction implements UserActionI {

    @Id
    @GeneratedValue
    private Long actionId;

    @OneToOne
    private User user;

    @OneToOne
    private Avatar avatar;

    @OneToOne
    private Dungeon dungeon;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    @OneToOne
    private Permission permission;

    private String message;

    @OneToOne
    private NPCInstance interactedNpc;

    @OneToOne
    private Item interactedItem;

    private Boolean requested = false;

    /**
     * Konstruktor zum Erzeugen einer TALK-Anfrage.
     *
     * @param ADungeon       Dungeon, der Anfrage.
     * @param AAvatar        Avatar des Anfragenden.
     * @param AActionType    AktionsTyp.
     * @param AMessage       Nachricht an den NPC.
     * @param AInteractedNPC Angefragter NPC.
     */
    public UserAction(Dungeon ADungeon, Avatar AAvatar, ActionType AActionType, String AMessage, NPCInstance AInteractedNPC) {
        this.dungeon = ADungeon;
        this.avatar = AAvatar;
        this.actionType = AActionType;
        this.message = AMessage;
        this.interactedNpc = AInteractedNPC;
        this.requested = true;
    }

    /**
     * Konstruktor zum Erzeugen einer HIT-Anfrage.
     *
     * @param ADungeon       Dungeon, der Anfrage.
     * @param AAvatar        Avatar des Anfragenden.
     * @param AActionType    AktionsTyp.
     * @param AInteractedNPC Angefragter NPC.
     */
    public UserAction(Dungeon ADungeon, Avatar AAvatar, ActionType AActionType, NPCInstance AInteractedNPC) {
        this.dungeon = ADungeon;
        this.avatar = AAvatar;
        this.actionType = AActionType;
        this.interactedNpc = AInteractedNPC;
        this.requested = true;
    }

    /**
     * Konstruktor zum Erzeugen einer CONSUME-Anfrage.
     *
     * @param ADungeon        Dungeon, der Anfrage.
     * @param AAvatar         Avatar des Anfragenden.
     * @param AActionType     AktionsTyp.
     * @param AInteractedItem Angefragter Gegenstand.
     */
    public UserAction(Dungeon ADungeon, Avatar AAvatar, ActionType AActionType, Item AInteractedItem) {
        this.dungeon = ADungeon;
        this.avatar = AAvatar;
        this.actionType = AActionType;
        this.interactedItem = AInteractedItem;
        this.requested = true;
    }

    /**
     * Konstruktor zum Erzeugen einer ENTRY_REQUEST-Anfrage.
     *
     * @param ADungeon    Dungeon, der Anfrage.
     * @param AUser       Anfragender Benutzer.
     * @param APermission Angefragte Berechtigung.
     * @param AActionType AktionsTyp.
     */
    public UserAction(Dungeon ADungeon, User AUser, Permission APermission, ActionType AActionType) {
        this.dungeon = ADungeon;
        this.user = AUser;
        this.permission = APermission;
        this.actionType = AActionType;
        this.requested = true;
    }

    /**
     * Standardkonstruktor zum Erzeugen einer Benutzeraktion.
     */
    public UserAction() {

    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long AActionId) {
        this.actionId = AActionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User AUser) {
        this.user = AUser;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar AAvatar) {
        this.avatar = AAvatar;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public void setDungeon(Dungeon ADungeon) {
        this.dungeon = ADungeon;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType AActionType) {
        this.actionType = AActionType;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission APermission) {
        this.permission = APermission;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String AMessage) {
        this.message = AMessage;
    }

    public NPCInstance getInteractedNpc() {
        return interactedNpc;
    }

    public void setInteractedNpc(NPCInstance AInteractedNPC) {
        this.interactedNpc = AInteractedNPC;
    }

    public Item getInteractedItem() {
        return interactedItem;
    }

    public void setInteractedItem(Item AInteractedItem) {
        this.interactedItem = AInteractedItem;
    }

    public Boolean getRequested() {
        return requested;
    }

    public void setRequested(Boolean ARequested) {
        this.requested = ARequested;
    }
}
