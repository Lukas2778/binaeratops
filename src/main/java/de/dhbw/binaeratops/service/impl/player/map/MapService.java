package de.dhbw.binaeratops.service.impl.player.map;

import java.util.Arrays;

public class MapService {
    //kann Raum an bestimmter Stelle gesetzt werden
    //kann Raum an bestimmter Stelle gelöscht werden
    //kann Mauer an bestimmter Stelle gesetzt werden
    //kann Mauer an bestimmter Stelle gelöscht werden (nur wenn Bild)
    //Objekt, das den aktuellen Dungeon speichert
    //Größe im Vorfeld festlegen 10x10 oder 8x8

    private int MAP_SIZE;
    Boolean[][] roomsSet; //Array mit Räumen
    Boolean[][] wallSet; //Array mit Mauern
    Boolean[][] falseTmp;

    public MapService(int AMapSize) {
        MAP_SIZE = AMapSize;
        roomsSet = new Boolean[MAP_SIZE][MAP_SIZE];
        wallSet = new Boolean[MAP_SIZE][MAP_SIZE];
        falseTmp = new Boolean[MAP_SIZE][MAP_SIZE];
        //Karte mit keinem Raum
        for (int i = 0; i < MAP_SIZE; ++i) {
            Arrays.fill(roomsSet[i], Boolean.FALSE);
            Arrays.fill(wallSet[i], Boolean.FALSE);
            Arrays.fill(falseTmp[i], Boolean.FALSE);
        }
    }

    public void setMAP_SIZE(final int AMapSize) {
        MAP_SIZE = AMapSize;
    }

    public int getMAP_SIZE() {
        return MAP_SIZE;
    }

    public boolean roomExists(int ALocationX, int ALocationY) {
        return roomsSet[ALocationX][ALocationY];
        //wenn Raum schon existiert, muss in der View mittels der entsprechenden ID die Raumeinstellungen angeigt werden
    }

    public boolean canPlaceRoom(int ALocationX, int ALocationY) {
        //Überprüfen, ob an der Position schon ein Raum existiert
        if (roomsSet[ALocationX][ALocationY]) {
            return false;
        }

        //Überprüfen, ob schon ein Feld gesetzt wurde
        if (Arrays.deepEquals(falseTmp, roomsSet)) {
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

    public void placeRoom(int ALocationX, int ALocationY) {
        //true in roomSet an bestimmte Stelle speichern
        //Raum erzeugen und in Datenbank speichern -> Raum_ID
        //Raum_ID wird an Dungeon
        roomsSet[ALocationX][ALocationY] = true;
    }

    public boolean canDeleteRoom(int ALocationX, int ALocationY) {
        //überprüfen, ob weniger als zwei Nachbarn
        int neighborCount = 0;
//        boolean neighborNorth = false;
//        boolean neighborEast = false;
//        boolean neighborSouth = false;
//        boolean neighborWest = false;
        //Nachbar Norden
        if (!(ALocationX - 1 < 0)) {
            if (roomsSet[ALocationX - 1][ALocationY]) {
                neighborCount++;
//                neighborNorth = true;
            }
        }
        //Nachbar Osten
        if (!(ALocationY + 1 > MAP_SIZE - 1)) {
            if (roomsSet[ALocationX][ALocationY + 1]) {
                neighborCount++;
//                neighborEast = true;
            }
        }
        //Nachbar Süden
        if (!(ALocationX + 1 > MAP_SIZE - 1)) {
            if (roomsSet[ALocationX + 1][ALocationY]) {
                neighborCount++;
//                neighborSouth = true;
            }
        }
        //Nachbar Westen
        if (!(ALocationY - 1 < 0)) {
            if (roomsSet[ALocationX][ALocationY - 1]) {
                neighborCount++;
//                neighborWest = true;
            }
        }
        //Ergebnis prüfen
        if (neighborCount < 2) {
            return true;
        }
        return false;
    }

    public void deleteRoom(int ALocationX, int ALocationY) {
        roomsSet[ALocationX][ALocationY] = false;
    }

    public boolean wallExists(int ALocationX, int ALocationY) {
        return wallSet[ALocationX][ALocationY];
    }

    public boolean canPlaceWall(int ALocationX, int ALocationY) {
        return true;
    }

    public void placeWall(int ALocationX, int ALocationY) {
        wallSet[ALocationX][ALocationY] = true;
    }

    public boolean canDeleteWall(int ALocationX, int ALocationY) {
        return true;
    }

    public void deleteWall(int ALocationX, int ALocationY) {
        wallSet[ALocationX][ALocationY] = false;
    }

}
