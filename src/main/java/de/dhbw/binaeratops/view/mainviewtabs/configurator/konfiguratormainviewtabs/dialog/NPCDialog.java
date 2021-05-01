package de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import de.dhbw.binaeratops.model.entitys.NPC;
import de.dhbw.binaeratops.model.entitys.Race;

import java.util.ArrayList;

public class NPCDialog extends Dialog {
    private TextField currentName;
    private ComboBox<Race> currentRace;
    private TextField currentDescription;

    private NPC currentNPC;
    private ArrayList<NPC> npcList;
    private ArrayList<Race> raceList;
    Grid<NPC> grid;

    public NPCDialog() {}

    public NPCDialog(ArrayList<NPC> npcList, ArrayList<Race> raceList, NPC currentNPC, Grid<NPC> grid) {
        this.npcList = npcList;
        this.raceList = raceList;
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

        currentRace.setItems(raceList);

        saveDialog.addClickListener(e -> {
            currentNPC.setNpcName(currentName.getValue());
            currentNPC.setRace(currentRace.getValue());
            currentNPC.setDescription(currentDescription.getValue());
            if (!npcList.contains(currentNPC)) {
                npcList.add(currentNPC);
            }
            refreshGrid();
            this.close();
        });
        closeDialog.addClickListener(e -> this.close());
    }

    public void fillDialog(NPC npc) {
        currentName.setValue(npc.getNpcName());
        currentRace.setValue(npc.getRace());
        currentDescription.setValue(npc.getDescription());
    }

    private void refreshGrid() {
        grid.setItems(npcList);
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
