package de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.Room;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;

public class NewRoomDialog extends Dialog {

    private Room newRoom;

    public NewRoomDialog(ConfiguratorServiceI configuratorService){

        TextField roomName = new TextField();
        roomName.setLabel("Name");
        Button okButton = new Button("Ok");

        okButton.addClickListener(e->{
            //newRoom = configuratorService.createRoom(roomName.getValue());
            this.close();
        });

        add(roomName, okButton);
    }

    public Room getRoom(){
        return  newRoom;
    }

}

