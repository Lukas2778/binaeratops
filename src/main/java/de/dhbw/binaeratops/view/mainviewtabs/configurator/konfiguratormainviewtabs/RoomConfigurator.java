package de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import de.dhbw.binaeratops.model.api.NPCI;
import de.dhbw.binaeratops.model.entitys.Item;
import de.dhbw.binaeratops.model.entitys.NPC;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import de.dhbw.binaeratops.service.impl.player.map.MapService;
import de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs.dialog.ItemSelectionDialog;
import de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs.dialog.NpcSelectionDialog;
import de.dhbw.binaeratops.view.player.map.MapView;
import de.dhbw.binaeratops.view.player.map.Tile;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@PageTitle("Raum")
@CssImport("./views/mainviewtabs/configurator/roomconfigurator-view.css")
public class RoomConfigurator extends VerticalLayout {

    NpcSelectionDialog npcSelectionDialog;
    ItemSelectionDialog itemSelectionDialog;
    VerticalLayout roomArea = new VerticalLayout();
    HorizontalLayout mapArea = new HorizontalLayout();
    VerticalLayout myRoomArea;
    ListBox<NPC> npcList = new ListBox<>();
    ListBox<Item> itemList = new ListBox<>();

    VerticalLayout map=new VerticalLayout();

    MapService mapService;

    private final int width = 8;
    Image[][] tiles =new Image[width][width];

    private List<NPC> npcs = new ArrayList<>();

    public RoomConfigurator(ConfiguratorServiceI configuratorService, MapService mapService){
        this.mapService=mapService;
        itemSelectionDialog = new ItemSelectionDialog(configuratorService);
        npcSelectionDialog = new NpcSelectionDialog(configuratorService);
        initRoom();
        initMap();

        SplitLayout layout = new SplitLayout();
        layout.addToPrimary(mapArea);
        layout.addToSecondary(myRoomArea);
        layout.setWidth("100%");

        add(layout);
    }

    private void initMap() {
        mapArea.setMinHeight(1000, Unit.PIXELS);
        mapArea.setMaxHeight(1000, Unit.PIXELS);
        mapArea.setMinWidth(500, Unit.PIXELS);

        //KARTE
        mapService.init(width);
        //map.setSizeFull();
        map.setJustifyContentMode(JustifyContentMode.CENTER);
        map.setAlignItems(Alignment.CENTER);
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
                    if (!mapService.roomExists(finalI, finalJ)) {
                        //Feld anwählen
                        if (mapService.canPlaceRoom(finalI, finalJ)) {
                            //wenn ein raum plaziert wird muss dieser erstellt werden und seine Konfiguration angezeigt werden
                            ArrayList<Tile> changeTieles= mapService.placeRoom(finalI, finalJ);
                            for (Tile t : changeTieles) {
                                tiles[t.getX()][t.getY()].setSrc("map/"+t.getPath()+".png");
                            }
                        }
                    }
                    //Feld abwählen
                    else {
                        if (mapService.canDeleteRoom(finalI, finalJ)) {
                            //iteriert über jede Kachel die von der änderung betroffen ist und setzt sie neu
                            for (Tile t : mapService.deleteRoom(finalI, finalJ)) {
                                tiles[t.getX()][t.getY()].setSrc("map/"+t.getPath()+".png");
                            }
                        }else{
                            Notification.show("Du kannst einen Raum nicht löschen," +
                                    " wenn der Dungeon dadurch geteilt wird!");
                        }
                    }
                });

                //click listener für die mauern
                borderHorizonButt.addClickListener(e->{
                    if(mapService.canToggleWall(finalI,finalJ,true)){
                        for (Tile t:
                                mapService.toggleWall(finalI,finalJ,true)) {
                            tiles[t.getX()][t.getY()].setSrc("map/"+t.getPath()+".png");
                        }
                    }else{
                        Notification.show("Fehler!");
                    }

                });
                borderVertButt.addClickListener(e->{
                    if(mapService.canToggleWall(finalI,finalJ,false)){
                        for (Tile t:
                                mapService.toggleWall(finalI,finalJ,false)) {
                            tiles[t.getX()][t.getY()].setSrc("map/"+t.getPath()+".png");
                        }
                    }else{
                        Notification.show("Fehler!");
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
        map.add(lines);

        mapArea.add(map);
        mapArea.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER); // Put content in the middle horizontally.
        mapArea.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER); // Put content in the middle vertically.
    }

    private void initRoom() {
        H2 roomName = new H2("Raumbezeichnung");
        TextArea description = new TextArea();
        description.setLabel("Beschreibung");
        description.setValue("123455");
        description.setMinHeight(200, Unit.PIXELS);
        description.setMinWidth(500, Unit.PIXELS);

        H2 neighborroomsTitle = new H2("Nachbarräume");

        TextField north = new TextField("Norden");
        north.setEnabled(false);

        TextField east = new TextField("Osten");
        east.setEnabled(false);

        TextField south = new TextField("Süden");
        south.setEnabled(false);

        TextField west = new TextField("Westen");
        west.setEnabled(false);

        VerticalLayout neigborRoomsArea = new VerticalLayout();
        neigborRoomsArea.add(neighborroomsTitle, new HorizontalLayout(north, east),new HorizontalLayout(south, west));

        H2 itemsHeadline = new H2("Gegenstände");
        Button editItemButton = new Button("hinzufügen");

    ConfiguratorServiceI configuratorService;

        HorizontalLayout itemsHeader = new HorizontalLayout();
        itemsHeader.add(itemsHeadline, editItemButton);

        VerticalLayout itemsArea = new VerticalLayout();
        itemsArea.add(itemsHeader, itemList);

        itemList.setEnabled(false);

        itemList.setRenderer(new ComponentRenderer<>(item ->{
            Label label = new Label(item.getItemName());
            label.getStyle().set("propertyName", "value");
            label.addClassName("itemLabel");
            return label;
        }));

        editItemButton.addClickListener(t -> {
            itemSelectionDialog.dialogResult = false;
            itemSelectionDialog.open();
        });

        itemSelectionDialog.addOpenedChangeListener(e->{
            if (itemSelectionDialog.dialogResult && !itemSelectionDialog.isOpened()) {
                itemList.clear();
                itemList.setItems(itemSelectionDialog.getItemSelection());
            }
        });

        H2 npcsHeadline = new H2("NPCs");
        Button editNPCButton = new Button("hinzufügen");


        HorizontalLayout npcsHeader = new HorizontalLayout();
        npcsHeader.add(npcsHeadline, editNPCButton);

        VerticalLayout npcsArea = new VerticalLayout();
        npcsArea.add(npcsHeader, npcList);

        npcList.setEnabled(false);

        npcList.setRenderer(new ComponentRenderer<>(item ->{
            Label label = new Label(item.getNpcName());
            label.getStyle().set("propertyName", "value");
            label.addClassName("itemLabel");
            return label;
        }));

        editNPCButton.addClickListener(t -> {
            npcSelectionDialog.open();
        });

        npcSelectionDialog.addOpenedChangeListener(e->{
            if (npcSelectionDialog.dialogResult) {
                npcList.clear();
                npcList.setItems(npcSelectionDialog.getNPCSelection());
            }
        });

        roomArea.add(description, neigborRoomsArea, new HorizontalLayout(itemsArea, npcsArea));
        roomArea.setSizeFull();

        myRoomArea = new VerticalLayout(roomName, roomArea);
        myRoomArea.setMaxWidth(600, Unit.PIXELS);
        myRoomArea.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER); // Put content in the middle horizontally.
        myRoomArea.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER); // Put content in the middle vertically.


    }
}
