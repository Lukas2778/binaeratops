package de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import de.dhbw.binaeratops.model.entitys.Item;
import de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs.dialog.ItemDialog;

import java.util.ArrayList;

@PageTitle("Raum")
@CssImport("./views/mainviewtabs/configurator/roomconfigurator-view.css")
public class ItemsConfigurator extends VerticalLayout {

    VerticalLayout items = new VerticalLayout();
    ArrayList<Item> itemList = new ArrayList<>();

    Grid<Item> grid = new Grid<>(Item.class);
    Button addItemButton = new Button("Gegenstand hinzufügen");
    Button editItemButton = new Button("Gegenstand anpassen");
    Button deleteItemButton = new Button("Gegenstand entfernen");

    ItemDialog itemDialog;

    private Item currentItem;

    public ItemsConfigurator() {
        initRoom();
        addClickListener();
        add(new H1("Gegenstände"), items);
    }

    private void initRoom() {
        fillItemsList();
        createGrid();

        items.add(new HorizontalLayout(addItemButton, editItemButton, deleteItemButton));
    }

    private void addClickListener () {
        addItemButton.addClickListener(e -> {
            currentItem = new Item();
            ItemDialog dialog = createDialog();
            dialog.open();
        });

        editItemButton.addClickListener(e -> {
            Item[] selectedItems = grid.getSelectedItems().toArray(Item[]::new);
            if (selectedItems.length >= 1) {
                currentItem = selectedItems[0];
                itemDialog = createDialog();
                itemDialog.fillDialog(currentItem);
                itemDialog.open();
            } else {
                Notification.show("Bitte wählen sie einen Gegenstand aus!");
            }
        });

        deleteItemButton.addClickListener(e -> {
            Item[] selectedItems = grid.getSelectedItems().toArray(Item[]::new);
            if (selectedItems.length >= 1) {
                currentItem = selectedItems[0];
                itemList.remove(currentItem);
                refreshGrid();
            }
        });
    }

    private ItemDialog createDialog() {
        itemDialog = new ItemDialog(itemList, currentItem, grid);

        return itemDialog;
    }

    private void createGrid() {
        grid.setItems(itemList);

        grid.removeAllColumns();
        Grid.Column<Item> nameColumn = grid.addColumn(Item::getItemName).setHeader("Name");
        Grid.Column<Item> sizeColumn = grid.addColumn(Item::getSize).setHeader("Größe");
        Grid.Column<Item> descriptionColumn = grid.addColumn(Item::getDescription).setHeader("Beschreibung");
        Grid.Column<Item> typeColumn = grid.addColumn(Item::getType).setHeader("Typ");

        HeaderRow filterRow = grid.appendHeaderRow();

        TextField nameField = new TextField();
        TextField sizeField = new TextField();
        TextField descriptionField = new TextField();
        TextField typeField = new TextField();

        nameField.addValueChangeListener(e -> {
            //TODO: Service.findByName
        });
        sizeField.addValueChangeListener(e -> {
            //TODO: Service.findBySize
        });
        descriptionField.addValueChangeListener(e -> {
            //TODO: Service.findByDescription
        });
        typeField.addValueChangeListener(e -> {
            //TODO: Service.findByType
        });

        filterRow.getCell(nameColumn).setComponent(nameField);
        filterRow.getCell(sizeColumn).setComponent(sizeField);
        filterRow.getCell(descriptionColumn).setComponent(descriptionField);
        filterRow.getCell(typeColumn).setComponent(typeField);

        nameField.setSizeFull();
        nameField.setPlaceholder("Filter");
        nameField.getElement().setAttribute("focus-target", "");
        sizeField.setSizeFull();
        sizeField.setPlaceholder("Filter");
        sizeField.getElement().setAttribute("focus-target", "");
        descriptionField.setSizeFull();
        descriptionField.setPlaceholder("Filter");
        descriptionField.getElement().setAttribute("focus-target", "");

        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);

        this.setWidthFull();
        grid.setWidth("100%");

        items.add(grid);
    }

    private void refreshGrid() {
        grid.setItems(itemList);
    }

    private void fillItemsList () {
        for (int i = 0; i < 20; i++) {
            Item item = new Item(String.valueOf(i), (long) i, String.valueOf(i));
            item.setItemId((long) i);
            itemList.add(item);
        }
    }
}
