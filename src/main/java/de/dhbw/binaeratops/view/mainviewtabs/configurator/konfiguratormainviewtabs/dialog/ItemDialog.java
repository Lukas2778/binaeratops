package de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import de.dhbw.binaeratops.model.entitys.Item;
import de.dhbw.binaeratops.model.enums.ItemType;

import java.util.ArrayList;

public class ItemDialog extends Dialog {
    private TextField currentName;
    private NumberField currentSize;
    private TextField currentDescription;
    private ComboBox<ItemType> currentType;

    private Item currentItem;
    private ArrayList<Item> itemList;
    Grid<Item> grid;

    public ItemDialog() {}

    public ItemDialog(ArrayList<Item> itemList, Item currentItem, Grid<Item> grid) {
        this.itemList = itemList;
        this.currentItem = currentItem;
        this.grid = grid;
        init();
    }

    private void init() {
        currentName = new TextField("Name");
        currentSize = new NumberField("Größe");
        currentDescription = new TextField("Beschreibung");
        currentType = new ComboBox<>("Typ");
        Button saveDialog = new Button("Speichern");
        Button closeDialog = new Button("Abbrechen");

        this.add(new VerticalLayout(currentName, currentSize, currentDescription, currentType, new HorizontalLayout(saveDialog, closeDialog)));

        currentType.setItems(ItemType.values());

        saveDialog.addClickListener(e -> {
            currentItem.setItemName(currentName.getValue());
            currentItem.setSize(currentSize.getValue().longValue());
            currentItem.setDescription(currentDescription.getValue());
            currentItem.setType(currentType.getValue());
            if (!itemList.contains(currentItem)) {
                itemList.add(currentItem);
            }
            refreshGrid();
            this.close();
        });
        closeDialog.addClickListener(e -> this.close());
    }

    public void fillDialog(Item item) {
        currentName.setValue(item.getItemName());
        currentSize.setValue(item.getSize().doubleValue());
        currentDescription.setValue(item.getDescription());
        currentType.setValue(item.getType());
    }

    private void refreshGrid() {
        grid.setItems(itemList);
    }

    public TextField getCurrentName() {
        return currentName;
    }

    public void setCurrentName(TextField currentName) {
        this.currentName = currentName;
    }

    public NumberField getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(NumberField currentSize) {
        this.currentSize = currentSize;
    }

    public TextField getCurrentDescription() {
        return currentDescription;
    }

    public void setCurrentDescription(TextField currentDescription) {
        this.currentDescription = currentDescription;
    }

    public ComboBox<ItemType> getCurrentType() {
        return currentType;
    }

    public void setCurrentType(ComboBox<ItemType> currentType) {
        this.currentType = currentType;
    }

    public Item getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(Item currentItem) {
        this.currentItem = currentItem;
    }
}
