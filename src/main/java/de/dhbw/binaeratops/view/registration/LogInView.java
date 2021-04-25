package de.dhbw.binaeratops.view.registration;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import de.dhbw.binaeratops.service.api.registration.AuthServiceI;
import de.dhbw.binaeratops.service.impl.registration.AuthException;
import org.springframework.beans.factory.annotation.Autowired;




@Route("login")
@PageTitle("Binäratops Anmeldung")
public class LogInView extends VerticalLayout {


    public LogInView(@Autowired AuthServiceI authServiceI) {

        TextField name=new TextField("Benutzername");
        PasswordField password=new PasswordField("Passwort");
        Button loginButton =new Button("Anmelden");

        loginButton.addClickListener(e->
        {
            try {
                authServiceI.authenticate(name.getValue(),password.getValue());
            } catch (AuthException authException) {
                Notification.show("Fehler bei der Anmeldung. Prüfen Sie ihre Daten!");
            }
        });
        add(
                new H1("Binäratops Dungeon Anmeldung"),
                name,
                password,
                loginButton,
                new RouterLink("Registrieren",RegisterView.class)
        );
    }
}
