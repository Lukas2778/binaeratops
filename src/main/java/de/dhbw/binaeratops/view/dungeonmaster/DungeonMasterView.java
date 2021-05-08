package de.dhbw.binaeratops.view.dungeonmaster;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import de.dhbw.binaeratops.model.map.Tile;
import de.dhbw.binaeratops.service.api.map.MapServiceI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * @author Mathias Rall
 * Date: 07.05.2021
 * Time: 16:20
 */

@CssImport("./views/game/map-master.css")
@PageTitle("Title")
public class DungeonMasterView extends Div implements HasUrlParameter<Long> {
    private final int WIDTH = 8;
    Image[][] tiles;

    private SplitLayout innerLayout = new SplitLayout();
    private SplitLayout mainLayout = new SplitLayout();


    private MapServiceI mapServiceI;

    public DungeonMasterView(@Autowired MapServiceI mapServiceI) {
        this.mapServiceI = mapServiceI;


        innerLayout.setOrientation(SplitLayout.Orientation.VERTICAL);
        //TODO chat ergänzen
        mainLayout.addToPrimary(new VerticalLayout());

    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long aLong) {
        innerLayout.addToPrimary(initMap(aLong));
        innerLayout.addToSecondary(new Label("Hallo" + aLong));
        mainLayout.addToSecondary(innerLayout);
        add(mainLayout);
    }


    VerticalLayout initMap(Long ADungeonId) {
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
