package de.dhbw.binaeratops.view.game;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.Avatar;
import de.dhbw.binaeratops.model.entitys.User;

import java.util.ArrayList;
import java.util.List;

@CssImport("./views/game/game-view.css")
public class AvatarSelectionDialog extends Dialog {

    HorizontalLayout buttLayout;
    private H2 title;
    private List<Avatar> avatarList;
    private Grid<Avatar> avatarGrid;

    public AvatarSelectionDialog() {
        getElement().getThemeList().add("avatar-dialog");
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);
        buttLayout=new HorizontalLayout();

        // Header
        title = new H2("Avatarauswahl");
        title.setId("avatar-dialog");
        Header header = new Header(title);

        avatarList = new ArrayList<>();
        User user = VaadinSession.getCurrent().getAttribute(User.class);
        avatarList.addAll(user.getAvatars());

        avatarGrid = new Grid<>();
        avatarGrid.setItems(avatarList);
        avatarGrid.setVerticalScrollingEnabled(true);
        avatarGrid.addColumn(Avatar::getName).setHeader("Avatarname");
        avatarGrid.addColumn(Avatar::getRace).setHeader("Rasse");
        avatarGrid.addColumn(Avatar::getRole).setHeader("Rolle");
        avatarGrid.addColumn(Avatar::getCurrentRoom).setHeader("letzter Raum");
        //avatarGrid.getStyle().set("height", "100px");
        //avatarGrid.getStyle().set("width", "90px");
        avatarGrid.setSizeFull();

        // Footer
        Button cancel = new Button("Verlassen", e -> close());
        cancel.getStyle().set("color", "red");

        Button createAvatar = new Button("Neuer Avatar", e -> close());
        createAvatar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        createAvatar.focus();

        Button enterDungeon = new Button("Eintreten", e -> close());
        createAvatar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        buttLayout.add(cancel, createAvatar, enterDungeon);

        // Add it all together
        add(header,avatarGrid,buttLayout);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        // Label the dialog (accessibility)
        getElement().executeJs("this.$.overlay.setAttribute('aria-labelledby', $0)", title.getId().get());
    }
}
