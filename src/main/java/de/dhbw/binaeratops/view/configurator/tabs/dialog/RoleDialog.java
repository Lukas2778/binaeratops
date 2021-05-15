/*
 *
 *  * (c) 2015 - 2021 ENisco GmbH & Co. KG
 *
 */

package de.dhbw.binaeratops.view.configurator.tabs.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import de.dhbw.binaeratops.model.entitys.Role;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Dialog-Oberfläche für die Komponente "Rolle hinzufügen" des Raum-Konfigurators.
 * <p>
 * Diese Ansicht stellt alle View-Komponenten für das Konfiguration einer Rolle bereit.
 * <p>
 * Dafür sendet sie die Benutzerangaben direkt an den entsprechenden Service.
 *
 * @author Pedro Treuer, Timon Gartung, Nicolas Haug, Lars Rösel, Mattias Rall, Lukas Göpel
 */
public class RoleDialog extends Dialog {

    private final ResourceBundle res = ResourceBundle.getBundle("language");

    private TextField currentRoleField;
    private TextField currentDescriptionField;

    private Role currentRole;
    Grid<Role> grid;
    private ConfiguratorServiceI configuratorServiceI;


    public RoleDialog() {
    }

    public RoleDialog(ArrayList<Role> roleList,
                      Role currentRole,
                      Grid<Role> grid,
                      ConfiguratorServiceI AConfiguratorServiceI) {

        this.currentRole = currentRole;
        this.grid = grid;
        this.configuratorServiceI = AConfiguratorServiceI;
        init();
    }

    private void init() {
        currentRoleField = new TextField(res.getString("view.configurator.dialog.role.field.name"));
        currentDescriptionField = new TextField(res.getString("view.configurator.dialog.role.field.description"));
        Button saveDialog = new Button(res.getString("view.configurator.dialog.role.button.save"));
        Button closeDialog = new Button(res.getString("view.configurator.dialog.role.button.cancel"));

        this.add(new VerticalLayout(currentRoleField,
                currentDescriptionField,
                new HorizontalLayout(saveDialog, closeDialog)));

        saveDialog.addClickListener(e -> {
            if (currentRoleField.getValue() != "") {

                currentRole.setRoleName(currentRoleField.getValue());
                currentRole.setDescription(currentDescriptionField.getValue());


                if (!findRole(currentRole.getRoleName(), currentRole.getDescription())) {
                    configuratorServiceI.createRole(currentRole.getRoleName(), currentRole.getDescription());
                    refreshGrid();
                    this.close();
                } else {
                    Notification.show(res.getString("view.configurator.dialog.role.notification.duplicate"));
                }

            } else {
                Notification.show(res.getString("view.configurator.dialog.role.notification.forgot"));

            }

        });
        closeDialog.addClickListener(e -> this.close());
    }

    private void refreshGrid() {

        grid.setItems(configuratorServiceI.getAllRoles());
    }

    private boolean findRole(String ARoleName, String ARoleDescription) {
        boolean result = false;
        for (Role role : configuratorServiceI.getAllRoles()) {
            if (role.getRoleName()
                    .equals(ARoleName) && role.getDescription()
                    .equals(ARoleDescription)) {
                result = true;
            }

        }
        return result;
    }

}
