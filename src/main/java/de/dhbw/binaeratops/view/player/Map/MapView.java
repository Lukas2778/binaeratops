package de.dhbw.binaeratops.view.player.Map;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import de.dhbw.binaeratops.service.impl.player.map.MapService;

import java.util.concurrent.atomic.AtomicReference;


/**
 * Karten-Komponente für den Konfigurator, sowie die Spielansicht.
 */
@CssImport("./views/player/map/map.css")
@RouteAlias("")
@Route("login")
public class MapView extends VerticalLayout {

    MapService myMapService;
    //Image image =new Image("images/map/KarteNOSW.png","Room");

    /**
     * Dies ist der Konstruktor zum Erzeugen der Karte.
     */
    public MapView() {
        addClassName("map-view");
        int width = 8;
        myMapService = new MapService(width);
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
                Image myButt = new Image("map/KarteBack.png", "Room");
                myButt.addClassName("button-container");
                myButt.addClassName("buttRoom");

                NativeButton borderHorizonButt = new NativeButton();
                borderHorizonButt.addClassName("button-container");
                borderHorizonButt.addClassName("buttHorizontal");

                NativeButton borderVertButt = new NativeButton();
                borderVertButt.addClassName("button-container");
                borderVertButt.addClassName("buttVertical");

                int finalI = i;
                int finalJ = j;
                myButt.addClickListener(
                        e -> {
                            if (!myMapService.roomExists(finalI, finalJ)) {
                                //Feld anwählen
                                if (myMapService.canPlaceRoom(finalI, finalJ)) {
                                    myMapService.placeRoom(finalI, finalJ);
                                    myButt.setSrc("map/KarteNOSW.png");
                                }
                            }
                            //Feld abwählen
                            else {
                                if (myMapService.canDeleteRoom(finalI, finalJ)) {
                                    myMapService.deleteRoom(finalI, finalJ);
                                    myButt.setSrc("map/KarteBack.png");
                                }
                            }
                        });
                borderHorizonButt.addClickListener(e->{
                    if(!myMapService.wallExists(finalI,finalJ)){
                        //Mauer anwählen
                        if(myMapService.canPlaceWall(finalI,finalJ)){
                            myMapService.placeWall(finalI,finalJ);
                            borderHorizonButt.getStyle().set("background", "brown");
                        }
                    }else{
                        //Mauer abwählen
                        if(myMapService.canDeleteWall(finalI,finalJ)){
                            myMapService.deleteWall(finalI,finalJ);
                            borderHorizonButt.getStyle().set("background", "grey");
                        }
                    }
                });
                borderVertButt.addClickListener(e->{
                    if(!myMapService.wallExists(finalI,finalJ)){
                        //Mauer anwählen
                        if(myMapService.canPlaceWall(finalI,finalJ)){
                            myMapService.placeWall(finalI,finalJ);
                            borderVertButt.getStyle().set("background", "brown");
                        }
                    }else{
                        //Mauer abwählen
                        if(myMapService.canDeleteWall(finalI,finalJ)){
                            myMapService.deleteWall(finalI,finalJ);
                            borderVertButt.getStyle().set("background", "grey");
                            //Layout neu erstellen, da sonst leere Bereiche der Mauern oder Mauern extra Bereich
                        }
                    }
                });
                line.add(myButt);
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


