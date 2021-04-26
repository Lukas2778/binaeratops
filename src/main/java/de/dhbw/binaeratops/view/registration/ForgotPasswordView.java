package de.dhbw.binaeratops.view.registration;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import de.dhbw.binaeratops.service.api.registration.AuthServiceI;
import de.dhbw.binaeratops.service.impl.registration.FalseUserException;
import org.springframework.beans.factory.annotation.Autowired;


@Route("forgotPassword")
public class ForgotPasswordView extends VerticalLayout {

    @Autowired
    AuthServiceI authServiceI;

    public ForgotPasswordView() {
        TextField name=new TextField("Benutzername");
        Button submit=new Button("E-Mail Senden");

        submit.addClickListener(e->
        {
            try {
                authServiceI.sendConfirmationEmail(name.getValue());
            } catch (FalseUserException falseUserException) {
                Notification.show("Ungültiger Bentzername");
            }
        });

        add(new H1("Hallo"),
                name,
                submit);
    }
}
