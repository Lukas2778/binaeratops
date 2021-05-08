package de.dhbw.binaeratops.view.registration;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.service.api.registration.AuthServiceI;
import de.dhbw.binaeratops.service.exceptions.FalseUserException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ResourceBundle;

/**
 * Diese Seite wird aufgerufen, wenn der Benutzer auf den 'Passwort vergessen'-Link klickt.
 */
@Route("forgotPassword")
public class ForgotPasswordView extends VerticalLayout {

    private final ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());

    @Autowired
    AuthServiceI authServiceI;

    /**
     * Dies ist der Konstruktor, zum Erzeugen der Passwort-Vergessen Seite.
     */
    public ForgotPasswordView() {
        // Titel der Seite
        UI current = UI.getCurrent();
        current.getPage().setTitle(res.getString("view.forgot.password.pagetitle"));

        TextField name = new TextField(res.getString("view.forgot.password.field.username"));
        Button submit = new Button(res.getString("view.forgot.password.button.send.email"));

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
                Notification.show(res.getString("view.forgot.password.notification.invalid.username"));
            }
        });

        add(new H1(res.getString("view.forgot.password.headline")),
                new H4(res.getString("view.forgot.password.h41")),
                new H4(res.getString("view.forgot.password.h42")),
                name,
                submit
        );
        name.focus();
    }
}
