package de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import de.dhbw.binaeratops.model.entitys.Action;
import de.dhbw.binaeratops.model.entitys.NPC;

import java.util.ArrayList;

public class ActionOverviewDialog extends Dialog {

    private NPC currentNPC;
    private Action currentAction;
    private ArrayList<Action> actionList;
    Grid<Action> actionGrid = new Grid<>(Action.class);
    private ArrayList<NPC> npcList;
    Grid<NPC> npcGrid;

    public ActionOverviewDialog() {}

    public ActionOverviewDialog(ArrayList<Action> actionList, ArrayList<NPC> npcList, NPC currentNPC, Grid<NPC> npcGrid) {
        this.actionList = actionList;
        this.npcList = npcList;
        this.currentNPC = currentNPC;
        this.npcGrid = npcGrid;
        init();
    }

    private void init() {
        Button addActionButton = new Button("Befehl hinzufügen");
        Button editActionButton = new Button("Befehl editieren");
        Button removeActionButton = new Button("Befehl entfernen");
        Button saveDialog = new Button("Speichern");
        Button closeDialog = new Button("Abbrechen");

        createGrid();

        this.add(new VerticalLayout(new HorizontalLayout(addActionButton, editActionButton, removeActionButton), new HorizontalLayout(saveDialog, closeDialog)));


        addActionButton.addClickListener(e -> {
            currentAction = new Action();
            ActionSingleDialog dialog = new ActionSingleDialog(actionList, currentAction, actionGrid);
            dialog.open();
        });
        editActionButton.addClickListener(e -> {
            Action[] selectedActions = actionGrid.getSelectedItems().toArray(Action[]::new);
            if (selectedActions.length >= 1) {
                currentAction = selectedActions[0];
                ActionSingleDialog dialog = new ActionSingleDialog(actionList, currentAction, actionGrid);
                dialog.fillDialog(currentAction);
                dialog.open();
            } else {
                Notification.show("Bitte wählen sie einen Befehl aus!");
            }
        });
        removeActionButton.addClickListener(e -> {
            Action[] selectedActions = actionGrid.getSelectedItems().toArray(Action[]::new);
            if (selectedActions.length >= 1) {
                currentAction = selectedActions[0];
                actionList.remove(currentAction);
                refreshActionGrid();
            } else {
                Notification.show("Bitte wählen sie einen Befehl aus!");
            }
        });
        saveDialog.addClickListener(e -> {
            currentNPC.setActions(actionList);
            refreshNPCGrid();
            this.close();
        });
        closeDialog.addClickListener(e -> this.close());
    }

    private void createGrid() {
        actionGrid.setItems(currentNPC.getActions());

        actionGrid.removeAllColumns();
        Grid.Column<Action> nameColumn = actionGrid.addColumn(Action::getName).setHeader("Name");
        Grid.Column<Action> actionColumn = actionGrid.addColumn(Action::getAction).setHeader("Action");

        HeaderRow filterRow = actionGrid.appendHeaderRow();

        TextField nameField = new TextField();
        TextField actionField = new TextField();

        nameField.addValueChangeListener(e -> {
            //TODO: Service.findByName
        });
        actionField.addValueChangeListener(e -> {
            //TODO: Service.findBySize
        });

        filterRow.getCell(nameColumn).setComponent(nameField);
        filterRow.getCell(actionColumn).setComponent(actionField);

        nameField.setSizeFull();
        nameField.setPlaceholder("Filter");
        nameField.getElement().setAttribute("focus-target", "");
        actionField.setSizeFull();
        actionField.setPlaceholder("Filter");
        actionField.getElement().setAttribute("focus-target", "");

        actionGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);

        this.setWidthFull();
        actionGrid.setWidth("100%");

        this.add(actionGrid);
    }

    private void refreshActionGrid() {
        actionGrid.setItems(actionList);
    }

    private void refreshNPCGrid() {
        npcGrid.setItems(npcList);
    }
}
