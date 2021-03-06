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
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.Race;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;

import java.util.ResourceBundle;

/**
 * Dialog-Oberfläche für die Komponente "Rasse hinzufügen" des Raum-Konfigurators.
 * <p>
 * Diese Ansicht stellt alle View-Komponenten für das Konfiguration einer Rasse bereit.
 * <p>
 * Dafür sendet sie die Benutzerangaben direkt an den entsprechenden Service.
 *
 * @author Pedro Treuer, Timon Gartung, Nicolas Haug, Lars Rösel, Mattias Rall, Lukas Göpel
 */
public class RaceDialog extends Dialog {
    private final ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());


    private TextField currentRaceField;
    private TextField currentDescriptionField;

    private Race currentRace;
    Grid<Race> grid;
    ConfiguratorServiceI configuratorServiceI;

    // TODO Kommentare schreiben
    public RaceDialog() {
    }

    public RaceDialog(Race currentRace, Grid<Race> grid, ConfiguratorServiceI AConfiguratorServiceI) {
        this.currentRace = currentRace;
        this.grid = grid;
        this.configuratorServiceI = AConfiguratorServiceI;
        init();
    }

    private void init() {
        currentRaceField = new TextField(res.getString("view.configurator.dialog.race.field.name"));
        currentDescriptionField = new TextField(res.getString("view.configurator.dialog.race.field.description"));
        NumberField lifepointsBonusField = new NumberField(res.getString("view.configurator.dialog.race.field.lifepointsBonus"));
        lifepointsBonusField.setHasControls(true);
        lifepointsBonusField.setMin(0);
        lifepointsBonusField.setMax(50);
        lifepointsBonusField.setValue(0.0);
        lifepointsBonusField.setWidth("160px");

        Button saveDialog = new Button(res.getString("view.configurator.dialog.race.button.save"));
        Button closeDialog = new Button(res.getString("view.configurator.dialog.race.button.cancel"));

        this.add(new VerticalLayout(currentRaceField, currentDescriptionField, lifepointsBonusField, new HorizontalLayout(saveDialog, closeDialog)));

        saveDialog.addClickListener(e -> {
            if (currentRaceField.getValue() != "") {

                currentRace.setRaceName(currentRaceField.getValue());
                currentRace.setDescription(currentDescriptionField.getValue());
                currentRace.setLifepointsBonus(lifepointsBonusField.getValue().longValue());


                if (!findRace(currentRace.getRaceName(), currentRace.getDescription())) {
                    configuratorServiceI.createRace(currentRace.getRaceName(), currentRace.getDescription(), currentRace.getLifepointsBonus());
                    refreshGrid();
                    this.close();
                } else {
                    Span label = new Span(res.getString("view.configurator.dialog.race.notification.duplicate"));
                    showErrorNotification(label);
                }

            } else {
                Span label = new Span(res.getString("view.configurator.dialog.race.notification.forgot"));
                showErrorNotification(label);
            }

        });
        saveDialog.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        closeDialog.addClickListener(e -> this.close());
    }

    private void refreshGrid() {
        grid.setItems(configuratorServiceI.getAllRace());
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

    private boolean findRace(String ARaceName, String ARaceDescription) {
        boolean result = false;
        for (Race race : configuratorServiceI.getAllRace()) {
            if (race.getRaceName()
                    .equals(ARaceName) && race.getDescription()
                    .equals(ARaceDescription)) {
                result = true;
            }

        }
        return result;
    }
}
