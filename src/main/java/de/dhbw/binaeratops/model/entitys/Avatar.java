package de.dhbw.binaeratops.model.entitys;

import de.dhbw.binaeratops.model.api.AvatarI;

import javax.persistence.*;

/**
 * Entity Objekt für einen Avatar.
 *
 * Es repräsentiert die Entity "Avatar" der Datenbank in der Programmlogik.
 *
 * Es implementiert dazu alle Funktionalitäten der Avatar Schnittstelle.
 *
 * @see AvatarI
 */
@Entity
public class Avatar implements AvatarI {

    @Id
    @GeneratedValue
    private Long avatarId;

    private Long userId;

    private Long dungeonId;

    private Long roomId;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String name;

    private Long inventoryId;

    private Long raceId;

    private Long roleId;

    /**
     * Konstruktor zum Erzeugen eines Avatars mit allen Eigenschaften.
     *
     * @param ADungeonId Dungeon, dem der Avatar zugeordnet ist.
     * @param AUserId Benutzer, dem der Avatar zugeordnet ist.
     * @param ARoomId Raum des Avatars, in dem er sich befindet.
     * @param AGender Geschlecht des Avatars.
     * @param AName Name des Avatars.
     * @param AInventoryId Inventar des Avatars.
     * @param ARaceId Rasse des Avatars.
     * @param ARoleId Rolle des Avatars.
     */
    public Avatar(long ADungeonId, long AUserId, long ARoomId, Gender AGender, String AName, long AInventoryId, long ARaceId, long ARoleId) {
        this.dungeonId = ADungeonId;
        this.userId = AUserId;
        this.roomId = ARoomId;
        this.gender = AGender;
        this.name = AName;
        this.inventoryId = AInventoryId;
        this.raceId = ARaceId;
        this.roleId = ARoleId;
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
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long AUserId) {
        userId = AUserId;
    }

    @Override
    public Long getDungeonId() {
        return dungeonId;
    }

    @Override
    public void setDungeonId(Long ADungeonId) {
        dungeonId = ADungeonId;
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

    @Override
    public Long getInventoryId() {
        return inventoryId;
    }

    @Override
    public void setInventoryId(Long AInventoryId) {
        inventoryId = AInventoryId;
    }

    @Override
    public Long getRaceId() {
        return raceId;
    }

    @Override
    public void setRaceId(Long ARaceId) {
        raceId = ARaceId;
    }

    @Override
    public Long getRoleId() {
        return roleId;
    }

    @Override
    public void setRoleId(Long ARoleId) {
        roleId = ARoleId;
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
                .append("]");
        return s.toString();
    }
}