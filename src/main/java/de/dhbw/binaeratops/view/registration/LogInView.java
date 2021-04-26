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
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.service.api.registration.AuthServiceI;
import de.dhbw.binaeratops.service.impl.registration.AuthException;
import org.springframework.beans.factory.annotation.Autowired;

@Route("login")
@PageTitle("Binäratops - Anmeldung")
public class LogInView extends VerticalLayout {

    /**
     * Login Fenster auf der Webapplikation.
     * Dies ist das erste Fenster, das der Benutzer sieht.
     * @param authServiceI Übergabe des Authentifizierungsservices.
     */
    public LogInView(@Autowired AuthServiceI authServiceI) {

        TextField name=new TextField("Benutzername");
        PasswordField password=new PasswordField("Passwort");
        Button loginButton =new Button("Anmelden");

        loginButton.addClickListener(e->
        {
            try {
                if(VaadinSession.getCurrent().getAttribute(User.class) != null &&
                VaadinSession.getCurrent().getAttribute(User.class).getName().equals(name.getValue())){
                    Notification.show("Sie sind bereits angemeldet.");
                }
                else{
                    authServiceI.authenticate(name.getValue(),password.getValue());
                    UI.getCurrent().navigate("dummy");
                }


            } catch (AuthException authException) {
                Notification.show("Fehler bei der Anmeldung. Prüfen Sie ihre Daten!");
            }
        });
        add(
                new H1("Binäratops - Anmeldung"),
                name,
                password,
                loginButton,
                new RouterLink("Registrieren",RegisterView.class)
        );
    }
}
