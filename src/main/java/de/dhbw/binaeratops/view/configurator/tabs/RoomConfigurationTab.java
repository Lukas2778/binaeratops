package de.dhbw.binaeratops.view.configurator.tabs;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import de.dhbw.binaeratops.model.entitys.Item;
import de.dhbw.binaeratops.model.entitys.NPC;
import de.dhbw.binaeratops.model.entitys.Room;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import de.dhbw.binaeratops.service.api.map.MapServiceI;
import de.dhbw.binaeratops.view.configurator.tabs.dialog.ItemSelectionDialog;
import de.dhbw.binaeratops.view.configurator.tabs.dialog.NpcSelectionDialog;
import de.dhbw.binaeratops.view.map.Tile;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@PageTitle("Raum")
@CssImport("./views/mainviewtabs/configurator/roomconfigurator-view.css")
public class RoomConfigurationTab extends VerticalLayout {

    NpcSelectionDialog npcSelectionDialog;
    ItemSelectionDialog itemSelectionDialog;

    ListBox<Item> itemList = new ListBox<>();
    ListBox<NPC> npcList = new ListBox<>();

    VerticalLayout mapArea = new VerticalLayout();
    VerticalLayout roomArea = new VerticalLayout();

    MapServiceI mapService;
    ConfiguratorServiceI configuratorServiceI;
    private Room currentRoom;

    private final int width = 8;
    Image[][] tiles = new Image[width][width];

    private List<NPC> npcArrayList = new ArrayList<>();

    public RoomConfigurationTab(ConfiguratorServiceI AConfiguratorServiceI, MapServiceI AMapServiceI) {
        mapService = AMapServiceI;
        configuratorServiceI=AConfiguratorServiceI;
        itemSelectionDialog = new ItemSelectionDialog(AConfiguratorServiceI);
        npcSelectionDialog = new NpcSelectionDialog(AConfiguratorServiceI);

        //TODO sartraum suchen und setzen
        try {
            this.currentRoom=configuratorServiceI.getDungeon().getRooms().get(0);
            initRoom();
        }catch (IndexOutOfBoundsException ignored){}

        //initRoom();
        initMap();

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.addToPrimary(mapArea);
        splitLayout.addToSecondary(roomArea);
        splitLayout.setWidth("100%");
        splitLayout.setPrimaryStyle("minWidth", "870px");
        splitLayout.setSecondaryStyle("minWidth", "550px");

        add(splitLayout);
    }

