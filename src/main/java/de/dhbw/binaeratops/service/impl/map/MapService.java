package de.dhbw.binaeratops.service.impl.map;

import de.dhbw.binaeratops.model.entitys.Room;
import de.dhbw.binaeratops.model.repository.DungeonRepositoryI;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import de.dhbw.binaeratops.service.api.map.MapServiceI;
import de.dhbw.binaeratops.model.map.Tile;
import de.dhbw.binaeratops.model.map.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Komponente "MapService".
 * <p>
 * Dieser Service beinhaltet die Businesslogik zur Interaktion im Konfigurator mit der Karte.
 * </p>
 * <p>
 * Für Schnittstelle dieser Komponente siehe @{@link MapServiceI}.
 * </p>
 *
 * @author Lukas Göpel, Matthias Rall, Lars Rösel
 */
@Scope(value = "session")
@Service
public class MapService implements MapServiceI {

    private ConfiguratorServiceI configuratorServiceI;
    //Alle Räume im Dungeon werden einer Hash Map gespeichert
    HashMap<Tuple<Integer>, Room> rooms;
    //Räume, die von einem Algorithmus schon durchsucht wurden, werden hier gespeichert
    HashMap<Tuple<Integer>, Room> searchedRooms;

    @Autowired
    DungeonRepositoryI dungeonRepositoryI;

    @Override
    public ArrayList<Tile> initConfigure(ConfiguratorServiceI AConfiguratorServiceI) {
        configuratorServiceI = AConfiguratorServiceI;
        ArrayList<Tile> tiles = new ArrayList<>();
        rooms = new HashMap<>();

        for (Room r :
                configuratorServiceI.getDungeon().getRooms()) {
            rooms.put(new Tuple<>(r.getXCoordinate(), r.getYCoordinate()), r);
            tiles.add(new Tile(r.getXCoordinate(), r.getYCoordinate(), tileName(r)));
        }
        return tiles;
    }

    @Override
    public Tuple<Integer> getMinXY(long ADungeonID){
        int minX = 8;
        int minY = 8;
        int maxX = 0;
        int maxY = 0;
        for (Room r : dungeonRepositoryI.findByDungeonId(ADungeonID).getRooms()) {
            if (r.getXCoordinate() < minX)
                minX = r.getXCoordinate();
            if (r.getYCoordinate() < minY)
                minY = r.getYCoordinate();
            if (r.getXCoordinate() > maxX)
                maxX = r.getXCoordinate();
            if (r.getYCoordinate() > maxY)
                maxY = r.getYCoordinate();
        }
        return new Tuple<>(minX,minY);
    }

