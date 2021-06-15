package de.dhbw.binaeratops.model.entitys;

import de.dhbw.binaeratops.model.api.AttendanceI;

import javax.persistence.*;

/**
 * @author Lukas Göpel
 * Date: 15.06.2021
 * Time: 09:28
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

    public Attendance(){}

    public Attendance(Dungeon ADungeon, Room ARoom){
        this.dungeon=ADungeon;
        this.room=ARoom;
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
