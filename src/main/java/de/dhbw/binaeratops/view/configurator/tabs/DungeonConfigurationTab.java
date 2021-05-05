/*
 *
 *  * (c) 2015 - 2021 ENisco GmbH & Co. KG
 *
 */

package de.dhbw.binaeratops.view.configurator.tabs;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.enums.Visibility;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@PageTitle("Dungeon-Konfiguration")
@CssImport("./views/mainviewtabs/configurator/charStats-view.css")

public class DungeonConfigurationTab extends VerticalLayout {
    VerticalLayout initFieldLayout;
    VerticalLayout permissionLayout;
    ArrayList<User> users;
    TextField titleField;
    TextField playerCountField;
    Visibility visibility;

    private ConfiguratorServiceI configuratorServiceI;

    public DungeonConfigurationTab(@Autowired ConfiguratorServiceI AConfiguratorServiceI) {
        this.configuratorServiceI = AConfiguratorServiceI;
        initFieldLayout = new VerticalLayout();
        permissionLayout = new VerticalLayout();
        users = new ArrayList<>();
        titleField = new TextField("Name des Dungeons");
        playerCountField = new TextField("Maximale Anzahl Spieler");

        initField();
        permissionList();

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setPrimaryStyle("minWidth", "550px");
        splitLayout.setSecondaryStyle("minWidth", "400px");

        splitLayout.addToPrimary(initFieldLayout);
        splitLayout.addToSecondary(permissionLayout);

        splitLayout.setSizeFull();
        add(splitLayout);
    }

    private void initField() {
        H1 title = new H1("Dungeon-Konfiguration");

        Details hint = new Details("Info",
                new Text("Eine gute Dungeonbeschreibung hilft den Spielern sich für dein\n"
                        + "Dungeon zu entscheiden. Die Dungeonbeschreibung ist oft der\n"
                        + "erste Eindruck!"));

        if(configuratorServiceI.getDungeon().getDungeonName() == null)
            titleField.setValue("Neuer Dungeon");
        else
            titleField.setValue(configuratorServiceI.getDungeon().getDungeonName());

        titleField.addValueChangeListener(e->{
            configuratorServiceI.getDungeon().setDungeonName(titleField.getValue());
            configuratorServiceI.saveDungeon();
        });
        titleField.setWidth("400px");
        //titleField.setValue(titleField.getValue());

        playerCountField.setValue("30");
        playerCountField.setWidth("170px");

        RadioButtonGroup<String> viewRadioButton = new RadioButtonGroup<>();
        viewRadioButton.setLabel("Sichtbarkeit");
        viewRadioButton.setItems("Öffentlich", "Privat", "In Konfiguration");
        viewRadioButton.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        viewRadioButton.setValue("Öffentlich");

        TextArea dungeonDescription = new TextArea("Dungeonbeschreibung");
        dungeonDescription.setWidth("500px");


        // create Dungeon Button
//        Button createDungeonButton = new Button("Erstelle Button");
//        createDungeonButton.setWidth("300px");
//        createDungeonButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//
//        createDungeonButton.addClickListener(e -> {
//            configuratorService.createDungeon("test", VaadinSession.getCurrent().getAttribute(User.class), Long.parseLong(playerCountField.getValue()), getVisibility(viewRadioButton.getValue()));
//        });
        //initFieldLayout.add(title, hint, titleField, playerCountField, viewRadioButton, dungeonDescription, createDungeonButton);
        initFieldLayout.add(title, titleField, playerCountField, viewRadioButton, hint, dungeonDescription);

    }

    private void permissionList() {

        //Beispieldaten
        User testUser = new User();
        testUser.setName("DungeonDestroyer");

        User testUser2 = new User();
        testUser2.setName("DungeonKiller");

        users.add(testUser);
        users.add(testUser2);

        H2 title = new H2("Spielberechtigung");
        Text permissionText = new Text("Hier kannst du schon im Voraus Spielern eine Berechtigung geben, deinen Dungeon zu spielen.");

        Grid<User> grid = new Grid<>();

        grid.setItems(users);
//        Column<User> nameColumn = grid.addColumn(User::getUsername)
//                .setHeader("Benutzername");


        TextField roleNameField = new TextField();
        TextField descriptionField = new TextField();

        roleNameField.getElement()
                .setAttribute("focus-target", "");
        descriptionField.getElement()
                .setAttribute("focus-target", "");

        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);

        HorizontalLayout buttonView = new HorizontalLayout();
        buttonView.setVerticalComponentAlignment(Alignment.END);

        Button addB = new Button("Hinzufügen");
        Button deleteB = new Button("Löschen");

        addB.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteB.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        buttonView.addAndExpand(addB, deleteB);
        permissionLayout.setSizeFull();
        permissionLayout.add(title, permissionText, grid, buttonView);

    }

    private Visibility getVisibility(String value) {
        if (value.equals("Öffentlich")) {
            visibility = Visibility.PUBLIC;
        } else if (value.equals("Privat")) {
            visibility = Visibility.PRIVATE;
        } else {
            visibility = Visibility.IN_CONFIGURATION;
        }

        return visibility;
    }


}





