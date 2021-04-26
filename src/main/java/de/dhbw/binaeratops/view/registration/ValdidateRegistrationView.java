package de.dhbw.binaeratops.view.registration;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.dhbw.binaeratops.service.api.registration.AuthServiceI;
import org.springframework.beans.factory.annotation.Autowired;

@Route("validateRegistration")
@PageTitle("Binäratops Code-Validierung")
public class ValdidateRegistrationView extends VerticalLayout {

    @Autowired
    AuthServiceI authServiceI;

    /**
     * Fenster zur Validierung des Benutzeraccounts über den per E-Mail versandten Code.
     */
    public ValdidateRegistrationView() {
        TextField name=new TextField("Benutzername");
        IntegerField code=new IntegerField("Code");
        Button submit=new Button("Account validieren");
        submit.addClickListener(e->{
                    authServiceI.confirm(name.getValue(),code.getValue());
                    UI.getCurrent().getPage().setLocation("login");
                });

        add(
                new H1("Bitte bestätige deine E-Mail Adresse!"),
                name,
                code,
                submit
        );
    }
}
