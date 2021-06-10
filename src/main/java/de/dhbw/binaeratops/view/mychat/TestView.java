package de.dhbw.binaeratops.view.mychat;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import de.dhbw.binaeratops.model.mychat.ChatMessage;

/**
 * @author Lukas Göpel
 * Date: 10.06.2021
 * Time: 09:33
 */

@Route("mychat")
public class TestView extends VerticalLayout {
    String userName;
    String userMessage;

    public TestView() {
        userName = "";
        userMessage="";

        TextField usernameField = new TextField("Username");

        Button connectButt = new Button("Connect");
        connectButt.addClickShortcut(Key.ENTER);
        add(usernameField, connectButt);

        connectButt.addClickListener(event -> {
            if(usernameField.getValue()!="") {
                userName = usernameField.getValue();
                removeAll();
                add(chatComponent());
                // connect to socket
            }
        });
    }

    private Component chatComponent() {
        ListBox<ChatMessage> messageListBox = new ListBox<>();
        messageListBox.setSizeFull();
        ListBox<Button> recipientListBox=new ListBox<>();
        recipientListBox.setSizeFull();

        TextField messageField=new TextField("Chat message");
        Button sendButt=new Button("Send");
        sendButt.addClickShortcut(Key.ENTER);
        Button disconnectButt =new Button("Disconnect");
        disconnectButt.getStyle().set("color", "red");
        disconnectButt.addClickListener(event -> {
           //disconnect from socket
        });

        sendButt.addClickListener(event -> {
            userMessage=messageField.getValue();
           // send user message
        });

        return new VerticalLayout(new HorizontalLayout(messageListBox, recipientListBox), new HorizontalLayout(messageField, sendButt, disconnectButt));
    }

    private void setConnected(boolean connected){
        // change variables in Vaadin surface
    }

    private void connect(){

    }
}
