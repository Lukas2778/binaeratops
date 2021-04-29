package de.dhbw.binaeratops.view.registration;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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

        HorizontalLayout validLoginLay=new HorizontalLayout();
        RouterLink loginLink=new RouterLink("Anmelden",LogInView.class);
        RouterLink validLink=new RouterLink("Zur Validierung",ValdidateRegistrationView.class);
        validLoginLay.add(loginLink,new Label(" - "),validLink);

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        submit.addClickShortcut(Key.ENTER);
        submit.addClickListener(e->
                {
                    checkRegistration(name.getValue(),eMail.getValue(),password.getValue(),verPassword.getValue());
                });

        add(
                new H1("Binäratops - Registrierung"),
                new H4("Bitte registriere dich mit deinen Daten."),
                new H4("Du bekommst nach Klick auf 'Registrieren' eine E-Mail von uns mit deinem persönlichen Code zugesandt."),
                name,
                eMail,
                password,
                verPassword,
                submit,
                validLoginLay
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
