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
import com.vaadin.flow.router.PageTitle;



@PageTitle("Raum")
@CssImport("./views/mainviewtabs/configurator/roomconfigurator-view.css")
public class RoomConfigurator extends VerticalLayout {

    VerticalLayout roomArea = new VerticalLayout();
    HorizontalLayout mapArea = new HorizontalLayout();
    VerticalLayout myRoomArea;



    public RoomConfigurator() {
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
        description.setMinWidth(250, Unit.PIXELS);

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
        ListBox itemList = new ListBox();

        HorizontalLayout itemsHeader = new HorizontalLayout();
        itemsHeader.add(itemsHeadline, editItemButton);

        VerticalLayout itemsArea = new VerticalLayout();
        itemsArea.add(itemsHeader, itemList);

        editItemButton.addClickListener(t -> {
            Label l = new Label("neuer Gegenstand");
            l.addClassName("itemLabel");
            itemList.add(l);
        });

        H2 npcsHeadline = new H2("NPCs");
        Button editNPCButton = new Button("hinzufügen");
        ListBox npcList = new ListBox();

        HorizontalLayout npcsHeader = new HorizontalLayout();
        npcsHeader.add(npcsHeadline, editNPCButton);

        VerticalLayout npcsArea = new VerticalLayout();
        npcsArea.add(npcsHeader, npcList);

        editNPCButton.addClickListener(t -> {
            npcList.add(new Label("neuer gegenstand"));
        });

        roomArea.add(description, neigborRoomsArea, new HorizontalLayout(itemsArea, npcsArea));

        roomArea.setSizeFull();

        myRoomArea = new VerticalLayout(roomName, roomArea);

        myRoomArea.setSizeFull();
        myRoomArea.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER); // Put content in the middle horizontally.
        myRoomArea.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER); // Put content in the middle vertically.

    }



}
