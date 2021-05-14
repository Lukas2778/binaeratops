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
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import de.dhbw.binaeratops.model.entitys.Race;
import de.dhbw.binaeratops.model.entitys.Role;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import java.util.ArrayList;

public class PermissionDialog
        extends Dialog
{
    private TextField currentUserField;

    private ArrayList<User> userList;
    Grid<User> grid;
    ConfiguratorServiceI configuratorServiceI;
    User currentUser;
    User currentUserDB;

    VerticalLayout layout = new VerticalLayout();
    HorizontalLayout buttonLayout = new HorizontalLayout();

    public PermissionDialog()
    {
    }

    public PermissionDialog(ArrayList<User> userList,
                            User currentUser,
                            Grid<User> grid,
                            ConfiguratorServiceI AConfiguratorServiceI)
    {
        this.userList = userList;
        this.currentUser = currentUser;
        this.grid = grid;
        this.configuratorServiceI = AConfiguratorServiceI;
        init();
    }

    private void init()
    {
        currentUserField = new TextField("Welcher Spieler bekommt eine Berechtigung?");
        currentUserField.setWidth("300px");

        Button saveButton = new Button("Speichern");
        Button closeButton = new Button("Abbrechen");

        buttonLayout.addAndExpand(saveButton, closeButton);
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        layout.add(currentUserField, buttonLayout);
        this.add(layout);

        saveButton.addClickListener(e -> {
            if ( currentUserField.getValue() != "" )
            {
                currentUser.setName(currentUserField.getValue());
                currentUserDB = configuratorServiceI.getUser(currentUser.getName());

                if ( findAllowedUser(currentUserField.getValue()) )
                {
                    Notification.show("Eine Spielberechtigung reicht ");
                }
                else if ( matchDungeonMasterId(currentUserField.getValue()) )
                {
                    Notification.show("Du darfst dein eigenes Spiel immer spielen ;)");
                }
                else
                {

                    if ( currentUserDB != null )
                    {
                        configuratorServiceI.getDungeon()
                                .addAllowedUser(currentUserDB);

                        configuratorServiceI.saveDungeon();

                        refreshGrid();
                        this.close();
                    }
                    else
                    {
                        Notification.show("Dieser Spieler wurde nicht gefunden");
                    }

                }
            }
            else
            {
                Notification.show("Nach nichts zu suchen ist auch kreativ ");

            }

        });

        closeButton.addClickListener(e -> this.close());
    }

    private void refreshGrid()
    {
        grid.setItems(configuratorServiceI.getDungeon()
                              .getAllowedUsers());
    }

    private boolean findAllowedUser(String AName)
    {
        boolean result = false;
        for ( User user : configuratorServiceI.getDungeon()
                .getAllowedUsers() )
        {
            if ( user.getName()
                    .equals(AName) )
            {
                result = true;
            }
        }
        return result;
    }

    private boolean matchDungeonMasterId(String AName)
    {
        boolean result = false;

        if ( configuratorServiceI.getDungeon()
                .getDungeonMasterId() == configuratorServiceI.getUser(AName)
                .getUserId() )
        {
            result = true;
        }

        return result;
    }

}
