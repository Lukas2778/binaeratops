package de.dhbw.binaeratops.service.api.map;

import de.dhbw.binaeratops.model.entitys.Room;
import de.dhbw.binaeratops.view.map.Tile;

import java.util.ArrayList;

/**
 * Dieser Service beinhaltet die Businesslogik zur Interaktion im Konfigurator mit der Karte.
 */
public interface MapServiceI {
    /**
     * Konstruktor des MapServices.
     *
     * @param AMapSize Größe der zu erstellenden Karte in Form von AMapSize x AMapSize Räume.
     */
    void init(int AMapSize);

    /**
     * Abfrage der Größe der Karte.
     *
     * @return Rückgabe der Größe der Karte in Form von Anzahl der Räume am Rand des Quadrats.
     */
    int getMAP_SIZE();

    /**
     * Überprüft, ob die Koordinate im Array und damit der Raum schon gesetzt wurde.
     *
     * @param ALocationX X Koordinate.
     * @param ALocationY Y Koordinate.
     * @return Rückgabe, ob der Raum schon gesetzt wurde oder nicht.
     */
    boolean roomExists(int ALocationX, int ALocationY);

    /**
     * Überprüft, ob der Raum an den übergebenen Koordinaten gesetzt werden kann.
     *
     * @param ALocationX X Koordinate.
     * @param ALocationY Y Koordinate.
     * @return Rückgabe, ob der Raum gesetzt werden kann oder nicht.
     */
    boolean canPlaceRoom(int ALocationX, int ALocationY);

    /**
     * Setzen des Raums an den übergebenen Koordinaten.
     *
     * @param ALocationX X Koordinate.
     * @param ALocationY Y Koordinate.
     * @return Liste mit den Kacheln, die geändert werden;
     */
    ArrayList<Tile> placeRoom(int ALocationX, int ALocationY);

    /**
     * Überprüfen, ob der Raum an den übergebenen Koordinaten gelöscht werden kann.
     * Dies passiert indem geprüft wird ob von einem beliebigen raum alle andern Räume erreichber sind.
     *
     * @param ALocationX X Koordinate.
     * @param ALocationY Y Koordinate.
     * @return Rückgabe, ob der Raum gelöscht werden kann oder nicht.
     */
    boolean canDeleteRoom(int ALocationX, int ALocationY);

    /**
     * Sucht rekursiv nach verbundenen Räumen und speichert die Ergebnisse in der Variable 'searchedRooms'.
     *
     * @param ACurrentRoom Übergabe des aktuellen Raums, von dem aus gesucht werden soll.
     */
    void canReachAllRooms(Room ACurrentRoom);

    /**
     * Holt den Raum über die eingegebene RaumID aus der Raum-HashMap.
     * @param AId RaumID.
     * @return Gibt den gesuchten Raum als Raum-Objekt zurück.
     */
    Room getRoomById(Long AId);

    /**
     * Gibt den ersten gefundenen Nachbarn des eingegebenen Raums zurück.
     * @param ARoom Raum dessen Nachbarn durchsucht werden sollen.
     * @return Gibt den ersten gefundenen Nachbarn zurück.
     */
    Room findANeighbor(Room ARoom);

    /**
     * Raum an den übergebenen Koordinaten löschen.
     *
     * @param ALocationX X Koordinate.
     * @param ALocationY Y Koordinate.
     * @return ArrayList.
     */
    ArrayList<Tile> deleteRoom(int ALocationX, int ALocationY);

    /**
     * Überprüfen, ob die Wand an den übergebenen Koordinaten geändet werden kann.
     *
     * @param ALocationX X Koordinate.
     * @param ALocationY Y Koordinate.
     * @param AHorizontal Eingabe, ob es sich um eine horizontale Wand handelt.
     * @return Rückgabe, ob die Wand gesetzt werden kann oder nicht.
     */
    boolean canToggleWall(int ALocationX, int ALocationY, boolean AHorizontal);

    /**
     * Wand an den übergebenen Koordinaten setzen.
     *
     * @param ALocationX X Koordinate.
     * @param ALocationY Y Koordinate.
     * @param AHorizontal Eingabe, ob es sich um eine horizontale Wand handelt.
     * @return ArrayList.
     */
    ArrayList<Tile> toggleWall(int ALocationX, int ALocationY, boolean AHorizontal);

    /**
     * @param ARoom Raum für den der name der Kachel erzeugt werden soll.
     * @return Kachelname für den gesuchten Raum.
     */
    String tileName(Room ARoom);
}
