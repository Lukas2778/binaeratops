package de.dhbw.binaeratops.model.api;

import de.dhbw.binaeratops.model.entitys.Gender;

/**
 * Schnittstelle für einen Avatar.
 * <p>
 * Sie stellt alle Funktionalitäten zum Umgang mit einem Avatar bereit.
 * <p>
 * Für Datenbankoperationen siehe {@link de.dhbw.binaeratops.model.repository.AvatarRepository}.
 * <p>
 * Für Implementierung siehe {@link de.dhbw.binaeratops.model.entitys.Avatar}
 *
 * @author Nicolas Haug
 */
public interface AvatarI {

    /**
     * Gibt die ID des Avatars zurück.
     *
     * @return Avatar-ID des Avatars.
     */
    Long getAvatarId();

    /**
     * Setzt die ID eines Avatars.
     *
     * @param AAvatarId Avatar-ID, die gesetzt werden soll.
     */
    void setAvatarId(Long AAvatarId);

    /**
     * Gibt die ID des Avatareigentümers zurück.
     *
     * @return Eigentümer-ID des Avatars.
     */
    Long getUserId();

    /**
     * Setzt die ID des Avatareigentümers.
     *
     * @param AUserId Eigentümer-ID des Avatars.
     */
    void setUserId(Long AUserId);
    /**
     * Gibt die ID des Dungeons zurück, in dem der Avatar sich befindet.
     *
     * @return ID des Dungeons, in dem der Avatar sich befindet.
     */
    Long getDungeonId();

    /**
     * Setzt die ID des Dungeons, in dem der Avatar sich befindet.
     *
     * @param ADungeonId ID des Dungeons, in dem der Avatar sich befindet.
     */
    void setDungeonId(Long ADungeonId);

    /**
     * Gibt die Raum-ID des Raumes zurück, in dem der Avatar sich aktuell befindet.
     *
     * @return Raum-ID des Raumes, in dem der Avatar sich aktuell befindet.
     */
    Long getRoomId();

    /**
     * Setzt die Raum-ID des Raumes, in dem der Avatar sich aktuell befindet.
     *
     * @param ARoomId Raum-ID des Raumes, in dem der Avatar sich aktuell befindet.
     */
    void setRoomId(Long ARoomId);

    /**
     * Gibt das Geschlecht des Avatars zurück.
     *
     * @return Gechlecht des Avatars.
     */
    Gender getGender();

    /**
     * Setzt das Geschlecht des Avatars.
     *
     * @param AGender Geschlecht des Avatars.
     */
    void setGender(Gender AGender);

    /**
     * Gibt den Namen des Avatars zurück.
     *
     * @return Namen des Avatars.
     */
    String getName();

    /**
     * Setzt den Namen des Avatars.
     *
     * @param AName Name des Avatars.
     */
    void setName(String AName);

    // TODO Invenatar hinzufügen zum Avatar.

    /**
     * Gibt die Inventar-ID zurück.
     * @return Inventar-ID.
     */
    Long getInventoryId();

    /**
     * Setzt die InventarID.
     * @param AInventoryId Inventar-ID.
     */
    void setInventoryId(Long AInventoryId);

    /**
     * Gibt die ID der Rasse des Avatars zurück.
     * @return ID der Rasse.
     */
    Long getRaceId();

    /**
     * Setzt die ID der Rasse des Avatars.
     * @param ARaceId ID der Rasse.
     */
    void setRaceId(Long ARaceId);

    /**
     * Gibt die ID der Rolle des Avatars zurück.
     * @return ID der Rolle.
     */
    Long getRoleId();

    /**
     * Setzt die ID der Rolle des Avatars.
     * @param ARoleId ID der Rolle.
     */
    void setRoleId(Long ARoleId);
}
