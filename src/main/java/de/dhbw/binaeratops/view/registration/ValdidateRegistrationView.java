package de.dhbw.binaeratops.view.registration;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.service.api.registration.AuthServiceI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ResourceBundle;

/**
 * Fenster zur Validierung des Benutzeraccounts über den per E-Mail versandten Code.
 */
@Route("validateRegistration")
public class ValdidateRegistrationView extends VerticalLayout {

    private final ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());


    /**
     * Dies ist der Konstruktor, zum Erzeugen der Validierungs-Registrierungs Seite.
     * @param AAuthServiceI AuthServiceI.
     */
    public ValdidateRegistrationView(@Autowired AuthServiceI AAuthServiceI) {
        // Titel der Seite
        UI current = UI.getCurrent();
        current.getPage().setTitle(res.getString("view.validate.registration.pagetitle"));

        TextField name=new TextField(res.getString("view.validate.registration.field.username"));
        IntegerField code=new IntegerField(res.getString("view.validate.registration.field.code"));
        Button submit=new Button(res.getString("view.validate.registration.button.submit"));

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        submit.addClickShortcut(Key.ENTER);
        submit.addClickListener(e->{
            if(AAuthServiceI.confirm(name.getValue(),code.getValue())){
                UI.getCurrent().getPage().setLocation("login");
            }
            else{
                Notification.show(res.getString("view.validate.registration.notification.check.code"));
            }
        });

        add(
                new H1(res.getString("view.validate.registration.header")),
                new H4(res.getString("view.validate.registration.subheader1")),
                new H4(res.getString("view.validate.registration.subheader2")),
                name,
                code,
                submit,
                new RouterLink(res.getString("view.validate.registration.link.login"),LogInView.class)
        );
        name.focus();
    }
}
