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
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import de.dhbw.binaeratops.model.entitys.Role;
import java.util.ArrayList;

@PageTitle("Charaktereigenschaft")
@CssImport("./views/mainviewtabs/configurator/charStats-view.css")

public class CharacterConfiguration
        extends VerticalLayout
{
    VerticalLayout initFeldLayout = new VerticalLayout();
    VerticalLayout roleListLayout = new VerticalLayout();
    VerticalLayout raceListLayout = new VerticalLayout();

    ArrayList<Role> roles = new ArrayList<>();

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

        //Beispieldaten
        Role testRole = new Role();
        testRole.setRoleId(0L);
        testRole.setRoleName("Ork");
        testRole.setDescription("Hässliches Wesen");

        Role testRole2 = new Role();
        testRole2.setRoleId(1L);
        testRole2.setRoleName("Lars");
        testRole2.setDescription(" Wesen");

        Role testRole3 = new Role();
        testRole3.setRoleId(2L);
        testRole3.setRoleName("Payy");
        testRole3.setDescription(" Wesessadn");

        Role testRole4 = new Role();
        testRole4.setRoleId(3L);
        testRole4.setRoleName("Ptetey");
        testRole4.setDescription(" Wesesasdsadn");

        Role testRole5 = new Role();
        testRole5.setRoleId(3L);
        testRole5.setRoleName("Ptetey");
        testRole5.setDescription(" Wesesasdsadn");

        Role testRole6 = new Role();
        testRole6.setRoleId(3L);
        testRole6.setRoleName("Ptetey");
        testRole6.setDescription(" Wesesasdsadn");

        roles.add(testRole);
        roles.add(testRole2);
        roles.add(testRole3);
        roles.add(testRole4);
        roles.add(testRole5);
        roles.add(testRole6);

        H2 titel = new H2("Rollenliste");

        Grid<Role> grid = new Grid<>();

        grid.setItems(roles);

        Column<Role> nameColumn = grid.addColumn(Role::getRoleName).setHeader("Rollenbezeichnung");
        Column<Role> descriptionColumn = grid.addColumn(Role::getDescription).setHeader("Beschreibung");

        TextField roleNameField = new TextField();
        TextField descriptionField = new TextField();

        roleNameField.getElement().setAttribute("focus-target", "");
        descriptionField.getElement().setAttribute("focus-target", "");

        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);

        HorizontalLayout buttonView = new HorizontalLayout();
        buttonView.setVerticalComponentAlignment(Alignment.END);

        Button addB = new Button("hinzufügen");
        Button deleteB = new Button("löschen");

        addB.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteB.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        buttonView.addAndExpand(addB, deleteB);

      //  roleListLayout.setSizeFull();
        roleListLayout.add(titel, grid, buttonView);


    }
    private void raceList()
    {

        //Beispieldaten
        Role testRole = new Role();
        testRole.setRoleId(0L);
        testRole.setRoleName("Ork");
        testRole.setDescription("Hässliches Wesen");

        Role testRole2 = new Role();
        testRole2.setRoleId(1L);
        testRole2.setRoleName("Lars");
        testRole2.setDescription(" Wesen");

        Role testRole3 = new Role();
        testRole3.setRoleId(2L);
        testRole3.setRoleName("Payy");
        testRole3.setDescription(" Wesessadn");

        Role testRole4 = new Role();
        testRole4.setRoleId(3L);
        testRole4.setRoleName("Ptetey");
        testRole4.setDescription(" Wesesasdsadn");
        roles.add(testRole);
        roles.add(testRole2);
        roles.add(testRole3);
        roles.add(testRole4);

        H2 titel = new H2("Rollenliste");

        Grid<Role> grid = new Grid<>();

        grid.setItems(roles);
        Column<Role> nameColumn = grid.addColumn(Role::getRoleName).setHeader("Rollenbezeichnung");
        Column<Role> descriptionColumn = grid.addColumn(Role::getDescription).setHeader("Beschreibung");

        TextField roleNameField = new TextField();
        TextField descriptionField = new TextField();

        roleNameField.getElement().setAttribute("focus-target", "");
        descriptionField.getElement().setAttribute("focus-target", "");

        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);

        HorizontalLayout buttonView = new HorizontalLayout();
        buttonView.setVerticalComponentAlignment(Alignment.END);

        Button addB = new Button("hinzufügen");
        Button deleteB = new Button("löschen");

        addB.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteB.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        buttonView.addAndExpand(addB, deleteB);
        raceListLayout.setSizeFull();
        raceListLayout.add(titel, grid, buttonView);

    }

}





