/*
 *
 *  * (c) 2015 - 2021 ENisco GmbH & Co. KG
 *
 */

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
import de.dhbw.binaeratops.model.entitys.Role;
import de.dhbw.binaeratops.model.enums.ItemType;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import java.util.ArrayList;

public class RoleDialog
        extends Dialog {
    private TextField currentRoleField;
    private TextField currentDescriptionField;

    private Role currentRole;
    private ArrayList<Role> roleList;
    Grid<Role> grid;
    private ConfiguratorServiceI configuratorServiceI;

    public RoleDialog() {}

    public RoleDialog(ArrayList<Role> roleList, Role currentRole, Grid<Role> grid, ConfiguratorServiceI AConfiguratorServiceI ) {
        this.roleList = roleList;
        this.currentRole = currentRole;
        this.grid = grid;
        this.configuratorServiceI = AConfiguratorServiceI;
        init();
    }
    private void init() {
        currentRoleField = new TextField("Rollenbezeichnung");
        currentDescriptionField = new TextField("Beschreibung");
        Button saveDialog = new Button("Speichern");
        Button closeDialog = new Button("Abbrechen");

        this.add(new VerticalLayout(currentRoleField,
                                    currentDescriptionField, new HorizontalLayout(saveDialog, closeDialog)));



        saveDialog.addClickListener(e -> {
            currentRole.setRoleName(currentRoleField.getValue());
            currentRole.setDescription(currentDescriptionField.getValue());


            if (!roleList.contains(currentRole)) {
                roleList.add(currentRole);
                configuratorServiceI.createRole(currentRole.getRoleName(), currentRole.getDescription());
            }
            refreshGrid();

            this.close();
        });
        closeDialog.addClickListener(e -> this.close());
    }


    private void refreshGrid() {
        grid.setItems(roleList);
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
