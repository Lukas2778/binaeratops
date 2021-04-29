package de.dhbw.binaeratops.model.api;

import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.model.enums.Status;
import de.dhbw.binaeratops.model.enums.Visibility;

import java.util.List;

/**
 * Schnittstelle für einen Dungeon.
 * <p>
 * Sie stellt alle Funktionalitäten zum Umgang mit einem Dungeon bereit.
 * <p>
 * Für Datenbankoperationen siehe {@link de.dhbw.binaeratops.model.repository.DungeonRepositoryI}.
 * <p>
 * Für Implementierung siehe {@link de.dhbw.binaeratops.model.entitys.Dungeon}
 *
 * @author Nicolas Haug
 */
public interface DungeonI {

    /**
     * Gibt die Dungeon-ID des Dungeons zurück.
     *
     * @return Dungeon-ID des Dungeons.
     */
    Long getDungeonId();

    /**
     * Setzt die Dungeon-ID des Dungeons.
     *
     * @param ADungeonId Zu setzende Dungeon-ID.
     */
    void setDungeonId(Long ADungeonId);

    /**
     * Gibt den Namen des Dungeons zurück.
     *
     * @return Name des Dungeons.
     */
    String getDungeonName();

    /**
     * Setzt den Namen des Dungeons.
     *
     * @param ADungeonName Zu setzender Name des Dungeons.
     */
    void setDungeonName(String ADungeonName);

    /**
     * Gibt die Sichtbarkeit des Dungeons zurück.
     *
     * @return Sichtbarkeit des Dungeons.
     *
     * @see Visibility
     */
    Visibility getDungeonVisibility();

    /**
     * Setzt die Sichtbarkeit des Dungeons.
     *
     * @param ADungeonVisibility Zu setzende Sichtbarkeit des Dungeons.
     *
     * @see Visibility
     */
    void setDungeonVisibility(Visibility ADungeonVisibility);

    /**
     * Gibt den Status des Dungeons zurück.
     *
     * @return Status des Dungeons.
     *
     * @see Status
     */
    Status getDungeonStatus();

    /**
     * Setzt den Status des Dungeons.
     *
     * @param ADungeonStatus Zu setzender Status des Dungeons.
     *
     * @see Status
     */
    public void setDungeonStatus(Status ADungeonStatus);

    /**
     * Gibt die User-ID des Dungeon-Masters zurück.
     *
     * @return User-ID des Dungeon-Masters.
     */
    Long getDungeonMasterId();

    /**
     * Setzt die User-ID des Dungeon-Masters.
     *
     * @param ADungeonMasterId Zu setzende User-ID des Dungeon-Masters.
     */
    void setDungeonMasterId(Long ADungeonMasterId);

    /**
     * Gibt die aktuelle Spieleranzahl des Dungeons zurück.
     *
     * @return Aktuelle Spieleranzahl des Dungeons.
     */
    Long getPlayerCount();

    /**
     * Setzt die aktuelle Spieleranzahl des Dungeons.
     *
     * @param APlayerCount Zu setzende aktuelle Spieleranzahl des Dungeons.
     */
    void setPlayerCount(Long APlayerCount);

    /**
     * Gibt die maximal erlaubte Spieleranzahl des Dungeons zurück.
     *
     * @return Maximal erlaubte Spieleranzahl des Dungeons.
     */
    Long getPlayerMaxSize();

    /**
     * Setzt die maximal erlaubte Spieleranzahl des Dungeons.
     *
     * @param APlayerMaxSize Maximal erlaubte Spieleranzahl des Dungeons.
     */
    void setPlayerMaxSize(Long APlayerMaxSize);

    /**
     * Gibt den Startraum des Dungeons zurück.
     *
     * @return Startraum des Dungeons.
     */
    Long getStartRoomId();

    /**
     * Setzt den Startraum des Dungeons.
     *
     * @param AStartRoomId Startraum des Dungeons.
     */
    void setStartRoomId(Long AStartRoomId);

    /**
     * Gibt die Standardinventarkapazität des Dungeons zurück.
     *
     * @return Standardinventarkapazität des Dungeons.
     */
    Long getDefaultInventoryCapacity();

    /**
     * Setzt die Standardinventarkapazität des Dungeons.
     *
     * @param ADefaultInventoryCapacity Zu setzende Standardinventarkapazität des Dungeons.
     */
    void setDefaultInventoryCapacity(Long ADefaultInventoryCapacity);

    /**
     * Gibt das Befehlssymbol des Dungeons zurück.
     *
     * @return Befehlssymbol des Dungeons.
     */
    Character getCommandSymbol();

    /**
     * Setzt das Befehlssymbol des Dungeons.
     *
     * @param ACommandSymbol Zu setzendes Befehlssymbol des Dungeons
     */
    void setCommandSymbol(Character ACommandSymbol);

    /**
     * Gibt die Liste der erstellten Avatare des Dungeons zurück.
     *
     * @return Liste der erstellten Avatare des Dungeons.
     */
    List<Avatar> getAvatars();

    /**
     * Gibt die Liste der Spieler zurück, die den Dungeon betreten darf.
     *
     * @return Liste der Spieler, die den Dungeon betreten darf.
     */
    List<User> getAllowedUsers();

    /**
     * Gibt die Liste der Spieler zurück, die nicht mehr anfragen dürfen dem Dungeon beizutreten.
     *
     * @return Liste der Spieler, die nicht mehr anfragen dürfen dem Dungeon beizutreten.
     */
    List<User> getBlockedUsers();

    /**
     * Gibt die Liste der Räume des Dungeons zurück.
     *
     * @return Liste der Räume des Dungeons.
     */
    List<Room> getRooms();

    /**
     * Gibt die Liste der Rollen des Dungeons zurück.
     *
     * @return Liste der Rollen des Dungeons.
     */
    List<Role> getRoles();

    /**
     * Gibt die Liste der Rassen des Dungeons zurück.
     *
     * @return Liste der Rassen des Dungeons.
     */
    List<Race> getRaces();
}
