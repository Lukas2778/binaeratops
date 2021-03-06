package de.dhbw.binaeratops.service.api.configuration;

import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.model.enums.Direction;
import de.dhbw.binaeratops.model.enums.ItemType;

import de.dhbw.binaeratops.model.enums.Status;
import de.dhbw.binaeratops.model.enums.Visibility;

import java.util.List;

/**
 * Interface für die Komponente "ConfiguratorService".
 * <p>
 * Dieser Service stellt alle Funktionalitäten zum Konfigurieren eines Dungeons bereit.
 * </p>
 * <p>
 * Für Implementierung dieser Komponente siehe {@link de.dhbw.binaeratops.service.impl.configurator.ConfiguratorService}.
 * </p>
 *
 * @author Timon Gartung, Pedro Treuer, Nicolas Haug, Lukas Göpel, Matthias Rall, Lars Rösel
 */
public interface ConfiguratorServiceI {

    /**
     * Initialisierung des Dungeons.
     *
     * @param AName   Dungeonname.
     * @param AUser   Benutzer.
     * @param AStatus Status.
     * @return Dungeon mit Standardwerten.
     */
    Dungeon createDungeon(String AName, User AUser, Status AStatus);

    /**
     * Initialisierung des Dungeons.
     *
     * @param AName       Dungeonname.
     * @param AUser       Benutzer.
     * @param APlayerSize Spieleranzahl.
     * @param AVisibility Sichtbarkeit.
     * @return Erstellter Dungeon.
     */
    Dungeon createDungeon(String AName, User AUser, Long APlayerSize, Visibility AVisibility);

    /**
     * Getter.
     *
     * @return Gibt den Dungeon zurück.
     */
    Dungeon getDungeon();


    /**
     * Initialisierung des Dungeons.
     *
     * @param ADungeonId Dungeon.
     */
    void setDungeon(Long ADungeonId);

    /**
     * Änderungen am Dungeon in die Datenbank abspeichern.
     */
    void saveDungeon();

    /**
     * Dungeon löschen.
     *
     * @param ADungeonId ID des Dungeons der gelöscht werden soll.
     */
    void deleteDungeon(Long ADungeonId);

    /**
     * Startraum des Dungeons setzen.
     *
     * @param ARoom Ein Raum.
     */
    void setStartRoom(Room ARoom);

    /**
     * Befehlssymbol des Dungeons holen.
     *
     * @return Befehlssymbol.
     */
    char getCommandSymbol();

    /**
     * Startraum des Dungeons setzen.
     *
     * @param ACommandSymbol Ein Text, der das Befehlssymbol ist.
     */
    void setCommandSymbol(char ACommandSymbol);

    /**
     * Maximale Anzahl der Spiler des Dungeons setzen.
     *
     * @param ACount Anzahl der maximalen Spieler.
     */
    void setMaxPlayercount(int ACount);

    /**
     * Gibt alle Räume eines Dungeons zurück.
     *
     * @return Liste mit Dungeons.
     */
    List<Room> getAllDungeonRooms();

    /**
     * Erstellt ein Gegenstand.
     *
     * @param AName        Gegenstandsbezeichnung.
     * @param AType        Gegenstandstyp.
     * @param ADescription Gegenstandsberschreibung.
     * @param ASize        Größe eines Gegenstandes.
     */
    void createItem(String AName, ItemType AType, String ADescription, Long ASize);

    /**
     * Item wird in der DB geupdated
     *
     * @param AItem Das zu verbessernde Item
     */
    void updateItem(Item AItem);

    /**
     * Gegenstand wird aus den Dungeon entfernt und gelöscht.
     *
     * @param AItem Der zu löschende Gegenstand.
     */
    void deleteItem(Item AItem);

    /**
     * Gibt das Objekt des angegebenen Gegenstandes zurück.
     *
     * @param AItemId ID des Gegenstandes.
     * @return Objekt des Gegenstandes.
     */
    Item getItem(Long AItemId);

    /**
     * Erstellen eines neuen NPCs.
     *
     * @param AName        Bezeichnung des NPCs.
     * @param ADescription Beschreibung des NPCs.
     * @param ARace        Rasse des NPCs.
     */
    void createNPC(String AName, String ADescription, Race ARace);

    /**
     * NPC wird in der DB geupdated
     *
     * @param ANPC Der zu verbessernde NPC
     */
    void updateNPC(NPC ANPC);

    /**
     * NPC wird aus den Dungeon entfernt und gelöscht.
     *
     * @param ANPC Der zu löschende NPC.
     */
    void deleteNPC(NPC ANPC);

    /**
     * Gibt das Objekt des angegebenen NPCs zurück.
     *
     * @param ANPCId ID des NPCs.
     * @return Objekt des NPCs.
     */
    Item getNPC(Long ANPCId);

    /**
     * Erstellen einer neuen Rolle.
     *
     * @param AName            Rollenname.
     * @param ADescription     Rollenbeschreibung.
     * @param ALifepointsBonus Lebenspunkte-Bonus.
     */
    void createRole(String AName, String ADescription, Long ALifepointsBonus);

    /**
     * Rolle wird aus den Dungeon entfernt und gelöscht.
     *
     * @param ARole Rolle.
     */
    void removeRole(Role ARole);

    /**
     * Gibt alle Rollen des Dungeons zurück.
     *
     * @return Liste mit Rollen.
     */
    List<Role> getAllRoles();

    /**
     * Erstellen einer neuen Rasse.
     *
     * @param AName            Rassename.
     * @param ADescription     Rassebeschreibung.
     * @param ALifepointsBonus Lebenspunkte-Bonus.
     */
    void createRace(String AName, String ADescription, Long ALifepointsBonus);

