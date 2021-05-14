package de.dhbw.binaeratops.model.entitys;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Matthias Rall
 * Date: 09.05.2021
 * Time: 18:17
 */
@Entity
public class NpcInstance {
    @GeneratedValue
    @Id
    private Long npcInstanceId;

    @ManyToOne
    private NPC npc;

    @ManyToOne
    private Room room;

    public NpcInstance(Long npcInstanceId, NPC npc, Room room) {
        this.npcInstanceId = npcInstanceId;
        this.npc = npc;
        this.room = room;
    }

    public NpcInstance(){}

    public Long getNpcInstanceId() {
        return npcInstanceId;
    }

    public void setNpcInstanceId(Long npcInstanceId) {
        this.npcInstanceId = npcInstanceId;
    }

    public NPC getNpc() {
        return npc;
    }

    public void setNpc(NPC npc) {
        this.npc = npc;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NpcInstance that = (NpcInstance) o;
        return Objects.equals(npcInstanceId, that.npcInstanceId) && Objects.equals(npc, that.npc) && Objects.equals(room, that.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(npcInstanceId, npc, room);
    }

    @Override
    public String toString() {
        return "NpcInstance{" +
                "npcInstanceId=" + npcInstanceId +
                ", npc=" + npc +
                ", room=" + room +
                '}';
    }

    public String getNpcName() {
        return npc.getNpcName();
    }
}
