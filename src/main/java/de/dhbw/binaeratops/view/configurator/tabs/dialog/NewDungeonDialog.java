package de.dhbw.binaeratops.view.configurator.tabs.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.enums.Status;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;

public class NewDungeonDialog extends Dialog {

    public NewDungeonDialog(ConfiguratorServiceI configuratorService){

        TextField dungeonName = new TextField();
        dungeonName.setLabel("Name");
        Button okButton = new Button("Ok");

        okButton.addClickListener(e->{
            configuratorService.createDungeon(dungeonName.getValue(), VaadinSession.getCurrent().getAttribute(User.class), Status.INACTIVE );
            this.close();
        });

        add(dungeonName, okButton);
    }

}
