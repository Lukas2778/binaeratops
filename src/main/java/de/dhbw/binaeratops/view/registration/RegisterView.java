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
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.service.api.registration.AuthServiceI;
import de.dhbw.binaeratops.service.exceptions.RegistrationException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ResourceBundle;

/**
 * Registrierungs Fenster. Erscheint, wenn auf den Button 'Registrieren' geklickt wird.
 * Diese Seite wird angezeigt, wenn der Benutzer seinen Benutzernamen eingegeben hat und die E-Mail an ihn gesendet wurde.
 */
@Route("register")
public class RegisterView extends VerticalLayout {

    private final ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());

    @Autowired
    AuthServiceI authServiceI;

    /**
     * Dies ist der Konstruktor, zum Erzeugen der Registrierungsseite.
     */
    public RegisterView() {
        // Titel der Seite
        UI current = UI.getCurrent();
        current.getPage().setTitle(res.getString("view.register.pagetitle"));

        TextField name=new TextField(res.getString("view.register.field.username"));
        TextField eMail = new TextField(res.getString("view.register.field.email"));
        PasswordField password=new PasswordField(res.getString("view.register.field.password"));
        PasswordField verPassword=new PasswordField(res.getString("view.register.field.password.repeat"));
        Button submit=new Button(res.getString("view.register.button.register"));

        HorizontalLayout validLoginLay=new HorizontalLayout();
        RouterLink loginLink=new RouterLink(res.getString("view.register.link.login"),LogInView.class);
        RouterLink validLink=new RouterLink(res.getString("view.register.link.validation"),ValdidateRegistrationView.class);
        validLoginLay.add(loginLink,new Label(" - "),validLink);

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        submit.addClickShortcut(Key.ENTER);
        submit.addClickListener(e->
                {
                    checkRegistration(name.getValue(),eMail.getValue(),password.getValue(),verPassword.getValue());
                });

        add(
                new H1(res.getString("view.register.header")),
                new H4(res.getString("view.register.subheader1")),
                new H4(res.getString("view.register.subheader2")),
                name,
                eMail,
                password,
                verPassword,
                submit,
                validLoginLay
        );
        name.focus();
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
            Notification.show(res.getString("view.register.notification.enter.username"));
        } else if (APassword.isEmpty()) {
            Notification.show(res.getString("view.register.notification.enter.password"));
        } else if (!APassword.equals(AVerPassword)) {
            Notification.show(res.getString("view.register.notification.password.not.equal"));
        } else {
            try{
                authServiceI.register(AName, APassword, AEMail);
                UI.getCurrent().getPage().setLocation("validateRegistration");
            } catch (RegistrationException e) {
                Notification.show(res.getString("view.register.notification.username.already.exists"));

            }catch (Exception e){
                Notification.show(res.getString("view.register.notification.check.email"));
            }

        }
    }

}
