package de.dhbw.binaeratops.model.api;

/**
 * Schnittstelle für eine Rasse.
 * <p>
 * Sie stellt alle Funktionalitäten zum Umgang mit einer Rasse bereit.
 * <p>
 * Für Datenbankoperationen siehe {@link de.dhbw.binaeratops.model.repository.RaceRepositoryI}.
 * <p>
 * Für Implementierung siehe {@link de.dhbw.binaeratops.model.entitys.Race}
 *
 * @author Nicolas Haug
 */
public interface RaceI {

    /**
     * Gibt die ID der Rasse zurück.
     *
     * @return ID der Rasse.
     */
    Long getRaceId();

    /**
     * Setzt die ID der Rasse.
     *
     * @param ARaceId Zu setzende ID der Rasse.
     */
    void setRaceId(Long ARaceId);

    /**
     * Gibt den Namen der Rasse zurück.
     *
     * @return Name der Rasse.
     */
    String getRaceName();

    /**
     * Setzt den Namen der Rasse.
     *
     * @param ARaceName Zu setzender Name der Rasse.
     */
    void setRaceName(String ARaceName);

    /**
     * Gibt die Beschreibung der Rasse zurück.
     *
     * @return Beschreibung der Rasse.
     */
    String getDescription();

    /**
     * Setzt die Beschreibung der Rasse.
     *
     * @param ADescription Zu setzender Beschreibung der Rasse.
     */
    void setDescription(String ADescription);

    /**
     * TODO
     * @return
     */
    Long getLifepointsBonus();

    /**
     * TODO
     * @param ALifepointBonus
     */
    void setLifepointsBonus(Long ALifepointBonus);

}
