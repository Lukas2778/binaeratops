package de.dhbw.binaeratops.model.entitys;

import de.dhbw.binaeratops.model.api.AvatarI;
import de.dhbw.binaeratops.model.enums.Gender;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;

import javax.persistence.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Entity Objekt für einen Avatar.
 * <p>
 * Es repräsentiert die Entity "Avatar" der Datenbank in der Programmlogik.
 * <p>
 * Es implementiert dazu alle Funktionalitäten der Avatar Schnittstelle.
 * <p>
 *
 * @author Nicolas Haug
 * @see AvatarI
 */
@Entity
public class Avatar implements AvatarI {

    @Id
    @GeneratedValue
    private Long avatarId;

    private Long roomId; // CURRENT

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String name;

    private Long lifepoints;

    private boolean active;

    private boolean requested = false;

    @ManyToOne
    private User user;

    @ManyToOne
    private Dungeon dungeon;

    @OneToMany(mappedBy = "inventoryAvatar", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ItemInstance> inventory = new ArrayList<>();

    @OneToMany(mappedBy = "equipmentAvatar", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ItemInstance> equipment = new ArrayList<>();

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Attendance> visitedRooms = new ArrayList<>();

    @OneToOne
    private Race race;

    @OneToOne
    private Role role;

    @OneToOne
    private Room currentRoom;

    /**
     * Konstruktor zum Erzeugen eines Avatars mit allen Eigenschaften.
     *
     * @param ARoom       Raum des Avatars, in dem er sich befindet.
     * @param AGender     Geschlecht des Avatars.
     * @param AName       Name des Avatars.
     * @param ARace       Rasse des Avatars.
     * @param ARole       Rolle des Avatars.
     * @param ALifepoints Lebenspunkte des Avatars.
     */
    public Avatar(Room ARoom, Gender AGender, String AName, Race ARace, Role ARole, Long ALifepoints) {
        this.currentRoom = ARoom;
        this.gender = AGender;
        this.name = AName;
        this.race = ARace;
        this.role = ARole;
        this.lifepoints = ALifepoints;
    }

    /**
     * Standardkonstruktor zum Erzeugen eines Avatars.
     */
    public Avatar() {

    }

    @Override
    public Long getAvatarId() {
        return avatarId;
    }

    @Override
    public void setAvatarId(Long AAvatarId) {
        avatarId = AAvatarId;
    }

    @Override
    public Long getRoomId() {
        return roomId;
    }

    @Override
    public void setRoomId(Long ARoomId) {
        roomId = ARoomId;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public void setGender(Gender AGender) {
        gender = AGender;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String AName) {
        name = AName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean AActive) {
        active = AActive;
    }

    public boolean hasRequested() {
        return requested;
    }

    public void setRequested(boolean ARequested) {
        requested = ARequested;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User AUser) {
        this.user = AUser;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public void setDungeon(Dungeon ADungeon) {
        this.dungeon = ADungeon;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race ARace) {
        this.race = ARace;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role ARole) {
        this.role = ARole;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room ACurrentRoom) {
        this.currentRoom = ACurrentRoom;
    }

    public List<ItemInstance> getInventory() {
        return inventory;
    }

    public void addInventoryItem(ItemInstance AItem) {
        AItem.setInventoryAvatar(this);
        inventory.add(AItem);
    }

    public void removeInventoryItem(ItemInstance AItem) {
        inventory.remove(AItem);
        AItem.setInventoryAvatar(null);
    }

    public List<ItemInstance> getEquipment() {
        return equipment;
    }

    public void addEquipmentItem(ItemInstance AItem) {
        AItem.setEquipmentAvatar(this);
        equipment.add(AItem);
    }

    public void removeEquipmentItem(ItemInstance AItem) {
        equipment.remove(AItem);
        AItem.setEquipmentAvatar(null);
    }

    public List<Attendance> getVisitedRooms() {
        return visitedRooms;
    }

    public void addVisitedRoom(Attendance AAttendance) {
        AAttendance.setAvatar(this);
        visitedRooms.add(AAttendance);
    }

    public void removeVisitedRoom(Attendance AAttendance) {
        visitedRooms.remove(AAttendance);
        AAttendance.setAvatar(null);
    }

    public Long getLifepoints() {
        return lifepoints;
    }

    public void setLifepoints(Long ALifepoints) {
        this.lifepoints = ALifepoints;
    }

    public void setLifepoints(Long ALifepoints, Long ALifepointsBonusRace, Long ALifepointsBonusRole) {
        this.lifepoints = ALifepoints + ALifepointsBonusRace + ALifepointsBonusRole;
    }

    @Override
    public boolean equals(Object AOther) {
        boolean equals = this == AOther;
        if (!equals && AOther instanceof Avatar) {
            Avatar other = (Avatar) AOther;
            equals = (avatarId == other.avatarId);
        }
        return equals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(avatarId);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Avatar[ID = ")
                .append(avatarId)
                .append(" | Geschlecht = ")
                .append(gender)
                .append(" | Name = ")
                .append(name)
                .append("]\n");
        return s.toString();
    }

    /**
     * Prüft die Gültigkeit eines Objekts und konvertiert das API-Interface zur
     * erwarteten Klasse der Implementation.
     *
     * @param AAvatar Objekt.
     * @return Objekt.
     * @throws InvalidImplementationException Objekt ungültig.
     */
    public static Avatar check(AvatarI AAvatar) throws InvalidImplementationException {
        if (!(AAvatar instanceof Avatar)) {
            throw new InvalidImplementationException(-1,
                    MessageFormat.format(ResourceBundle.getBundle("language").getString("error.invalid.implementation"),
                            Avatar.class, AAvatar.getClass()));
        }

        return (Avatar) AAvatar;
    }
}