package de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import de.dhbw.binaeratops.model.entitys.Item;
import de.dhbw.binaeratops.model.entitys.NPC;
import de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs.dialog.ItemSelectionDialog;
import de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs.dialog.NpcSelectionDialog;

import java.util.ArrayList;
import java.util.List;


@PageTitle("Raum")
@CssImport("./views/mainviewtabs/configurator/roomconfigurator-view.css")
public class RoomConfigurator extends VerticalLayout {

    NpcSelectionDialog npcSelectionDialog = new NpcSelectionDialog();
    ItemSelectionDialog itemSelectionDialog = new ItemSelectionDialog();
    VerticalLayout roomArea = new VerticalLayout();
    HorizontalLayout mapArea = new HorizontalLayout();
    VerticalLayout myRoomArea;
    ListBox<NPC> npcList = new ListBox<NPC>();
    ListBox<Item> itemList = new ListBox<Item>();

    private List<NPC> npcs = new ArrayList<>();


    public RoomConfigurator() {
        initRoom();
        initMap();

        SplitLayout layout = new SplitLayout();
        layout.addToPrimary(mapArea);
        layout.addToSecondary(myRoomArea);
        layout.setWidth("100%");

        layout.setMaxHeight(700, Unit.PIXELS);
        add(layout);


    }

    private void initMap() {
        mapArea.setMinHeight(1000, Unit.PIXELS);
        mapArea.setMaxHeight(1000, Unit.PIXELS);
        mapArea.setMinWidth(500, Unit.PIXELS);

        mapArea.add(new H1("MAP - Coming Soon ..."));


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
