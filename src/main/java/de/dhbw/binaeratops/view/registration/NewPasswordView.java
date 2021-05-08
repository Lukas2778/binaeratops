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
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.service.api.registration.AuthServiceI;
import de.dhbw.binaeratops.service.exceptions.FalseUserException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ResourceBundle;

/**
 * Diese Seite wird angezeigt, wenn der Benutzer seinen Benutzernamen eingegeben hat und die E-Mail an ihn gesendet wurde.
 */
@Route("setNewPassword")
public class NewPasswordView extends VerticalLayout {

    private final ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());

    @Autowired
    private AuthServiceI authServiceI;

    /**
     * Dies ist der Konstruktor, zum Erzeugen der Neues-Passwort-wird-gesetzt Seite.
     */
    public NewPasswordView() {
        // Titel der Seite
        UI current = UI.getCurrent();
        current.getPage().setTitle(res.getString("view.new.password.pagetitle"));

        TextField name=new TextField(res.getString("view.new.password.field.username"));
        PasswordField newPassword=new PasswordField(res.getString("view.new.password.field.new.password"));
        PasswordField newPassword2=new PasswordField(res.getString("view.new.password.field.new.password.repeat"));
        IntegerField code=new IntegerField(res.getString("view.new.password.field.code"));
        Button submit=new Button(res.getString("view.new.password.button.submit"));

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
                    Notification.show(res.getString("view.new.password.notification.password.not.equal"));
            } catch (FalseUserException falseUserException) {
                Notification.show(res.getString("view.new.password.notification.username.not.found"));
            }
        });
        add(
                new H1(res.getString("view.new.password.header")),
                new H4(res.getString("view.new.password.subheader")),
                name,
                newPassword,
                newPassword2,
                code,
                submit
        );
        name.focus();
    }
}
