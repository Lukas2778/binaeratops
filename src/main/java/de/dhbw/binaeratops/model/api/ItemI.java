package de.dhbw.binaeratops.model.api;

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
     *
     * @see ItemType
     */
    ItemType getType();

    /**
     * Setzt die Art des Gegenstandes.
     *
     * @param AType Zu setzende Art des Gegenstandes.
     *
     * @see ItemType
     */
    void setType(ItemType AType);
}
