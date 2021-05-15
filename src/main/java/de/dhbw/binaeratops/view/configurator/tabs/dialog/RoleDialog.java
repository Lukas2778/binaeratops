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

/**
 * Dialog-Oberfläche für die Komponente "Rolle hinzufügen" des Raum-Konfigurators.
 * <p>
 * Diese Ansicht stellt alle View-Komponenten für das Konfiguration einer Rolle bereit.
 * <p>
 * Dafür sendet sie die Benutzerangaben direkt an den entsprechenden Service.
 *
 * @author Pedro Treuer, Timon Gartung, Nicolas Haug, Lars Rösel, Mattias Rall, Lukas Göpel
 */
public class RoleDialog
        extends Dialog
{
    private TextField currentRoleField;
    private TextField currentDescriptionField;

    private Role currentRole;
    Grid<Role> grid;
    private ConfiguratorServiceI configuratorServiceI;



    public RoleDialog()
    {
    }

    public RoleDialog(ArrayList<Role> roleList,
                      Role currentRole,
                      Grid<Role> grid,
                      ConfiguratorServiceI AConfiguratorServiceI)
    {

        this.currentRole = currentRole;
        this.grid = grid;
        this.configuratorServiceI = AConfiguratorServiceI;
        init();
    }

    private void init()
    {
        currentRoleField = new TextField("Rollenbezeichnung");
        currentDescriptionField = new TextField("Beschreibung");
        Button saveDialog = new Button("Speichern");
        Button closeDialog = new Button("Abbrechen");

        this.add(new VerticalLayout(currentRoleField,
                                    currentDescriptionField,
                                    new HorizontalLayout(saveDialog, closeDialog)));

        saveDialog.addClickListener(e -> {
            if ( currentRoleField.getValue() != "" )
            {

                currentRole.setRoleName(currentRoleField.getValue());
                currentRole.setDescription(currentDescriptionField.getValue());


                if ( !findRole(currentRole.getRoleName(), currentRole.getDescription()) )
                {
                    configuratorServiceI.createRole(currentRole.getRoleName(), currentRole.getDescription());
                    refreshGrid();
                    this.close();
                }
                else
                {
                    Notification.show("Warum denn zwei Mal die gleiche Rolle erstellen? Einmal reicht ;)");
                }

            }
            else
            {
                Notification.show("Du hast die Rollenbezeichnung vergessen ;)");

            }

        });
        closeDialog.addClickListener(e -> this.close());
    }

    private void refreshGrid()
    {

        grid.setItems(configuratorServiceI.getAllRoles());
    }

    private boolean findRole(String ARoleName, String ARoleDescription)
    {
        boolean result = false;
        for ( Role role : configuratorServiceI.getAllRoles() )
        {
            if ( role.getRoleName()
                    .equals(ARoleName) && role.getDescription()
                    .equals(ARoleDescription) )
            {
                result = true;
            }

        }
        return result;
    }

}
