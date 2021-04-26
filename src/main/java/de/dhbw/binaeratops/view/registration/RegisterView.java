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
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ValidationException;

/**
 * Registrierungs Fenster. Erscheint, wenn auf den Button 'Registrieren' geklickt wird.
 * Diese Seite wird angezeigt, wenn der Benutzer seinen Benutzernamen eingegeben hat und die E-Mail an ihn gesendet wurde.
 */
@Route("register")
@PageTitle("Binäratops - Registrierung")
public class RegisterView extends VerticalLayout {
    @Autowired
    AuthServiceI authServiceI;

    /**
     * Dies ist der Konstruktor, zum Erzeugen der Registrierungsseite.
     */
    public RegisterView() {
        TextField name=new TextField("Benutzername");
        TextField eMail = new TextField("E-Mail Adresse");
        PasswordField password=new PasswordField("Passwort");
        PasswordField verPassword=new PasswordField("Passwort wiederholen");
        Button submit=new Button("Registrieren");

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        submit.addClickListener(e->
                {
                    checkRegistration(name.getValue(),eMail.getValue(),password.getValue(),verPassword.getValue());
                });

        add(
                new H1("Binäratops - Registrierung"),
                name,
                eMail,
                password,
                verPassword,
                submit,
                new RouterLink("Anmeldung",LogInView.class),
                new RouterLink("Validierung",ValdidateRegistrationView.class)
        );

    }

    /**
     * Überprüfung der Eingaben der Registrierung.
     * @param AName Übergabe des eingegebenen Benutzernamen.
     * @param AEMail Übergabe der eingegebenen E-Mail Adresse.
     * @param APassword Übergabe des eingegebenen Passworts.
     * @param AVerPassword Übergabe des eingegebenen Passworts zur Verifizierung.
     */
    private void checkRegistration(String AName,String AEMail,String APassword, String AVerPassword) {
        if (AName.trim().isEmpty()) {
            Notification.show("Du musst einen Benutzernamen eingeben!");
        } else if (APassword.isEmpty()) {
            Notification.show("Du musst ein Passwort erstellen!");
        } else if (!APassword.equals(AVerPassword)) {
            Notification.show("Die Passwörter sind nicht identisch!");
        } else {
            try{
                authServiceI.register(AName, APassword, AEMail);
                UI.getCurrent().getPage().setLocation("validateRegistration");
            } catch (RegistrationException e) {
                Notification.show("Der eingegebene Benutzername ist leider bereits vergeben. Wähle einen anderen!");

            }catch (Exception e){
                Notification.show("Überprüfe bitte deine E-Mail Adresse!");
            }

        }
    }

}
