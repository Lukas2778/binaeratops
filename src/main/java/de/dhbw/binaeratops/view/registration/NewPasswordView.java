package de.dhbw.binaeratops.view.registration;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import de.dhbw.binaeratops.service.api.registration.AuthServiceI;
import de.dhbw.binaeratops.service.impl.registration.FalseUserException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Diese Seite wird angezeigt, wenn der Benutzer seinen Benutzernamen eingegeben hat und die E-Mail an ihn gesendet wurde.
 */
@Route("setNewPassword")
public class NewPasswordView extends VerticalLayout {

    @Autowired
    private AuthServiceI authServiceI;

    /**
     * Dies ist der Konstruktor, zum Erzeugen der Neues-Passwort-wird-gesetzt Seite.
     */
    public NewPasswordView() {
        TextField name=new TextField("Benutername");
        PasswordField newPassword=new PasswordField("Neues Passwort");
        PasswordField newPassword2=new PasswordField("Neues Passwort bestätigen");
        IntegerField code=new IntegerField("Code eingeben");
        Button submit=new Button("Passwort ändern");

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        submit.addClickShortcut(Key.ENTER);
        submit.addClickListener(e->{
            try {
                if(newPassword.getValue().equals(newPassword2.getValue())) {
                    authServiceI.changePassword(name.getValue(), newPassword.getValue(), code.getValue());
                    UI.getCurrent().navigate("logout");
                }
                else
                    Notification.show("Die Passwörter stimmen nicht überein!");
            } catch (FalseUserException falseUserException) {
                Notification.show("Dieser Benutzername wurde nicht gefunden!");
            }
        });
        add(
                new H1("Passwort ändern"),
                new H4("Gib hier deinen Benutzernamen, ein neues Passwort und den Code ein, den du von uns per E-Mail erhalten hast."),
                name,
                newPassword,
                newPassword2,
                code,
                submit
        );
    }
}
