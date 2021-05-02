package de.dhbw.binaeratops.service.impl.player.map;

import de.dhbw.binaeratops.model.entitys.Room;
import de.dhbw.binaeratops.model.repository.DungeonRepositoryI;
import de.dhbw.binaeratops.model.repository.RoomRepositoryI;
import de.dhbw.binaeratops.view.player.map.Tile;
import de.dhbw.binaeratops.view.player.map.Tupel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Dieser Service beinhaltet die Businesslogik zur Interaktion im Konfigurator mit der Karte.
 */
@Service
public class MapService {

    @Autowired
    private RoomRepositoryI roomRepositoryI;

    @Autowired
    private DungeonRepositoryI dungeonRepositoryI;


    private int MAP_SIZE;
    Boolean[][] roomsSet; //Array mit Räumen

    HashMap<Tupel<Integer>, Room> rooms = new HashMap<>();


    //räume, die von einem Algorthmus schon durchsucht wurden
    HashMap<Tupel<Integer>, Room> searchedRooms = new HashMap<>();

    //TODO dungeonId setzen im konstruktor
    Long dungeonId;


    /**
     * Konstruktor des MapServices.
     *
     * @param AMapSize Größe der zu erstellenden Karte in Form von AMapSize x AMapSize Räume.
     */
    public void init(int AMapSize) {
        //beim wiederaufnehmen einer konfiguration muss der alte stand aus der datenbank geladen werden
        //dungeonRepositoryI.findByDungeonId(1L).getRooms();

        this.MAP_SIZE = AMapSize;
        roomsSet = new Boolean[MAP_SIZE][MAP_SIZE];
        //Karte mit keinem Raum
        for (int i = 0; i < MAP_SIZE; ++i) {
            Arrays.fill(roomsSet[i], Boolean.FALSE);
        }
    }

    /**
     * Abfrage der Größe der Karte.
     *
     * @return Rückgabe der Größe der Karte in Form von Anzahl der Räume am Rand des Quadrats.
     */
    public int getMAP_SIZE() {
        return MAP_SIZE;
    }

    /**
     * Überprüft, ob die Koordinate im Array und damit der Raum schon gesetzt wurde.
     *
     * @param ALocationX X Koordinate.
     * @param ALocationY Y Koordinate.
     * @return Rückgabe, ob der Raum schon gesetzt wurde oder nicht.
     */
    public boolean roomExists(int ALocationX, int ALocationY) {
        return roomsSet[ALocationX][ALocationY];
        //wenn Raum schon existiert, muss in der View mittels der entsprechenden ID die Raumeinstellungen angeigt werden
    }

    /**
     * Überprüft, ob der Raum an den übergebenen Koordinaten gesetzt werden kann.
     *
     * @param ALocationX X Koordinate.
     * @param ALocationY Y Koordinate.
     * @return Rückgabe, ob der Raum gesetzt werden kann oder nicht.
     * TODO refactor manche abfragen könnten verständlicher sein
     */
    public boolean canPlaceRoom(int ALocationX, int ALocationY) {
        //Überprüfen, ob an der Position schon ein Raum existiert
        if (roomsSet[ALocationX][ALocationY]) {
            return false;
        }

        //Überprüfen, ob schon ein Feld gesetzt wurde
        if (rooms.size() == 0) {
            return true;
        } else {
            //überprüfen, ob geklicktes Feld der Nachbar ist
            //Nachbar Norden
            if (!(ALocationX - 1 < 0)) {
                if (roomsSet[ALocationX - 1][ALocationY]) {
                    return true;
                }
            }
            //Nachbar Osten
            if (!(ALocationY + 1 > MAP_SIZE - 1)) {
                if (roomsSet[ALocationX][ALocationY + 1]) {
                    return true;
                }
            }
            //Nachbar Süden
            if (!(ALocationX + 1 > MAP_SIZE - 1)) {
                if (roomsSet[ALocationX + 1][ALocationY]) {
                    return true;
                }
            }
            //Nachbar Westen
            if (!(ALocationY - 1 < 0)) {
                if (roomsSet[ALocationX][ALocationY - 1])
                    return true;
            }
        }
        return false;
    }

