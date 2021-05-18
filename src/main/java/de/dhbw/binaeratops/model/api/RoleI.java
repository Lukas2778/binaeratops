package de.dhbw.binaeratops.model.api;

import de.dhbw.binaeratops.model.entitys.Dungeon;

/**
 * Schnittstelle für eine Rolle.
 * <p>
 * Sie stellt alle Funktionalitäten zum Umgang mit einer Rolle bereit.
 * <p>
 * Für Datenbankoperationen siehe {@link de.dhbw.binaeratops.model.repository.RoleRepositoryI}.
 * <p>
 * Für Implementierung siehe {@link de.dhbw.binaeratops.model.entitys.Role}
 *
 * @author Nicolas Haug
 */
public interface RoleI {

    /**
     * Gibt ID der Rolle zurück.
     *
     * @return ID der Rolle.
     */
    Long getRoleId();

    /**
     * Setzt die ID der Rolle.
     *
     * @param ARoleId Zu setzende ID der Rolle.
     */
    void setRoleId(Long ARoleId);

    /**
     * Gibt den Namen der Rolle zurück.
     *
     * @return Name der Rolle.
     */
    String getRoleName();

    /**
     * Setzt den Namen der Rolle.
     *
     * @param ARoleName Zu setzender Name der Rolle.
     */
    void setRoleName(String ARoleName);

    /**
     * Gibt die Beschreibung der Rolle zurück.
     *
     * @return Beschreibung der Rolle.
     */
    String getDescription();

    /**
     * Setzt die Beschreibung der Rolle.
     *
     * @param ADescription Zu setzende Beschreibung der Rolle.
     */
    void setDescription(String ADescription);

    /**
     * Gibt den Dungeon der Rolle zurück.
     *
     * @return Dungeon der Rolle.
     */
    Dungeon getDungeon();

    /**
     * Setzt den Dungeon der Rolle.
     *
     * @param ADungeon Dungeon der Rolle.
     */
    void setDungeon(Dungeon ADungeon);

    /**
     * Gibt den Lebenspunkte-Bonus der Rolle zurück.
     *
     * @return Lebenspunkte-Bonus der Rolle.
     */
    Long getLifepointsBonus();

    /**
     * Setzt den Lebenspunkte-Bonus der Rolle.
     *
     * @param ALifepointBonus Zu setzender Lebenspunkte-Bonus der Rolle.
     */
    void setLifepointsBonus(Long ALifepointBonus);
}
