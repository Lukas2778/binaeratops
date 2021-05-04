package de.dhbw.binaeratops.service.api.configuration;

import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.model.enums.Direction;
import de.dhbw.binaeratops.model.enums.ItemType;

import de.dhbw.binaeratops.model.enums.Visibility;
import java.util.List;

public interface ConfiguratorServiceI {

    /**
     * Initialisierung des Dungeons.
     * @param AName Dungeonname.
     * @param AUser Benutzer.
     * @return Dungeon mit Standardwerten.
     */
    Dungeon createDungeon(String AName, User AUser);

    /**
     * Initialisierung des Dungeons.
     * @param AName Dungeonname.
     * @param AUser Benutzer.
     * @param APlayerSize Spieleranzahl.
     * @param AVisibility Sichtbarkeit.
     * @return Erstellter Dungeon.
     */
    Dungeon createDungeon(String AName, User AUser, Long APlayerSize, Visibility AVisibility);

    /**
     * Getter.
     * @return Gibt den Dungeon zurück.
     */
    Dungeon getDungeon();


    /**
     * Initialisierung des Dungeons.
     * @param ADungeonId Dungeon.
     */
    void setDungeon(Long ADungeonId);

    /**
     * Änderungen am Dungeon in die Datenbank abspeichern.
     */
    void saveDungeon();

    /**
     * Startraum des Dungeons setzen.
     * @param ARoom Ein Raum.
     */
    void setStartRoom(Room ARoom);

    /**
     * Startraum des Dungeons setzen.
     * @param ACommandSymbol Ein Text, der das Befehlssymbol ist.
     */
    void setCommandSymbol(String ACommandSymbol);

    /**
     * Maximale Anzahl der Spiler des Dungeons setzen.
     * @param ACount Anzahl der maximalen Spieler.
     */
    void setMaxPlayercount(int ACount);

    /**
     * Gibt alle Räume eines Dungeons zurück.
     * @return Liste mit Dungeons.
     */
    List<Room> getAllDungeonRooms();

    /**
     * Erstellt ein Gegenstand.
     * @param AName Gegenstandsbezeichnung.
     * @param AType Gegenstandstyp.
     * @param ADescription Gegenstandsberschreibung.
     * @param ASize Größe eines Gegenstandes.
     */
    void createItem(String AName, ItemType AType, String ADescription, Long ASize);

    /**
     * Item wird in der DB geupdated
     * @param AItem Das zu verbessernde Item
     */
    void updateItem(Item AItem);

    /**
     * Gegenstand wird aus den Dungeon entfernt und gelöscht.
     * @param AItem Der zu löschende Gegenstand.
     */
    void deleteItem(Item AItem);

    /**
     * Gibt das Objekt des angegebenen Gegenstandes zurück.
     * @param AItemId ID des Gegenstandes.
     * @return Objekt des Gegenstandes.
     */
    Item getItem(Long AItemId);

    /**
     * Erstellen eines neuen NPCs.
     * @param AName Bezeichnung des NPCs.
     * @param ADescription Beschreibung des NPCs.
     * @param ARace Rasse des NPCs.
     */
    void createNPC(String AName, String ADescription, Race ARace);

    /**
     * NPC wird in der DB geupdated
     * @param ANPC Der zu verbessernde NPC
     */
    void updateNPC(NPC ANPC);

    /**
     * NPC wird aus den Dungeon entfernt und gelöscht.
     * @param ANPC Der zu löschende NPC.
     */
    void deleteNPC(NPC ANPC);

    /**
     * Gibt das Objekt des angegebenen NPCs zurück.
     * @param ANPCId ID des NPCs.
     * @return Objekt des NPCs.
     */
    Item getNPC(Long ANPCId);

    /**
     * Erstellen einer neuen Rolle.
     * @param AName Rollenname.
     * @param ADescription Rollenbeschreibung.
     */
    void createRole(String AName, String ADescription);

    /**
     * Rolle wird aus den Dungeon entfernt und gelöscht.
     * @param ARole Rolle.
     */
    void removeRole(Role ARole);

    /**
     * Gibt alle Rollen des Dungeons zurück.
     * @return Liste mit Rollen.
     */
    List<Role> getAllRoles();

    /**
     * Erstellen einer neuen Rasse.
     * @param AName Rassename.
     * @param ADescription Rassebeschreibung.
     */
    void createRace(String AName, String ADescription);

    /**
     * Rasse wird aus den Dungeon entfernt und gelöscht.
     * @param ARace Rasse.
     */
    void removeRace(Race ARace);

    /**
     * Gibt alle Rassen des Dungeons zurück.
     * @return Liste mit Rassen.
     */
    List<Race> getAllRace();

    /**
     * Setzen der Inventarstandardgröße.
     * @param ACapacity Kapazität.
     */
    void setDefaultInventoryCapacity(int ACapacity);

    /**
     * Erstellen eines neuen Raumes.
     * @param AName Raumname.
     */
    void createRoom(String AName);

    /**
     * Setz den Nachbarraum des Raumes. Die Himmelsrichtung gibt die Position an.
     * @param ADirection Himmelsrichtung.
     * @param ARoomId Raum.
     * @param ANeigghborRoom Nachbarraum.
     */
    void setNeighborRoom(Direction ADirection, Long ARoomId, Long ANeigghborRoom);

    /**
     * Entfernt den Nachbarraum an der angegebenen Richtung.
     * @param ADirection Himmelsrichtung.
     */
    void removeNeighborRoom(String ADirection);

    /**
     * Die Gegenstandsliste wird gesetzt und die vorherige gelöscht.
     * @param ARoom Raum.
     * @param AItemList Gegenstandsliste.
     */
    void setItems(Room ARoom, List<Item> AItemList);

    /**
     * Die NPCliste wird gesetzt und die vorherige gelöscht.
     * @param ARoom Raum.
     * @param ANPCList NPCliste.
     */
    void setNPCs(Room ARoom, List<NPC> ANPCList);

    /**
     * Gibt alle Gegenstände eines Dungeons zurück.
     * @return Liste mit Gegenständen.
     */
    List<Item> getAllItems();

    /**
     * Gibt alle Gegenstände eines Dungeons zurück.
     * @return Liste mit Gegenständen.
     */
    List<NPC> getAllNPCs();

    /**
     * Entfernt den Raum aus dem Dungeon und löscht ihn.
     * @param ARoom ID eines Raumes.
     */
    void deleteRoom(Room ARoom);

    /**
     * Gibt das Objekt des angegebenen Raumes zurück.
     * @param ARoomID ID eines Raumes.
     * @return Raum.
     */
    Room getRoom(Long ARoomID);

    void addRoom(Room ARoom);

    void saveRoom(Room ARoom);
}