    /**
     * Setzen des Raums an den übergebenen Koordinaten.
     *
     * @param ALocationX X Koordinate.
     * @param ALocationY Y Koordinate.
     * @return Liste mit den Kacheln, die geändert werden;
     */
    public ArrayList<Tile> placeRoom(int ALocationX, int ALocationY) {
        //true in roomSet an bestimmte Stelle speichern
        //Raum erzeugen und in Datenbank speichern -> Raum_ID
        //Raum_ID wird an Dungeon
        ArrayList<Tile> tiles = new ArrayList<>();

        Room myRoom = new Room(ALocationX, ALocationY);
        //Raum wird gespeichert
        roomRepositoryI.save(myRoom);

        //Raum wird in die Map hinzugefügt
        rooms.put(new Tupel<>(ALocationX, ALocationY), myRoom);

        //Norden

        if (ALocationX > 0 && roomsSet[ALocationX - 1][ALocationY]) {
            //Nördlichen raum finden und durchgang verknüpfen und speichern
            Room north = rooms.get(new Tupel<>(ALocationX - 1, ALocationY));
            north.setSouthRoomId(myRoom.getRoomId());
            roomRepositoryI.save(north);
            //der rückgabe list den nördlichen raum mitgeben
            tiles.add(new Tile(ALocationX - 1, ALocationY, tileName(north)));
            // den südlichen raum des aktuellen speichern
            myRoom.setNorthRoomId(north.getRoomId());
            roomRepositoryI.save(myRoom);
        }
        //Osten
        if (ALocationY < MAP_SIZE - 1 && roomsSet[ALocationX][ALocationY + 1]) {
            //Östlichen raum finden und durchgang verknüpfen und speichern
            Room east = rooms.get(new Tupel<>(ALocationX, ALocationY + 1));
            east.setWestRoomId(myRoom.getRoomId());
            roomRepositoryI.save(east);
            //der rückgabe list den östlichen raum mitgeben
            tiles.add(new Tile(ALocationX, ALocationY + 1, tileName(east)));
            // den östlichen raum des aktuellen speichern
            myRoom.setEastRoomId(east.getRoomId());
            roomRepositoryI.save(myRoom);
        }
        //Süden
        if (ALocationX < MAP_SIZE - 1 && roomsSet[ALocationX + 1][ALocationY]) {
            //südlichen raum finden und durchgang verknüpfen und speichern
            Room south = rooms.get(new Tupel<>(ALocationX + 1, ALocationY));
            south.setNorthRoomId(myRoom.getRoomId());
            roomRepositoryI.save(south);
            //der rückgabe list den Südlichen raum mitgeben
            tiles.add(new Tile(ALocationX + 1, ALocationY, tileName(south)));
            // den nördlichen raum des aktuellen speichern
            myRoom.setSouthRoomId(south.getRoomId());
            roomRepositoryI.save(myRoom);
        }
        //Westen
        if (ALocationY > 0 && roomsSet[ALocationX][ALocationY - 1]) {
            //Westlichen raum finden und durchgang verknüpfen und speichern
            Room west = rooms.get(new Tupel<>(ALocationX, ALocationY - 1));
            west.setEastRoomId(myRoom.getRoomId());
            roomRepositoryI.save(west);
            //der rückgabe list den westlichen raum mitgeben
            tiles.add(new Tile(ALocationX, ALocationY - 1, tileName(west)));
            // den östlichen raum des aktuellen speichern
            myRoom.setWestRoomId(west.getRoomId());
            roomRepositoryI.save(myRoom);
        }

        tiles.add(new Tile(ALocationX, ALocationY, tileName(myRoom)));

        roomsSet[ALocationX][ALocationY] = true;

        return tiles;
    }


