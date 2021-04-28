package de.dhbw.binaeratops.model.entitys;

import de.dhbw.binaeratops.model.api.DungeonI;
import de.dhbw.binaeratops.model.enums.Status;
import de.dhbw.binaeratops.model.enums.Visibility;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity Objekt für einen Dungeon.
 * <p>
 * Es repräsentiert die Entity "Dungeon" der Datenbank in der Programmlogik.
 * <p>
 * Es implementiert dazu alle Funktionalitäten der Dungeon Schnittstelle.
 * <p>
 * @see DungeonI
 *
 * @author Nicolas Haug
 */
@Entity
public class Dungeon implements DungeonI {

    @Id
    @GeneratedValue
    private Long dungeonId;

    @NotNull
    private String dungeonName;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Visibility dungeonVisibility;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Status dungeonStatus;

    @NotNull
    private Long dungeonMasterId;

    private Long playerCount;

    @NotNull
    private Long playerMaxSize;

    @NotNull
    private Long startRoomId;

    @NotNull
    private Long defaultInventoryCapacity;

    @NotNull
    private Character commandSymbol;

    @OneToMany
    private final List<Avatar> avatars = new ArrayList<>();

    @OneToMany
    private final List<User> allowedUsers = new ArrayList<>();

    @OneToMany
    private final List<User> blockedUsers = new ArrayList<>();

    @OneToMany
    private final List<Room> rooms = new ArrayList<>();

    @OneToMany
    private final List<Role> roles = new ArrayList<>();

    @OneToMany
    private final List<Race> races = new ArrayList<>();

    public Dungeon(String ADungeonName, Long ADungeonMaster, Long APlayerMaxSize,
                   Long AStartRoomId, Long ADefaultInventoryCapacity, Character ACommandSymbol) {
        this.dungeonName = ADungeonName;
        this.dungeonMasterId = ADungeonMaster;
        this.playerMaxSize = APlayerMaxSize;
        this.startRoomId = AStartRoomId;
        this.defaultInventoryCapacity = ADefaultInventoryCapacity;
        this.commandSymbol = ACommandSymbol;
    }

    public Dungeon() {

    }

    public Long getDungeonId() {
        return dungeonId;
    }

    public void setDungeonId(Long ADungeonId) {
        this.dungeonId = ADungeonId;
    }

    public String getDungeonName() {
        return dungeonName;
    }

    public void setDungeonName(String ADungeonName) {
        this.dungeonName = ADungeonName;
    }

    public Visibility getDungeonVisibility() {
        return dungeonVisibility;
    }

    public void setDungeonVisibility(Visibility ADungeonVisibility) {
        this.dungeonVisibility = ADungeonVisibility;
    }

    public Status getDungeonStatus() {
        return dungeonStatus;
    }

    public void setDungeonStatus(Status ADungeonStatus) {
        this.dungeonStatus = ADungeonStatus;
    }

    public Long getDungeonMasterId() {
        return dungeonMasterId;
    }

    public void setDungeonMasterId(Long ADungeonMasterId) {
        this.dungeonMasterId = ADungeonMasterId;
    }

    public Long getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(Long APlayerCount) {
        this.playerCount = APlayerCount;
    }

    public Long getPlayerMaxSize() {
        return playerMaxSize;
    }

    public void setPlayerMaxSize(Long APlayerMaxSize) {
        this.playerMaxSize = APlayerMaxSize;
    }

    public Long getStartRoomId() {
        return startRoomId;
    }

    public void setStartRoomId(Long AStartRoomId) {
        this.startRoomId = AStartRoomId;
    }

    public Long getDefaultInventoryCapacity() {
        return defaultInventoryCapacity;
    }

    public void setDefaultInventoryCapacity(Long ADefaultInventoryCapacity) {
        this.defaultInventoryCapacity = ADefaultInventoryCapacity;
    }

    public Character getCommandSymbol() {
        return commandSymbol;
    }

    public void setCommandSymbol(Character ACommandSymbol) {
        this.commandSymbol = ACommandSymbol;
    }

    public List<Avatar> getAvatars() {
        return avatars;
    }

    public List<User> getAllowedUsers() {
        return allowedUsers;
    }

    public List<User> getBlockedUsers() {
        return blockedUsers;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public List<Race> getRaces() {
        return races;
    }
}
