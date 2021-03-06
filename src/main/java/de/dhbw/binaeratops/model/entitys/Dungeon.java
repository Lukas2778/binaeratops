package de.dhbw.binaeratops.model.entitys;

import de.dhbw.binaeratops.model.api.DungeonI;
import de.dhbw.binaeratops.model.enums.Status;
import de.dhbw.binaeratops.model.enums.Visibility;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Entity Objekt für einen Dungeon.
 * <p>
 * Es repräsentiert die Entity "Dungeon" der Datenbank in der Programmlogik.
 * <p>
 * Es implementiert dazu alle Funktionalitäten der Dungeon Schnittstelle.
 * <p>
 *
 * @author Nicolas Haug
 * @see DungeonI
 */
@Entity
public class Dungeon implements DungeonI {

    @Id
    @GeneratedValue
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    private Long dungeonId;

    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    @NotNull
    private String dungeonName;

    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    @Enumerated(EnumType.STRING)
    private Visibility dungeonVisibility;

    //@Column(columnDefinition = "varchar(255) default 'INACTIVE'")
    @Enumerated(EnumType.STRING)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    private Status dungeonStatus;

    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    private Long dungeonMasterId;

    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    private Long playerCount;

    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    private Long playerMaxSize;

    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    private Long startRoomId;

    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    private Long defaultInventoryCapacity;

    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    private String description;

    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    private Character commandSymbol;

    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    private Long standardAvatarLifepoints;

    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    @ManyToOne
    private User user; //Ersteller des Dungeons

    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    @OneToMany(mappedBy = "dungeon", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Avatar> avatars = new ArrayList<>();

    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    @OneToMany(mappedBy = "allowedDungeon", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, orphanRemoval = true)
    private final List<Permission> allowedUsers = new ArrayList<>();

    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    @OneToMany(mappedBy = "blockedDungeon", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, orphanRemoval = true)
    private final List<Permission> blockedUsers = new ArrayList<>();

    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    @OneToMany(mappedBy = "requestedDungeon", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, orphanRemoval = true)
    private final List<Permission> requestedUsers = new ArrayList<>();

    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    @OneToMany(mappedBy = "currentDungeon", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, orphanRemoval = true)
    private final List<User> currentUsers = new ArrayList<>();

    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    @OneToMany(mappedBy = "dungeon", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Room> rooms = new ArrayList<>();

    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    @OneToMany(mappedBy = "dungeon", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<NPC> npcs = new ArrayList<>();

    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    @OneToMany(mappedBy = "dungeon", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Item> items = new ArrayList<>();

    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    @OneToMany(mappedBy = "dungeon", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Role> roles = new ArrayList<>();

    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    @OneToMany(mappedBy = "dungeon", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Race> races = new ArrayList<>();

    /**
     * Konstruktor zum Erzeugen eines Dungeons mit allen Eigenschaften.
     *
     * @param ADungeonName              Name des Dungeons.
     * @param ADungeonMaster            ID des Dungeon-Masters.
     * @param APlayerMaxSize            Maximale Spieleranzahl.
     * @param AStartRoomId              ID des Startraumes.
     * @param ADefaultInventoryCapacity Standardinventarkapazität des Dungeons.
     * @param ACommandSymbol            Befehlszeichen des Dungeons.
     * @param AStandardAvatarLifepoints Standardavatarlebenspunkte des Dungeons.
     */
    public Dungeon(String ADungeonName, Long ADungeonMaster, Long APlayerMaxSize,
                   Long AStartRoomId, Long ADefaultInventoryCapacity, Character ACommandSymbol, Long AStandardAvatarLifepoints) {
        this.dungeonName = ADungeonName;
        this.dungeonMasterId = ADungeonMaster;
        this.playerMaxSize = APlayerMaxSize;
        this.startRoomId = AStartRoomId;
        this.defaultInventoryCapacity = ADefaultInventoryCapacity;
        this.commandSymbol = ACommandSymbol;
        this.standardAvatarLifepoints = AStandardAvatarLifepoints;
    }

    /**
     * Konstruktor zum Erzeugen eines Dungeons mit dem Namen.
     *
     * @param ADungeonName Name des Dungeons.
     */
    public Dungeon(String ADungeonName) {
        this.dungeonName = ADungeonName;
    }

    /**
     * Konstruktor zum Erzeugen eines Dungeons mit dem Namen und Dungeon-Master.
     *
     * @param ADungeonName   Name des Dungeons.
     * @param ADungeonMaster ID des Dungeon-Masters.
     */
    public Dungeon(String ADungeonName, Long ADungeonMaster) {
        this.dungeonName = ADungeonName;
        this.dungeonMasterId = ADungeonMaster;
    }

    /**
     * Konstruktor zum Erzeugen eines Dungeons mit dem Namen, dem Dungeon Master, der maximalen Spieleranzahl und der Sichtbarkeit.
     *
     * @param ADungeonName   Name des Dungeons.
     * @param ADungeonMaster ID des Dungeon-Masters.
     * @param APlayerMaxSize Maximale Spieleranzahl.
     * @param AVisibility    Sichtbarkeit des Dungeons.
     */
    public Dungeon(String ADungeonName, Long ADungeonMaster, Long APlayerMaxSize, Visibility AVisibility) {
        this.dungeonName = ADungeonName;
        this.dungeonMasterId = ADungeonMaster;
        this.playerMaxSize = APlayerMaxSize;
        this.dungeonVisibility = AVisibility;
    }

    /**
     * Standardkonstruktor zum Erzeugen eines Dungeons.
     */
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String ADescription) {
        this.description = ADescription;
    }

    public Character getCommandSymbol() {
        return commandSymbol;
    }

    public void setCommandSymbol(Character ACommandSymbol) {
        this.commandSymbol = ACommandSymbol;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User AUser) {
        this.user = AUser;
    }

    public List<Avatar> getAvatars() {
        return avatars;
    }

    public Avatar getAvatarById(Long AAvatarId) {
        for (Avatar a : getAvatars()) {
            if (a.getAvatarId().equals(AAvatarId))
                return a;
        }
        return null;
    }

    public void addAvatar(Avatar AAvatar) {
        AAvatar.setDungeon(this);
        avatars.add(AAvatar);
    }

    public void removeAvatar(Avatar AAvatar) {
        avatars.remove(AAvatar);
        AAvatar.setDungeon(null);
    }

    public List<Permission> getAllowedUsers() {
        return allowedUsers;
    }

    public void addAllowedUser(Permission AUser) {
        AUser.setAllowedDungeon(this);
        allowedUsers.add(AUser);
    }

    public void removeAllowedUser(Permission AUser) {
        allowedUsers.remove(AUser);
        AUser.setAllowedDungeon(null);
    }

    public List<Permission> getBlockedUsers() {
        return blockedUsers;
    }

    public void addBlockedUser(Permission AUser) {
        if (!blockedUsers.contains(AUser)) {
            AUser.setBlockedDungeon(this);
            blockedUsers.add(AUser);
        }
    }

    public void removeBlockedUser(Permission AUser) {
        blockedUsers.remove(AUser);
        AUser.setBlockedDungeon(null);
    }

    public List<Permission> getRequestedUsers() {
        return requestedUsers;
    }

    public void addRequestedUser(Permission AUser) {
        AUser.setRequestedDungeon(this);
        requestedUsers.add(AUser);
    }

    public void removeRequestUser(Permission AUser) {
        AUser.setRequestedDungeon(null);
        requestedUsers.remove(AUser);
    }

    public List<User> getCurrentUsers() {
        return currentUsers;
    }

    public void addCurrentUser(User AUser) {
        currentUsers.add(AUser);
        setPlayerCount((long) currentUsers.size());
        AUser.setCurrentDungeon(this);
    }

    public void removeCurrentUser(User AUser) {
        currentUsers.remove(AUser);
        setPlayerCount((long) currentUsers.size());
        AUser.setCurrentDungeon(null);
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void addRoom(Room ARoom) {
        ARoom.setDungeon(this);
        rooms.add(ARoom);
    }

    public void removeRoom(Room ARoom) {
        rooms.remove(ARoom);
        ARoom.setDungeon(null);
    }

    public List<NPC> getNpcs() {
        return npcs;
    }

    public void addNpc(NPC ANpc) {
        ANpc.setDungeon(this);
        npcs.add(ANpc);
    }

    public void removeNpc(NPC ANpc) {
        npcs.remove(ANpc);
        ANpc.setDungeon(null);
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item AItem) {
        AItem.setDungeon(this);
        items.add(AItem);
    }

    public void removeItem(Item AItem) {
        items.remove(AItem);
        AItem.setDungeon(null);
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void addRole(Role ARole) {
        ARole.setDungeon(this);
        roles.add(ARole);
    }

    public void removeRole(Role ARole) {
        roles.remove(ARole);
        ARole.setDungeon(null);
    }

    public List<Race> getRaces() {
        return races;
    }

    public void addRace(Race ARace) {
        ARace.setDungeon(this);
        races.add(ARace);
    }

    public void removeRace(Race ARace) {
        races.remove(ARace);
        ARace.setDungeon(null);
    }

    public Long getStandardAvatarLifepoints() {
        return this.standardAvatarLifepoints;
    }

    public void setStandardAvatarLifepoints(Long AStandardAvatarLifepoints) {
        this.standardAvatarLifepoints = AStandardAvatarLifepoints;
    }

    @Override
    public boolean equals(Object AOther) {
        boolean equals = this == AOther;

        if (!equals && AOther instanceof Dungeon) {
            Dungeon other = (Dungeon) AOther;
            equals = (dungeonId.equals(other.dungeonId));
        }

        return equals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dungeonId);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Dungeon[ID = ")
                .append(dungeonId)
                .append(" | Name = ")
                .append(dungeonName)
                .append(" | Sichtbarkeit = ")
                .append(dungeonVisibility)
                .append(" | Status = ")
                .append(dungeonStatus)
                .append(" | Dungeon-Master = ")
                .append(dungeonMasterId)
                .append(" | Maximale Spieleranzahl = ")
                .append(playerMaxSize)
                .append(" | Startraum = ")
                .append(startRoomId)
                .append(" | Inventarkapazität = ")
                .append(defaultInventoryCapacity)
                .append(" | Befehlszeichen = ")
                .append(commandSymbol)
                .append("]\n");
        return s.toString();
    }

    /**
     * Prüft die Gültigkeit eines Objekts und konvertiert das API-Interface zur
     * erwarteten Klasse der Implementation.
     *
     * @param ADungeon Objekt.
     * @return Objekt.
     * @throws InvalidImplementationException Objekt ungültig.
     */
    public static Dungeon check(DungeonI ADungeon) throws InvalidImplementationException {
        if (!(ADungeon instanceof Dungeon)) {
            throw new InvalidImplementationException(-1,
                    MessageFormat.format(ResourceBundle.getBundle("language").getString("error.invalid.implementation"),
                            Dungeon.class, ADungeon.getClass()));
        }

        return (Dungeon) ADungeon;
    }
}
