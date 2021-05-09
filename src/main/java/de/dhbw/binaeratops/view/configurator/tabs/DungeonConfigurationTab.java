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
import java.sql.SQLOutput;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@PageTitle("Dungeon-Konfiguration")
@CssImport("./views/mainviewtabs/configurator/charStats-view.css")

public class DungeonConfigurationTab extends VerticalLayout {

    private final ResourceBundle res = ResourceBundle.getBundle("language");

    VerticalLayout initFieldLayout;
    VerticalLayout permissionLayout;
    ArrayList<User> users;
    TextField titleField;
    TextField playerCountField;
    Visibility visibility;
    String visibilityValue;

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

        playerCountField.addValueChangeListener(e-> {
            configuratorServiceI.getDungeon().setPlayerMaxSize(Long.parseLong(playerCountField.getValue()));
            configuratorServiceI.saveDungeon();
        });

        playerCountField.setWidth("170px");

        if(configuratorServiceI.getDungeon().getPlayerMaxSize() == null)
            playerCountField.setValue("30");
        else
            playerCountField.setValue(String.valueOf(configuratorServiceI.getDungeon().getPlayerMaxSize()));

        RadioButtonGroup<String> viewRadioButton = new RadioButtonGroup<>();
        viewRadioButton.setLabel("Sichtbarkeit");
        viewRadioButton.setItems("Öffentlich", "Privat", "In Konfiguration");
        viewRadioButton.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

        viewRadioButton.addValueChangeListener(e-> {
            configuratorServiceI.getDungeon().setDungeonVisibility(getVisibility(viewRadioButton.getValue()));
            configuratorServiceI.saveDungeon();
        });

        Details commandSymbolDefinition = new Details("Was sind Befehlszeichen",
                new Text("Um Befehle im Dungeon ausführen zu können muss man vor diese ein bestimmtes Zeichen setzen, " +
                        "das sogenannte Befehlszeichen. Falls dir das / nicht gefällt kannst du es hier anpassen. " +
                        "Es ist ein beliebiges Zeichen erlaubt, aber Buchstaben und Zahlen sind nicht zu empfehlen."));

        TextField commandSymbolField = new TextField("Befehlszeichen");
        commandSymbolField.setMinLength(1);
        commandSymbolField.setMaxLength(1);
        commandSymbolField.setWidth("50px");

        commandSymbolField.addValueChangeListener(e -> {
            if (!commandSymbolField.isInvalid()) {
                configuratorServiceI.setCommandSymbol(commandSymbolField.getValue().charAt(0));
            }
        });
        commandSymbolField.setValue(String.valueOf(configuratorServiceI.getCommandSymbol()));

        if(configuratorServiceI.getDungeon().getDungeonVisibility() == null)
            viewRadioButton.setValue("Öffentlich");
        else
            viewRadioButton.setValue(getVisibility(configuratorServiceI.getDungeon().getDungeonVisibility()) );

        Details hint = new Details("Info",
                                   new Text("Eine gute Dungeonbeschreibung hilft den Spielern sich für dein\n"
                                                    + "Dungeon zu entscheiden. Die Dungeonbeschreibung ist oft der\n"
                                                    + "erste Eindruck!"));

        TextArea dungeonDescription = new TextArea("Dungeonbeschreibung");
        dungeonDescription.setWidth("500px");

        if(configuratorServiceI.getDungeon().getDescription() != null)
        dungeonDescription.setValue(configuratorServiceI.getDungeon().getDescription());

        dungeonDescription.addValueChangeListener(e-> {
            configuratorServiceI.getDungeon().setDescription(dungeonDescription.getValue());
            configuratorServiceI.saveDungeon();

        });
        initFieldLayout.add(title, titleField, playerCountField, viewRadioButton, commandSymbolField, hint, dungeonDescription);

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

    private String getVisibility(Visibility vis) {
        if (vis == Visibility.PUBLIC) {
            visibilityValue = "Öffentlich";
        } else if (vis == Visibility.PRIVATE) {
            visibilityValue = "Privat";
        } else {
            visibilityValue = "In Konfiguration";
        }

        return visibilityValue;
    }


}





