package de.dhbw.binaeratops.view.registration;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import de.dhbw.binaeratops.service.api.registration.AuthServiceI;
import de.dhbw.binaeratops.service.impl.registration.FalseUserException;
import org.springframework.beans.factory.annotation.Autowired;






@Route("newPassword")
public class NewPasswordView extends VerticalLayout {


    @Autowired
    private AuthServiceI authServiceI;


    public NewPasswordView() {
        TextField name=new TextField("Benutername");
        PasswordField newPassword=new PasswordField("neues Passwort");
        PasswordField newPassword2=new PasswordField("neues Passwort");
        IntegerField code=new IntegerField("Bestätigungscode");
        Button submit=new Button("Passwort ändern");

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        submit.addClickListener(e->{
            try {
                if(newPassword==newPassword2)
                    authServiceI.changePassword(name.getValue(),newPassword.getValue(), code.getValue());
                else
                    Notification.show("beide Passwörter müssen gleich sein");
            } catch (FalseUserException falseUserException) {
                Notification.show("Username nicht gefunden");
            }
        });

    }
}
