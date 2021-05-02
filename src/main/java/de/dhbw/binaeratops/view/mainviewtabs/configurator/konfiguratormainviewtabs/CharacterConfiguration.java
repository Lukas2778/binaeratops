/*
 *
 *  * (c) 2015 - 2021 ENisco GmbH & Co. KG
 *
 */

package de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import de.dhbw.binaeratops.model.entitys.Race;
import de.dhbw.binaeratops.model.entitys.Role;
import de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs.dialog.RaceDialog;
import de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs.dialog.RoleDialog;
import java.util.ArrayList;

@PageTitle("Charaktereigenschaft")
@CssImport("./views/mainviewtabs/configurator/charStats-view.css")

public class CharacterConfiguration
        extends VerticalLayout
{
    VerticalLayout initFeldLayout = new VerticalLayout();
    VerticalLayout roleListLayout = new VerticalLayout();
    VerticalLayout raceListLayout = new VerticalLayout();

    ArrayList<Role> roleArrayList = new ArrayList<>();
    ArrayList<Race> raceArrayList = new ArrayList<>();

    Button addB = new Button("hinzufügen");
    Button deleteB = new Button("löschen");

    Button addRaceButton = new Button("hinzufügen");
    Button deleteRaceButton = new Button("löschen");

    Grid<Role> grid = new Grid<>();
    Grid<Race> raceGrid = new Grid<>();

    RoleDialog roleDialog;
    RaceDialog raceDialog;

    private Role currentRole;
    private Race currentRace;

    public CharacterConfiguration()
    {

        initFeld();
        roleList();
        raceList();

        SplitLayout layout = new SplitLayout();
        SplitLayout innerLayout = new SplitLayout();

        layout.setSecondaryStyle("minWidth", "1200px");
        layout.setPrimaryStyle("minWidth", "450px");
        layout.setPrimaryStyle("minHeight", "800px");

        innerLayout.setPrimaryStyle("minWidth", "400px");
        innerLayout.setPrimaryStyle("minWidth", "500px");

        innerLayout.setSecondaryStyle("minWidth", "400px");
        innerLayout.setSecondaryStyle("minWidth", "500px");

        innerLayout.addToPrimary(roleListLayout);
        innerLayout.addToSecondary(raceListLayout);

        layout.addToPrimary(initFeldLayout);
        layout.addToSecondary(innerLayout);

        layout.setSizeFull();
        add(layout);
    }

    private void initFeld()
    {
        H1 titel = new H1("Charaktereigenschaften");

        Details hinweis = new Details("Hinweis",
                                      new Text(
                                              "Hier Kann man Rollen und Rassen hinzufügen, die der Avatar des Spielers "
                                                      + "oder NPCs sein können. Auch die Inventargröße des Spielers ist hier zu bestimmen. "
                                                      + "Man kann dem Spieler auch die Möglichkeit geben ein Geschlecht auszuwählen."));
        hinweis.addOpenedChangeListener(e -> Notification.show(e.isOpened() ? "Opened" : "Closed"));

        NumberField inventorySize = new NumberField("Größe des Inventars");
        inventorySize.setHasControls(true);

        RadioButtonGroup<String> genderRadioButton = new RadioButtonGroup<>();
        genderRadioButton.setLabel("Soll der Spieler ein Geschlecht wählen können?");
        genderRadioButton.setItems("Aktivieren", "Deaktivieren");
        genderRadioButton.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        genderRadioButton.setValue("Aktivieren");

        initFeldLayout.add(titel, hinweis, inventorySize, genderRadioButton);

    }

    private void roleList()
    {
        addRoleClickListener();

        deleteRoleClickListener();



        H2 titel = new H2("Rollenliste");

        grid.setItems(roleArrayList);

        Column<Role> nameColumn = grid.addColumn(Role::getRoleName)
                .setHeader("Rollenbezeichnung");
        Column<Role> descriptionColumn = grid.addColumn(Role::getDescription)
                .setHeader("Beschreibung");

        TextField roleNameField = new TextField();
        TextField descriptionField = new TextField();

        roleNameField.getElement()
                .setAttribute("focus-target", "");
        descriptionField.getElement()
                .setAttribute("focus-target", "");

        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);

        HorizontalLayout buttonView = new HorizontalLayout();
        buttonView.setVerticalComponentAlignment(Alignment.END);

        addB.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteB.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        buttonView.addAndExpand(addB, deleteB);

        //  roleListLayout.setSizeFull();
        roleListLayout.add(titel, grid, buttonView);

    }

    private void raceList()
    {

        addRaceClickListener();

        deleteRaceClickListener();

        H2 titel = new H2("Rassenliste");


        raceGrid.setItems(raceArrayList);
        Column<Race> nameColumn = raceGrid.addColumn(Race::getRaceName)
                .setHeader("Rassenbezeichnung");
        Column<Race> descriptionColumn = raceGrid.addColumn(Race::getDescription)
                .setHeader("Beschreibung");

        TextField raceNameField = new TextField();
        TextField descriptionField = new TextField();

        raceNameField.getElement()
                .setAttribute("focus-target", "");
        descriptionField.getElement()
                .setAttribute("focus-target", "");

        raceGrid.addItemDoubleClickListener(event -> {
            raceGrid.getEditor().editItem(event.getItem());
            raceNameField.focus();
        });

        raceGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);

        HorizontalLayout buttonView = new HorizontalLayout();
        buttonView.setVerticalComponentAlignment(Alignment.END);



        addRaceButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteRaceButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        buttonView.addAndExpand(addRaceButton, deleteRaceButton);
        //raceListLayout.setSizeFull();
        raceListLayout.add(titel, raceGrid, buttonView);

    }

    private void refreshRoleGrid()
    {
        grid.setItems(roleArrayList);
    }

    private void refreshRaceGrid()
    {
        raceGrid.setItems(raceArrayList);
    }

    private RoleDialog createRoleDialog()
    {
        roleDialog = new RoleDialog(roleArrayList, currentRole, grid);

        return roleDialog;
    }

    private RaceDialog createRaceDialog()
    {
        raceDialog = new RaceDialog(raceArrayList, currentRace, raceGrid);

        return raceDialog;
    }

    private void addRoleClickListener()
    {
        addB.addClickListener(e -> {
            currentRole = new Role();
            RoleDialog dialog = createRoleDialog();
            dialog.open();
        });
    }
    private void addRaceClickListener()
    {
        addRaceButton.addClickListener(e -> {
            currentRace = new Race();
            RaceDialog dialog = createRaceDialog();
            dialog.open();
        });
    }

    private void deleteRoleClickListener()
    {
        deleteB.addClickListener(e -> {
            Role[] selectedRole = grid.getSelectedItems()
                    .toArray(Role[]::new);
            if ( selectedRole.length >= 1 )
            {
                currentRole = selectedRole[0];
                roleArrayList.remove(currentRole);
                refreshRoleGrid();
            }
        });
    }

    private void deleteRaceClickListener()
    {
        deleteRaceButton.addClickListener(e -> {
            Race[] selectedRace = raceGrid.getSelectedItems().toArray(Race[]::new);
            if ( selectedRace.length >= 1 )
            {
                currentRace = selectedRace[0];
                raceArrayList.remove(currentRace);
                refreshRaceGrid();
            }
        });
    }


}





