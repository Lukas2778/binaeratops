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
import de.dhbw.binaeratops.model.entitys.Race;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs.dialog.ActionOverviewDialog;
import de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs.dialog.NPCDialog;

import java.util.ArrayList;

@PageTitle("Raum")
@CssImport("./views/mainviewtabs/configurator/roomconfigurator-view.css")
public class NPCConfigurator extends VerticalLayout {


    ConfiguratorServiceI configuratorServiceI;

    VerticalLayout items = new VerticalLayout();
    ArrayList<NPC> npcList = new ArrayList<>();
    ArrayList<Race> raceList = new ArrayList<>();

    Grid<NPC> grid = new Grid<>(NPC.class);
    Button addNPCButton = new Button("NPC hinzufügen");
    Button editNPCButton = new Button("NPC anpassen");
    Button editActionButton = new Button("Befehle anpassen");
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
        fillNPCList();
        fillRaceList();
        createGrid();

        items.add(new HorizontalLayout(addNPCButton, editNPCButton, editActionButton, deleteNPCButton));
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

        editActionButton.addClickListener(e -> {
            NPC[] selectedItems = grid.getSelectedItems().toArray(NPC[]::new);
            if (selectedItems.length >= 1) {
                currentNPC = selectedItems[0];
                ActionOverviewDialog actionOverviewDialog = createActionDialog();
                actionOverviewDialog.open();
            } else {
                Notification.show("Bitte wählen sie einen Gegenstand aus!");
            }
        });

        deleteNPCButton.addClickListener(e -> {
            NPC[] selectedItems = grid.getSelectedItems().toArray(NPC[]::new);
            if (selectedItems.length >= 1) {
                currentNPC = selectedItems[0];
                npcList.remove(currentNPC);
                refreshGrid();
            } else {
                Notification.show("Bitte wählen sie einen Gegenstand aus!");
            }
        });
    }

    private NPCDialog createNPCDialog() {
        npcDialog = new NPCDialog(configuratorServiceI,npcList, raceList, currentNPC, grid);
        return npcDialog;
    }

    private ActionOverviewDialog createActionDialog() {
        return new ActionOverviewDialog((ArrayList<Action>) currentNPC.getActions(), npcList, currentNPC, grid);
    }

    private void createGrid() {
        grid.setItems(npcList);

        grid.removeAllColumns();
        Grid.Column<NPC> nameColumn = grid.addColumn(NPC::getNpcName).setHeader("Name");
        Grid.Column<NPC> raceColumn = grid.addColumn(npc -> npc.getRace().getRaceName()).setHeader("Rasse");
        Grid.Column<NPC> descriptionColumn = grid.addColumn(NPC::getDescription).setHeader("Beschreibung");
        Grid.Column<NPC> actionColumn = grid.addColumn(npc -> {
            StringBuilder sb = new StringBuilder();
            npc.getActions().forEach(action -> {
                sb.append(action.getName());
                sb.append("; ");
            });
            return sb.toString();
        }).setHeader("Befehle");

        HeaderRow filterRow = grid.appendHeaderRow();

        TextField nameField = new TextField();
        TextField raceField = new TextField();
        TextField descriptionField = new TextField();
        TextField actionField = new TextField();

        nameField.addValueChangeListener(e -> {
            //TODO: Service.findByName
        });
        raceField.addValueChangeListener(e -> {
            //TODO: Service.findBySize
        });
        descriptionField.addValueChangeListener(e -> {
            //TODO: Service.findByDescription
        });
        actionField.addValueChangeListener(e -> {
            //TODO
        });

        filterRow.getCell(nameColumn).setComponent(nameField);
        filterRow.getCell(descriptionColumn).setComponent(raceField);
        filterRow.getCell(raceColumn).setComponent(descriptionField);
        filterRow.getCell(actionColumn).setComponent(actionField);

        nameField.setSizeFull();
        nameField.setPlaceholder("Filter");
        nameField.getElement().setAttribute("focus-target", "");
        raceField.setSizeFull();
        raceField.setPlaceholder("Filter");
        raceField.getElement().setAttribute("focus-target", "");
        descriptionField.setSizeFull();
        descriptionField.setPlaceholder("Filter");
        descriptionField.getElement().setAttribute("focus-target", "");
        actionField.setSizeFull();
        actionField.setPlaceholder("Filter");
        actionField.getElement().setAttribute("focus-target", "");

        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);

        this.setWidthFull();
        grid.setWidth("100%");

        items.add(grid);
    }

    private void refreshGrid() {
        grid.setItems(npcList);
    }

    private void fillNPCList() {
        for (int i = 0; i < 20; i++) {
            NPC npc = new NPC(String.valueOf(i), new Race(String.valueOf(i), String.valueOf(i)), String.valueOf(i));
            npc.setNpcId((long) i);
            npcList.add(npc);
        }
    }

    private void fillRaceList () {
        for (int i = 0; i < 20; i++) {
            Race race = new Race(String.valueOf(i), String.valueOf(i));
            race.setRaceId((long) i);
            raceList.add(race);
        }
    }
}
