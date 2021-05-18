package de.dhbw.binaeratops.model.api;

import de.dhbw.binaeratops.model.entitys.Avatar;
import de.dhbw.binaeratops.model.entitys.Item;
import de.dhbw.binaeratops.model.entitys.NPC;
import de.dhbw.binaeratops.model.entitys.Room;

/**
 * Schnittstelle für eine Gegenstand-Instanz.
 * <p>
 * Sie stellt alle Funktionalitäten zum Umgang mit einer Gegenstand-Instanz bereit.
 * <p>
 * Für Datenbankoperationen siehe {@link de.dhbw.binaeratops.model.repository.ItemInstanceRepositoryI}.
 * <p>
 * Für Implementierung siehe {@link de.dhbw.binaeratops.model.entitys.ItemInstance}
 *
 * @author Nicolas Haug
 */
public interface ItemInstanceI {

    /**
     * Gibt die ID der Gegenstand-Instanz zurück.
     *
     * @return ID der Gegenstand-Instanz.
     */
    Long getItemInstanceId();

    /**
     * Setzt die ID der Gegenstand-Instanz.
     *
     * @param AItemId ID der Gegenstand-Instanz.
     */
    void setItemInstanceId(Long AItemId);

    /**
     * Gibt die Gegenstand-Blaupause der Gegenstand-Instanz zurück.
     *
     * @return Gegenstand-Blaupause.
     */
    Item getItem();

    /**
     * Setzt die Gegenstand-Blaupause der Gegenstand-Instanz.
     *
     * @param AItem Gegenstand-Blaupause.
     */
    void setItem(Item AItem);

    /**
     * Gibt den Raum des Gegenstandes zurück.
     *
     * @return Raum des Gegenstandes.
     */
    Room getRoom();

    /**
     * Setzt den Raum des Gegenstandes.
     *
     * @param ARoom Raum des Gegenstandes.
     */
    void setRoom(Room ARoom);

    /**
     * Gibt die Zuordnung zum Inventar des Avatars zurück.
     *
     * @return Zuordnung zum Inventar des Avatars.
     */
    Avatar getInventoryAvatar();

    /**
     * Setzt die Zuordnung des Gegenstandes zu dem Inventar des Avatars.
     *
     * @param AInventoryAvatar Zuordnung des Gegenstandes zu einem Inventar des Avatars.
     */
    void setInventoryAvatar(Avatar AInventoryAvatar);

    /**
     * Gibt die Zuordnung zum Equipment des Avatars zurück.
     *
     * @return Zuordnung zum Equipment des Avatars.
     */
    Avatar getEquipmentAvatar();

    /**
     * Setzt die Zuordnung des Gegenstandes zu dem Equipment des Avatars.
     *
     * @param AEquipmentAvatar Zuordnung des Gegenstandes zu einem Inventar des Avatars.
     */
    void setEquipmentAvatar(Avatar AEquipmentAvatar);

    /**
     * Gibt die Zuordnung zum Gepäck des NPCs zurück.
     *
     * @return Zuordnung zum Gepäck des NPCs.
     */
    NPC getNpc();

    /**
     * Setzt die Zuordnung des Gegenstandes zu dem Gepäck des NPCs.
     *
     * @param ANpc Zuordnung des Gegenstandes zu dem Gepäck des NPCs.
     */
    void setNpc(NPC ANpc);
}
