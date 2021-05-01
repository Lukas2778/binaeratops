package de.dhbw.binaeratops.model.entitys;

import de.dhbw.binaeratops.model.api.DungeonI;
import de.dhbw.binaeratops.model.enums.Status;
import de.dhbw.binaeratops.model.enums.Visibility;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;

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

    /**
     * Konstruktor zum Erzeugen eines Dungeons mit allen Eigenschaften.
     *
     * @param ADungeonName Name des Dungeons.
     * @param ADungeonMaster ID des Dungeon-Masters.
     * @param APlayerMaxSize Maximale Spieleranzahl.
     * @param AStartRoomId ID des Startraumes.
     * @param ADefaultInventoryCapacity Standardinventarkapazität des Dungeons.
     * @param ACommandSymbol Befehlszeichen des Dungeons.
     */
    public Dungeon(String ADungeonName, Long ADungeonMaster, Long APlayerMaxSize,
                   Long AStartRoomId, Long ADefaultInventoryCapacity, Character ACommandSymbol) {
        this.dungeonName = ADungeonName;
        this.dungeonMasterId = ADungeonMaster;
        this.playerMaxSize = APlayerMaxSize;
        this.startRoomId = AStartRoomId;
        this.defaultInventoryCapacity = ADefaultInventoryCapacity;
        this.commandSymbol = ACommandSymbol;
    }

    /**
     * Konstruktor zum Erzeugen eines Dungeons mit dem Namen.
     * @param ADungeonName Name des Dungeons.
     */
    public Dungeon(String ADungeonName) {
        this.dungeonName = ADungeonName;
    }

    /**
     * Konstruktor zum Erzeugen eines Dungeons mit dem Namen und Dungeon-Master.
     * @param ADungeonName Name des Dungeons.
     * @param ADungeonMaster ID des Dungeon-Masters.
     */
    public Dungeon(String ADungeonName, Long ADungeonMaster) {
        this.dungeonName = ADungeonName;
        this.dungeonMasterId = ADungeonMaster;
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

    @Override
    public boolean equals(Object AOther) {
        boolean equals = this == AOther;

        if (!equals && AOther instanceof Dungeon) {
            Dungeon other = (Dungeon) AOther;
            equals = (dungeonId == other.dungeonId);
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
