package de.dhbw.binaeratops.view.map;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.dhbw.binaeratops.service.api.map.MapServiceI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;


/**
 * Karten-Komponente für den Konfigurator, sowie die Spielansicht.
 */
@CssImport("./views/player/map/map.css")
//@Route("Mapp")
public class MapView extends VerticalLayout {

    MapServiceI myMapService;

    private final int width = 8;
    Image[][] tiles = new Image[width][width];

    /**
     * Dies ist der Konstruktor zum Erzeugen der Karte.
     *
     * @param AMyMapService MapService.
     */
    public MapView(@Autowired MapServiceI AMyMapService) {
        myMapService = AMyMapService;
        addClassName("map-view");

        myMapService.init(width, );

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        VerticalLayout lines = new VerticalLayout();
        lines.setSpacing(false);

        HorizontalLayout lineRoomBorder;
        HorizontalLayout line;

        for (int i = 0; i < width; ++i) {
            line = new HorizontalLayout();
            line.setSpacing(false);
            line.setSizeFull();

            lineRoomBorder = new HorizontalLayout();
            lineRoomBorder.setSpacing(false);
            lineRoomBorder.setSizeFull();
            for (int j = 0; j < width; ++j) {
                tiles[i][j] = new Image("map/KarteBack.png", "Room");
                tiles[i][j].addClassName("button-container");
                tiles[i][j].addClassName("buttRoom");

                NativeButton borderHorizonButt = new NativeButton();
                borderHorizonButt.addClassName("button-container");
                borderHorizonButt.addClassName("buttHorizontal");

                NativeButton borderVertButt = new NativeButton();
                borderVertButt.addClassName("button-container");
                borderVertButt.addClassName("buttVertical");

                int finalI = i;
                int finalJ = j;

                //click listener für die kacheln
                tiles[i][j].addClickListener(e -> {
                    if (!myMapService.roomExists(finalI, finalJ)) {
                        //Feld anwählen
                        if (myMapService.canPlaceRoom(finalI, finalJ)) {
                            //wenn ein raum plaziert wird muss dieser erstellt werden und seine Konfiguration angezeigt werden
                            ArrayList<Tile> changeTieles = myMapService.placeRoom(finalI, finalJ);
                            for (Tile t : changeTieles) {
                                tiles[t.getX()][t.getY()].setSrc("map/" + t.getPath() + ".png");
                            }
                        }
                    }
                    //Feld abwählen
                    else {
                        if (myMapService.canDeleteRoom(finalI, finalJ)) {
                            //iteriert über jede Kachel die von der änderung betroffen ist und setzt sie neu
                            for (Tile t : myMapService.deleteRoom(finalI, finalJ)) {
                                tiles[t.getX()][t.getY()].setSrc("map/" + t.getPath() + ".png");
                            }
                        } else {
                            Notification.show("Du kannst einen Raum nicht löschen," +
                                    " wenn der Dungeon dadurch geteilt wird!");
                        }
                    }
                });

                //click listener für die mauern
                borderHorizonButt.addClickListener(e -> {
                    if (myMapService.canToggleWall(finalI, finalJ, true)) {
                        for (Tile t :
                                myMapService.toggleWall(finalI, finalJ, true)) {
                            tiles[t.getX()][t.getY()].setSrc("map/" + t.getPath() + ".png");
                        }
                    } else {
                        Notification.show("Diese Aktion ist nicht möglich!");
                    }

                });
                borderVertButt.addClickListener(e -> {
                    if (myMapService.canToggleWall(finalI, finalJ, false)) {
                        for (Tile t :
                                myMapService.toggleWall(finalI, finalJ, false)) {
                            tiles[t.getX()][t.getY()].setSrc("map/" + t.getPath() + ".png");
                        }
                    } else {
                        Notification.show("Diese Aktion ist nicht möglich!");
                    }
                });

                line.add(tiles[i][j]);
                if (finalJ < width - 1) {
                    line.add(borderVertButt);
                }
                if (finalI < width - 1) {
                    lineRoomBorder.add(borderHorizonButt);
                }
            }
            lines.add(line);
            lines.add(lineRoomBorder);
        }

        lines.setJustifyContentMode(JustifyContentMode.CENTER);
        lines.setAlignItems(Alignment.CENTER);
        add(lines);
    }
}


