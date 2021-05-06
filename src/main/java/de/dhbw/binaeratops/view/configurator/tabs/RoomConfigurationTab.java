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
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.Item;
import de.dhbw.binaeratops.model.entitys.NPC;
import de.dhbw.binaeratops.model.entitys.Room;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import de.dhbw.binaeratops.service.api.map.MapServiceI;
import de.dhbw.binaeratops.view.configurator.tabs.dialog.ItemSelectionDialog;
import de.dhbw.binaeratops.view.configurator.tabs.dialog.NpcSelectionDialog;
import de.dhbw.binaeratops.model.map.Tile;

import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

@PageTitle("Raum")
@CssImport("./views/mainviewtabs/configurator/roomconfigurator-view.css")
@CssImport("./views/mainviewtabs/configurator/map.css")
public class RoomConfigurationTab extends VerticalLayout {

    private ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());
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

    public RoomConfigurationTab(ConfiguratorServiceI AConfiguratorServiceI, MapServiceI AMapServiceI) {
        mapService = AMapServiceI;
        configuratorServiceI = AConfiguratorServiceI;
        itemSelectionDialog = new ItemSelectionDialog(AConfiguratorServiceI);
        npcSelectionDialog = new NpcSelectionDialog(AConfiguratorServiceI);

        //TODO sartraum suchen und setzen
        try {
            this.currentRoom = configuratorServiceI.getDungeon().getRooms().get(0);
            initRoom();
        } catch (IndexOutOfBoundsException ignored) {
        }

        //initRoom();
        initMap();

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.addToPrimary(mapArea);
        splitLayout.addToSecondary(roomArea);
        splitLayout.setWidth("100%");
        //splitLayout.setPrimaryStyle("minWidth", "870px");
        splitLayout.setPrimaryStyle("minWidth", "150px");
        splitLayout.setPrimaryStyle("width", "1950px");
        splitLayout.setSecondaryStyle("minWidth", "350px");

        add(splitLayout);
    }

    private void initMap() {

        //KARTE
        //TODO folgende Zeile prüfen
        //mapService.init(width,configuratorServiceI.getDungeon().getDungeonId());
        ArrayList<Tile> initTiles = mapService.init(configuratorServiceI);
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
                    //wenn der Raum noch nicht existiert, kann er erstellt werden
                    if (!mapService.roomExists(finalI, finalJ)) {
                        //Feld anwählen
                        if (mapService.canPlaceRoom(finalI, finalJ)) {
                            //wenn ein raum plaziert wird muss dieser erstellt werden und seine Konfiguration angezeigt werden
                            ArrayList<Tile> changeTieles = mapService.placeRoom(finalI, finalJ);
                            for (Tile t : changeTieles) {
                                tiles[t.getX()][t.getY()].setSrc("map/" + t.getPath() + ".png");
                                tiles[t.getX()][t.getY()].setText(mapService.getRoomByCoordinate(t.getX(),t.getY()).getRoomName());
                            }
                            currentRoom = mapService.getRoomByCoordinate(finalI, finalJ);
                        }
                    }
                    //wenn der Raum existiert, kann er bearbeitet werden
                    else {
                        if(mapService.getRoomByCoordinate(finalI,finalJ) != null)
                            currentRoom=mapService.getRoomByCoordinate(finalI,finalJ);
                        //Notification.show(currentRoom.getRoomId().toString());
                        tiles[finalI][finalJ].setText(mapService.getRoomByCoordinate(finalI,finalJ).getRoomName());
                    }
                    initRoom();
                });

                //click listener für die mauern
                borderHorizonButt.addClickListener(e -> {
                    if (mapService.canToggleWall(finalI, finalJ, true)) {
                        for (Tile t :
                                mapService.toggleWall(finalI, finalJ, true)) {
                            tiles[t.getX()][t.getY()].setSrc("map/" + t.getPath() + ".png");
                        }
                    } else {
                        Notification.show(res.getString("view.configurator.room.notification.wallerror"));
                    }

                });
                borderVertButt.addClickListener(e -> {
                    if (mapService.canToggleWall(finalI, finalJ, false)) {
                        for (Tile t :
                                mapService.toggleWall(finalI, finalJ, false)) {
                            tiles[t.getX()][t.getY()].setSrc("map/" + t.getPath() + ".png");
                        }
                    } else {
                        Notification.show(res.getString("view.configurator.room.notification.wallerror"));
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

        //alle anderen Räume abwählen
        try {
            for (Image[] tL : tiles) {
                for (Image t : tL) {
                    t.getStyle().set("opacity", "1");
                }
            }
        }catch (Exception ignore){}

        //button-container:hover vom CSS-File wieder einschalten


        //aktuellen Raum anwählen
        try {
            tiles[currentRoom.getXCoordinate()][currentRoom.getYCoordinate()].getStyle().set("opacity", "0.5");
        }catch (Exception ignored){}

        String chosenRoom=currentRoom.getRoomName();
        HorizontalLayout roomThings=new HorizontalLayout();
        VerticalLayout itemLayout=new VerticalLayout();
        VerticalLayout npcLayout=new VerticalLayout();

        H2 configureRoomsTitle = new H2(res.getString("view.configurator.room.configureRoomsTitle"));

        TextField startRoom=new TextField(res.getString("view.configurator.room.startroom"));
        H3 actualRoomHeadline=new H3(res.getString("view.configurator.room.actualroomheadline"));
        Button deleteRoomButt=new Button(res.getString("view.configurator.room.delete"),e->{
            if(currentRoom != null) {
                if (mapService.canDeleteRoom(currentRoom.getXCoordinate(), currentRoom.getYCoordinate())) {
                    for (Tile t : mapService.deleteRoom(currentRoom.getXCoordinate(), currentRoom.getYCoordinate())) {
                        tiles[t.getX()][t.getY()].setSrc("map/" + t.getPath() + ".png");
                    }
                    //wird der Raum gelöscht, soll die Hervorhebung nicht weiter existieren
                    tiles[currentRoom.getXCoordinate()][currentRoom.getYCoordinate()].getStyle().set("opacity", "1");
                    //TODO zum startraum navigieren
                } else {
                    Notification.show(res.getString("view.configurator.room.notification.deleteroomerror"));
                }
            }
        });
        deleteRoomButt.getStyle().set("color", "red");
        TextField roomName =new TextField(res.getString("view.configurator.room.roomname"));

        roomName.setValue(Objects.requireNonNullElse(chosenRoom,
                res.getString("view.configurator.room.defaultroomname")));
        roomName.setValueChangeMode(ValueChangeMode.ON_BLUR);
        roomName.addValueChangeListener(e -> {
            currentRoom.setRoomName(roomName.getValue());
            configuratorServiceI.saveRoom(currentRoom);
        });

        TextArea roomDescription = new TextArea(res.getString("view.configurator.room.roomdescription"));
        roomDescription.setValue(Objects.requireNonNullElse(currentRoom.getDescription(),
                res.getString("view.configurator.room.defaultroomdescription")));
        roomDescription.setMinWidth(400, Unit.PIXELS);
        roomDescription.setValueChangeMode(ValueChangeMode.ON_BLUR);
        roomDescription.addValueChangeListener(e -> {
            currentRoom.setDescription(roomDescription.getValue());
            configuratorServiceI.saveRoom(currentRoom);
        });

        H4 itemsNPCsHeadline=new H4(res.getString("view.configurator.room.itemnpcheadline"));
        itemsNPCsHeadline.getStyle().set("color", "grey");

        H4 itemsHeadline = new H4(res.getString("view.configurator.room.itemheadline"));
        Button editItemButton = new Button(res.getString("view.configurator.room.edititemnpc"));
        H4 npcHeadline = new H4(res.getString("view.configurator.room.npcheadline"));
        Button editNPCButton = new Button(res.getString("view.configurator.room.edititemnpc"));

        itemLayout.add(itemsHeadline, itemList, editItemButton);
        npcLayout.add(npcHeadline, npcList, editNPCButton);

        roomThings.add(itemLayout, npcLayout);

        roomArea.add(configureRoomsTitle, startRoom, actualRoomHeadline, roomName, roomDescription,
                deleteRoomButt, itemsNPCsHeadline, roomThings);

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
        editNPCButton.addClickListener(t -> npcSelectionDialog.open());
        npcSelectionDialog.addOpenedChangeListener(e -> {
            if (npcSelectionDialog.dialogResult) {
                npcList.clear();
                npcList.setItems(npcSelectionDialog.getNPCSelection());
            }
        });
    }
}
