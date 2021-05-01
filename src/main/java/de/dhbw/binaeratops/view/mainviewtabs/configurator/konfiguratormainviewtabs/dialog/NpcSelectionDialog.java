package de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs.dialog;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.dhbw.binaeratops.model.entitys.NPC;
import de.dhbw.binaeratops.model.entitys.Race;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NpcSelectionDialog extends Dialog {

    public boolean dialogResult = false;

    public Button comfirmButton = new Button("Übernehmen");
    public Button cancelButton = new Button("Abbrechen");
    Grid<NPC> npcGrid = new Grid(NPC.class);


    public NpcSelectionDialog(){
        H1 title = new H1("NPC Liste");
        H2 headline = new H2("NPCs für ...");


        npcGrid.setWidth(500, Unit.PIXELS);

        List<NPC> npcList = new ArrayList<>();

        NPC npc1 = new NPC("Olaf", new Race("Arzt", "kann Leute auf magische Art heilen"),  "sehr guter Arzt");
        NPC npc2 = new NPC("Ralf", new Race("Arzt", "kann Leute auf magische Art heilen"),  "sehr guter Arzt");
        NPC npc3 = new NPC("Nicolas", new Race("Arzt", "kann Leute auf magische Art heilen"),  "sehr guter Arzt");

        npc1.setNpcId(2L);
        npc2.setNpcId(34L);
        npc3.setNpcId(77L);

        npcList.add(npc1);
        npcList.add(npc2);
        npcList.add(npc3);

        npcGrid.setItems(npcList);
        npcGrid.removeAllColumns();

        npcGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        npcGrid.addColumn(NPC::getNpcName).setHeader("Name");
        npcGrid.addColumn(npc -> npc.getRace().getRaceName()).setHeader("Rasse");
        npcGrid.addColumn(NPC::getDescription).setHeader("Beschreibung");

        add(new VerticalLayout(title, headline, npcGrid, comfirmButton, cancelButton));

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
        List<NPC> npc = Arrays.asList(npcGrid.getSelectedItems().toArray(NPC[]::new));
        return npc;
    }
}
