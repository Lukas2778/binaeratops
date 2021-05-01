package de.dhbw.binaeratops.model.entitys;

import de.dhbw.binaeratops.model.api.RoomI;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

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

    private Integer xCoordinate;

    private Integer yCoordinate;

    /**
     * Konstruktor zum Erzeugen eines Raumes mit allen Eigenschaften.
     *
     * @param ARoomName Name des Raumes.
     * @param ADescription Beschreibung des Raumes.
     */
    public Room(String ARoomName, String ADescription) {
        this.roomName = ARoomName;
        this.description = ADescription;
    }

    /**
     * Konstruktor zum Erzeugen eines Raumes mit allen Eigenschaften.
     *

     * @param AXCoordinate Positon des Raums.
     * @param AYCoordinate Positon des Raums.
     */
    public Room( int AXCoordinate, int AYCoordinate) {

        this.xCoordinate=AXCoordinate;
        this.yCoordinate=AYCoordinate;
    }

    /**
     * Standardkonstruktor zum Erzeugen eines Raumes.
     */
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

    public Integer getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(Integer xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public Integer getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(Integer yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    @Override
    public boolean equals(Object AOther) {
        boolean equals = this == AOther;

        if (!equals && AOther instanceof Room) {
            Room other = (Room) AOther;
            equals = (roomId == other.roomId);
            // && (name == other.name || (name != null &&
            //                    name.equalsIgnoreCase(other.name)))
        }

        return equals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId);
    }

    //TODO position hinzufügen
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Raum[ID = ")
                .append(roomId)
                .append(" | Name = ")
                .append(roomName)
                .append(" | Beschreibung = ")
                .append(description)
                .append("]\n");
        return s.toString();
    }

    /**
     * Prüft die Gültigkeit eines Objekts und konvertiert das API-Interface zur
     * erwarteten Klasse der Implementation.
     *
     * @param ARoom Objekt.
     * @return Objekt.
     * @throws InvalidImplementationException Objekt ungültig.
     */
    public static Room check(RoomI ARoom) throws InvalidImplementationException {
        if (!(ARoom instanceof Room)) {
            throw new InvalidImplementationException(-1,
                    MessageFormat.format(ResourceBundle.getBundle("language").getString("error.invalid.implementation"),
                            Room.class, ARoom.getClass()));
        }

        return (Room) ARoom;
    }
}
