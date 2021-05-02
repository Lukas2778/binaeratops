package de.dhbw.binaeratops.view.configurator.tabs.dialog;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.dhbw.binaeratops.model.entitys.NPC;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NpcSelectionDialog extends Dialog {

    public boolean dialogResult = false;
    List<NPC> selectedNpcs = new ArrayList<NPC>();

    public Button comfirmButton = new Button("Übernehmen");
    public Button cancelButton = new Button("Abbrechen");
    Grid<NPC> npcGrid = new Grid(NPC.class);


    public NpcSelectionDialog(ConfiguratorServiceI configuratorService){
        H1 title = new H1("NPC Liste");
        H2 headline = new H2("NPCs für ...");


        npcGrid.setWidth(500, Unit.PIXELS);

        //List<NPC> npcList = configuratorService.getAllNPCs();

        //npcGrid.setItems(npcList);
        npcGrid.removeAllColumns();

        npcGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        npcGrid.addColumn(NPC::getNpcName).setHeader("Name");
        npcGrid.addColumn(npc -> npc.getRace().getRaceName()).setHeader("Rasse");
        npcGrid.addColumn(NPC::getDescription).setHeader("Beschreibung");

        add(new VerticalLayout(title, headline, npcGrid, comfirmButton, cancelButton));

        this.addOpenedChangeListener(e->{
            if (isOpened()){
                List<NPC> tempList = configuratorService.getAllNPCs();
                npcGrid.setItems(tempList);
                for (NPC myNPC: selectedNpcs){
                    if(tempList.contains(myNPC)){
                        npcGrid.select(myNPC);
                    }
                }
            }
        });

        cancelButton.addClickListener(e->{
            dialogResult = false;
            close();
        });

        comfirmButton.addClickListener(e->{
            dialogResult = true;
            close();
        });
    }

    public List<NPC> getNPCSelection(){
        selectedNpcs = Arrays.asList(npcGrid.getSelectedItems().toArray(NPC[]::new));
        return selectedNpcs;
    }
}
