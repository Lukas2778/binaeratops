package de.dhbw.binaeratops.model.api;

import de.dhbw.binaeratops.model.entitys.Item;
import de.dhbw.binaeratops.model.entitys.Race;

import java.util.List;

/**
 * Schnittstelle für einen NPC.
 * <p>
 * Sie stellt alle Funktionalitäten zum Umgang mit einem NPC bereit.
 * <p>
 * Für Datenbankoperationen siehe {@link de.dhbw.binaeratops.model.repository.NPCRepositoryI}.
 * <p>
 * Für Implementierung siehe {@link de.dhbw.binaeratops.model.entitys.NPC}
 *
 * @author Nicolas Haug
 */
public interface NPCI {

    /**
     * Gibt die ID des NPCs zurück.
     *
     * @return ID des NPCs.
     */
    Long getNpcId();

    /**
     * Setzt die NPC-ID des NPCs.
     *
     * @param ANpcId Zu setzende NPC-ID des NPCs.
     */
    void setNpcId(Long ANpcId);

    /**
     * Gibt den Namen des NPCs zurück.
     *
     * @return Name des NPCs.
     */
    String getNpcName();

    /**
     * Setzt den Namen des NPCs.
     *
     * @param ANpcName Zu setzender Name des NPCs.
     */
    void setNpcName(String ANpcName);

    /**
     * Gibt die Rasse des NPCs zurück.
     *
     * @return Rasse des NPCs.
     */
    Race getRace();

    /**
     * Setzt die Rasse des NPCs.
     *
     * @param ARace Zu setzende Rasse des NPCs.
     */
    void setRace(Race ARace);

    /**
     * Gibt die Beschreibung des NPCs zurück.
     *
     * @return Beschreibung des NPCs.
     */
    String getDescription();

    /**
     * Setzt die Beschreibung des NPCs.
     *
     * @param ADecription Zu setzende Beschreibung des NPCs.
     */
    void setDescription(String ADecription);

    /**
     * Gibt das Gepäck des NPCs zurück.
     * <p>
     * Unter Gepäck versteht man in diesem Kontext, dass der NPC beliebige
     * Gegenstände mit sich tragen kann.
     *
     * @return Gepäck des NPCs.
     */
    List<Item> getLuggage();
}
