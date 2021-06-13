package de.dhbw.binaeratops.view.registration;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.service.api.registration.AuthServiceI;
import de.dhbw.binaeratops.service.exceptions.RegistrationException;
import de.dhbw.binaeratops.view.TranslationProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Oberfläche für die Komponente "Registrierung".
 * <p>
 * Diese Ansicht stellt alle View-Komponenten für das Registrieren eines Benutzers bereit.
 * <p>
 * Dafür sendet sie die Benutzerangaben direkt an den entsprechenden Service,
 * welcher dann eine E-Mail an den Benutzer schickt.
 *
 * @author Matthias Rall, Lukas Göpel, Nicolas Haug
 */

/**
 * Registrierungs Fenster. Erscheint, wenn auf den Button 'Registrieren' geklickt wird.
 * Diese Seite wird angezeigt, wenn der Benutzer seinen Benutzernamen eingegeben hat und die E-Mail an ihn gesendet wurde.
 */
@Route("register")
@CssImport("./views/registration/language.css")
public class RegisterView extends VerticalLayout implements HasDynamicTitle {

    private ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());
    private TranslationProvider transProv = new TranslationProvider();

    @Autowired
    AuthServiceI authServiceI;

    /**
     * Dies ist der Konstruktor, zum Erzeugen der Registrierungsseite.
     */
    public RegisterView() {
        TextField name = new TextField(res.getString("view.register.field.username"));
        TextField eMail = new TextField(res.getString("view.register.field.email"));
        PasswordField password = new PasswordField(res.getString("view.register.field.password"));
        PasswordField verPassword = new PasswordField(res.getString("view.register.field.password.repeat"));
        Button submit = new Button(res.getString("view.register.button.register"));

        HorizontalLayout validLoginLay = new HorizontalLayout();
        RouterLink loginLink = new RouterLink(res.getString("view.register.link.login"), LogInView.class);
        RouterLink validLink = new RouterLink(res.getString("view.register.link.validation"), ValidateRegistrationView.class);
        validLoginLay.add(loginLink, new Label(" - "), validLink);

        setAlignItems(Alignment.CENTER);

        submit.addClickShortcut(Key.ENTER);
        submit.addClickListener(e ->
                {
                    checkRegistration(name.getValue(), eMail.getValue(), password.getValue(), verPassword.getValue());
                }
        );

        /* Sprache */
        Select<Locale> languageSelect = new Select<>();
        languageSelect.setLabel(res.getString("view.main.select"));
        languageSelect.setPlaceholder(res.getString("view.main.select"));
        List<Locale> locales = transProv.getProvidedLocales();

        languageSelect.setItemLabelGenerator(Locale::getDisplayLanguage);
        languageSelect.setItems(locales);
        languageSelect.setValue(VaadinSession.getCurrent().getLocale());

        languageSelect.addValueChangeListener(e -> {
            if (VaadinSession.getCurrent().getLocale() == Locale.US) {
                VaadinSession.getCurrent().setLocale(Locale.GERMANY);
            } else if (VaadinSession.getCurrent().getLocale() == Locale.GERMANY) {
                VaadinSession.getCurrent().setLocale(Locale.US);
            }
            res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());
            UI.getCurrent().getPage().reload();
        });

        /* Sprache Icon */
        Icon icon = new Icon(VaadinIcon.GLOBE);
        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE);
        menuBar.setOpenOnHover(true);
        MenuItem menuItem = menuBar.addItem(icon);
        SubMenu subMenu = menuItem.getSubMenu();
        subMenu.addItem(languageSelect);

        Div languageDiv = new Div();
        languageDiv.addClassName("language-register");
        languageDiv.add(menuBar);

        Div register = new Div();
        register.addClassName("view");
        VerticalLayout verticalLogin = new VerticalLayout();
        verticalLogin.setAlignItems(Alignment.CENTER);
        verticalLogin.add(
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
        register.add(verticalLogin);

        add(
                languageDiv,
                register
        );
        name.focus();
    }

    /**
     * Überprüfung der Eingaben der Registrierung.
     *
     * @param AName        Übergabe des eingegebenen Benutzernamen.
     * @param AEMail       Übergabe der eingegebenen E-Mail Adresse.
     * @param APassword    Übergabe des eingegebenen Passworts.
     * @param AVerPassword Übergabe des eingegebenen Passworts zur Verifizierung.
     */
    private void checkRegistration(String AName, String AEMail, String APassword, String AVerPassword) {
        if (AName.trim().isEmpty()) {
            showErrorNotification(new Span(res.getString("view.register.notification.enter.username")));
        } else if (APassword.isEmpty()) {
            showErrorNotification(new Span(res.getString("view.register.notification.enter.password")));
        } else if (!APassword.equals(AVerPassword)) {
            showErrorNotification(new Span(res.getString("view.register.notification.password.not.equal")));
        } else {
            try {
                authServiceI.register(AName, APassword, AEMail);
                UI.getCurrent().getPage().setLocation("validateRegistration");
            } catch (RegistrationException e) {
                showErrorNotification(new Span(res.getString("view.register.notification.username.already.exists")));
            } catch (Exception e) {
                showErrorNotification(new Span(res.getString("view.register.notification.check.email")));
            }

        }
    }

    private void showErrorNotification(Span ALabel) {
        Notification notification = new Notification();
        Button closeButton = new Button("", e -> {
            notification.close();
        });
        closeButton.setIcon(new Icon(VaadinIcon.CLOSE));
        closeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        notification.add(ALabel, closeButton);
        ALabel.getStyle().set("margin-right", "0.3rem");
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.setDuration(10000);
        notification.setPosition(Notification.Position.TOP_END);
        notification.open();
    }

    @Override
    public String getPageTitle() {
        return res.getString("view.register.pagetitle");
    }
}
