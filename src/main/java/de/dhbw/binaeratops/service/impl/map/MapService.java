package de.dhbw.binaeratops.service.impl.map;

import de.dhbw.binaeratops.model.entitys.Room;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import de.dhbw.binaeratops.service.api.map.MapServiceI;
import de.dhbw.binaeratops.model.map.Tile;
import de.dhbw.binaeratops.model.map.Tuple;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;


@Scope(value = "session")
@Service
public class MapService implements MapServiceI {

    private ConfiguratorServiceI configuratorServiceI;
    //Alle Räume im Dungeon.
    HashMap<Tuple<Integer>, Room> rooms;
    //Räume, die von einem Algorithmus schon durchsucht wurden werden hier gespeichert
    HashMap<Tuple<Integer>, Room> searchedRooms;


    @Override
    public ArrayList<Tile> init(ConfiguratorServiceI AConfiguratorServiceI) {

        configuratorServiceI = AConfiguratorServiceI;
        ArrayList<Tile> tiles = new ArrayList<>();
        rooms = new HashMap<>();
        searchedRooms = new HashMap<>();

        for (Room r :
                configuratorServiceI.getDungeon().getRooms()) {
            rooms.put(new Tuple<>(r.getXCoordinate(), r.getYCoordinate()), r);
            tiles.add(new Tile(r.getXCoordinate(), r.getYCoordinate(), tileName(r)));
        }
        return tiles;
    }


    @Override
    public boolean roomExists(int ALocationX, int ALocationY) {
        return rooms.containsKey(new Tuple<>(ALocationX, ALocationY));
    }

    @Override
    public boolean canPlaceRoom(int ALocationX, int ALocationY) {
        //Überprüfen, ob an der Position schon ein Raum existiert
        if (rooms.containsKey(new Tuple<>(ALocationX, ALocationY))) {
            return false;
        }

        //Überprüfen, ob schon ein Feld gesetzt wurde
        if (rooms.size() == 0) {
            return true;
        } else {
            //überprüfen, ob geklicktes Feld der Nachbar ist eines vorhandenen felds ist
            return rooms.containsKey(new Tuple<>(ALocationX - 1, ALocationY))
                    || rooms.containsKey(new Tuple<>(ALocationX, ALocationY + 1))
                    || rooms.containsKey(new Tuple<>(ALocationX + 1, ALocationY))
                    || rooms.containsKey(new Tuple<>(ALocationX, ALocationY - 1));
        }
    }

    @Override
    public ArrayList<Tile> placeRoom(int ALocationX, int ALocationY) {

        ArrayList<Tile> tiles = new ArrayList<>();
        Room myRoom = new Room(ALocationX, ALocationY);
        //Raum wird in die Datenbank gespeichert
        configuratorServiceI.addRoom(myRoom);

        //Raum wird in die Map hinzugefügt
        rooms.put(new Tuple<>(ALocationX, ALocationY), myRoom);

        //Überprüfen, ob es umliegende Räume gibt, die verändert werden müssen

        //Norden
        if (rooms.containsKey(new Tuple<>(ALocationX - 1, ALocationY))) {
            //Nördlichen raum finden und durchgang verknüpfen und speichern
            Room north = rooms.get(new Tuple<>(ALocationX - 1, ALocationY));
            north.setSouthRoomId(myRoom.getRoomId());
            //Raum in der Datenbank überschreiben
            configuratorServiceI.saveRoom(north);
            //der Tile Liste wird die nördliche Kachel hinzugefügt, diese muss sich in der Oberfläche ändern (Tür)
            tiles.add(new Tile(ALocationX - 1, ALocationY, tileName(north)));
            //dem aktuellen Raum wird die Referenz zum nördlichen Raum hinzugefügt
            myRoom.setNorthRoomId(north.getRoomId());
        }
        //Osten
        if (rooms.containsKey(new Tuple<>(ALocationX, ALocationY + 1))) {
            Room east = rooms.get(new Tuple<>(ALocationX, ALocationY + 1));
            east.setWestRoomId(myRoom.getRoomId());
            configuratorServiceI.saveRoom(east);
            tiles.add(new Tile(ALocationX, ALocationY + 1, tileName(east)));
            myRoom.setEastRoomId(east.getRoomId());
        }
        //Süden
        if (rooms.containsKey(new Tuple<>(ALocationX + 1, ALocationY))) {
            Room south = rooms.get(new Tuple<>(ALocationX + 1, ALocationY));
            south.setNorthRoomId(myRoom.getRoomId());
            configuratorServiceI.saveRoom(south);
            tiles.add(new Tile(ALocationX + 1, ALocationY, tileName(south)));
            myRoom.setSouthRoomId(south.getRoomId());
        }
        //Westen
        if (rooms.containsKey(new Tuple<>(ALocationX, ALocationY - 1))) {
            Room west = rooms.get(new Tuple<>(ALocationX, ALocationY - 1));
            west.setEastRoomId(myRoom.getRoomId());
            configuratorServiceI.saveRoom(west);
            tiles.add(new Tile(ALocationX, ALocationY - 1, tileName(west)));
            myRoom.setWestRoomId(west.getRoomId());
        }

        //neu erstellter Raum wird in der Datenbank aktualisiert
        configuratorServiceI.saveRoom(myRoom);

        //neu erstellten Raum der Tiles Liste hinzufügen
        tiles.add(new Tile(ALocationX, ALocationY, tileName(myRoom)));
        return tiles;
    }


