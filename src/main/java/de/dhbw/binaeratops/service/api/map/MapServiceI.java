package de.dhbw.binaeratops.service.api.map;

import de.dhbw.binaeratops.model.entitys.Room;
import de.dhbw.binaeratops.model.map.Tuple;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import de.dhbw.binaeratops.model.map.Tile;

import java.util.ArrayList;

/**
 * Interface für die Komponente "MapService".
 * <p>
 * Dieser Service beinhaltet die Businesslogik zur Interaktion im Konfigurator mit der Karte.
 * </p>
 * <p>
 * Für Implementierung dieser Komponente siehe @{@link de.dhbw.binaeratops.service.impl.map.MapService}.
 * </p>
 *
 * @author Lukas Göpel, Matthias Rall, Lars Rösel
 */
public interface MapServiceI {
    /**
     * Konstruktor des MapServices.
     *
     * @param AConfiguratorService Service zur Verwaltung der Konfiguration.
     * @return Kacheln der Map.
     */
    ArrayList<Tile> initConfigure(ConfiguratorServiceI AConfiguratorService);

    /**
     * Auslesen der Karte aus der Datenbank für den übergebenen Dungeon.
     *
     * @param ADungeonId ID des Dungeons.
     * @param AGameView  Wenn die Karte für die Spieleransicht erzeugt werden soll, muss hier True gesetzt werden.
     * @return Kacheln der Map.
     */
    Tile[][] getMapGame(long ADungeonId, boolean AGameView);

    /**
     * @param ADungeonID ID.
     * @return so n zeug.
     */
    Tuple<Integer> getMinXY(long ADungeonID);

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
     * @param ALocationX  X Koordinate.
     * @param ALocationY  Y Koordinate.
     * @param AHorizontal Eingabe, ob es sich um eine horizontale Wand handelt.
     * @return Rückgabe, ob die Wand gesetzt werden kann oder nicht.
     */
    boolean canToggleWall(int ALocationX, int ALocationY, boolean AHorizontal);

    /**
     * Wand an den übergebenen Koordinaten setzen.
     *
     * @param ALocationX  X Koordinate.
     * @param ALocationY  Y Koordinate.
     * @param AHorizontal Eingabe, ob es sich um eine horizontale Wand handelt.
     * @return ArrayList.
     */
    ArrayList<Tile> toggleWall(int ALocationX, int ALocationY, boolean AHorizontal);

    /**
     * Gibt den Raum mit den übergebenen Koordinaten zurück.
     *
     * @param ALocationX X-Koordinate des Raumes.
     * @param ALocationY Y-Koordinate des Raumes.
     * @return Raum an den übergebenen Koordinaten.
     */
    Room getRoomByCoordinate(int ALocationX, int ALocationY);
}
