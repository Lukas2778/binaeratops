package de.dhbw.binaeratops.view.game;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;

public class AvatarCreatorDialog extends Dialog {

    TextField nameTextField;
    ComboBox roleCombobox;
    Html roleHeader;
    Html roleDescription;
    ComboBox raceCombobox;
    Html raceHeader;
    Html raceDescription;
    Button cancelButton;
    Button createRole;

    public AvatarCreatorDialog() {

    }
}
