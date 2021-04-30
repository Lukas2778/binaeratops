package de.dhbw.binaeratops.view.registration;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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

/**
 * Login Fenster auf der Webapplikation.
 * Dies ist das erste Fenster, das der Benutzer sieht.
 *
 */
//wenn keine Adresse zu einer bestimmten Seite in der URL eingegeben wird, wird sofort auf die Login-Seite verwiesen
//@RouteAlias("")
//@Route("login")
@PageTitle("Binäratops - Anmeldung")
public class LogInView extends VerticalLayout {
    /**
     * Dies ist der Konstruktor zum Erzeugen der Login Seite.
     * @param authServiceI Übergabe des Authentifizierungsservices.
     */
    public LogInView(@Autowired AuthServiceI authServiceI) {

        TextField name = new TextField("Benutzername");
        PasswordField password = new PasswordField("Passwort");
        Button loginButton = new Button("Anmelden");

        HorizontalLayout changePasswRegisterLay=new HorizontalLayout();
        RouterLink changePasswLink=new RouterLink("Passwort vergessen", ForgotPasswordView.class);
        RouterLink registerLink = new RouterLink("Registrieren", RegisterView.class);
        changePasswRegisterLay.add(registerLink,new Label(" - "),changePasswLink);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        loginButton.addClickShortcut(Key.ENTER);
        loginButton.addClickListener(e ->
        {
            try {
                if (VaadinSession.getCurrent().getAttribute(User.class) != null &&
                        VaadinSession.getCurrent().getAttribute(User.class).getUsername().equals(name.getValue())) {
                    Notification.show("Sie sind bereits angemeldet.");
                } else {
                    authServiceI.authenticate(name.getValue(), password.getValue());
                    UI.getCurrent().navigate("aboutUs");
                }
            } catch (AuthException authException) {
                Notification.show("Fehler bei der Anmeldung. Prüfen Sie ihre Daten!");
            } catch (NotVerifiedException notVerifiedException) {
                Notification.show("Dein Account ist noch nicht validiert! Bitte gib deinen Code, den du von uns per E-Mail erhalten hast ein.");
                UI.getCurrent().navigate("validateRegistration");
            }
        });
        add(
                new H1("Binäratops - Anmeldung"),
                new H4("Bitte registriere dich zuerst, sofern du noch keinen Account hast."),
                name,
                password,
                loginButton,
                changePasswRegisterLay
        );
    }
}
