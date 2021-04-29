package de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;


@PageTitle("Raum")
@CssImport("./views/mainviewtabs/configurator/roomconfigurator-view.css")
public class RoomConfigurator extends VerticalLayout {

    VerticalLayout room = new VerticalLayout();

    public RoomConfigurator() {
        initRoom();
        add(new H1("My Room Configurator"), room);


    }

    private void initRoom() {
        TextArea beschreibung = new TextArea();
        beschreibung.setLabel("Beschreibung");
        beschreibung.setValue("123455");
        beschreibung.setMinHeight(200, Unit.PIXELS);
        beschreibung.setMinWidth(250, Unit.PIXELS);

        ComboBox north = new ComboBox("Norden");

        ComboBox east = new ComboBox("Osten");

        ComboBox south = new ComboBox("Süden");

        ComboBox west = new ComboBox("Westen");

        Button editItemButton = new Button("Gegenstand bearbeiten");
        ListBox itemListe = new ListBox();

        editItemButton.addClickListener(t->{
            itemListe.add(new Label("neuer Gegenstand"));
        });

        Button editNPCButton = new Button("Gegenstand bearbeiten");
        ListBox npcListe = new ListBox();

        editNPCButton.addClickListener(t->{
            npcListe.add(new Label("neuer gegenstand"));
        });

        Div myRoom = new Div();




        room.add(beschreibung, north, east, south, west,editItemButton, itemListe,editNPCButton, npcListe);
    }

}
