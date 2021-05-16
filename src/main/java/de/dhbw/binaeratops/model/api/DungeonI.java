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
     * @see Visibility
     */
    Visibility getDungeonVisibility();

    /**
     * Setzt die Sichtbarkeit des Dungeons.
     *
     * @param ADungeonVisibility Zu setzende Sichtbarkeit des Dungeons.
     * @see Visibility
     */
    void setDungeonVisibility(Visibility ADungeonVisibility);

    /**
     * Gibt den Status des Dungeons zurück.
     *
     * @return Status des Dungeons.
     * @see Status
     */
    Status getDungeonStatus();

    /**
     * Setzt den Status des Dungeons.
     *
     * @param ADungeonStatus Zu setzender Status des Dungeons.
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
     * Gibt die Beschreibung des Dungeons zurück.
     *
     * @return Beschreibung des Dungeons.
     */
    String getDescription();

    /**
     * Setzt die Beschreibung des Dungeons.
     *
     * @param ADescription Beschreibung des Dungeons.
     */
    void setDescription(String ADescription);

    /**
     * Gibt den Ersteller des Dungeons zurück.
     *
     * @return Ersteller des Dungeons.
     */
    User getUser();

    /**
     * Setzt den Ersteller des Dungeons.
     *
     * @param AUser Ersteller des Dungeons.
     */
    void setUser(User AUser);

    /**
     * Gibt die Liste der erstellten Avatare des Dungeons zurück.
     *
     * @return Liste der erstellten Avatare des Dungeons.
     */
    List<Avatar> getAvatars();

    /**
     * Gibt den Avatar zurück der über die übergebene ID gefunden wurde.
     * @param AAvatarId Avatar ID.
     * @return Gefundener Avatar oder null falls nicht gefunden.
     */
    Avatar getAvatarById(Long AAvatarId);

    /**
     * Fügt dem Dungeon einen Avatar hinzu.
     *
     * @param AAvatar Avatar, der hinzugefügt werden soll.
     */
    void addAvatar(Avatar AAvatar);

    /**
     * Entfernt dem Dungeon den übergebenen Avatar,
     *
     * @param AAvatar Avatar, der entfernt werden soll.
     */
    void removeAvatar(Avatar AAvatar);

    /**
     * Gibt die Liste der Spieler zurück, die den Dungeon betreten darf.
     *
     * @return Liste der Spieler, die den Dungeon betreten darf.
     */
    List<User> getAllowedUsers();

    /**
     * Fügt den übergebenen Benutzer der WhiteList hinzu.
     *
     * @param AUser Benutzer, welcher der WhiteList hinzugefügt werden soll,
     */
    void addAllowedUser(User AUser);

    /**
     * Entfernt den übergebenen Benutzer der Whitelist.
     *
     * @param AUser Benutzer, welcher der Whitelist entfernt werden soll.
     */
    void removeAllowedUser(User AUser);

    /**
     * Gibt die Liste der Spieler zurück, die nicht mehr anfragen dürfen dem Dungeon beizutreten.
     *
     * @return Liste der Spieler, die nicht mehr anfragen dürfen dem Dungeon beizutreten.
     */
    List<User> getBlockedUsers();

    /**
     * Fügt den übergebenen Benutzer der Blacklist hinzu.
     *
     * @param AUser Benutzer, welcher der Blacklist hinzugefügt werden soll,
     */
    void addBlockedUser(User AUser);

    /**
     * Gibt die Liste der Spieler zurück, die sich momentan im Dungeon befinden.
     *
     * @return Liste der Spieler, die sich momentan im Dungeon befinden.
     */
    List<User> getCurrentUsers();

    /**
     * Fügt den übergebenen Benutzer der Liste der aktuellen Spieler hinzu.
     *
     * @param AUser Benutzer, welcher der Liste hinzugefügt werden soll.
     */
    void addCurrentUser(User AUser);

    /**
     * Entfernt den übergebenen Benutzer aus der Liste der aktuellen Spieler.
     *
     * @param AUser Benutzer, welcher der Liste entfernt werden soll.
     */
    void removeCurrentUser(User AUser);

    /**
     * Entfernt den übergebenen Benutzer der Blacklist.
     *
     * @param AUser Benutzer, welcher der Blacklist entfernt werden soll.
     */
    void removeBlockedUser(User AUser);

    /**
     * Gibt die Liste der Räume des Dungeons zurück.
     *
     * @return Liste der Räume des Dungeons.
     */
    List<Room> getRooms();

    /**
     * Fügt dem Dungeon einen Raum hinzu.
     *
     * @param ARoom Raum, der dem Dungeon hinzugefügt werden soll.
     */
    void addRoom(Room ARoom);

    /**
     * Entfernt dem Dungeon den übergebenen Raum.
     *
     * @param ARoom Raum, der entfernt werden soll.
     */
    void removeRoom(Room ARoom);

    /**
     * Gibt alle NPCs des Dungeons zurück.
     *
     * @return NPCs des Dungeons.
     */
    List<NPC> getNpcs();

    /**
     * Fügt dem Dungeon einen NPC hinzu.
     *
     * @param ANpc NPC, der hinzugefügt werden soll.
     */
    void addNpc(NPC ANpc);

    /**
     * Entfernt dem Dungeon einen NPC.
     *
     * @param ANpc NPC, der entfernt werden soll.
     */
    void removeNpc(NPC ANpc);

    /**
     * Gibt alle Gegenstände des Dungeons zurück.
     *
     * @return Gegenstände des Dungeons.
     */
    List<Item> getItems();

    /**
     * Fügt dem Dungeon den übergebenen Gegenstand hinzu.
     *
     * @param AItem Gegenstand, der hinzugefügt werden soll.
     */
    void addItem(Item AItem);

    /**
     * Entfernt dem Dungeon den übergebenen Gegenstand.
     *
     * @param AItem Gegenstand, der entfernt werden soll.
     */
    void removeItem(Item AItem);

    /**
     * Gibt die Liste der Rollen des Dungeons zurück.
     *
     * @return Liste der Rollen des Dungeons.
     */
    List<Role> getRoles();

    /**
     * Fügt dem Dungeon eine Rolle hinzu.
     *
     * @param ARole Rolle, die hinzugefügt werden soll.
     */
    void addRole(Role ARole);

    /**
     * Entfernt dem Dungeon die übergebene Rolle.
     *
     * @param ARole Rolle, die entfernt werden soll.
     */
    void removeRole(Role ARole);

    /**
     * Gibt die Liste der Rassen des Dungeons zurück.
     *
     * @return Liste der Rassen des Dungeons.
     */
    List<Race> getRaces();

    /**
     * Fügt dem Dungeon eine Rasse hinzu.
     *
     * @param ARace Rasse, die hinzugefügt werden soll.
     */
    void addRace(Race ARace);

    /**
     * Entfernt dem Dungeon die übergebene Rasse.
     *
     * @param ARace Rasse, die entfernt werden soll.
     */
    void removeRace(Race ARace);

    Long getStandardAvatarLifepoints();

    void setStandardAvatarLifepoints(Long AStandardAvatarLifepoints);

}
