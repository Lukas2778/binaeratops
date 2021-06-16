package de.dhbw.binaeratops.model.entitys;

import de.dhbw.binaeratops.model.api.AttendanceI;

import javax.persistence.*;

/**
 * Entity Objekt für einen Raumaufenthalt.
 * <p>
 * Es repräsentiert die Entity "Raumaufenthalt" der Datenbank in der Programmlogik.
 * <p>
 * Es implementiert dazu alle Funktionalitäten der Raumaufenthalt Schnittstelle.
 * <p>
 *
 * @author Lukas Göpel, Nicolas Haug
 * @see AttendanceI
 */
@Entity
public class Attendance implements AttendanceI {

    @Id
    @GeneratedValue
    private Long attendanceId;

    @OneToOne
    private Dungeon dungeon;

    @ManyToOne
    private Avatar avatar;

    @ManyToOne
    private Room room;

    /**
     * Konstruktor zum Erzeugen eines Raumaufenhalts mit Dungeon und Raum.
     *
     * @param ADungeon Dungeon, des Aufenthalts.
     * @param ARoom    Raum, des Aufenthalts.
     */
    public Attendance(Dungeon ADungeon, Room ARoom) {
        this.dungeon = ADungeon;
        this.room = ARoom;
    }

    /**
     * Standardkonstruktor zum Erzeugen eines Raumaufenthalts.
     */
    public Attendance() {
    }

    @Override
    public Long getAttendanceId() {
        return attendanceId;
    }

    @Override
    public void setAttendanceId(Long AAttendanceId) {
        this.attendanceId = AAttendanceId;
    }

    @Override
    public Dungeon getDungeon() {
        return dungeon;
    }

    @Override
    public void setDungeon(Dungeon ADungeon) {
        this.dungeon = ADungeon;
    }

    @Override
    public Avatar getAvatar() {
        return avatar;
    }

    @Override
    public void setAvatar(Avatar AAvatar) {
        this.avatar = AAvatar;
    }

    @Override
    public Room getRoom() {
        return room;
    }

    @Override
    public void setRoom(Room ARoom) {
        this.room = ARoom;
    }
}
