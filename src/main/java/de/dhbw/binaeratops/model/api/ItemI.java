package de.dhbw.binaeratops.model.api;

import de.dhbw.binaeratops.model.entitys.Avatar;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.NPC;
import de.dhbw.binaeratops.model.entitys.Room;
import de.dhbw.binaeratops.model.enums.ItemType;

/**
 * Schnittstelle für einen Gegenstand.
 * <p>
 * Sie stellt alle Funktionalitäten zum Umgang mit einem Gegenstand bereit.
 * <p>
 * Für Datenbankoperationen siehe {@link de.dhbw.binaeratops.model.repository.ItemRepositoryI}.
 * <p>
 * Für Implementierung siehe {@link de.dhbw.binaeratops.model.entitys.Item}
 *
 * @author Nicolas Haug
 */
public interface ItemI {

    /**
     * Gibt die ID des Gegenstandes zurück.
     *
     * @return ID des Gegenstandes.
     */
    Long getItemId();

    /**
     * Setzt die ID des Gegenstandes.
     *
     * @param AItemId Zu setzende ID des Gegenstandes.
     */
    void setItemId(Long AItemId);

    /**
     * Gibt den Namen des Gegenstandes zurück.
     *
     * @return Name des Gegenstandes.
     */
    String getItemName();

    /**
     * Setzt den Namen des Gegenstandes.
     *
     * @param AItemName Zu setzender Name des Gegenstandes.
     */
    void setItemName(String AItemName);

    /**
     * Gibt die Größe des Gegenstandes zurück.
     *
     * @return Größe des Gegenstandes.
     */
    Long getSize();

    /**
     * Setzt die Größe des Gegenstandes.
     *
     * @param ASize Zu setzende Größe des Gegenstandes.
     */
    void setSize(Long ASize);

    /**
     * Gibt die Beschreibung des Gegenstandes zurück.
     *
     * @return Beschreibung des Gegenstandes.
     */
    String getDescription();

    /**
     * Setzt die Beschreibung des Gegenstandes.
     *
     * @param ADescription Zu setzende Beschreibung des Gegenstandes.
     */
    void setDescription(String ADescription);

    /**
     * Gibt den Art des Gegenstandes zurück.
     *
     * @return Art des Gegenstandes.
     * @see ItemType
     */
    ItemType getType();

    /**
     * Setzt die Art des Gegenstandes.
     *
     * @param AType Zu setzende Art des Gegenstandes.
     * @see ItemType
     */
    void setType(ItemType AType);

    /**
     * Gibt den Dungeon des Gegenstandes zurück.
     *
     * @return Dungeon des Gegenstandes.
     */
    Dungeon getDungeon();

    /**
     * Setzt den Dungeon des Gegenstandes.
     *
     * @param ADungeon Dungeon des Gegenstandes.
     */
    void setDungeon(Dungeon ADungeon);

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