    /**
     * Überprüfen, ob der Raum an den übergebenen Koordinaten gelöscht werden kann.
     * Dies passiert indem geprüft wird ob von einem beliebigen raum alle andern Räume erreichber sind.
     *
     * @param ALocationX X Koordinate.
     * @param ALocationY Y Koordinate.
     * @return Rückgabe, ob der Raum gelöscht werden kann oder nicht.
     */
    public boolean canDeleteRoom(int ALocationX, int ALocationY) {


        //wenn es nur einen raum gibt, kann dieser entfernt werden
        if (rooms.size() <= 2) {
            return true;
        } else {
            Room room = rooms.get(new Tupel<>(ALocationX, ALocationY));
            searchedRooms.put(new Tupel<>(ALocationX, ALocationY), room);
            canReachAllRooms(findANeighbor(room));
            if (searchedRooms.size() == rooms.size()) {
                searchedRooms.clear();
                return true;
            } else {
                searchedRooms.clear();
                return false;
            }
        }
    }

    /**
     * Sucht rekursiv nach verbundenen Räumen und speichert die Ergebnisse in der Variable 'searchedRooms'.
     *
     * @param ACurrentRoom Übergabe des aktuellen Raums, von dem aus gesucht werden soll.
     */
    private void canReachAllRooms(Room ACurrentRoom) {
        int x = ACurrentRoom.getxCoordinate();
        int y = ACurrentRoom.getyCoordinate();

        Room north = getRoomById(ACurrentRoom.getNorthRoomId());
        Room east = getRoomById(ACurrentRoom.getEastRoomId());
        Room west = getRoomById(ACurrentRoom.getWestRoomId());
        Room south = getRoomById(ACurrentRoom.getSouthRoomId());

        if (north != null && !searchedRooms.containsKey(new Tupel<>(north.getxCoordinate(), north.getyCoordinate()))) {
            searchedRooms.put(new Tupel<>(north.getxCoordinate(), north.getyCoordinate()), north);
            canReachAllRooms(north);
        }
        if (east != null && !searchedRooms.containsKey(new Tupel<>(east.getxCoordinate(), east.getyCoordinate()))) {
            searchedRooms.put(new Tupel<>(east.getxCoordinate(), east.getyCoordinate()), east);
            canReachAllRooms(east);
        }
        if (west != null && !searchedRooms.containsKey(new Tupel<>(west.getxCoordinate(), west.getyCoordinate()))) {
            searchedRooms.put(new Tupel<>(west.getxCoordinate(), west.getyCoordinate()), west);
            canReachAllRooms(west);
        }
        if (south != null && !searchedRooms.containsKey(new Tupel<>(south.getxCoordinate(), south.getyCoordinate()))) {
            searchedRooms.put(new Tupel<>(south.getxCoordinate(), south.getyCoordinate()), south);
            canReachAllRooms(south);
        }
    }

    /**
     * Holt den Raum über die eingegebene RaumID aus der Raum-HashMap.
     * @param AId RaumID.
     * @return Gibt den gesuchten Raum als Raum-Objekt zurück.
     */
    private Room getRoomById(Long AId) {
        if (AId == null)
            return null;
        for (Map.Entry<Tupel<Integer>, Room> e : rooms.entrySet()) {
            if (e.getValue().getRoomId().equals(AId)) {
                return e.getValue();
            }
        }
        return null;
    }

    /**
     * Gibt den ersten gefundenen Nachbarn des eingegebenen Raums zurück.
     * @param ARoom Raum dessen Nachbarn durchsucht werden sollen.
     * @return Gibt den ersten gefundenen Nachbarn zurück.
     */
    private Room findANeighbor(Room ARoom) {
        if (ARoom.getNorthRoomId() != null) {
            return getRoomById(ARoom.getNorthRoomId());
        }
        if (ARoom.getEastRoomId() != null) {
            return getRoomById(ARoom.getEastRoomId());
        }
        if (ARoom.getWestRoomId() != null) {
            return getRoomById(ARoom.getWestRoomId());
        }
        if (ARoom.getSouthRoomId() != null) {
            return getRoomById(ARoom.getSouthRoomId());
        }
        return null;
    }

