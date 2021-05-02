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
 * @see AvatarI
 *
 * @author Nicolas Haug
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

    @ManyToOne
    private User user;

    @ManyToOne
    private Dungeon dungeon;

    @OneToMany(mappedBy = "inventoryAvatar", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Item> inventory = new ArrayList<>();

    @OneToMany(mappedBy = "equipmentAvatar", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Item> equipment = new ArrayList<>();

    @OneToOne
    private Race race;

    @OneToOne
    private Role role;

    /**
     * Konstruktor zum Erzeugen eines Avatars mit allen Eigenschaften.
     *
     * @param ARoomId Raum des Avatars, in dem er sich befindet.
     * @param AGender Geschlecht des Avatars.
     * @param AName Name des Avatars.
     * @param ARace Rasse des Avatars.
     * @param ARole Rolle des Avatars.
     */
    public Avatar(Long ARoomId, Gender AGender, String AName, Race ARace, Role ARole) {
        this.roomId = ARoomId;
        this.gender = AGender;
        this.name = AName;
        this.race = ARace;
        this.role = ARole;
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

    public List<Item> getInventory() {
        return inventory;
    }

    public void addInventoryItem(Item AItem) {
        AItem.setInventoryAvatar(this);
        inventory.add(AItem);
    }

    public void removeInventoryItem(Item AItem) {
        inventory.remove(AItem);
        AItem.setRoom(null);
    }

    public List<Item> getEquipment() {
        return equipment;
    }

    public void addEquipmentItem(Item AItem) {
        AItem.setEquipmentAvatar(this);
        equipment.add(AItem);
    }

    public void removeEquipmentItem(Item AItem) {
        equipment.remove(AItem);
        AItem.setRoom(null);
    }

    @Override
    public boolean equals(Object AOther) {
        boolean equals = this == AOther;

        if (!equals && AOther instanceof Avatar) {
            Avatar other = (Avatar) AOther;
            equals = (avatarId == other.avatarId);
            // && (name == other.name || (name != null &&
            //                    name.equalsIgnoreCase(other.name)))
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