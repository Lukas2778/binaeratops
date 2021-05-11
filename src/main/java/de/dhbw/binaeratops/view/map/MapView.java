package de.dhbw.binaeratops.view.map;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.dhbw.binaeratops.model.map.Tile;
import de.dhbw.binaeratops.service.api.map.MapServiceI;
import de.dhbw.binaeratops.service.impl.map.MapService;

/**
 * @author Lukas Göpel
 * Date: 11.05.2021
 * Time: 19:59
 */
public class MapView {

    public Image[][] tiles;

    private MapServiceI mapServiceI;
    public MapView(Image[][] ATiles){
        tiles=ATiles;
        mapServiceI=new MapService();
    }

    public VerticalLayout initMap(Long ADungeonId) {
        Tile[][] newTiles = mapServiceI.getMapGame(ADungeonId);
        tiles = new Image[newTiles.length][newTiles[0].length];
        VerticalLayout columns = new VerticalLayout();
        columns.setSpacing(false);
        for (int i = 0; i < newTiles.length; i++) {
            HorizontalLayout rows = new HorizontalLayout();
            rows.setSpacing(false);
            for (int j = 0; j < newTiles[0].length; j++) {
                tiles[i][j] = new Image("map/" + newTiles[i][j].getPath() + ".png", "Room");
                tiles[i][j].addClassName("room");
                rows.add(tiles[i][j]);
            }
            columns.add(rows);
        }
        return columns;
    }

}
