package de.dhbw.binaeratops.view.configurator.tabs.dialog;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Dialog-Oberfläche für die Komponente "NPC Auswahl" des Raum-Konfigurators.
 * <p>
 * Diese Ansicht stellt alle View-Komponenten für das Hinzufügen eines NPCs zu einem Raum bereit.
 * <p>
 * Dafür sendet sie die Benutzerangaben direkt an den entsprechenden Service.
 *
 * @author Pedro Treuer, Timon Gartung, Nicolas Haug, Lars Rösel, Mattias Rall, Lukas Göpel
 */
public class NpcSelectionDialog extends Dialog {

    public boolean dialogResult = false;
    List<NPC> selectedNpcs = new ArrayList<NPC>();

    public Button comfirmButton = new Button("Übernehmen");
    public Button cancelButton = new Button("Abbrechen");
    HashMap<NPC, IntegerField> npcIntegerFieldHashMap = new HashMap<>();
    private ListBox<NpcInstance> npcList;
    Grid<NPC> npcGrid = new Grid(NPC.class);
    ConfiguratorServiceI configuratorServiceI;
    Room room;

    public NpcSelectionDialog(ConfiguratorServiceI AConfiguratorService, Room ARoom, ListBox<NpcInstance> npcListBox){
        configuratorServiceI = AConfiguratorService;
        room = ARoom;
        npcList = npcListBox;
        H1 title = new H1("NPC Liste");
        H2 headline = new H2("NPCs für ...");

        npcGrid.setWidth(500, Unit.PIXELS);

        npcGrid.removeAllColumns();

        npcGrid.addComponentColumn(npc -> {
            IntegerField nf = new IntegerField();
            npcIntegerFieldHashMap.put(npc, nf);

            nf.setValue(configuratorServiceI.getNumberOfNPC(room, npc));

            return nf;
        }).setHeader("Anzahl");
        npcGrid.addColumn(NPC::getNpcName).setHeader("Name");
        npcGrid.addColumn(npc -> npc.getRace().getRaceName()).setHeader("Rasse");
        npcGrid.addColumn(NPC::getDescription).setHeader("Beschreibung");

        add(new VerticalLayout(title, headline, npcGrid, comfirmButton, cancelButton));

        cancelButton.addClickListener(e->{
            dialogResult = false;
            close();
        });

        comfirmButton.addClickListener(e->{
            if(validate()) {
                List<NpcInstance> instances = new ArrayList<>();
                for (NPC npc : npcIntegerFieldHashMap.keySet()) {
                    if (!npcIntegerFieldHashMap.get(npc).isEmpty() && npcIntegerFieldHashMap.get(npc).getValue() >= 1) {
                        for (int i = 0; i < npcIntegerFieldHashMap.get(npc).getValue(); i++) {
                            NpcInstance instance = new NpcInstance();
                            instance.setNpc(npc);
                            instances.add(instance);
                        }
                    }
                }
                AConfiguratorService.setNpcInstances(ARoom, instances);
                refreshNpcList();
                close();
            }
        });

        npcGrid.setItems(configuratorServiceI.getAllNPCs());
    }

    public List<NPC> getNPCSelection(){
        selectedNpcs = Arrays.asList(npcGrid.getSelectedItems().toArray(NPC[]::new));
        return selectedNpcs;
    }

    private void refreshNpcList () {
        npcList.setItems(configuratorServiceI.getAllNPCs(room));
    }

    private boolean validate() {
        for (NPC npc : npcIntegerFieldHashMap.keySet()) {
            if (npcIntegerFieldHashMap.get(npc).isInvalid()) {
                return false;
            }
        }
        return true;
    }
}
