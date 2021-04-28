package de.dhbw.binaeratops.view.player;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

@RouteAlias("")
@Route("login")
public class MapView extends VerticalLayout {


    public MapView() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        VerticalLayout lines = new VerticalLayout();
        lines.setSpacing(false);

        HorizontalLayout line;

        int width = 20;
        //int counter = 0;
        Boolean clicked[][] = new Boolean[width][width];
        //alle Werte auf false setzen
        for (int i = 0; i < width; ++i) {
            Arrays.fill(clicked[i], Boolean.FALSE);
        }
        Boolean tmp[][] = new Boolean[width][width];
        //alle Werte auf false setzen
        for (int i = 0; i < width; ++i) {
            Arrays.fill(tmp[i], Boolean.FALSE);
        }

        for (int i = 0; i < width; ++i) {
            line = new HorizontalLayout();
            line.setSpacing(false);
            line.setSizeFull();
            for (int j = 0; j < width; ++j) {
                Button myButt = new Button();
                myButt.getStyle().set("border-radius", "0px");
                myButt.getStyle().set("background", "grey");
                myButt.setHeight("73px");
                int finalI = i;
                int finalJ = j;
                myButt.addClickListener(
                        e -> {
                            //Feld anwählen
                            if (!clicked[finalI][finalJ]) {
                                //Überprüfen, ob schon ein Feld gesetzt wurde
                                if (Arrays.deepEquals(tmp, clicked)) {
                                    myButt.getStyle().set("background", "red");
                                    clicked[finalI][finalJ] = true;
                                } else {
                                    //überprüfen, ob geklicktes Feld der Nachbar ist

                                    //Nachbar Norden
                                    if (!(finalI - 1 < 0)) {
                                        if (clicked[finalI - 1][finalJ]) {
                                            myButt.getStyle().set("background", "red");
                                            clicked[finalI][finalJ] = true;
                                        }
                                    }

                                    //Nachbar Osten
                                    if (!(finalJ + 1 > width - 1)) {
                                        if (clicked[finalI][finalJ + 1]) {
                                            myButt.getStyle().set("background", "red");
                                            clicked[finalI][finalJ] = true;
                                        }
                                    }

                                    //Nachbar Süden
                                    if (!(finalI + 1 > width - 1)) {
                                        if (clicked[finalI + 1][finalJ]) {
                                            myButt.getStyle().set("background", "red");
                                            clicked[finalI][finalJ] = true;
                                        }
                                    }

                                    //Nachbar Westen
                                    if (!(finalJ - 1 < 0)) {
                                        if (clicked[finalI][finalJ - 1]) {
                                            myButt.getStyle().set("background", "red");
                                            clicked[finalI][finalJ] = true;
                                        }
                                    }
                                }
                            }
                            //Feld abwählen
                            else {
                                //wenn mehr als einen Nachbar, ist Feld nicht löschbar, außer es gibt vier Felder bei denen eines davon gelöscht werden soll, aber keine Felder sonst anschließen
                                boolean deleatable=false;

                                //überprüfen, ob weniger als zwei Nachbarn
                                int neighborCount=0;
                                boolean neighborNorth=false;
                                boolean neighborEast=false;
                                boolean neighborSouth=false;
                                boolean neighborWest=false;
                                //Nachbar Norden
                                if (!(finalI - 1 < 0)) {
                                    if (clicked[finalI - 1][finalJ]) {
                                       neighborCount++;
                                        neighborNorth=true;
                                    }
                                }
                                //Nachbar Osten
                                if (!(finalJ + 1 > width - 1)) {
                                    if (clicked[finalI][finalJ + 1]) {
                                        neighborCount++;
                                        neighborEast=true;
                                    }
                                }
                                //Nachbar Süden
                                if (!(finalI + 1 > width - 1)) {
                                    if (clicked[finalI + 1][finalJ]) {
                                        neighborCount++;
                                        neighborSouth=true;
                                    }
                                }
                                //Nachbar Westen
                                if (!(finalJ - 1 < 0)) {
                                    if (clicked[finalI][finalJ - 1]) {
                                        neighborCount++;
                                        neighborWest=true;
                                    }
                                }
                                //Ergebnis prüfen
                                if(neighborCount<2){
                                    deleatable=true;
                                }
//                                else{
//                                    //es gibt vier Felder bei denen eines davon gelöscht werden soll, aber keine Felder sonst anschließen
//                                    if((neighborNorth&&neighborWest)||(neighborNorth&&neighborEast)||(neighborSouth&&neighborWest)||(neighborSouth&&neighborEast)){
//
//                                    }
//                                }
                                if(deleatable) {
                                    myButt.getStyle().set("background", "grey");
                                    clicked[finalI][finalJ] = false;
                                }
                            }
                        });
                line.add(myButt);
                //++counter;
            }
            line.getStyle().set("margin", "-4px");
            line.getStyle().set("padding", "0px");
            lines.add(line);
        }

        lines.setJustifyContentMode(JustifyContentMode.CENTER);
        lines.setAlignItems(Alignment.CENTER);
        add(lines);
    }
}


