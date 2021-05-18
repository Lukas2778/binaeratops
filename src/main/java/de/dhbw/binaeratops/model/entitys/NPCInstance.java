package de.dhbw.binaeratops.model.entitys;

import de.dhbw.binaeratops.model.api.NPCInstanceI;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;

import javax.persistence.*;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Entity Objekt für eine NPC-Instanz.
 * <p>
 * Es repräsentiert die Entity "NPC-Instanz" der Datenbank in der Programmlogik.
 * <p>
 * Es implementiert dazu alle Funktionalitäten der NPC-Instanz Schnittstelle.
 * <p>
 *
 * @author Nicolas Haug
 * @see de.dhbw.binaeratops.model.api.NPCInstanceI
 * @see de.dhbw.binaeratops.model.api.NPCI
 */
@Entity
public class NPCInstance implements NPCInstanceI {
    @GeneratedValue
    @Id
    private Long npcInstanceId;

    @ManyToOne
    private NPC npc;

    @ManyToOne
    private Room room;

    /**
     * Standardkonstruktor zum Erzeugen einer NPC-Instanz mit ID, NPC-Blaupause und dem Raum der NPC-Instanz.
     *
     * @param npcInstanceId ID der NPC-Instanz.
     * @param npc           NPC-Blaupause.
     * @param room          Raum der NPC-Instanz.
     */
    public NPCInstance(Long npcInstanceId, NPC npc, Room room) {
        this.npcInstanceId = npcInstanceId;
        this.npc = npc;
        this.room = room;
    }

    /**
     * Standardkonstruktor.
     */
    public NPCInstance() {
    }

    public Long getNpcInstanceId() {
        return npcInstanceId;
    }

    public void setNpcInstanceId(Long ANpcInstanceId) {
        this.npcInstanceId = ANpcInstanceId;
    }

    public NPC getNpc() {
        return npc;
    }

    public void setNpc(NPC ANpc) {
        this.npc = ANpc;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room ARoom) {
        this.room = ARoom;
    }

    public String getNpcName() {
        return npc.getNpcName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NPCInstance that = (NPCInstance) o;
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

    /**
     * Prüft die Gültigkeit eines Objekts und konvertiert das API-Interface zur
     * erwarteten Klasse der Implementation.
     *
     * @param ANpcInstance Objekt.
     * @return Objekt.
     * @throws InvalidImplementationException Objekt ungültig.
     */
    public static NPCInstance check(NPCInstanceI ANpcInstance) throws InvalidImplementationException {
        if (!(ANpcInstance instanceof NPCInstance)) {
            throw new InvalidImplementationException(-1,
                    MessageFormat.format(ResourceBundle.getBundle("language").getString("error.invalid.implementation"),
                            NPCInstance.class, ANpcInstance.getClass()));
        }

        return (NPCInstance) ANpcInstance;
    }
}
