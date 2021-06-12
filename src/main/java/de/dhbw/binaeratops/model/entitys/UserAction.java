package de.dhbw.binaeratops.model.entitys;

import javax.persistence.*;

@Entity
public class UserAction {

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

    public UserAction(Dungeon ADungeon, Avatar AAvatar, ActionType AActionType, String AMessage, NPCInstance AInteractedNPC) {
        this.dungeon = ADungeon;
        this.avatar = AAvatar;
        this.actionType = AActionType;
        this.message = AMessage;
        this.interactedNpc = AInteractedNPC;
        this.requested = true;
    }

    public UserAction(Dungeon ADungeon, Avatar AAvatar, ActionType AActionType, Item AInteractedItem) {
        this.dungeon = ADungeon;
        this.avatar = AAvatar;
        this.actionType = AActionType;
        this.interactedItem = AInteractedItem;
        this.requested = true;
    }

    public UserAction(Dungeon ADungeon, User AUser, Permission APermission, ActionType AActionType) {
        this.dungeon = ADungeon;
        this.user = AUser;
        this.permission = APermission;
        this.actionType = AActionType;
        this.requested = true;
    }

    public UserAction() {

    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NPCInstance getInteractedNpc() {
        return interactedNpc;
    }

    public void setInteractedNpc(NPCInstance interactedNpc) {
        this.interactedNpc = interactedNpc;
    }

    public Item getInteractedItem() {
        return interactedItem;
    }

    public void setInteractedItem(Item interactedItem) {
        this.interactedItem = interactedItem;
    }

    public Boolean getRequested() {
        return requested;
    }

    public void setRequested(Boolean requested) {
        this.requested = requested;
    }
}
