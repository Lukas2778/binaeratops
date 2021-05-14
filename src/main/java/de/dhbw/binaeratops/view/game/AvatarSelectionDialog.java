package de.dhbw.binaeratops.view.game;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.listbox.ListBox;
import de.dhbw.binaeratops.model.entitys.Avatar;

public class AvatarSelectionDialog extends Dialog {
    ListBox<Avatar> avatarListBox;
    Button cancelButton;
    Button createAvatar;
    Button enterDungeonButton;

    public AvatarSelectionDialog() {
    }
}
