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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import de.dhbw.binaeratops.service.api.registration.AuthServiceI;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Fenster zur Validierung des Benutzeraccounts über den per E-Mail versandten Code.
 */
@Route("validateRegistration")
@PageTitle("Binäratops Code-Validierung")
public class ValdidateRegistrationView extends VerticalLayout {

    @Autowired
    AuthServiceI authServiceI;

    /**
     * Dies ist der Konstruktor, zum Erzeugen der Validierungs-Registrierungs Seite.
     */
    public ValdidateRegistrationView() {
        TextField name=new TextField("Benutzername");
        IntegerField code=new IntegerField("Code");
        Button submit=new Button("Account validieren");

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        submit.addClickShortcut(Key.ENTER);
        submit.addClickListener(e->{
            if(authServiceI.confirm(name.getValue(),code.getValue())){
                UI.getCurrent().getPage().setLocation("login");
            }
            else{
                Notification.show("Bitte überprüfe den eingegebenen Code!");
            }
        });

        add(
                new H1("E-Mail Adresse bestätigen"),
                new H4("Wir haben dir soeben eine E-Mail mit deinem persönlichen Zugangscode an die von dir eingegebene E-Mail Adresse gesendet."),
                new H4("Bitte gib diesen Code zur Verifikation deiner Identität hier ein."),
                name,
                code,
                submit,
                new RouterLink("Anmelden",LogInView.class)
        );
    }
}