    /**
     * Rasse wird aus den Dungeon entfernt und gelöscht.
     *
     * @param ARace Rasse.
     */
    void removeRace(Race ARace);

    /**
     * Gibt alle Rassen des Dungeons zurück.
     *
     * @return Liste mit Rassen.
     */
    List<Race> getAllRace();

    /**
     * Setzen der Inventarstandardgröße.
     *
     * @param ACapacity Kapazität.
     */
    void setDefaultInventoryCapacity(int ACapacity);

    /**
     * Erstellen eines neuen Raumes.
     *
     * @param AName Raumname.
     */
    void createRoom(String AName);

    /**
     * Setz den Nachbarraum des Raumes. Die Himmelsrichtung gibt die Position an.
     *
     * @param ADirection     Himmelsrichtung.
     * @param ARoomId        Raum.
     * @param ANeigghborRoom Nachbarraum.
     */
    void setNeighborRoom(Direction ADirection, Long ARoomId, Long ANeigghborRoom);

    /**
     * Entfernt den Nachbarraum an der angegebenen Richtung.
     *
     * @param ADirection Himmelsrichtung.
     */
    void removeNeighborRoom(String ADirection);

    /**
     * Erhalte die Anzahl von ItemInstances die von AItem im Raum ARoom vorhanden sind.
     *
     * @param ARoom Raum.
     * @param AItem Item.
     * @return Anzahl von ItemInstances.
     */
    double getNumberOfItem(Room ARoom, Item AItem);

    /**
     * Die Gegenstandsliste wird gesetzt und die vorherige gelöscht.
     *
     * @param ARoom     Raum.
     * @param AItemList Gegenstandsliste.
     */
    void setItemInstances(Room ARoom, List<ItemInstance> AItemList);

    /**
     * Gibt die Anzahl von NPCs in einem Raum zurück.
     *
     * @param ARoom Raum, aus dem die NPCs geholt werden sollen.
     * @param ANPC  NPC, dessen Anzahl von Instanzen in einem Raum gesucht werden soll.
     * @return Anzahl von NPC-Instanzen.
     */
    double getNumberOfNPC(Room ARoom, NPC ANPC);

    /**
     * Die NPCliste wird gesetzt und die vorherige gelöscht.
     *
     * @param ARoom    Raum.
     * @param ANPCList NPCliste.
     */
    void setNpcInstances(Room ARoom, List<NPCInstance> ANPCList);

    /**
     * Gibt alle Gegenstände eines Dungeons zurück.
     *
     * @return Liste mit Gegenständen.
     */
    List<Item> getAllItems();

    /**
     * Gibt alle Gegenstände eines Raumes zurück.
     *
     * @param ARoom Raum.
     * @return Liste mit Gegenständen.
     */
    List<ItemInstance> getAllItems(Room ARoom);

    /**
     * Gibt alle NPCs eines Dungeons zurück.
     *
     * @return Liste mit Gegenständen.
     */
    List<NPC> getAllNPCs();

    /**
     * Gibt alle NPCs eines Raumes zurück.
     *
     * @param ARoom Raum.
     * @return Liste mit NPCs.
     */
    List<NPCInstance> getAllNPCs(Room ARoom);

    /**
     * Entfernt den Raum aus dem Dungeon und löscht ihn.
     *
     * @param ARoom ID eines Raumes.
     */
    void deleteRoom(Room ARoom);

    /**
     * Gibt das Objekt des angegebenen Raumes zurück.
     *
     * @param ARoomID ID eines Raumes.
     * @return Raum.
     */
    Room getRoom(Long ARoomID);

    /**
     * Fügt dem Dungeon den übergebenen Raum hinzu.
     *
     * @param ARoom Raum der hinzugefügt werden soll.
     */
    void addRoom(Room ARoom);

    /**
     * Speichert den übergebenen Raum.
     *
     * @param ARoom Raum der gespeichert werden soll.
     */
    void saveRoom(Room ARoom);

    /**
     * Fügt dem Dungeon eine Gegenstand-Blaupause hinzu.
     *
     * @param AInstance Gegenstand-Blaupause, die dem Dungeon hinzugefügt werden soll.
     */
    void addItemInstance(ItemInstance AInstance);

    /**
     * Gibt alle Users aus der DB zurück.
     *
     * @return Liste mit Usern
     */
    List<User> getAllUsers();

    /**
     * Gibt den Benutzer mit dem übergebenen Namen zurück.
     *
     * @param AName Name des Benutzers.
     * @return Benutzer.
     */
    User getUser(String AName);

    /**
     * Speichert den übergebenen Benutzer in die Datenbank.
     *
     * @param AUser Benutzer, der gespeichert werden soll.
     */
    void saveUser(User AUser);

    /**
     * Entfernt die Berechtigung vom übergebenen Benutzer.
     *
     * @param AUser Benutzer, dem die Berechtigung entfernt werden soll.
     */
    void removePermission(User AUser);

    /**
     * Entfernt die Berechtigung vom übergebenen Benutzer.
     *
     * @param AUser Benutzer, dem die Berechtigung entfernt werden soll.
     */
    void removeBlockedPermission(User AUser);

    /**
     * Speichert die übergebene Berechtigung.
     *
     * @param APermission Berechtigung, die gespeichert werden soll.
     */
    void savePermission(Permission APermission);

    /**
     * Gibt alle erlaubten Berechtigungen des Dungeons zurück.
     *
     * @return Alle erlaubten Berechtigungen.
     */
    List<Permission> getAllowedPermissions();

    /**
     * Gibt alle blockierten Berechtigungen des Dungeons zurück.
     *
     * @return Alle blockierten Berechtigungen.
     */
    List<Permission> getBlockedPermissions();
}