    /**
     * Raum an den übergebenen Koordinaten löschen.
     *
     * @param ALocationX X Koordinate.
     * @param ALocationY Y Koordinate.
     */
    public ArrayList<Tile> deleteRoom(int ALocationX, int ALocationY) {
        roomsSet[ALocationX][ALocationY] = false;

        Room room = rooms.get(new Tupel<>(ALocationX, ALocationY));
        ArrayList<Tile> tiles = new ArrayList<>();

        //Norden
        if (ALocationX > 0 && roomsSet[ALocationX - 1][ALocationY]) {
            Room north = rooms.get(new Tupel(ALocationX - 1, ALocationY));
            north.setSouthRoomId(null);
            roomRepositoryI.save(north);
            tiles.add(new Tile(ALocationX - 1, ALocationY, tileName(north)));
        }
        //Osten
        if (ALocationY < MAP_SIZE - 1 && roomsSet[ALocationX][ALocationY + 1]) {
            Room east = rooms.get(new Tupel(ALocationX, ALocationY + 1));
            east.setWestRoomId(null);
            roomRepositoryI.save(east);
            tiles.add(new Tile(ALocationX, ALocationY + 1, tileName(east)));
        }
        //Süden
        if (ALocationX < MAP_SIZE - 1 && roomsSet[ALocationX + 1][ALocationY]) {
            Room south = rooms.get(new Tupel(ALocationX + 1, ALocationY));
            south.setNorthRoomId(null);
            roomRepositoryI.save(south);
            tiles.add(new Tile(ALocationX + 1, ALocationY, tileName(south)));
        }
        //Westen
        if (ALocationY > 0 && roomsSet[ALocationX][ALocationY - 1]) {
            Room west = rooms.get(new Tupel(ALocationX, ALocationY - 1));
            west.setEastRoomId(null);
            roomRepositoryI.save(west);
            tiles.add(new Tile(ALocationX, ALocationY - 1, tileName(west)));
        }

        roomRepositoryI.delete(room);
        rooms.remove(new Tupel<>(ALocationX, ALocationY));
        roomsSet[ALocationX][ALocationY] = false;
        tiles.add(new Tile(ALocationX, ALocationY, "KarteBack"));

        return tiles;
    }

    /**
     * Überprüfen, ob die Wand an den übergebenen Koordinaten geändet werden kann.
     *
     * @param ALocationX X Koordinate.
     * @param ALocationY Y Koordinate.
     * @param AHorizontal Eingabe, ob es sich um eine horizontale Wand handelt.
     * @return Rückgabe, ob die Wand gesetzt werden kann oder nicht.
     */
    public boolean canToggleWall(int ALocationX, int ALocationY, boolean AHorizontal) {
        //hier werden die mauern, die räume westlich oder östlich von sich haben verarbeitet
        if (!AHorizontal && roomsSet[ALocationX][ALocationY] && roomsSet[ALocationX][ALocationY + 1]) {
            Room room = rooms.get(new Tupel<>(ALocationX, ALocationY));
            if (room.getEastRoomId() == null) {
                return true;
            } else {
                if (rooms.size() <= 3)
                    return false;

                Room roomEast = rooms.get(new Tupel<>(ALocationX, ALocationY + 1));
                // wir setzen testweise eine Mauer um zu prüfen ob der dungeon abgesnitten wird
                room.setEastRoomId(null);
                roomEast.setWestRoomId(null);

                Room startRoom = findANeighbor(room);
                if (startRoom == null) {
                    room.setEastRoomId(roomEast.getRoomId());
                    roomEast.setWestRoomId(room.getRoomId());
                    return false;
                }

                canReachAllRooms(startRoom);

                // wir setzen die mauern wieder zurück
                room.setEastRoomId(roomEast.getRoomId());
                roomEast.setWestRoomId(room.getRoomId());

                if (rooms.size() == searchedRooms.size()) {
                    searchedRooms.clear();
                    return true;
                } else {
                    searchedRooms.clear();
                    return false;
                }
            }
        }
        //hier werden die mauern, die nordlich und sülich räume haben verarbeitet
        else if (AHorizontal && roomsSet[ALocationX][ALocationY] && roomsSet[ALocationX + 1][ALocationY]) {
            Room room = rooms.get(new Tupel<>(ALocationX, ALocationY));
            if (room.getSouthRoomId() == null) {
                return true;
            } else {
                if (rooms.size() <= 3)
                    return false;

                Room roomSouth = rooms.get(new Tupel<>(ALocationX + 1, ALocationY));
                // wir setzen testweise eine Mauer um zu prüfen ob der dungeon abgesnitten wird
                room.setSouthRoomId(null);
                roomSouth.setNorthRoomId(null);

                Room startRoom = findANeighbor(room);
                if (startRoom == null) {
                    room.setSouthRoomId(roomSouth.getRoomId());
                    roomSouth.setNorthRoomId(room.getRoomId());
                    return false;
                }

                canReachAllRooms(startRoom);

                // wir setzen die mauern wieder zurück
                room.setSouthRoomId(roomSouth.getRoomId());
                roomSouth.setNorthRoomId(room.getRoomId());

                if (rooms.size() == searchedRooms.size()) {
                    searchedRooms.clear();
                    return true;
                } else {
                    searchedRooms.clear();
                    return false;
                }
            }
        } else {
            return false;
        }
    }


