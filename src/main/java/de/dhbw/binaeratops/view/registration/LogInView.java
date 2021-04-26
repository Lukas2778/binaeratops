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
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.service.api.registration.AuthServiceI;
import de.dhbw.binaeratops.service.impl.registration.AuthException;
import de.dhbw.binaeratops.service.impl.registration.NotVerifiedException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ValidationException;

@RouteAlias("") //wenn keine Adresse zu einer bestimmten Seite in der URL eingegeben wird, wird sofort auf die Login-Seite verwiesen
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

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

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
            } catch (NotVerifiedException notVerifiedException){
                Notification.show("Dein Account ist noch nicht validiert! Bitte gib deinen Code, den du von uns per E-Mail erhalten hast ein.");
                UI.getCurrent().navigate("validateRegistration");
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
