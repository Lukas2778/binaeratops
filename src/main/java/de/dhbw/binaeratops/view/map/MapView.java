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

    public VerticalLayout initMap(MapServiceI AMapService, Long ADungeonId, Image[][] ATiles) {
        Tile[][] newTiles = AMapService.getMapGame(ADungeonId);
        ATiles = new Image[newTiles.length][newTiles[0].length];
        VerticalLayout columns = new VerticalLayout();
        columns.setSpacing(false);
        for (int i = 0; i < newTiles.length; i++) {
            HorizontalLayout rows = new HorizontalLayout();
            rows.setSpacing(false);
            for (int j = 0; j < newTiles[0].length; j++) {
                ATiles[i][j] = new Image("map/" + newTiles[i][j].getPath() + ".png", "Room");
                ATiles[i][j].addClassName("room");
                rows.add(ATiles[i][j]);
            }
            columns.add(rows);
        }
        return columns;
    }

}
