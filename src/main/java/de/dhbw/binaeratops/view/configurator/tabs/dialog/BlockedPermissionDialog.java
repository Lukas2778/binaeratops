/*
 *
 *  * (c) 2015 - 2021 ENisco GmbH & Co. KG
 *
 */

package de.dhbw.binaeratops.view.configurator.tabs.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.Permission;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Dialog-Oberfläche für die Komponente "Blockierte Spielberechtigung" des Dungeoneigenschaften-Konfigurators.
 * <p>
 * Diese Ansicht stellt alle View-Komponenten für das Hinzufügen einer Blockierten-Berechtigung zu einem Dungeon bereit.
 * <p>
 * Dafür sendet sie die Benutzereingaben direkt an den entsprechenden Service.
 *
 * @author Nicolas Haug
 */
public class BlockedPermissionDialog extends Dialog {
    private TextField currentUserField;

    private final ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());

    private ArrayList<User> userList;
    Grid<Permission> grid;
    ConfiguratorServiceI configuratorServiceI;
    User currentUser;
    User currentUserDB;

    VerticalLayout layout = new VerticalLayout();
    HorizontalLayout buttonLayout = new HorizontalLayout();
    // TODO Kommentare schreiben
    public BlockedPermissionDialog() {
    }

    public BlockedPermissionDialog(ArrayList<User> userList,

                                   User currentUser,
                                   Grid<Permission> grid,
                                   ConfiguratorServiceI AConfiguratorServiceI) {
        this.userList = userList;

        this.currentUser = currentUser;
        this.grid = grid;
        this.configuratorServiceI = AConfiguratorServiceI;
        init();
    }

    private void init() {
        currentUserField = new TextField(res.getString("view.configurator.dialog.blocked.permission.field.question"));
        currentUserField.setWidth("300px");

        Button saveButton = new Button(res.getString("view.configurator.dialog.permission.button.save"));
        Button closeButton = new Button(res.getString("view.configurator.dialog.permission.button.cancel"));

        buttonLayout.addAndExpand(saveButton, closeButton);
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        layout.add(currentUserField, buttonLayout);
        this.add(layout);

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickListener(e -> {
            if (currentUserField.getValue() != "") {
                currentUser.setName(currentUserField.getValue());
                currentUserDB = configuratorServiceI.getUser(currentUser.getName());

                if (findBlockedUser(currentUser.getName())) {
                    Span label = new Span(res.getString("view.configurator.dialog.blocked.permission.notification.duplicate"));
                    showErrorNotification(label);

                } else {
                    if (matchDungeonMasterId(currentUserField.getValue())) {
                        Span label = new Span(res.getString("view.configurator.dialog.blocked.permission.notification.self"));
                        showErrorNotification(label);
                    } else if (currentUserDB != null) {
                        //Hier wird die Berechtigung erteilt, falls alle Abfagen erfolgreich waren
                        Permission p = new Permission(currentUserDB);
                        configuratorServiceI.getDungeon().addBlockedUser(p);
                        configuratorServiceI.savePermission(p);
                        configuratorServiceI.saveUser(currentUserDB);
                        configuratorServiceI.saveDungeon();
                        refreshGrid();
                        this.close();
                    }
                }
            } else {
                Span label = new Span(res.getString("view.configurator.dialog.permission.notification.no.input"));
                showErrorNotification(label);
            }

        });

        closeButton.addClickListener(e -> this.

                close());
    }

    private void refreshGrid() {
        grid.setItems(configuratorServiceI.getDungeon().getBlockedUsers());
    }

    private void showErrorNotification(Span ALabel) {
        Notification notification = new Notification();
        Button closeButton = new Button("", e -> {
            notification.close();
        });
        closeButton.setIcon(new Icon(VaadinIcon.CLOSE));
        closeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        notification.add(ALabel, closeButton);
        ALabel.getStyle().set("margin-right", "0.3rem");
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.setDuration(10000);
        notification.setPosition(Notification.Position.TOP_END);
        notification.open();
    }

    private boolean matchDungeonMasterId(String AName) {
        boolean result = false;

        try{
            if (configuratorServiceI.getDungeon().getDungeonMasterId() == configuratorServiceI.getUser(AName).getUserId()) {
                result = true;
            }

        }catch(Exception e){
            Span label = new Span(res.getString("view.configurator.dialog.permission.notification.user.not.found"));
            showErrorNotification(label);
        }


        return result;
    }

    private boolean findBlockedUser(String AName) {
        boolean result = false;
        for (Permission user : configuratorServiceI.getDungeon()
                .getBlockedUsers()) {
            if (user.getUser().getName()
                    .equals(AName)) {
                result = true;
            }
        }
        return result;
    }


}
