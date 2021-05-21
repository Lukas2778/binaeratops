package de.dhbw.binaeratops.view.configurator.tabs.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.Item;
import de.dhbw.binaeratops.model.entitys.ItemInstance;
import de.dhbw.binaeratops.model.entitys.Room;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;

import java.text.MessageFormat;
import java.util.*;

/**
 * Dialog-Oberfläche für die Komponente "Item Auswahl" des Raum-Konfigurators.
 * <p>
 * Diese Ansicht stellt alle View-Komponenten für das Hinzufügen eines Gegenstandes zu einem Raum bereit.
 * <p>
 * Dafür sendet sie die Benutzerangaben direkt an den entsprechenden Service.
 *
 * @author Pedro Treuer, Timon Gartung, Nicolas Haug, Lars Rösel, Mattias Rall, Lukas Göpel
 */
public class ItemSelectionDialog extends Dialog {

    private final ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());

    public boolean dialogResult = false;

    public Button confirmButton = new Button(res.getString("view.configurator.dialog.item.select.button.confirm"));
    public Button cancelButton = new Button(res.getString("view.configurator.dialog.item.select.button.cancel"));
    Grid<Item> itemGrid = new Grid(Item.class);
    HashMap<Item, NumberField> itemIntegerFieldHashMap = new HashMap<>();
    private ListBox<ItemInstance> itemList;
    private Room room;

    ConfiguratorServiceI configuratorServiceI;

    /**
     * Konstruktor für die Auswahl eines Items.
     * @param AConfiguratorService Konfigurator übergeben.
     * @param ARoom Raum übergeben.
     * @param itemList Liste der Items übergeben.
     */
    public ItemSelectionDialog(ConfiguratorServiceI AConfiguratorService, Room ARoom, ListBox<ItemInstance> itemList){
        room = ARoom;
        this.itemList = itemList;
        configuratorServiceI = AConfiguratorService;
        H1 title = new H1(res.getString("view.configurator.dialog.item.select.headline1"));
        H2 headline = new H2(MessageFormat.format(res.getString("view.configurator.dialog.item.select.headline2"), room.getRoomName()));

        itemGrid.removeAllColumns();

        itemGrid.addComponentColumn(item -> {
            NumberField nf = new NumberField();
            itemIntegerFieldHashMap.put(item, nf);
            nf.setHasControls(true);
            nf.setMin(0);
            nf.setStep(1.0);
            nf.getStyle().set("width", "6.5em");
            nf.setValue(configuratorServiceI.getNumberOfItem(room, item));

            return nf;
        }).setHeader(res.getString("view.configurator.dialog.item.select.grid.amount"));
        itemGrid.addColumn(Item::getItemName).setHeader(res.getString("view.configurator.dialog.item.select.grid.name"));
        itemGrid.addColumn(Item::getSize).setHeader(res.getString("view.configurator.dialog.item.select.grid.size"));
        itemGrid.addColumn(Item::getDescription).setHeader(res.getString("view.configurator.dialog.item.select.grid.description"));

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
