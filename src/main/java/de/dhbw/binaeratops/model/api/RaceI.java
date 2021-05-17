package de.dhbw.binaeratops.model.api;

import de.dhbw.binaeratops.model.entitys.Dungeon;

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
     * Gibt den Dungeon der Rasse zurück.
     *
     * @return Dungeon der Rasse.
     */
    Dungeon getDungeon();

    /**
     * Setzt den Dungeon der Rasse.
     *
     * @param ADungeon Dungeon der Rasse.
     */
    void setDungeon(Dungeon ADungeon);

    /**
     * Gibt den Lebenspunkte-Bonus der Rasse zurück.
     *
     * @return Lebenspunkte-Bonus der Rasse.
     */
    Long getLifepointsBonus();

    /**
     * Setzt den Lebenspunkte-Bonus der Rasse.
     *
     * @param ALifepointBonus Zu setzender Lebenspunkte-Bonus der Rasse.
     */
    void setLifepointsBonus(Long ALifepointBonus);
}
