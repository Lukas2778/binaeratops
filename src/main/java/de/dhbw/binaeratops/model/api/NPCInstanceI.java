package de.dhbw.binaeratops.model.api;

import de.dhbw.binaeratops.model.entitys.NPC;
import de.dhbw.binaeratops.model.entitys.Room;

/**
 * Schnittstelle für eine NPC-Instanz.
 * <p>
 * Sie stellt alle Funktionalitäten zum Umgang mit einer NPC-Instanz bereit.
 * <p>
 * Für Datenbankoperationen siehe {@link de.dhbw.binaeratops.model.repository.NpcInstanceRepositoryI}.
 * <p>
 * Für Implementierung siehe {@link de.dhbw.binaeratops.model.entitys.NPCInstance}
 *
 * @author Nicolas Haug
 */
public interface NPCInstanceI {

    /**
     * Gibt die ID der NPC-Instanz zurück.
     *
     * @return ID der NPC-Instanz.
     */
    Long getNpcInstanceId();

    /**
     * Setzt die ID der NPC-Instanz.
     *
     * @param ANpcInstanceId ID der NPC-Instanz.
     */
    void setNpcInstanceId(Long ANpcInstanceId);

    /**
     * Gibt die NPC-Blaupause der NPC-Instanz zurück.
     *
     * @return NPC-Blaupause der NPC-Instanz
     */
    NPC getNpc();

    /**
     * Setzt die NPC-Blaupause der NPC-Instanz.
     *
     * @param ANpc NPC-Blaupause der NPC-Instanz
     */
    void setNpc(NPC ANpc);

    /**
     * Gibt den Raum des NPCs zurück.
     *
     * @return Raum des NPCs.
     */
    Room getRoom();

    /**
     * Setzt den Raum des NPCs.
     *
     * @param ARoom Raum des NPCs.
     */
    void setRoom(Room ARoom);

    /**
     * Gibt den Namen der NPC-Blaupause zurück.
     * @return Name der NPC-Blaupause.
     */
    String getNpcName();
}
