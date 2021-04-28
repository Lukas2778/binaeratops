package de.dhbw.binaeratops.model.entitys;

import de.dhbw.binaeratops.model.api.RoomI;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity Objekt für einen Raum.
 * <p>
 * Es repräsentiert die Entity "Raum" der Datenbank in der Programmlogik.
 * <p>
 * Es implementiert dazu alle Funktionalitäten der Raum Schnittstelle.
 * <p>
 * @see RoomI
 *
 * @author Nicolas Haug
 */
@Entity
public class Room implements RoomI {

    @Id
    @GeneratedValue
    private Long roomId;

    private String roomName;

    private String description;

    private Long northRoomId;

    private Long eastRoomId;

    private Long southRoomId;

    private Long westRoomId;

    @OneToMany
    private final List<Item> items = new ArrayList<>();

    @OneToMany
    private final List<NPC> npcs = new ArrayList<>();

    public Room(String ARoomName, String ADescription) {
        this.roomName = ARoomName;
        this.description = ADescription;
    }

    public Room() {

    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long ARoomId) {
        this.roomId = ARoomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String ARoomName) {
        this.roomName = ARoomName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String ADescription) {
        this.description = ADescription;
    }

    public Long getNorthRoomId() {
        return northRoomId;
    }

    public void setNorthRoomId(Long ANorthRoomId) {
        this.northRoomId = ANorthRoomId;
    }

    public Long getEastRoomId() {
        return eastRoomId;
    }

    public void setEastRoomId(Long AEastRoomId) {
        this.eastRoomId = AEastRoomId;
    }

    public Long getSouthRoomId() {
        return southRoomId;
    }

    public void setSouthRoomId(Long ASouthRoomId) {
        this.southRoomId = ASouthRoomId;
    }

    public Long getWestRoomId() {
        return westRoomId;
    }

    public void setWestRoomId(Long AWestRoomId) {
        this.westRoomId = AWestRoomId;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<NPC> getNpcs() {
        return npcs;
    }
}
