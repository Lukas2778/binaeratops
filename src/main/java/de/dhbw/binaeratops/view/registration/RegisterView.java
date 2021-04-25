package de.dhbw.binaeratops.view.registration;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import de.dhbw.binaeratops.service.api.registration.AuthServiceI;
import de.dhbw.binaeratops.service.impl.registration.RegistrationException;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;
import org.springframework.beans.factory.annotation.Autowired;


@Route("register")
@PageTitle("Binäratops Registrierung")
public class RegisterView extends VerticalLayout {
    @Autowired
    AuthServiceI authServiceI;

    public RegisterView() {
        TextField name=new TextField("Benutzername");
        TextField eMail = new TextField("E-Mail Adresse");
        PasswordField password=new PasswordField("Passwort");
        PasswordField password2=new PasswordField("wiederhole Passwort");

        Button submit=new Button("Registrieren");

        submit.addClickListener(e->
                {
                    register(name.getValue(),eMail.getValue(),password.getValue(),password2.getValue());
                });

        add(
                new H1("Registrierung"),
                name,
                eMail,
                password,
                password2,
                submit,
                new RouterLink("Anmeldung",LogInView.class),
                new RouterLink("Validierung",ValdidateRegistrationView.class)
        );

    }

    private void register(String name,String eMail,String password, String password2) {
        if (name.trim().isEmpty()) {
            Notification.show("geben Sie einen namen ein");
        } else if (password.isEmpty()) {
            Notification.show("geben sie ein Passwort ein");
        } else if (!password.equals(password2)) {
            Notification.show("beide Passwörter müssen gleich sein");
        } else {
            try {
                authServiceI.register(name, password, eMail);

                UI.getCurrent().getPage().setLocation("validateRegistration");
            } catch (RegistrationException e) {
                Notification.show("der Name ist bereits vergeben");

            }

        }
    }

}
