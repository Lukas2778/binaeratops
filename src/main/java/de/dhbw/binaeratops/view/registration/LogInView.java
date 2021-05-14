package de.dhbw.binaeratops.view.registration;

import com.vaadin.flow.component.ClientCallable;
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
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.service.api.registration.AuthServiceI;
import de.dhbw.binaeratops.service.exceptions.AuthException;
import de.dhbw.binaeratops.service.exceptions.NotVerifiedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ResourceBundle;

/**
 * Login Fenster auf der Webapplikation.
 * Dies ist das erste Fenster, das der Benutzer sieht.
 *
 */
//wenn keine Adresse zu einer bestimmten Seite in der URL eingegeben wird, wird sofort auf die Login-Seite verwiesen
@RouteAlias("")
@PWA(name = "Binäratops", shortName = "Binäratops", enableInstallPrompt = false)
@Route("login")
public class LogInView extends VerticalLayout implements HasDynamicTitle, PageConfigurator {

    private final ResourceBundle res = ResourceBundle.getBundle("language");

    /**
     * Dies ist der Konstruktor zum Erzeugen der Login Seite.
     * @param authServiceI Übergabe des Authentifizierungsservices.
     */
    public LogInView(@Autowired AuthServiceI authServiceI) {
        //UI.getCurrent().addBeforeLeaveListener(e->System.out.println("test"));
        setId("SomeViewL");



        TextField name = new TextField(res.getString("view.login.field.username"));
        PasswordField password = new PasswordField(res.getString("view.login.field.password"));
        Button loginButton = new Button(res.getString("view.login.button.login"));

        HorizontalLayout changePasswRegisterLay=new HorizontalLayout();
        RouterLink changePasswLink=new RouterLink(res.getString("view.login.button.forgotpassword"), ForgotPasswordView.class);
        RouterLink registerLink = new RouterLink(res.getString("view.login.button.register"), RegisterView.class);
        changePasswRegisterLay.add(registerLink,new Label(" - "),changePasswLink);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        loginButton.addClickShortcut(Key.ENTER);
        loginButton.addClickListener(e ->
        {
            try {
                if (VaadinSession.getCurrent().getAttribute(User.class) != null &&
                        VaadinSession.getCurrent().getAttribute(User.class).getName().equals(name.getValue())) {
                    Notification.show(res.getString("view.login.notification.already.logged.in"));
                } else {
                    authServiceI.authenticate(name.getValue(), password.getValue());
                }
                UI.getCurrent().navigate("aboutUs");
            } catch (AuthException authException) {
                Notification.show(res.getString("view.login.notification.invalid.password"));
            } catch (NotVerifiedException notVerifiedException) {
                Notification.show(res.getString("view.login.notification.not.verified"));
                UI.getCurrent().navigate("validateRegistration");
            }
        });
        add(
                new H1(res.getString("view.login.pagetitle")),
                new H4(res.getString("view.login.header")),
                name,
                password,
                loginButton,
                changePasswRegisterLay
        );
        name.focus();
    }

    @Override
    public String getPageTitle() {
        return res.getString("view.login.pagetitle");
    }

    @Override
    public void configurePage(InitialPageSettings initialPageSettings) {
        String script = "window.onbeforeunload = function (e) { var e = e || window.event; document.getElementById(\"SomeViewL\").$server.browserIsLeavingLogin(); return; };";
        initialPageSettings.addInlineWithContents(InitialPageSettings.Position.PREPEND, script, InitialPageSettings.WrapMode.JAVASCRIPT);
    }

    @ClientCallable
    public void browserIsLeavingLogin() {
        System.out.println("Called browserIsLeavingLogin");
    }
}
