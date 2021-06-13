package de.dhbw.binaeratops.view.configurator.tabs.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.NPC;
import de.dhbw.binaeratops.model.entitys.Race;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;

import java.util.ResourceBundle;

/**
 * Dialog-Oberfläche für die Komponente "NPC hinzufügen" des Raum-Konfigurators.
 * <p>
 * Diese Ansicht stellt alle View-Komponenten für das Konfiguration eines NPCs bereit.
 * <p>
 * Dafür sendet sie die Benutzerangaben direkt an den entsprechenden Service.
 *
 * @author Pedro Treuer, Timon Gartung, Nicolas Haug, Lars Rösel, Mattias Rall, Lukas Göpel
 */
public class NPCDialog extends Dialog {

    private final ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());

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

    /**
     * Initialisierung des NPCDialogs.
     */
    private void init() {
        currentName = new TextField(res.getString("view.configurator.dialog.npc.field.name"));
        currentRace = new ComboBox<>(res.getString("view.configurator.dialog.npc.combobox.race"));
        currentDescription = new TextField(res.getString("view.configurator.dialog.npc.field.description"));
        Button saveDialog = new Button(res.getString("view.configurator.dialog.npc.button.save"));
        Button closeDialog = new Button(res.getString("view.configurator.dialog.npc.button.cancel"));

        currentRace.setItemLabelGenerator(Race::getRaceName);

        this.add(new VerticalLayout(currentName, currentDescription, currentRace, new HorizontalLayout(saveDialog, closeDialog)));

        currentRace.setItems(configuratorService.getAllRace());

        saveDialog.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
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
                Notification.show(res.getString("view.configurator.dialog.npc.notification.check.input"));
            }
        });
        closeDialog.addClickListener(e -> this.close());
    }

    /**
     * Überprüft die Felder des NPC Dialogs.
     * @return Validiert.
     */
    private boolean validate() {
        if (currentName.isEmpty() || currentRace.isEmpty() || currentDescription.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Werte für den NPC Dialog aus der Datenbank laden.
     * @param npc NPC für den Dialog übergeben.
     */
    public void fillDialog(NPC npc) {
        currentNPC = npc;
        currentName.setValue(npc.getNpcName());
        currentRace.setValue(npc.getRace());
        currentDescription.setValue(npc.getDescription());
    }

    /**
     * NPC Grid aktualisieren.
     */
    private void refreshGrid() {
        grid.setItems(configuratorService.getAllNPCs());
    }

    /**
     * Textfeld NPC Name.
     * @return Textfeld.
     */
    public TextField getCurrentName() {
        return currentName;
    }

    /**
     * Aktuellen NPC Namen setzen.
     * @param currentName Name übergeben.
     */
    public void setCurrentName(TextField currentName) {
        this.currentName = currentName;
    }

    /**
     * NPC Bezeichnung Textfeld erstellen.
     * @return TextField.
     */
    public TextField getCurrentDescription() {
        return currentDescription;
    }

    /**
     * Combobox für die Rasse erstellen.
     * @return ComboBox.
     */
    public ComboBox<Race> getCurrentRace() {
        return currentRace;
    }

    /**
     * Rasse aus der ComboBox Auswahl setzen.
     * @param currentRace Combobox mit der Auswahl.
     */
    public void setCurrentRace(ComboBox<Race> currentRace) {
        this.currentRace = currentRace;
    }

    /**
     * Beschreibung aus dem Beschreibungsfeld setzen.
     * @param currentDescription Beschreibungsfeld.
     */
    public void setCurrentDescription(TextField currentDescription) {
        this.currentDescription = currentDescription;
    }

    /**
     * Aktuellen NPC auslesen.
     * @return NPC zurückgeben.
     */
    public NPC getCurrentNPC() {
        return currentNPC;
    }

    /**
     * Aktuellen NPC setzen.
     * @param currentNPC NPC.
     */
    public void setCurrentNPC(NPC currentNPC) {
        this.currentNPC = currentNPC;
    }
}
