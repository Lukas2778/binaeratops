package de.dhbw.binaeratops.model.api;

import de.dhbw.binaeratops.model.entitys.Race;
import de.dhbw.binaeratops.model.entitys.Role;
import de.dhbw.binaeratops.model.enums.Gender;
import de.dhbw.binaeratops.model.entitys.Item;

import java.util.List;

/**
 * Schnittstelle für einen Avatar.
 * <p>
 * Sie stellt alle Funktionalitäten zum Umgang mit einem Avatar bereit.
 * <p>
 * Für Datenbankoperationen siehe {@link de.dhbw.binaeratops.model.repository.AvatarRepositoryI}.
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
     *
     * @see Gender
     */
    Gender getGender();

    /**
     * Setzt das Geschlecht des Avatars.
     *
     * @param AGender Geschlecht des Avatars.
     *
     * @see Gender
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

    /**
     * Gibt die Rasse des Avatars zurück.
     *
     * @return Rasse des Avatars.
     */
    Race getRace();

    /**
     * Setzt die Rasse des Avatars.
     *
     * @param ARace Zu setzende Rasse.
     */
    void setRace(Race ARace);

    /**
     * Gibt die Rolle des Avatars zurück.
     *
     * @return Rolle des Avatars.
     */
    Role getRole();

    /**
     * Setzt die Rolle des Avatars.
     *
     * @param ARole Zu setzende Rolle.
     */
    void setRole(Role ARole);

    /**
     * Gibt das Inventar eines Avatars als Liste von Gegenständen zurück.
     *
     * @return Liste von Gegenständen (Inventar).
     */
    List<Item> getInventory();

    /**
     * Gibt das ausgerüstete Equipment eines Avatars als Liste von Gegenständen zurück.
     * <p>
     * WICHTIG: Diese Liste darf jeden Gegenstandstyp nur 1x beinhalten.
     * @return Liste von Gegenständen (Equipment)
     */
    List<Item> getEquipment();
}