    /**
     * Wand an den übergebenen Koordinaten setzen.
     *
     * @param ALocationX X Koordinate.
     * @param ALocationY Y Koordinate.
     * @param AHorizontal Eingabe, ob es sich um eine horizontale Wand handelt.
     */
    public ArrayList<Tile> toggleWall(int ALocationX, int ALocationY, boolean AHorizontal) {
        ArrayList<Tile> tiles = new ArrayList<>();
        Room room = rooms.get(new Tupel<>(ALocationX, ALocationY));

        if (!AHorizontal) {
            Room eastRoom = rooms.get(new Tupel<>(ALocationX, ALocationY + 1));
            if (room.getEastRoomId() == null) {
                room.setEastRoomId(eastRoom.getRoomId());
                eastRoom.setWestRoomId(room.getRoomId());
            } else {
                room.setEastRoomId(null);
                eastRoom.setWestRoomId(null);
            }
            roomRepositoryI.save(room);
            roomRepositoryI.save(eastRoom);
            tiles.add(new Tile(ALocationX, ALocationY, tileName(room)));
            tiles.add(new Tile(ALocationX, ALocationY + 1, tileName(eastRoom)));
        } else if (AHorizontal) {
            Room southRoom = rooms.get(new Tupel<>(ALocationX + 1, ALocationY));
            if (room.getSouthRoomId() == null) {
                room.setSouthRoomId(southRoom.getRoomId());
                southRoom.setNorthRoomId(room.getRoomId());
            } else {
                room.setSouthRoomId(null);
                southRoom.setNorthRoomId(null);
            }
            roomRepositoryI.save(room);
            roomRepositoryI.save(southRoom);
            tiles.add(new Tile(ALocationX, ALocationY, tileName(room)));
            tiles.add(new Tile(ALocationX + 1, ALocationY, tileName(southRoom)));
        }
        return tiles;
    }


    /**
     * @param ARoom Raum für den der name der Kachel erzeugt werden soll.
     * @return Kachelname für den gesuchten Raum.
     */
    private String tileName(Room ARoom) {
        String returnS = "Karte";
        if (ARoom.getNorthRoomId() != null)
            returnS += "N";
        if (ARoom.getEastRoomId() != null)
            returnS += "O";
        if (ARoom.getSouthRoomId() != null)
            returnS += "S";
        if (ARoom.getWestRoomId() != null)
            returnS += "W";
        return returnS;
    }

}
