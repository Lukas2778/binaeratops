package de.dhbw.binaeratops.view.map;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.dhbw.binaeratops.model.api.RoomI;
import de.dhbw.binaeratops.model.entitys.Room;
import de.dhbw.binaeratops.model.map.Tile;
import de.dhbw.binaeratops.service.api.map.MapServiceI;

import java.util.List;

/**
 * @author Lukas Göpel
 * Date: 11.05.2021
 * Time: 19:59
 */
public class MapView {

    Image[][] myTiles;

    public VerticalLayout initMap(MapServiceI AMapService, Long ADungeonId, Image[][] ATiles) {
        myTiles=ATiles;
        Tile[][] newTiles = AMapService.getMapGame(ADungeonId);
        myTiles = new Image[newTiles.length][newTiles[0].length];
        VerticalLayout columns = new VerticalLayout();
        columns.setSpacing(false);
        for (int i = 0; i < newTiles.length; i++) {
            HorizontalLayout rows = new HorizontalLayout();
            rows.setSpacing(false);
            for (int j = 0; j < newTiles[0].length; j++) {
                myTiles[i][j] = new Image("map/" + newTiles[i][j].getPath() + ".png", "Room");
                myTiles[i][j].addClassName("room");
                rows.add(myTiles[i][j]);
            }
            columns.add(rows);
        }
        return columns;
    }

    public void updateMap(Room ACurrentRoom, List<Room> AVisitedRooms) {
        //alle Stellen des Dungeons schwarz färben, die noch nicht durch den Avatar erforscht wurden
        int x = ACurrentRoom.getXCoordinate();
        int y = ACurrentRoom.getYCoordinate();

        try {
        for (int i = 0; i < myTiles.length; i++) {
            for (int j = 0; j < myTiles[0].length; j++) {
                for (RoomI room : AVisitedRooms) {
                    if (!(room.getXCoordinate().equals(i) && room.getYCoordinate().equals(j))) {
                        //noch nicht besucht -> Raum ausblenden
                        myTiles[i][j].getStyle().set("opacity", "0.0");
                    }
                    else {
                        //schon besucht
                        myTiles[i][j].getStyle().set("opacity", "1.0");
                    }
                }
            }
        }
        } catch (Exception ignored) {
        }

        //alle anderen Räume roten Rand abwählen
        try {
            for (Image[] tL : myTiles) {
                for (Image t : tL) {
                    t.getStyle().set("border-style", "none");
                    t.getStyle().set("border-color", "inherit");
                }
            }
        } catch (Exception ignored) {
        }

        //aktuellen Raum roten Rand anwählen
        try {
            myTiles[x][y].getStyle().set("border-style", "solid");
            myTiles[x][y].getStyle().set("border-color", "red");
        } catch (Exception ignored) {
        }
    }
}
