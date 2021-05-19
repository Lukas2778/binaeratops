package de.dhbw.binaeratops.view.configurator.tabs;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
    // TODO Kommentare schreiben
    public ItemConfigurationTab(@Autowired ConfiguratorServiceI configuratorServiceI) {
        this.configuratorServiceI = configuratorServiceI;
        initRoom();
        addClickListener();

        Details hint = new Details(res.getString("view.configurator.item.hint"),
                                   new Text(res.getString("view.configurator.item.hint.description")));

        add(new H1(res.getString("view.configurator.item.headline")), hint , items);
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
        grid.addColumn(Item::getItemName).setHeader(res.getString("view.configurator.item.grid.itemname"));
        grid.addColumn(Item::getSize).setHeader(res.getString("view.configurator.item.grid.size"));
        grid.addColumn(Item::getDescription).setHeader(res.getString("view.configurator.item.grid.description"));
        grid.addColumn(Item::getType).setHeader(res.getString("view.configurator.item.grid.type"));

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