    @Override
    public boolean canDeleteRoom(int ALocationX, int ALocationY) {
        //wenn es nur zwei räume gibt, kann dieser entfernt werden
        if (rooms.size() <= 2) {
            return true;
        } else {
            Room room = rooms.get(new Tuple<>(ALocationX, ALocationY));
            searchedRooms.put(new Tuple<>(ALocationX, ALocationY), room);
            canReachAllRooms(Objects.requireNonNull(findANeighbor(room)));
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
        Room north = getRoomById(ACurrentRoom.getNorthRoomId());
        Room east = getRoomById(ACurrentRoom.getEastRoomId());
        Room west = getRoomById(ACurrentRoom.getWestRoomId());
        Room south = getRoomById(ACurrentRoom.getSouthRoomId());

        if (north != null && !searchedRooms.containsKey(new Tuple<>(north.getXCoordinate(), north.getYCoordinate()))) {
            searchedRooms.put(new Tuple<>(north.getXCoordinate(), north.getYCoordinate()), north);
            canReachAllRooms(north);
        }
        if (east != null && !searchedRooms.containsKey(new Tuple<>(east.getXCoordinate(), east.getYCoordinate()))) {
            searchedRooms.put(new Tuple<>(east.getXCoordinate(), east.getYCoordinate()), east);
            canReachAllRooms(east);
        }
        if (west != null && !searchedRooms.containsKey(new Tuple<>(west.getXCoordinate(), west.getYCoordinate()))) {
            searchedRooms.put(new Tuple<>(west.getXCoordinate(), west.getYCoordinate()), west);
            canReachAllRooms(west);
        }
        if (south != null && !searchedRooms.containsKey(new Tuple<>(south.getXCoordinate(), south.getYCoordinate()))) {
            searchedRooms.put(new Tuple<>(south.getXCoordinate(), south.getYCoordinate()), south);
            canReachAllRooms(south);
        }
    }


    /**
     * Holt den Raum über die eingegebene RaumID aus der Raum-HashMap.
     *
     * @param AId RaumID.
     * @return Gibt den gesuchten Raum als Raum-Objekt zurück.
     */
    private Room getRoomById(Long AId) {
        if (AId == null)
            return null;
        for (Map.Entry<Tuple<Integer>, Room> e : rooms.entrySet()) {
            if (e.getValue().getRoomId().equals(AId)) {
                return e.getValue();
            }
        }
        return null;
    }

    /**
     * Gibt den ersten gefundenen Nachbarn des eingegebenen Raums zurück.
     *
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

    @Override
    public ArrayList<Tile> deleteRoom(int ALocationX, int ALocationY) {
        Room myRoom = rooms.get(new Tuple<>(ALocationX, ALocationY));
        ArrayList<Tile> tiles = new ArrayList<>();

        //Norden
        if (rooms.containsKey(new Tuple<>(ALocationX - 1, ALocationY))) {
            Room north = rooms.get(new Tuple<>(ALocationX - 1, ALocationY));
            north.setSouthRoomId(null);
            configuratorServiceI.saveRoom(north);
            tiles.add(new Tile(ALocationX - 1, ALocationY, tileName(north)));
        }
        //Osten
        if (rooms.containsKey(new Tuple<>(ALocationX, ALocationY + 1))) {
            Room east = rooms.get(new Tuple<>(ALocationX, ALocationY + 1));
            east.setWestRoomId(null);
            configuratorServiceI.saveRoom(east);
            tiles.add(new Tile(ALocationX, ALocationY + 1, tileName(east)));
        }
        //Süden
        if (rooms.containsKey(new Tuple<>(ALocationX + 1, ALocationY))) {
            Room south = rooms.get(new Tuple<>(ALocationX + 1, ALocationY));
            south.setNorthRoomId(null);
            configuratorServiceI.saveRoom(south);
            tiles.add(new Tile(ALocationX + 1, ALocationY, tileName(south)));
        }
        //Westen
        if (rooms.containsKey(new Tuple<>(ALocationX, ALocationY - 1))) {
            Room west = rooms.get(new Tuple<>(ALocationX, ALocationY - 1));
            west.setEastRoomId(null);
            configuratorServiceI.saveRoom(west);
            tiles.add(new Tile(ALocationX, ALocationY - 1, tileName(west)));
        }

        rooms.remove(new Tuple<>(ALocationX, ALocationY));
        configuratorServiceI.deleteRoom(myRoom);
        tiles.add(new Tile(ALocationX, ALocationY, "KarteBack"));
        return tiles;
    }

    @Override
    public boolean canToggleWall(int ALocationX, int ALocationY, boolean AHorizontal) {
        //hier werden die mauern, die räume westlich oder östlich von sich haben verarbeitet
        if (!AHorizontal && rooms.containsKey(new Tuple<>(ALocationX, ALocationY))
                && rooms.containsKey(new Tuple<>(ALocationX, ALocationY + 1))) {
            Room room = rooms.get(new Tuple<>(ALocationX, ALocationY));
            if (room.getEastRoomId() == null) {
                return true;
            } else {
                if (rooms.size() <= 3)
                    return false;

                Room roomEast = rooms.get(new Tuple<>(ALocationX, ALocationY + 1));
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
        //hier werden die Mauern, die nördlich und südlich räume haben verarbeitet
        else if (AHorizontal && rooms.containsKey(new Tuple<>(ALocationX, ALocationY))
                && rooms.containsKey(new Tuple<>(ALocationX + 1, ALocationY))) {
            Room room = rooms.get(new Tuple<>(ALocationX, ALocationY));
            if (room.getSouthRoomId() == null) {
                return true;
            } else {
                if (rooms.size() <= 3)
                    return false;

                Room roomSouth = rooms.get(new Tuple<>(ALocationX + 1, ALocationY));
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


    @Override
    public ArrayList<Tile> toggleWall(int ALocationX, int ALocationY, boolean AHorizontal) {
        ArrayList<Tile> tiles = new ArrayList<>();
        Room room = rooms.get(new Tuple<>(ALocationX, ALocationY));

        if (!AHorizontal) {
            Room eastRoom = rooms.get(new Tuple<>(ALocationX, ALocationY + 1));
            if (room.getEastRoomId() == null) {
                room.setEastRoomId(eastRoom.getRoomId());
                eastRoom.setWestRoomId(room.getRoomId());
            } else {
                room.setEastRoomId(null);
                eastRoom.setWestRoomId(null);
            }
            configuratorServiceI.saveRoom(eastRoom);
            tiles.add(new Tile(ALocationX, ALocationY, tileName(room)));
            tiles.add(new Tile(ALocationX, ALocationY + 1, tileName(eastRoom)));
        } else {
            Room southRoom = rooms.get(new Tuple<>(ALocationX + 1, ALocationY));
            if (room.getSouthRoomId() == null) {
                room.setSouthRoomId(southRoom.getRoomId());
                southRoom.setNorthRoomId(room.getRoomId());
            } else {
                room.setSouthRoomId(null);
                southRoom.setNorthRoomId(null);
            }
            configuratorServiceI.saveRoom(southRoom);
            tiles.add(new Tile(ALocationX, ALocationY, tileName(room)));
            tiles.add(new Tile(ALocationX + 1, ALocationY, tileName(southRoom)));
        }
        configuratorServiceI.saveRoom(room);
        return tiles;
    }

    @Override
    public Room getRoomByCoordinate(int ALocationX, int ALocationY) {
        return rooms.getOrDefault(new Tuple<>(ALocationX, ALocationY), null);
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
