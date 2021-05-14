package de.dhbw.binaeratops.view.configurator.tabs.dialog;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import de.dhbw.binaeratops.model.entitys.Item;
import de.dhbw.binaeratops.model.entitys.ItemInstance;
import de.dhbw.binaeratops.model.entitys.Room;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ItemSelectionDialog extends Dialog {

    public boolean dialogResult = false;

    public Button confirmButton = new Button("Übernehmen");
    public Button cancelButton = new Button("Abbrechen");
    Grid<Item> itemGrid = new Grid(Item.class);
    HashMap<Item, IntegerField> itemIntegerFieldHashMap = new HashMap<>();
    private ListBox<ItemInstance> itemList;
    private Room room;

    ConfiguratorServiceI configuratorServiceI;


    public ItemSelectionDialog(ConfiguratorServiceI AConfiguratorService, Room ARoom, ListBox<ItemInstance> itemList){
        room = ARoom;
        this.itemList = itemList;
        configuratorServiceI = AConfiguratorService;
        H1 title = new H1("Item Liste");
        H2 headline = new H2("Items für ...");

        itemGrid.setWidth(500, Unit.PIXELS);

        itemGrid.removeAllColumns();

        itemGrid.addComponentColumn(item -> {
            IntegerField nf = new IntegerField();
            itemIntegerFieldHashMap.put(item, nf);

            nf.setValue(configuratorServiceI.getNumberOfItem(room, item));

            return nf;
        }).setHeader("Anzahl");
        itemGrid.addColumn(Item::getItemName).setHeader("Name");
        itemGrid.addColumn(Item::getSize).setHeader("Größe");
        itemGrid.addColumn(Item::getDescription).setHeader("Beschreibung");

        add(new VerticalLayout(title, headline, itemGrid, confirmButton, cancelButton));

        cancelButton.addClickListener(e->{
            dialogResult = false;
            close();
        });

        confirmButton.addClickListener(e->{
            if(validate()) {
                List<ItemInstance> instances = new ArrayList<>();
                for (Item item : itemIntegerFieldHashMap.keySet()) {
                    if (!itemIntegerFieldHashMap.get(item).isEmpty() && itemIntegerFieldHashMap.get(item).getValue() >= 1) {
                        for (int i = 0; i < itemIntegerFieldHashMap.get(item).getValue(); i++) {
                            ItemInstance instance = new ItemInstance();
                            instance.setItem(item);
                            instances.add(instance);
                        }
                    }
                }
                configuratorServiceI.setItemInstances(ARoom, instances);
                refreshItemList();
                close();
            }
        });

        itemGrid.setItems(configuratorServiceI.getAllItems());
    }

    public List<Item> getItemSelection(){
        List<Item> l = Arrays.asList(itemGrid.getSelectedItems().toArray(Item[]::new));
        return l;
    }

    private void refreshItemList () {
        itemList.setItems(configuratorServiceI.getAllItems(room));
    }

    private boolean validate() {
        for (Item item : itemIntegerFieldHashMap.keySet()) {
            if (itemIntegerFieldHashMap.get(item).isInvalid()) {
                return false;
            }
        }
        return true;
    }
}
