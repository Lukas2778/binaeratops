/*
 *
 *  * (c) 2015 - 2021 ENisco GmbH & Co. KG
 *
 */

package de.dhbw.binaeratops.view.configurator.tabs.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import de.dhbw.binaeratops.model.entitys.Race;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import java.util.ArrayList;

public class RaceDialog
        extends Dialog {
    private TextField currentRaceField;
    private TextField currentDescriptionField;

    private Race currentRace;
    private ArrayList<Race> raceList;
    Grid<Race> grid;
    ConfiguratorServiceI configuratorServiceI;

    public RaceDialog() {}

    public RaceDialog(ArrayList<Race> raceList, Race currentRace, Grid<Race> grid, ConfiguratorServiceI AConfiguratorServiceI) {
        this.raceList = raceList;
        this.currentRace = currentRace;
        this.grid = grid;
        this.configuratorServiceI = AConfiguratorServiceI;
        init();
    }
    private void init() {
        currentRaceField = new TextField("Rassenbezeichnung");
        currentDescriptionField = new TextField("Beschreibung");
        Button saveDialog = new Button("Speichern");
        Button closeDialog = new Button("Abbrechen");

        this.add(new VerticalLayout(currentRaceField,
                                    currentDescriptionField, new HorizontalLayout(saveDialog, closeDialog)));



        saveDialog.addClickListener(e -> {
            currentRace.setRaceName(currentRaceField.getValue());
            currentRace.setDescription(currentDescriptionField.getValue());


            if (!configuratorServiceI.getAllRace().contains(currentRace))  {
                configuratorServiceI.createRace(currentRace.getRaceName(), currentRace.getDescription());
            }
            refreshGrid();

            this.close();
        });
        closeDialog.addClickListener(e -> this.close());
    }


    private void refreshGrid() {
        grid.setItems(configuratorServiceI.getAllRace());
   }
//
//    public TextField getCurrentName() {
//        return currentName;
//    }
//
//    public void setCurrentName(TextField currentName) {
//        this.currentName = currentName;
//    }
//
//    public NumberField getCurrentSize() {
//        return currentSize;
//    }
//
//    public void setCurrentSize(NumberField currentSize) {
//        this.currentSize = currentSize;
//    }
//
//    public TextField getCurrentDescriptionField() {
//        return currentDescriptionField;
//    }
//
//    public void setCurrentDescriptionField(TextField currentDescriptionField) {
//        this.currentDescriptionField = currentDescriptionField;
//    }
//
//    public ComboBox<ItemType> getCurrentType() {
//        return currentType;
//    }
//
//    public void setCurrentType(ComboBox<ItemType> currentType) {
//        this.currentType = currentType;
//    }
//
//    public Item getCurrentItem() {
//        return currentItem;
//    }
//
//    public void setCurrentItem(Item currentItem) {
//        this.currentItem = currentItem;
//    }
}
