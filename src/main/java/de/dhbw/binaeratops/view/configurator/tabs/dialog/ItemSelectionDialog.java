package de.dhbw.binaeratops.view.configurator.tabs.dialog;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.dhbw.binaeratops.model.entitys.Item;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemSelectionDialog extends Dialog {

    public boolean dialogResult = false;

    public Button comfirmButton = new Button("Übernehmen");
    public Button cancelButton = new Button("Abbrechen");
    Grid<Item> itemGrid = new Grid(Item.class);
    List<Item> selectedItems = new ArrayList<>();


    public ItemSelectionDialog(ConfiguratorServiceI AConfiguratorService){
        H1 title = new H1("Item Liste");
        H2 headline = new H2("Items für ...");


        itemGrid.setWidth(500, Unit.PIXELS);

        itemGrid.removeAllColumns();

        itemGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        itemGrid.addColumn(Item::getItemName).setHeader("Name");
        itemGrid.addColumn(Item::getSize).setHeader("Größe");
        itemGrid.addColumn(Item::getDescription).setHeader("Beschreibung");

        add(new VerticalLayout(title, headline, itemGrid, comfirmButton, cancelButton));

        this.addOpenedChangeListener(e->{
            if (isOpened()){
                List<Item> tempList = AConfiguratorService.getAllItems();
                itemGrid.setItems(tempList);
                for (Item myItem: selectedItems){
                    if(tempList.contains(myItem)){
                        itemGrid.select(myItem);
                    }
                }
            }
        });

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
