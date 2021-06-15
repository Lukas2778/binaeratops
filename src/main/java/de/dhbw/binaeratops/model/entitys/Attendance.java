package de.dhbw.binaeratops.model.entitys;

import javax.persistence.*;

/**
 * @author Lukas Göpel
 * Date: 15.06.2021
 * Time: 09:28
 */

@Entity
public class Attendance{

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

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long AAttendanceId) {
        this.attendanceId = AAttendanceId;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public void setDungeon(Dungeon ADungeon) {
        this.dungeon = ADungeon;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar AAvatar) {
        this.avatar = AAvatar;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room ARoom) {
        this.room = ARoom;
    }
}
