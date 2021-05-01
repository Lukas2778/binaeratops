package de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import de.dhbw.binaeratops.model.entitys.Action;

import java.util.ArrayList;

public class ActionSingleDialog extends Dialog {
    private TextField currentName;
    private TextField currentAction;

    private Action selectedAction;
    private ArrayList<Action> actionList;
    Grid<Action> grid;

    public ActionSingleDialog() {}

    public ActionSingleDialog(ArrayList<Action> actionList, Action selectedAction, Grid<Action> grid) {
        this.actionList = actionList;
        this.selectedAction = selectedAction;
        this.grid = grid;
        init();
    }

    private void init() {
        currentName = new TextField("Name");
        currentAction = new TextField("Befehl");
        Button saveDialog = new Button("Speichern");
        Button closeDialog = new Button("Abbrechen");

        this.add(new VerticalLayout(currentName, currentAction, new HorizontalLayout(saveDialog, closeDialog)));

        saveDialog.addClickListener(e -> {
            selectedAction.setName(currentName.getValue());
            selectedAction.setAction(currentAction.getValue());
            if (!actionList.contains(selectedAction)) {
                actionList.add(selectedAction);
            }
            refreshGrid();
            this.close();
        });
        closeDialog.addClickListener(e -> this.close());
    }

    public void fillDialog(Action action) {
        currentName.setValue(action.getName());
        currentAction.setValue(action.getAction());
    }

    private void refreshGrid() {
        grid.setItems(actionList);
    }
}
