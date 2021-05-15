package de.dhbw.binaeratops.view.configurator.tabs;

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
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.NPC;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import de.dhbw.binaeratops.view.configurator.tabs.dialog.NPCDialog;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ResourceBundle;

/**
 * Tab-Oberfläche für die Komponente "NPC" des Konfigurators.
 * <p>
 * Diese Ansicht stellt alle View-Komponenten für die Konfiguration der NPCs bereit.
 * <p>
 * Dafür sendet sie die Benutzerangaben direkt an den entsprechenden Service.
 *
 * @author Pedro Treuer, Timon Gartung, Nicolas Haug, Lars Rösel, Mattias Rall, Lukas Göpel
 */
@PageTitle("Raum")
@CssImport("./views/mainviewtabs/configurator/roomconfigurator-view.css")
public class NPCConfigurationTab extends VerticalLayout implements HasDynamicTitle {

    private final ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());

    ConfiguratorServiceI configuratorService;
    VerticalLayout items = new VerticalLayout();

    Grid<NPC> grid = new Grid<>(NPC.class);
    Button addNPCButton = new Button(res.getString("view.configurator.npc.button.add.npc"));
    Button editNPCButton = new Button(res.getString("view.configurator.npc.button.edit.npc"));
    Button deleteNPCButton = new Button(res.getString("view.configurator.npc.button.remove.npc"));

    NPCDialog npcDialog;
    private NPC currentNPC;

    public NPCConfigurationTab(@Autowired ConfiguratorServiceI configuratorServiceI) {
        configuratorService = configuratorServiceI;
        initRoom();
        addClickListener();
        add(new H1(res.getString("view.configurator.npc.headline")), items);
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
                Notification.show(res.getString("view.configurator.npc.notification.select.npc"));
            }
        });

        deleteNPCButton.addClickListener(e -> {
            NPC[] selectedItems = grid.getSelectedItems().toArray(NPC[]::new);
            if (selectedItems.length >= 1) {
                currentNPC = selectedItems[0];
                configuratorService.deleteNPC(currentNPC);
                refreshGrid();
            } else {
                Notification.show(res.getString("view.configurator.npc.notification.select.npc"));
            }
        });
    }

    private NPCDialog createNPCDialog() {
        npcDialog = new NPCDialog(configuratorService, currentNPC, grid);
        return npcDialog;
    }
    

    private void createGrid() {
        grid.setItems(configuratorService.getAllNPCs());

        grid.removeAllColumns();
        Grid.Column<NPC> nameColumn = grid.addColumn(NPC::getNpcName)
                .setComparator((npc1, npc2) -> npc1.getNpcName()
                        .compareToIgnoreCase(npc2.getNpcName()))
                .setHeader(res.getString("view.configurator.npc.grid.npcname"));
        Grid.Column<NPC> raceColumn = grid.addColumn(npc -> npc.getRace().getRaceName())
                .setComparator((npc1, npc2) -> npc1.getRace().getRaceName()
                        .compareToIgnoreCase(npc2.getRace().getRaceName()))
                .setHeader(res.getString("view.configurator.npc.grid.race"));
        Grid.Column<NPC> descriptionColumn = grid.addColumn(NPC::getDescription)
                .setComparator((npc1, npc2) -> npc1.getDescription()
                        .compareToIgnoreCase(npc2.getDescription()))
                .setHeader(res.getString("view.configurator.npc.grid.description"));

        HeaderRow filterRow = grid.appendHeaderRow();

        TextField nameField = new TextField();
        TextField raceField = new TextField();
        TextField descriptionField = new TextField();

        nameField.addValueChangeListener(e -> {
            //TODO: Service.findByName
        });
        raceField.addValueChangeListener(e -> {
            //TODO: Service.findByRace
        });
        descriptionField.addValueChangeListener(e -> {
            //TODO: Service.findByDescription
        });

        filterRow.getCell(nameColumn).setComponent(nameField);
        filterRow.getCell(descriptionColumn).setComponent(raceField);
        nameField.setSizeFull();
        filterRow.getCell(raceColumn).setComponent(descriptionField);

        nameField.setPlaceholder(res.getString("view.configurator.npc.grid.filter.placehold"));
        nameField.getElement().setAttribute("focus-target", "");
        raceField.setSizeFull();
        raceField.setPlaceholder(res.getString("view.configurator.npc.grid.filter.placehold"));
        raceField.getElement().setAttribute("focus-target", "");
        descriptionField.setSizeFull();
        descriptionField.setPlaceholder(res.getString("view.configurator.npc.grid.filter.placehold"));
        descriptionField.getElement().setAttribute("focus-target", "");

        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);

        this.setWidthFull();
        grid.setWidth("100%");

        items.add(grid);
    }

    private void refreshGrid() {
        grid.setItems(configuratorService.getAllNPCs());
    }

    @Override
    public String getPageTitle() {
        return res.getString("view.configurator.npc.pagetitle");
    }
}
