package de.dhbw.binaeratops.model.api;

import de.dhbw.binaeratops.model.entitys.Item;
import de.dhbw.binaeratops.model.entitys.NPC;

import java.util.List;

/**
 * Schnittstelle für einen Raum.
 * <p>
 * Sie stellt alle Funktionalitäten zum Umgang mit einem Raum bereit.
 * <p>
 * Für Datenbankoperationen siehe {@link de.dhbw.binaeratops.model.repository.RoomRepositoryI}.
 * <p>
 * Für Implementierung siehe {@link de.dhbw.binaeratops.model.entitys.Room}
 *
 * @author Nicolas Haug
 */
public interface RoomI {

    /**
     * Gibt die ID des Raumes zurück.
     *
     * @return ID des Raumes.
     */
    Long getRoomId();

    /**
     * Setzt die ID des Raumes.
     *
     * @param ARoomId Zu setzende ID des Raumes.
     */
    void setRoomId(Long ARoomId);

    /**
     * Gibt den Namen des Raumes zurück.
     *
     * @return Name des Raumes.
     */
    String getRoomName();

    /**
     * Setzt den Namen des Raumes.
     *
     * @param ARoomName Zu setzender Name des Raumes.
     */
    void setRoomName(String ARoomName);

    /**
     * Gibt die Beschreibung des Raumes zurück.
     *
     * @return Beschreibung des Raumes.
     */
    String getDescription();

    /**
     * Setzt die Beschreibung des Raumes.
     *
     * @param ADescription Zu setzende Beschreibung des Raumes.
     */
    void setDescription(String ADescription);

    /**
     * Gibt ID des nördlichen Nachbarraumes zurück.
     *
     * @return ID des nördlichen Nachbarraumes.
     */
    Long getNorthRoomId();

    /**
     * Setzt die ID des nördlichen Nachbarraumes.
     *
     * @param ANorthRoomId Zu setzende ID des nördlichen Nachbarraumes.
     */
    void setNorthRoomId(Long ANorthRoomId);

    /**
     * Gibt ID des östlichen Nachbarraumes zurück.
     *
     * @return ID des östlichen Nachbarraumes.
     */
    Long getEastRoomId();

    /**
     * Setzt die ID des östlichen Nachbarraumes.
     *
     * @param AEastRoomId Zu setzende ID des östlichen Nachbarraumes.
     */
    void setEastRoomId(Long AEastRoomId);

    /**
     * Gibt ID des südlichen Nachbarraumes zurück.
     *
     * @return ID des südlichen Nachbarraumes.
     */
    Long getSouthRoomId();

    /**
     * Setzt die ID des südlichen Nachbarraumes.
     *
     * @param ASouthRoomId Zu setzende ID des südlichen Nachbarraumes.
     */
    void setSouthRoomId(Long ASouthRoomId);

    /**
     * Gibt ID des westlichen Nachbarraumes zurück.
     *
     * @return ID des westlichen Nachbarraumes.
     */
    Long getWestRoomId();

    /**
     * Setzt die ID des westlichen Nachbarraumes.
     *
     * @param AWestRoomId Zu setzende ID des westlichen Nachbarraumes.
     */
    void setWestRoomId(Long AWestRoomId);

    /**
     * Gibt die Gegenstände des Raumes als Liste von Gegenständes zurück.
     *
     * @return Liste von Gegenständen im Raum.
     */
    List<Item> getItems();

    /**
     * Fügt einem Raum einen Gegenstand hinzu.
     * @param AItem Gegenstand, der hinzugefügt werden soll.
     */
    void addItem(Item AItem);

    /**
     * Entfernt den übergebenen Gegenstand dem Raum.
     * @param AItem Gegenstand, der entfernt werden soll.
     */
    void removeItem(Item AItem);

    /**
     * Gibt die NPCs des Raumes als Liste von NPCs zurück.
     *
     * @return Liste von NPCs im Raum.
     */
    List<NPC> getNpcs();

    /**
     * Fügt dem Raum den NPC hinzu.
     * @param ANpc NPC, der hinzugefügt werden soll.
     */
    void addNpc(NPC ANpc);

    /**
     * Entfernt dem Raum den NPC.
     * @param ANpc NPC, der entfernt werden soll.
     */
    void removeNPC(NPC ANpc);

    /**
     * Gibt die X-Koordinate des Raumes zurück.
     * @return X-Koordinate.
     */
    Integer getXCoordinate();

    /**
     * Setzt die X-Koordinate des Raumes.
     * @param AXCoordinate Zu setzende X-Koordinate des Raumes.
     */
    void setXCoordinate(Integer AXCoordinate);

    /**
     * Gibt die Y-Koordinate des Raumes zurück.
     * @return Y-Koordinate des Raumes.
     */
    Integer getYCoordinate();

    /**
     * Setzt die Y-Koordinate des Raumes.
     * @param AYCoordinate Zu setzende Y-Koordinate des Raumes.
     */
    void setYCoordinate(Integer AYCoordinate);
}