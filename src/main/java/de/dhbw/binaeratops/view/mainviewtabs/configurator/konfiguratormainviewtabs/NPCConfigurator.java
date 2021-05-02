package de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import de.dhbw.binaeratops.model.entitys.Action;
import de.dhbw.binaeratops.model.entitys.NPC;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs.dialog.ActionOverviewDialog;
import de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs.dialog.NPCDialog;

import java.util.ArrayList;

@PageTitle("Raum")
@CssImport("./views/mainviewtabs/configurator/roomconfigurator-view.css")
public class NPCConfigurator extends VerticalLayout {


    ConfiguratorServiceI configuratorServiceI;

    VerticalLayout items = new VerticalLayout();

    Grid<NPC> grid = new Grid<>(NPC.class);
    Button addNPCButton = new Button("NPC hinzufügen");
    Button editNPCButton = new Button("NPC anpassen");
    Button deleteNPCButton = new Button("NPC entfernen");

    NPCDialog npcDialog;

    private NPC currentNPC;

    public NPCConfigurator(ConfiguratorServiceI configuratorServiceI) {
        this.configuratorServiceI = configuratorServiceI;
        initRoom();
        addClickListener();
        add(new H1("NPCs"), items);
    }

    private void initRoom() {
        createGrid();

        items.add(new HorizontalLayout(addNPCButton, editNPCButton, deleteNPCButton));
    }

    private void addClickListener () {
        addNPCButton.addClickListener(e -> {
            currentNPC = new NPC();
            NPCDialog dialog = createNPCDialog();
            dialog.open();
        });

        editNPCButton.addClickListener(e -> {
            NPC[] selectedItems = grid.getSelectedItems().toArray(NPC[]::new);
            if (selectedItems.length >= 1) {
                currentNPC = selectedItems[0];
                npcDialog = createNPCDialog();
                npcDialog.fillDialog(currentNPC);
                npcDialog.open();
            } else {
                Notification.show("Bitte wählen sie einen Gegenstand aus!");
            }
        });

        deleteNPCButton.addClickListener(e -> {
            NPC[] selectedItems = grid.getSelectedItems().toArray(NPC[]::new);
            if (selectedItems.length >= 1) {
                currentNPC = selectedItems[0];
                configuratorServiceI.deleteNPC(currentNPC);
                refreshGrid();
            } else {
                Notification.show("Bitte wählen sie einen Gegenstand aus!");
            }
        });
    }

    private NPCDialog createNPCDialog() {
        npcDialog = new NPCDialog(configuratorServiceI, currentNPC, grid);
        return npcDialog;
    }

    private ActionOverviewDialog createActionDialog() {
        return new ActionOverviewDialog(configuratorServiceI, (ArrayList<Action>) currentNPC.getActions(), currentNPC, grid);
    }

    private void createGrid() {
        grid.setItems(configuratorServiceI.getAllNPCs());

        grid.removeAllColumns();
        Grid.Column<NPC> nameColumn = grid.addColumn(NPC::getNpcName).setHeader("Name");
        Grid.Column<NPC> raceColumn = grid.addColumn(npc -> npc.getRace().getRaceName()).setHeader("Rasse");
        Grid.Column<NPC> descriptionColumn = grid.addColumn(NPC::getDescription).setHeader("Beschreibung");

        HeaderRow filterRow = grid.appendHeaderRow();

        TextField nameField = new TextField();
        TextField raceField = new TextField();
        TextField descriptionField = new TextField();

        nameField.addValueChangeListener(e -> {
            //TODO: Service.findByName
        });
        raceField.addValueChangeListener(e -> {
            //TODO: Service.findBySize
        });
        descriptionField.addValueChangeListener(e -> {
            //TODO: Service.findByDescription
        });

        filterRow.getCell(nameColumn).setComponent(nameField);
        filterRow.getCell(descriptionColumn).setComponent(raceField);
        filterRow.getCell(raceColumn).setComponent(descriptionField);

        nameField.setSizeFull();
        nameField.setPlaceholder("Filter");
        nameField.getElement().setAttribute("focus-target", "");
        raceField.setSizeFull();
        raceField.setPlaceholder("Filter");
        raceField.getElement().setAttribute("focus-target", "");
        descriptionField.setSizeFull();
        descriptionField.setPlaceholder("Filter");
        descriptionField.getElement().setAttribute("focus-target", "");

        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);

        this.setWidthFull();
        grid.setWidth("100%");

        items.add(grid);
    }

    private void refreshGrid() {
        grid.setItems(configuratorServiceI.getAllNPCs());
    }
}
