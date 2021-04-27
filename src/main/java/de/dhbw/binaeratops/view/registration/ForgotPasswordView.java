package de.dhbw.binaeratops.view.registration;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.service.api.registration.AuthServiceI;
import de.dhbw.binaeratops.service.impl.registration.FalseUserException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Diese Seite wird aufgerufen, wenn der Benutzer auf den 'Passwort vergessen'-Link klickt.
 */
@Route("forgotPassword")
public class ForgotPasswordView extends VerticalLayout {

    @Autowired
    AuthServiceI authServiceI;

    /**
     * Dies ist der Konstruktor, zum Erzeugen der Passwort-Vergessen Seite.
     */
    public ForgotPasswordView() {
        TextField name = new TextField("Benutzername");
        Button submit = new Button("E-Mail senden");

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        submit.addClickShortcut(Key.ENTER);
        submit.addClickListener(e->
        {
            try {
                authServiceI.sendConfirmationEmail(name.getValue());
                UI.getCurrent().navigate("setNewPassword");
            } catch (FalseUserException falseUserException) {
                Notification.show("Ungültiger Bentzername!");
            }
        });

        add(new H1("Passwort ändern"),
                name,
                submit
        );
    }
}
