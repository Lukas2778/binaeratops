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
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.Permission;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.enums.Visibility;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import de.dhbw.binaeratops.view.configurator.tabs.dialog.PermissionDialog;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Tab-Oberfläche für die Komponente "General" des Konfigurators.
 * <p>
 * Diese Ansicht stellt alle View-Komponenten für die Konfiguration der Dungeoneigenschaften bereit.
 * <p>
 * Dafür sendet sie die Benutzerangaben direkt an den entsprechenden Service.
 *
 * @author Pedro Treuer, Nicolas Haug
 */
@CssImport("./views/mainviewtabs/configurator/charStats-view.css")
public class DungeonConfigurationTab extends VerticalLayout implements HasDynamicTitle {

    private final ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());

    VerticalLayout initFieldLayout;
    VerticalLayout permissionLayout;
    ArrayList<User> userList;
    TextField titleField;
    TextField playerCountField;
    Visibility visibility;
    String visibilityValue;
    User currentUser;

    Grid<Permission> grid = new Grid<>();

    private ConfiguratorServiceI configuratorService;
    // TODO Kommentare schreiben
    public DungeonConfigurationTab(@Autowired ConfiguratorServiceI AConfiguratorServiceI) {
        this.configuratorService = AConfiguratorServiceI;
        initFieldLayout = new VerticalLayout();
        permissionLayout = new VerticalLayout();
        userList = new ArrayList<>();
        titleField = new TextField(res.getString("view.configurator.dungeon.field.dungeonname"));

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
        H1 title = new H1(res.getString("view.configurator.dungeon.h1.titledungeon"));

        if (configuratorService.getDungeon().getDungeonName() == null)
            titleField.setValue(res.getString("view.configurator.dungeon.field.valuenew.dungeonname"));
        else
            titleField.setValue(configuratorService.getDungeon().getDungeonName());

        titleField.addValueChangeListener(e -> {
            configuratorService.getDungeon().setDungeonName(titleField.getValue());
            configuratorService.saveDungeon();
        });

        titleField.setWidth("200px");

        NumberField playerCountField = new NumberField(res.getString("view.configurator.dungeon.field.maxplayercount"));
        playerCountField.setHasControls(true);
        playerCountField.setMin(2);

        playerCountField.addValueChangeListener(e -> {
            configuratorService.getDungeon().setPlayerMaxSize(playerCountField.getValue().longValue());
            configuratorService.saveDungeon();
        });

        playerCountField.setWidth("200px");

        if (configuratorService.getDungeon().getPlayerMaxSize() == null)
            playerCountField.setValue(30.0);
        else
            playerCountField.setValue((double) configuratorService.getDungeon().getPlayerMaxSize());

        RadioButtonGroup<String> viewRadioButton = new RadioButtonGroup<>();
        viewRadioButton.setLabel(res.getString("view.configurator.dungeon.radiobutton.visibility"));
        viewRadioButton.setItems(res.getString("view.configurator.dungeon.radiobutton.public"),
                res.getString("view.configurator.dungeon.radiobutton.private"),
                res.getString("view.configurator.dungeon.radiobutton.in.configuration"));
        viewRadioButton.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

        //Zuerst wird überprüft, ob min. eine Rasse erstellt wurde, dann ob min. eine Rolle erstellt wurde und dann ob der Startraum festgelegt wurde.
        viewRadioButton.addValueChangeListener(e -> {
            if(viewRadioButton.getValue() == res.getString("view.configurator.dungeon.radiobutton.public")){

                if(configuratorService.getDungeon().getRaces().size() >0){

                    if(configuratorService.getDungeon().getRoles().size()>0){

                        if(configuratorService.getRoom(configuratorService.getDungeon().getStartRoomId())!= null){
                            configuratorService.getDungeon().setDungeonVisibility(getVisibility(viewRadioButton.getValue()));
                            configuratorService.saveDungeon();
                        }else{
                            Notification.show(res.getString("view.configurator.dungeon.notification.select.startroom"));
                            viewRadioButton.setValue(res.getString("view.configurator.dungeon.radiobutton.in.configuration"));
                        }
                    }else{
                        Notification.show(res.getString("view.configurator.dungeon.notification.need.role"));
                        viewRadioButton.setValue(res.getString("view.configurator.dungeon.radiobutton.in.configuration"));
                    }

                }else{
                    Notification.show(res.getString("view.configurator.dungeon.notification.need.race"));
                    viewRadioButton.setValue(res.getString("view.configurator.dungeon.radiobutton.in.configuration"));
                }

            }else if( viewRadioButton.getValue() == res.getString("view.configurator.dungeon.radiobutton.private")){

                if(configuratorService.getDungeon().getRaces().size() >0){

                    if(configuratorService.getDungeon().getRoles().size()>0){

                        if(configuratorService.getRoom(configuratorService.getDungeon().getStartRoomId())!= null){
                            configuratorService.getDungeon().setDungeonVisibility(getVisibility(viewRadioButton.getValue()));
                            configuratorService.saveDungeon();
                        }else{
                            Notification.show(res.getString("view.configurator.dungeon.notification.select.startroom"));
                            viewRadioButton.setValue(res.getString("view.configurator.dungeon.radiobutton.in.configuration"));
                        }
                    }else{
                        Notification.show(res.getString("view.configurator.dungeon.notification.need.role"));
                        viewRadioButton.setValue(res.getString("view.configurator.dungeon.radiobutton.in.configuration"));
                    }

                }else{
                    Notification.show(res.getString("view.configurator.dungeon.notification.need.race"));
                    viewRadioButton.setValue(res.getString("view.configurator.dungeon.radiobutton.in.configuration"));
                }

            } else{
                configuratorService.getDungeon().setDungeonVisibility(getVisibility(viewRadioButton.getValue()));
                configuratorService.saveDungeon();
            }
        });

        if (configuratorService.getDungeon().getDungeonVisibility() == null)
            viewRadioButton.setValue(res.getString("view.configurator.dungeon.radiobutton.in.configuration"));
        else
            viewRadioButton.setValue(getVisibility(configuratorService.getDungeon().getDungeonVisibility()));

        Details commandSymbolDefinition = new Details(res.getString("view.configurator.dungeon.details.cmdsymbol.title"),
                new Text(res.getString("view.configurator.dungeon.details.cmdsymbol.info")));

        TextField commandSymbolField = new TextField(res.getString("view.configurator.dungeon.field.cmdsymbol"));
        commandSymbolField.setMinLength(1);
        commandSymbolField.setMaxLength(1);
        commandSymbolField.setWidth("200px");

        commandSymbolField.addValueChangeListener(e -> {
            if (!commandSymbolField.isInvalid()) {
                configuratorService.setCommandSymbol(commandSymbolField.getValue().charAt(0));
            }
        });
        commandSymbolField.setValue(String.valueOf(configuratorService.getCommandSymbol()));


        Details hint = new Details(res.getString("view.configurator.dungeon.details.info.description.title"),
                new Text(res.getString("view.configurator.dungeon.details.info.description.info")));

        TextArea dungeonDescription = new TextArea(res.getString("view.configurator.dungeon.area.dungeondescription"));
        dungeonDescription.setWidth("500px");

        dungeonDescription.addValueChangeListener(e -> {
            configuratorService.getDungeon().setDescription(dungeonDescription.getValue());
            configuratorService.saveDungeon();

        });

        if (configuratorService.getDungeon().getDescription() != null)
            dungeonDescription.setValue(configuratorService.getDungeon().getDescription());

        initFieldLayout.add(title, titleField, playerCountField, viewRadioButton, commandSymbolDefinition, commandSymbolField, hint, dungeonDescription);

    }

    private void permissionList() {

        H2 title = new H2(res.getString("view.configurator.dungeon.h1.titlepermission"));

        Text permissionText = new Text(res.getString("view.configurator.dungeon.text.permission"));

        Details hint = new Details(res.getString("view.configurator.dungeon.details.info.permission.title"),
                                   new Text(res.getString("view.configurator.dungeon.details.info.permission.info")));


        if(configuratorService.getDungeon().getAllowedUsers() != null)
        grid.setItems(configuratorService.getDungeon().getAllowedUsers());

        TextField roleNameField = new TextField();
        TextField descriptionField = new TextField();

        Column<Permission> nameColumn = grid.addColumn(user -> user.getUser().getName())
                .setHeader(res.getString("view.configurator.dungeon.grid.column.approved.players"));

        roleNameField.getElement()
                .setAttribute("focus-target", "");
        descriptionField.getElement()
                .setAttribute("focus-target", "");

        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);

        HorizontalLayout buttonView = new HorizontalLayout();
        buttonView.setVerticalComponentAlignment(Alignment.END);

        Button addB = new Button(res.getString("view.configurator.dungeon.button.addpermission"));
        Button deleteB = new Button(res.getString("view.configurator.dungeon.button.deletepermission"));

        addB.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteB.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        addB.addClickListener(e -> {
            currentUser = new User();
            PermissionDialog dialog = new PermissionDialog(userList, currentUser, grid, configuratorService);
            dialog.open();
        });

        deleteB.addClickListener(e -> {
            Permission[] selectedUser = grid.getSelectedItems()
                    .toArray(Permission[]::new);
            if (selectedUser.length >= 1) {
                Permission selectedPerm = selectedUser[0];
                currentUser = selectedPerm.getUser();
                configuratorService.removePermission(currentUser);
                // configuratorService.getDungeon().removePermission(configuratorService.getDungeon().getPermission(configuratorService.getDungeon(), currentUser));

                refreshGrid();

            }
        });

        buttonView.addAndExpand(addB, deleteB);
        permissionLayout.setSizeFull();
        permissionLayout.add(title, permissionText, hint,grid, buttonView);

    }

    private Visibility getVisibility(String value) {
        if (value.equals(res.getString("view.configurator.dungeon.radiobutton.public"))) {
            visibility = Visibility.PUBLIC;
        } else if (value.equals(res.getString("view.configurator.dungeon.radiobutton.private"))) {
            visibility = Visibility.PRIVATE;
        } else {
            visibility = Visibility.IN_CONFIGURATION;
        }

        return visibility;
    }

    private String getVisibility(Visibility vis) {
        if (vis == Visibility.PUBLIC) {
            visibilityValue = res.getString("view.configurator.dungeon.radiobutton.public");
        } else if (vis == Visibility.PRIVATE) {
            visibilityValue = res.getString("view.configurator.dungeon.radiobutton.private");
        } else {
            visibilityValue = res.getString("view.configurator.dungeon.radiobutton.in.configuration");
        }

        return visibilityValue;
    }

    private void refreshGrid() {
        grid.setItems(configuratorService.getAllowedPermissions());
    }

    @Override
    public String getPageTitle() {
        return res.getString("view.configurator.dungeon.pagetitle");
    }
}