    @Override
    public Tile[][] getMapGame(long ADungeonID) {
        ArrayList<Tile> tiles = new ArrayList<>();
        int minX = 8;
        int minY = 8;
        int maxX = 0;
        int maxY = 0;
        for (Room r : dungeonRepositoryI.findByDungeonId(ADungeonID).getRooms()) {
            tiles.add(new Tile(r.getXCoordinate(), r.getYCoordinate(), tileName(r)));
            if (r.getXCoordinate() < minX)
                minX = r.getXCoordinate();
            if (r.getYCoordinate() < minY)
                minY = r.getYCoordinate();
            if (r.getXCoordinate() > maxX)
                maxX = r.getXCoordinate();
            if (r.getYCoordinate() > maxY)
                maxY = r.getYCoordinate();
        }
        if (tiles.size() == 0){
            Tile[][] a= new Tile[1][1];
            a[0][0]=new Tile(0,0,"KarteBack");
            return a;
        }

        int sizeX = maxX - minX + 1;
        int sizeY = maxY - minY + 1;
        Tile[][] output = new Tile[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                output[i][j] = new Tile(i, j, "KarteBack");
            }
        }
        for (Tile t : tiles) {
            t.setX(t.getX() - minX);
            t.setY(t.getY() - minY);
            output[t.getX()][t.getY()] = t;
        }
        return output;
    }


    @Override
    public boolean roomExists(int ALocationX, int ALocationY) {
        return rooms.containsKey(new Tuple<>(ALocationX, ALocationY));
    }

    @Override
    public boolean canPlaceRoom(int ALocationX, int ALocationY) {
        //überprüfen, ob an der Position schon ein Raum existiert
        if (rooms.containsKey(new Tuple<>(ALocationX, ALocationY))) {
            return false;
        }

        //überprüfen, ob schon ein Feld gesetzt wurde
        if (rooms.size() == 0) {
            return true;
        } else {
            //überprüfen, ob ein geklicktes Feld der Nachbar eines vorhandenen Felds ist
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

        //Raum wird der Map hinzugefügt
        rooms.put(new Tuple<>(ALocationX, ALocationY), myRoom);

        //überprüfen, ob es umliegende Räume gibt, die verändert werden müssen

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

        //neu erstellter Raum der Tiles-Liste hinzufügen
        tiles.add(new Tile(ALocationX, ALocationY, tileName(myRoom)));
        return tiles;
    }


    @Override
    public boolean canDeleteRoom(int ALocationX, int ALocationY) {
        if (rooms.size() == 1)
            return true;
        Room room = rooms.get(new Tuple<>(ALocationX, ALocationY));
        LinkedList<Room> allNeighbours = getAllNeighbours(room);
        Room startRoom = allNeighbours.pop();
        for (Room r : allNeighbours) {
            ArrayList<Room> searchedRooms = new ArrayList<>();
            searchedRooms.add(room);
            if (!canReachRoom(startRoom, r, searchedRooms))
                return false;
        }
        return true;
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

    private boolean canReachRoom(Room r1, Room r2, ArrayList<Room> checkedRooms) {
        //Man sucht mit r1 r2. Entweder r1 ist r2 und es war erfolgreich oder man sucht noch weiter. Mit checked Rooms werden schon besuchte Räume gespeichert.
        if (r1 == null || checkedRooms.contains(r1))
            return false;
        if (r1 == r2)
            return true;
        checkedRooms.add(r1);
        return canReachRoom(getRoomById(r1.getNorthRoomId()), r2, checkedRooms)
                || canReachRoom(getRoomById(r1.getEastRoomId()), r2, checkedRooms)
                || canReachRoom(getRoomById(r1.getSouthRoomId()), r2, checkedRooms)
                || canReachRoom(getRoomById(r1.getWestRoomId()), r2, checkedRooms);
    }

    @Override
    public boolean canToggleWall(int ALocationX, int ALocationY, boolean AHorizontal) {
        //Die Koordinate muss existieren
        if (rooms.containsKey(new Tuple<>(ALocationX, ALocationY))) {
            Room room = rooms.get(new Tuple<>(ALocationX, ALocationY));
            //Bei der Wand geht es um das Trennen von room und dem östlich liegenden Raum
            if (!AHorizontal) {
                Room roomEast = getRoomById(room.getEastRoomId());
                //Wenn roomEast nicht existiert
                if (roomEast == null)
                    return true;
                room.setEastRoomId(null);
                roomEast.setWestRoomId(null);
                //Kann ich auf irgendeinen anderen Weg von room zu roomEast kommen. Setze aber auf jeden Fall die Wände wieder
                if (!canReachRoom(room, roomEast, new ArrayList<>())) {
                    room.setEastRoomId(roomEast.getRoomId());
                    roomEast.setWestRoomId(room.getRoomId());
                    return false;
                } else {
                    room.setEastRoomId(roomEast.getRoomId());
                    roomEast.setWestRoomId(room.getRoomId());
                    return true;
                }
            } else {
                //In diesem else geht es um das Trennen von room und dem südlich liegenden Raum
                Room roomSouth = getRoomById(room.getSouthRoomId());
                //Wenn roomSouth nicht existiert
                if (roomSouth == null)
                    return true;
                room.setSouthRoomId(null);
                roomSouth.setNorthRoomId(null);
                //Kann ich auf irgendeinen anderen Weg von room zu roomSouth kommen. Setze aber auf jeden Fall die Wände wieder
                if (!canReachRoom(room, roomSouth, new ArrayList<>())) {
                    room.setSouthRoomId(roomSouth.getRoomId());
                    roomSouth.setNorthRoomId(room.getRoomId());
                    return false;
                } else {
                    room.setSouthRoomId(roomSouth.getRoomId());
                    roomSouth.setNorthRoomId(room.getRoomId());
                    return true;
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

    private LinkedList<Room> getAllNeighbours(Room ARoom) {
        LinkedList<Room> returnR = new LinkedList<>();
        if (ARoom.getNorthRoomId() != null)
            returnR.push(getRoomById(ARoom.getNorthRoomId()));
        if (ARoom.getEastRoomId() != null)
            returnR.push(getRoomById(ARoom.getEastRoomId()));
        if (ARoom.getSouthRoomId() != null)
            returnR.push(getRoomById(ARoom.getSouthRoomId()));
        if (ARoom.getWestRoomId() != null)
            returnR.push(getRoomById(ARoom.getWestRoomId()));
        return returnR;
    }

}
