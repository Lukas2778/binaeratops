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
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.Race;
import de.dhbw.binaeratops.model.entitys.Role;
import de.dhbw.binaeratops.model.enums.Visibility;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import de.dhbw.binaeratops.view.configurator.tabs.dialog.RaceDialog;
import de.dhbw.binaeratops.view.configurator.tabs.dialog.RoleDialog;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Tab-Oberfläche für die Komponente "Charaktereigenschaften" des Konfigurators.
 * <p>
 * Diese Ansicht stellt alle View-Komponenten für die Konfiguration der Charaktereigenschaften bereit.
 * <p>
 * Dafür sendet sie die Benutzerangaben direkt an den entsprechenden Service.
 *
 * @author Pedro Treuer, Timon Gartung, Nicolas Haug
 */
@CssImport("./views/mainviewtabs/configurator/charStats-view.css")
public class CharacterConfigurationTab extends VerticalLayout implements HasDynamicTitle {

    private final ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());

    VerticalLayout initFeldLayout = new VerticalLayout();
    VerticalLayout roleListLayout = new VerticalLayout();
    VerticalLayout raceListLayout = new VerticalLayout();

    ArrayList<Role> roleArrayList = new ArrayList<>();
    ArrayList<Race> raceArrayList = new ArrayList<>();

    Button addB = new Button(res.getString("view.configurator.character.add"));
    Button deleteB = new Button(res.getString("view.configurator.character.delete"));

    Button addRaceButton = new Button(res.getString("view.configurator.character.add"));
    Button deleteRaceButton = new Button(res.getString("view.configurator.character.delete"));

    Grid<Role> grid = new Grid<>();
    Grid<Race> raceGrid = new Grid<>();

    RoleDialog roleDialog;
    RaceDialog raceDialog;

    private Role currentRole;
    private Race currentRace;

    private ConfiguratorServiceI configuratorService;
    // TODO Kommentare schreiben
    public CharacterConfigurationTab(@Autowired ConfiguratorServiceI AConfiguratorServiceI) {
        configuratorService = AConfiguratorServiceI;

        initFeld();
        roleList();
        raceList();

        SplitLayout splitLayout = new SplitLayout();
        SplitLayout innerLayout = new SplitLayout();

        splitLayout.setPrimaryStyle("minWidth", "450px");
        splitLayout.setSecondaryStyle("minWidth", "1000px");

        innerLayout.setPrimaryStyle("minWidth", "400px");
        innerLayout.setPrimaryStyle("minWidth", "500px");
        innerLayout.setSecondaryStyle("minWidth", "400px");
        innerLayout.setSecondaryStyle("minWidth", "500px");
        innerLayout.addToPrimary(roleListLayout);
        innerLayout.addToSecondary(raceListLayout);

        splitLayout.addToPrimary(initFeldLayout);
        splitLayout.addToSecondary(innerLayout);

        splitLayout.setSizeFull();
        add(splitLayout);
    }

    private void initFeld() {
        H1 title = new H1(res.getString("view.configurator.character.headline"));

        Details hint = new Details(res.getString("view.configurator.character.details.title"),
                new Text(res.getString("view.configurator.character.details.info")));
        //hint.addOpenedChangeListener(e -> Notification.show(e.isOpened() ? "Opened" : "Closed"));

        NumberField lifepointsField = new NumberField(res.getString("view.configurator.character.numberfield.lifepoints"));
        lifepointsField.setHasControls(true);
        lifepointsField.setWidth("200px");
        lifepointsField.setMin(1);

        if(configuratorService.getDungeon().getStandardAvatarLifepoints() != null)
            lifepointsField.setValue((double) configuratorService.getDungeon().getStandardAvatarLifepoints());
        else{
            lifepointsField.setValue(20.0);
            configuratorService.getDungeon().setStandardAvatarLifepoints(lifepointsField.getValue().longValue());
        }


        lifepointsField.addValueChangeListener(e-> {
            configuratorService.getDungeon().setStandardAvatarLifepoints(lifepointsField.getValue().longValue());
            configuratorService.saveDungeon();
        });

        NumberField inventorySize = new NumberField(res.getString("view.configurator.character.numberfield"));
        inventorySize.setHasControls(true);
        inventorySize.setMin(2);
        inventorySize.setWidth("200px");
        //inventorySize.setMax(100);

        if(configuratorService.getDungeon().getDefaultInventoryCapacity() != null)
            inventorySize.setValue((double) configuratorService.getDungeon().getDefaultInventoryCapacity());
        else{
            inventorySize.setValue(50.0);
            configuratorService.getDungeon().setDefaultInventoryCapacity(inventorySize.getValue().longValue());
        }


        inventorySize.addValueChangeListener(e-> {
            configuratorService.getDungeon().setDefaultInventoryCapacity(inventorySize.getValue().longValue());
            configuratorService.saveDungeon();
        });

//        RadioButtonGroup<String> genderRadioButton = new RadioButtonGroup<>();
//        genderRadioButton.setLabel(res.getString("view.configurator.character.radiobutton.label"));
//        genderRadioButton.setItems(res.getString("view.configurator.character.radiobutton.activate.gender"),
//                res.getString("view.configurator.character.radiobutton.deactivate.gender"));
//        genderRadioButton.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
//        genderRadioButton.setValue(res.getString("view.configurator.character.radiobutton.activate.gender"));

        initFeldLayout.add(title, hint,lifepointsField ,inventorySize);

    }

    private void roleList() {
        addRoleClickListener();
        deleteRoleClickListener();
        H2 title = new H2(res.getString("view.configurator.character.headline.roles"));

        if(configuratorService.getDungeon().getRoles() != null)
            grid.setItems(configuratorService.getAllRoles());


        Column<Role> nameColumn = grid.addColumn(Role::getRoleName).setHeader(res.getString("view.configurator.character.role.grid.name"));

        Column<Role> descriptionColumn = grid.addColumn(Role::getDescription).setHeader(res.getString("view.configurator.character.role.grid.description"));

        Column<Role> lifepointsBonusColumn = grid.addColumn(Role::getLifepointsBonus).setHeader(res.getString("view.configurator.character.role.grid.lifepointsbonus"));



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
        roleListLayout.add(title, grid, buttonView);

    }

    private void raceList() {
        addRaceClickListener();
        deleteRaceClickListener();

        H2 title = new H2(res.getString("view.configurator.character.headline.races"));

        if(configuratorService.getDungeon().getRaces() != null)
            raceGrid.setItems(configuratorService.getAllRace());


        Column<Race> nameColumn = raceGrid.addColumn(Race::getRaceName)
                .setHeader(res.getString("view.configurator.character.race.grid.name"));
        Column<Race> descriptionColumn = raceGrid.addColumn(Race::getDescription).setHeader(res.getString("view.configurator.character.race.grid.description"));

        Column<Race> lifepointsBonusColumn = raceGrid.addColumn(Race::getLifepointsBonus).setHeader(res.getString("view.configurator.character.role.grid.lifepointsbonus"));

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
        raceListLayout.add(title, raceGrid, buttonView);

    }

    private void refreshRoleGrid() {
        grid.setItems(configuratorService.getAllRoles());
    }

    private void refreshRaceGrid() {
        raceGrid.setItems(configuratorService.getAllRace());
    }

    private RoleDialog createRoleDialog() {
        roleDialog = new RoleDialog(currentRole, grid, configuratorService);
        return roleDialog;
    }

    private RaceDialog createRaceDialog() {
        raceDialog = new RaceDialog(currentRace, raceGrid, configuratorService);
        return raceDialog;
    }

    private void addRoleClickListener() {
        addB.addClickListener(e -> {
            currentRole = new Role();
            RoleDialog dialog = createRoleDialog();
            dialog.open();
        });
    }

    private void addRaceClickListener() {
        addRaceButton.addClickListener(e -> {
            currentRace = new Race();
            RaceDialog dialog = createRaceDialog();
            dialog.open();
        });
    }

    private void deleteRoleClickListener() {
        deleteB.addClickListener(e -> {
            try{
                if(configuratorService.getDungeon().getRoles().size() == 1 && configuratorService.getDungeon().getDungeonVisibility() != Visibility.IN_CONFIGURATION){
                    Notification.show(res.getString("view.configurator.character.notification.in.config"));
                }else{
                    Role[] selectedRole = grid.getSelectedItems()
                            .toArray(Role[]::new);
                    if (selectedRole.length >= 1) {
                        currentRole = selectedRole[0];
                        configuratorService.removeRole(currentRole);
                        refreshRoleGrid();
                    }

                }
            }catch(Exception es){
                Notification.show(res.getString("view.configurator.character.notification.inconfig.role"));
            }




        });
    }

    private void deleteRaceClickListener() {
        deleteRaceButton.addClickListener(e -> {
            try{
                if(configuratorService.getDungeon().getRaces().size() == 1 && configuratorService.getDungeon().getDungeonVisibility() != Visibility.IN_CONFIGURATION){
                    Notification.show(res.getString("view.configurator.character.notification.in.config.race"));
                }else
                {
                    Race[] selectedRace = raceGrid.getSelectedItems()
                            .toArray(Race[]::new);
                    if ( selectedRace.length >= 1 )
                    {
                        currentRace = selectedRace[0];
                        configuratorService.removeRace(currentRace);
                        refreshRaceGrid();

                    }
                }
            }catch(Exception es){
                Notification.show(res.getString("view.configurator.character.notification.in.config.race2"));
            }

        });
    }


    @Override
    public String getPageTitle() {
        return res.getString("view.configurator.character.pagetitle");
    }
}





