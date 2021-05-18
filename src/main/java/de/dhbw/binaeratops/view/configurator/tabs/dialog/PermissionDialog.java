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
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Dialog-Oberfläche für die Komponente "Spielberechtigung" des Dungeoneigenschaften-Konfigurators.
 * <p>
 * Diese Ansicht stellt alle View-Komponenten für das Hinzufügen einer Berechtigung zu einem Dungeon bereit.
 * <p>
 * Dafür sendet sie die Benutzereingaben direkt an den entsprechenden Service.
 *
 * @author Pedro Treuer, Timon Gartung, Nicolas Haug, Lars Rösel, Mattias Rall, Lukas Göpel
 */
public class PermissionDialog extends Dialog {
    private TextField currentUserField;

    private final ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());

    private ArrayList<User> userList;
    Grid<User> grid;
    ConfiguratorServiceI configuratorServiceI;
    User currentUser;
    User currentUserDB;

    VerticalLayout layout = new VerticalLayout();
    HorizontalLayout buttonLayout = new HorizontalLayout();
    // TODO Kommentare schreiben
    public PermissionDialog() {
    }

    public PermissionDialog(ArrayList<User> userList,

                            User currentUser,
                            Grid<User> grid,
                            ConfiguratorServiceI AConfiguratorServiceI) {
        this.userList = userList;

        this.currentUser = currentUser;
        this.grid = grid;
        this.configuratorServiceI = AConfiguratorServiceI;
        init();
    }

    private void init() {
        currentUserField = new TextField(res.getString("view.configurator.dialog.permission.field.question"));
        currentUserField.setWidth("300px");

        Button saveButton = new Button(res.getString("view.configurator.dialog.permission.button.save"));
        Button closeButton = new Button(res.getString("view.configurator.dialog.permission.button.cancel"));

        buttonLayout.addAndExpand(saveButton, closeButton);
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        layout.add(currentUserField, buttonLayout);
        this.add(layout);

        saveButton.addClickListener(e -> {
            if (currentUserField.getValue() != "") {
                currentUser.setName(currentUserField.getValue());
                currentUserDB = configuratorServiceI.getUser(currentUser.getName());

                if (findAllowedUser(currentUser.getName())) {
                    Notification.show(res.getString("view.configurator.dialog.permission.notification.duplicate"));

                } else {
                    if (matchDungeonMasterId(currentUserField.getValue())) {
                        Notification.show(res.getString("view.configurator.dialog.permission.notification.self"));
                    } else if (currentUserDB != null) {
                        //Hier wird die Berechtigung erteilt, falls alle Abfagen erfolgreich waren
                        configuratorServiceI.getDungeon().addAllowedUser(currentUserDB);
                        configuratorServiceI.saveUser(currentUserDB);
                        configuratorServiceI.saveDungeon();

                        refreshGrid();
                        this.close();
                    }
                }
            } else {
                Notification.show(res.getString("view.configurator.dialog.permission.notification.no.input"));

            }

        });

        closeButton.addClickListener(e -> this.

                close());
    }

    private void refreshGrid() {
        grid.setItems(configuratorServiceI.getDungeon().getAllowedUsers());
    }


    private boolean matchDungeonMasterId(String AName) {
        boolean result = false;

        try{
            if (configuratorServiceI.getDungeon().getDungeonMasterId() == configuratorServiceI.getUser(AName).getUserId()) {
                result = true;
            }

        }catch(Exception e){
            Notification.show(res.getString("view.configurator.dialog.permission.notification.user.not.found"));
        }


        return result;
    }

    private boolean findAllowedUser(String AName) {
        boolean result = false;
        for (User user : configuratorServiceI.getDungeon()
                .getAllowedUsers()) {
            if (user.getName()
                    .equals(AName)) {
                result = true;
            }
        }
        return result;
    }


}
