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
import de.dhbw.binaeratops.model.entitys.Race;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import java.util.ArrayList;

public class RaceDialog
        extends Dialog
{
    private TextField currentRaceField;
    private TextField currentDescriptionField;

    private Race currentRace;
    private ArrayList<Race> raceList;
    Grid<Race> grid;
    ConfiguratorServiceI configuratorServiceI;

    public RaceDialog()
    {
    }

    public RaceDialog(ArrayList<Race> raceList,
                      Race currentRace,
                      Grid<Race> grid,
                      ConfiguratorServiceI AConfiguratorServiceI)
    {
        this.raceList = raceList;
        this.currentRace = currentRace;
        this.grid = grid;
        this.configuratorServiceI = AConfiguratorServiceI;
        init();
    }

    private void init()
    {
        currentRaceField = new TextField("Rassenbezeichnung");
        currentDescriptionField = new TextField("Beschreibung");
        Button saveDialog = new Button("Speichern");
        Button closeDialog = new Button("Abbrechen");

        this.add(new VerticalLayout(currentRaceField,
                                    currentDescriptionField,
                                    new HorizontalLayout(saveDialog, closeDialog)));

        saveDialog.addClickListener(e -> {
            if ( currentRaceField.getValue() != "" )
            {

                currentRace.setRaceName(currentRaceField.getValue());
                currentRace.setDescription(currentDescriptionField.getValue());


                if ( !findRace(currentRace.getRaceName(), currentRace.getDescription()) )
                {
                    configuratorServiceI.createRace(currentRace.getRaceName(), currentRace.getDescription());
                    refreshGrid();
                    this.close();
                }
                else
                {
                    Notification.show("Warum denn zwei Mal die gleiche Rasse erstellen? Eine reicht ;)");
                }

            }
            else
            {
                Notification.show("Du hast die Rassenbezeichnung vergessen ;)");

            }

        });
//
//        saveDialog.addClickListener(e -> {
//            currentRace.setRaceName(currentRaceField.getValue());
//            currentRace.setDescription(currentDescriptionField.getValue());
//
//            if ( !configuratorServiceI.getAllRace()
//                    .contains(currentRace) )
//            {
//                configuratorServiceI.createRace(currentRace.getRaceName(), currentRace.getDescription());
//            }
//            refreshGrid();
//
//            this.close();
//        });
        closeDialog.addClickListener(e -> this.close());
    }

    private void refreshGrid()
    {
        grid.setItems(configuratorServiceI.getAllRace());
    }

    private boolean findRace(String ARaceName, String ARaceDescription)
    {
        boolean result = false;
        for ( Race race : configuratorServiceI.getAllRace() )
        {
            if ( race.getRaceName()
                    .equals(ARaceName) && race.getDescription()
                    .equals(ARaceDescription) )
            {
                result = true;
            }

        }
        return result;
    }
}
