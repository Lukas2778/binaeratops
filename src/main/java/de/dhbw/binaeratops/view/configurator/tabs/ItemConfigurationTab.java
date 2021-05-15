package de.dhbw.binaeratops.view.configurator.tabs;

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
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.Item;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import de.dhbw.binaeratops.view.configurator.tabs.dialog.ItemDialog;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ResourceBundle;

/**
 * Tab-Oberfläche für die Komponente "Gegenstände" des Konfigurators.
 * <p>
 * Diese Ansicht stellt alle View-Komponenten für die Konfiguration der Gegenstände bereit.
 * <p>
 * Dafür sendet sie die Benutzerangaben direkt an den entsprechenden Service.
 *
 * @author Pedro Treuer, Timon Gartung, Nicolas Haug, Lars Rösel, Mattias Rall, Lukas Göpel
 */
@CssImport("./views/mainviewtabs/configurator/roomconfigurator-view.css")
public class ItemConfigurationTab extends VerticalLayout implements HasDynamicTitle {

    private final ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());

    ConfiguratorServiceI configuratorServiceI;

    VerticalLayout items = new VerticalLayout();

    Grid<Item> grid = new Grid<>(Item.class);
    Button addItemButton = new Button(res.getString("view.configurator.item.button.add.item"));
    Button editItemButton = new Button(res.getString("view.configurator.item.button.edit.item"));
    Button deleteItemButton = new Button(res.getString("view.configurator.item.button.remove.item"));

    ItemDialog itemDialog;

    private Item currentItem;

    public ItemConfigurationTab(@Autowired ConfiguratorServiceI configuratorServiceI) {
        this.configuratorServiceI = configuratorServiceI;
        initRoom();
        addClickListener();
        add(new H1(res.getString("view.configurator.item.headline")), items);
    }

    private void initRoom() {
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
                Notification.show(res.getString("view.configurator.item.notification.select.item"));
            }
        });

        deleteItemButton.addClickListener(e -> {
            Item[] selectedItems = grid.getSelectedItems().toArray(Item[]::new);
            if (selectedItems.length >= 1) {
                currentItem = selectedItems[0];
                configuratorServiceI.deleteItem(currentItem);
                refreshGrid();
            } else {
                Notification.show(res.getString("view.configurator.item.notification.select.item"));
            }
        });
    }

    private ItemDialog createDialog() {
        itemDialog = new ItemDialog(configuratorServiceI, currentItem, grid);

        return itemDialog;
    }

    private void createGrid() {
        grid.setItems(configuratorServiceI.getAllItems());

        grid.removeAllColumns();
        Grid.Column<Item> nameColumn = grid.addColumn(Item::getItemName).setHeader(res.getString("view.configurator.item.grid.itemname"));
        Grid.Column<Item> sizeColumn = grid.addColumn(Item::getSize).setHeader(res.getString("view.configurator.item.grid.size"));
        Grid.Column<Item> descriptionColumn = grid.addColumn(Item::getDescription).setHeader(res.getString("view.configurator.item.grid.description"));
        Grid.Column<Item> typeColumn = grid.addColumn(Item::getType).setHeader(res.getString("view.configurator.item.grid.type"));

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
        nameField.setPlaceholder(res.getString("view.configurator.item.filter.placehold"));
        nameField.getElement().setAttribute("focus-target", "");
        sizeField.setSizeFull();
        sizeField.setPlaceholder(res.getString("view.configurator.item.filter.placehold"));
        sizeField.getElement().setAttribute("focus-target", "");
        descriptionField.setSizeFull();
        descriptionField.setPlaceholder(res.getString("view.configurator.item.filter.placehold"));
        descriptionField.getElement().setAttribute("focus-target", "");
        typeField.setSizeFull();
        typeField.setPlaceholder(res.getString("view.configurator.item.filter.placehold"));
        typeField.getElement().setAttribute("focus-target", "");

        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);

        this.setWidthFull();
        grid.setWidth("100%");

        items.add(grid);
    }

    private void refreshGrid() {
        grid.setItems(configuratorServiceI.getAllItems());
    }

    @Override
    public String getPageTitle() {
        return res.getString("view.configurator.item.pagetitle");
    }
}
