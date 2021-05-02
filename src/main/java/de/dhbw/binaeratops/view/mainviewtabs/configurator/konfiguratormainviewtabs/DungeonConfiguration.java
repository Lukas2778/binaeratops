/*
 *
 *  * (c) 2015 - 2021 ENisco GmbH & Co. KG
 *
 */

package de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs;

import com.vaadin.flow.component.Key;
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
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.Item;
import de.dhbw.binaeratops.model.entitys.Role;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.enums.Visibility;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs.dialog.ItemDialog;
import de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs.dialog.RoleDialog;
import java.util.ArrayList;
import org.apache.commons.compress.archivers.dump.DumpArchiveEntry.PERMISSION;

@PageTitle("Dungeon-Konfiguration")
@CssImport("./views/mainviewtabs/configurator/charStats-view.css")

public class DungeonConfiguration
        extends VerticalLayout
{
    VerticalLayout initFeldLayout = new VerticalLayout();
    VerticalLayout permissionLayout = new VerticalLayout();

    ArrayList<User> users = new ArrayList<>();

    TextField titelField = new TextField("Titel");
    TextField playerCountField = new TextField("Maximale Spieleranzahl");

    Visibility visibility;

private ConfiguratorServiceI configuratorService;


    public DungeonConfiguration(ConfiguratorServiceI AConfiguratorServiceI)
    {

        this.configuratorService = AConfiguratorServiceI;

        initFeld();
        permissionList();

        SplitLayout layout = new SplitLayout();

        layout.setSecondaryStyle("minWidth", "400px");
        layout.setPrimaryStyle("minWidth", "450px");
        layout.setPrimaryStyle("minHeight", "800px");

        layout.addToPrimary(initFeldLayout);
        layout.addToSecondary(permissionLayout);

        layout.setSizeFull();
        add(layout);
    }

    private void initFeld()
    {
        H1 titel = new H1("Dungeon-Konfiguration");

        Details hinweis = new Details("Allgemeines",
                                      new Text("Eine gute Dungeonbeschreibung hilft den Spielern sich für dein\n"
                                                       + "Dungeon zu entscheiden. Die Dungeonbeschreibung ist oft der\n"
                                                       + "erste Eindruck!"));
        hinweis.addOpenedChangeListener(e -> Notification.show(e.isOpened() ? "Opened" : "Closed"));


        titelField.setWidth("300px");
        titelField.setValue(titelField.getValue());


        playerCountField.setWidth("300px");

        RadioButtonGroup<String> viewRadioButton = new RadioButtonGroup<>();
        viewRadioButton.setLabel("Sichtbarkeit");
        viewRadioButton.setItems("Öffentlich", "Privat", "In Konfiguration");
        viewRadioButton.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        viewRadioButton.setValue("Öffentlich");

        TextArea dungeonDescription = new TextArea("Dungeonbeschreibung");
        dungeonDescription.setWidth("300px");


        // create Dungeon Button
        Button createDungeonButton = new Button("Erstelle Button");
        createDungeonButton.setWidth("300px");
        createDungeonButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        createDungeonButton.addClickListener(e -> {
        configuratorService.createDungeon(titelField.getValue(), VaadinSession.getCurrent().getAttribute(User.class), Long.parseLong(playerCountField.getValue()), getVisibility(viewRadioButton.getValue()));
    });
        initFeldLayout.add(titel, hinweis, titelField, playerCountField, viewRadioButton, dungeonDescription, createDungeonButton);
    }

    private void permissionList()
    {

        //Beispieldaten
        User testUser = new User();
        testUser.setUsername("DungeonDestroyer");

        User testUser2 = new User();
        testUser2.setUsername("DungeonKiller");

        users.add(testUser);
        users.add(testUser2);

        H2 titel = new H2("Spielberechtigung");
        Text permissionText = new Text("Hier kannst du schon im Voraus Spieler eine Berechtigung geben dein Dungeon zu spielen.");

        Grid<User> grid = new Grid<>();

        grid.setItems(users);
        Column<User> nameColumn = grid.addColumn(User::getUsername)
                .setHeader("Benutzername");


        TextField roleNameField = new TextField();
        TextField descriptionField = new TextField();

        roleNameField.getElement()
                .setAttribute("focus-target", "");
        descriptionField.getElement()
                .setAttribute("focus-target", "");

        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);

        HorizontalLayout buttonView = new HorizontalLayout();
        buttonView.setVerticalComponentAlignment(Alignment.END);

        Button addB = new Button("hinzufügen");
        Button deleteB = new Button("löschen");

        addB.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteB.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        buttonView.addAndExpand(addB, deleteB);
        permissionLayout.setSizeFull();
        permissionLayout.add(titel, permissionText, grid, buttonView);

    }

    private Visibility getVisibility(String value){
        if(value == "Öffentlich"){
             visibility= Visibility.PUBLIC;
        }else if(value == "Privat"){
            visibility= Visibility.PRIVATE;
        }else{
            visibility= Visibility.IN_CONFIGURATION;
        }

        return visibility;
    }


}