    private void initMap() {

        //KARTE
        //TODO folgende Zeile prüfen
        //mapService.init(width,configuratorServiceI.getDungeon().getDungeonId());
        ArrayList<Tile> initTiles=mapService.init(width,configuratorServiceI);
        //map.setSizeFull();
        mapArea.setJustifyContentMode(JustifyContentMode.CENTER);
        mapArea.setAlignItems(Alignment.CENTER);
        VerticalLayout lines = new VerticalLayout();
        lines.setSpacing(false);

        HorizontalLayout lineRoomBorder;
        HorizontalLayout line;

        //Karte erstellen
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
                            ArrayList<Tile> changeTieles = mapService.placeRoom(finalI, finalJ);
                            for (Tile t : changeTieles) {
                                tiles[t.getX()][t.getY()].setSrc("map/" + t.getPath() + ".png");
                            }
                        }
                        currentRoom=mapService.getRoomByCoordinate(finalI,finalJ);
                        initRoom();
                    }
                    //TODO Raum in den rechten dialog laden
                    else {
                        if(mapService.getRoomByCoordinate(finalI,finalJ) != null)
                            currentRoom=mapService.getRoomByCoordinate(finalI,finalJ);
                        Notification.show(currentRoom.getRoomId().toString());
                        initRoom();
                        /*
                        if (mapService.canDeleteRoom(finalI, finalJ)) {
                            //iteriert über jede Kachel die von der änderung betroffen ist und setzt sie neu
                            for (Tile t : mapService.deleteRoom(finalI, finalJ)) {
                                tiles[t.getX()][t.getY()].setSrc("map/" + t.getPath() + ".png");
                            }
                        } else {
                            Notification.show("Du kannst einen Raum nicht löschen," +
                                    " wenn der Dungeon dadurch geteilt wird!");
                        }
                        */

                    }
                });

                //click listener für die mauern
                borderHorizonButt.addClickListener(e -> {
                    if (mapService.canToggleWall(finalI, finalJ, true)) {
                        for (Tile t :
                                mapService.toggleWall(finalI, finalJ, true)) {
                            tiles[t.getX()][t.getY()].setSrc("map/" + t.getPath() + ".png");
                        }
                    } else {
                        Notification.show("Fehler!");
                    }

                });
                borderVertButt.addClickListener(e -> {
                    if (mapService.canToggleWall(finalI, finalJ, false)) {
                        for (Tile t :
                                mapService.toggleWall(finalI, finalJ, false)) {
                            tiles[t.getX()][t.getY()].setSrc("map/" + t.getPath() + ".png");
                        }
                    } else {
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

        //Oberfläche bekommt die Bilder aus der Datenbank gesetzt
        for (Tile t : initTiles) {
            tiles[t.getX()][t.getY()].setSrc("map/" + t.getPath() + ".png");
        }

        lines.setJustifyContentMode(JustifyContentMode.CENTER);
        lines.setAlignItems(Alignment.CENTER);
        mapArea.add(lines);
    }

    private void initRoom() {
        //TODO Raumname wird übergeben
        roomArea.removeAll();
        String chosenRoom=currentRoom.getRoomName();
        HorizontalLayout titleAndDelLayout=new HorizontalLayout();

        H2 configureRoomsTitle = new H2("Räume bearbeiten");
        Button deleteRoomButt=new Button("Raum löschen",e->{
            if(currentRoom != null) {
                if (mapService.canDeleteRoom(currentRoom.getXCoordinate(), currentRoom.getYCoordinate())) {
                    for (Tile t : mapService.deleteRoom(currentRoom.getXCoordinate(), currentRoom.getYCoordinate())) {
                        tiles[t.getX()][t.getY()].setSrc("map/" + t.getPath() + ".png");
                    }
                    //TODO zum startraum navigieren
                } else {
                    Notification.show("Du kannst einen Raum nicht löschen," +
                            " wenn der Dungeon dadurch geteilt wird!");
                }
            }
        });

        deleteRoomButt.getStyle().set("color", "red");
        deleteRoomButt.getStyle().set("margin-top", "auto");
        titleAndDelLayout.add(configureRoomsTitle, deleteRoomButt);

        TextField startRoom=new TextField("Startraum");
        H4 actualRoomHeadline=new H4("Aktueller Raum:");
        TextField roomName =new TextField("Name des Raums");

        roomName.setValue(Objects.requireNonNullElse(chosenRoom, "Beispiel Name"));
        roomName.setValueChangeMode(ValueChangeMode.ON_CHANGE);
        roomName.addValueChangeListener(e->{
            currentRoom.setRoomName(roomName.getValue());
            configuratorServiceI.saveRoom(currentRoom);
        });

        TextArea roomDescription = new TextArea("Beschreibung");
        roomDescription.setValue(Objects.requireNonNullElse(currentRoom.getDescription(),"Beispiel beschreibung"));
        roomDescription.setMinWidth(500, Unit.PIXELS);
        roomDescription.setValueChangeMode(ValueChangeMode.ON_BLUR);
        roomDescription.addValueChangeListener(e->{
            currentRoom.setDescription(roomDescription.getValue());
            configuratorServiceI.saveRoom(currentRoom);
        });

        H3 itemsAndNPCs=new H3("Was soll in diesem Raum vorhanden sein?");

        H4 itemsHeadline = new H4("Gegenstände");
        Button editItemButton = new Button("Hinzufügen");
        H4 npcHeadline = new H4("NPCs");
        Button editNPCButton = new Button("Hinzufügen");

        roomArea.add(titleAndDelLayout, startRoom, actualRoomHeadline, roomName,roomDescription,itemsAndNPCs,itemsHeadline,itemList, editItemButton,npcHeadline, npcList, editNPCButton);

        itemList.setEnabled(false);
        itemList.setRenderer(new ComponentRenderer<>(item -> {
            Label label = new Label(item.getItemName());
            label.getStyle().set("propertyName", "value");
            label.addClassName("itemLabel");
            return label;
        }));
        editItemButton.addClickListener(t -> {
            itemSelectionDialog.dialogResult = false;
            itemSelectionDialog.open();
        });
        itemSelectionDialog.addOpenedChangeListener(e -> {
            if (itemSelectionDialog.dialogResult && !itemSelectionDialog.isOpened()) {
                itemList.clear();
                itemList.setItems(itemSelectionDialog.getItemSelection());
            }
        });

        npcList.setEnabled(false);
        npcList.setRenderer(new ComponentRenderer<>(item -> {
            Label label = new Label(item.getNpcName());
            label.getStyle().set("propertyName", "value");
            label.addClassName("itemLabel");
            return label;
        }));
        editNPCButton.addClickListener(t -> {
            npcSelectionDialog.open();
        });
        npcSelectionDialog.addOpenedChangeListener(e -> {
            if (npcSelectionDialog.dialogResult) {
                npcList.clear();
                npcList.setItems(npcSelectionDialog.getNPCSelection());
            }
        });

//        myRoomArea.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER); // Put content in the middle horizontally.
//        myRoomArea.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER); // Put content in the middle vertically.
    }


}
