package de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import de.dhbw.binaeratops.model.entitys.NPC;
import de.dhbw.binaeratops.model.entitys.Race;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;

public class NPCDialog extends Dialog {

    ConfiguratorServiceI configuratorService;

    private TextField currentName;
    private ComboBox<Race> currentRace;
    private TextField currentDescription;

    private NPC currentNPC;
    Grid<NPC> grid;

    public NPCDialog() {}

    public NPCDialog(ConfiguratorServiceI AConfiguratorServiceI, NPC currentNPC, Grid<NPC> grid) {
        configuratorService = AConfiguratorServiceI;
        this.currentNPC = currentNPC;
        this.grid = grid;
        init();
    }

    private void init() {
        currentName = new TextField("Name");
        currentRace = new ComboBox<>("Rasse");
        currentDescription = new TextField("Beschreibung");
        Button saveDialog = new Button("Speichern");
        Button closeDialog = new Button("Abbrechen");

        currentRace.setItemLabelGenerator(Race::getRaceName);

        this.add(new VerticalLayout(currentName, currentDescription, currentRace, new HorizontalLayout(saveDialog, closeDialog)));

        currentRace.setItems(configuratorService.getAllRace());

        saveDialog.addClickListener(e -> {
            if (validate()) {
                currentNPC.setNpcName(currentName.getValue());
                currentNPC.setRace(currentRace.getValue());
                currentNPC.setDescription(currentDescription.getValue());
                if (!configuratorService.getAllNPCs().contains(currentNPC)) {
                    configuratorService.createNPC(currentName.getValue(), currentDescription.getValue(), currentRace.getValue());
                } else {
                    configuratorService.updateNPC(currentNPC);
                }
                refreshGrid();
                this.close();
            } else {
                Notification.show("Bitte kontrolliere deine Eingabe!");
            }
        });
        closeDialog.addClickListener(e -> this.close());
    }

    private boolean validate() {
        if (currentName.isEmpty() || currentRace.isEmpty() || currentDescription.isEmpty()) {
            return false;
        }
        return true;
    }

    public void fillDialog(NPC npc) {
        currentNPC = npc;
        currentName.setValue(npc.getNpcName());
        currentRace.setValue(npc.getRace());
        currentDescription.setValue(npc.getDescription());
    }

    private void refreshGrid() {
        grid.setItems(configuratorService.getAllNPCs());
    }

    public TextField getCurrentName() {
        return currentName;
    }

    public void setCurrentName(TextField currentName) {
        this.currentName = currentName;
    }

    public TextField getCurrentDescription() {
        return currentDescription;
    }

    public ComboBox<Race> getCurrentRace() {
        return currentRace;
    }

    public void setCurrentRace(ComboBox<Race> currentRace) {
        this.currentRace = currentRace;
    }

    public void setCurrentDescription(TextField currentDescription) {
        this.currentDescription = currentDescription;
    }

    public NPC getCurrentNPC() {
        return currentNPC;
    }

    public void setCurrentNPC(NPC currentNPC) {
        this.currentNPC = currentNPC;
    }
}
