package de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs.dialog;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.dhbw.binaeratops.model.entitys.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemSelectionDialog extends Dialog {

    public boolean dialogResult = false;

    public Button comfirmButton = new Button("Übernehmen");
    public Button cancelButton = new Button("Abbrechen");
    Grid<Item> itemGrid = new Grid(Item.class);


    public ItemSelectionDialog(){
        H1 title = new H1("Item Liste");
        H2 headline = new H2("Items für ...");


        itemGrid.setWidth(500, Unit.PIXELS);

        List<Item> itemList = new ArrayList<>();

        Item item1 = new Item("Karotte", 45L,  "sehr guter Arzt");
        Item item2 = new Item("Kartoffel", 23L,  "sehr guter Arzt");
        Item item3 = new Item("Zeugs",12L ,  "sehr guter Arzt");

        item1.setItemId(2L);
        item2.setItemId(34L);
        item3.setItemId(77L);

        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);

        itemGrid.setItems(itemList);
        itemGrid.removeAllColumns();

        itemGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        itemGrid.addColumn(Item::getItemName).setHeader("Name");
        itemGrid.addColumn(Item::getSize).setHeader("Größe");
        itemGrid.addColumn(Item::getDescription).setHeader("Beschreibung");

        add(new VerticalLayout(title, headline, itemGrid, comfirmButton, cancelButton));

        cancelButton.addClickListener(e->{
            dialogResult = false;
            close();
        });

        comfirmButton.addClickListener(e->{
            dialogResult = true;
            close();
        });
    }

    public List<Item> getItemSelection(){
        List<Item> l = Arrays.asList(itemGrid.getSelectedItems().toArray(Item[]::new));
        return l;
    }
}
