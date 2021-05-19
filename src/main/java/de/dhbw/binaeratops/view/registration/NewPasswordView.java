package de.dhbw.binaeratops.view.registration;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.service.api.registration.AuthServiceI;
import de.dhbw.binaeratops.service.exceptions.FalseUserException;
import de.dhbw.binaeratops.view.TranslationProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Oberfläche für die Komponente "Neues Passwort vergeben".
 * <p>
 * Diese Ansicht stellt alle View-Komponenten für das Setzen des neuen Benutzerpasswortes bereit.
 * <p>
 * Dafür sendet sie die Benutzerangaben direkt an den entsprechenden Service, welcher dann eine E-Mail absendet.
 *
 * @author Matthias Rall, Lukas Göpel, Nicolas Haug
 */
@Route("setNewPassword")
@CssImport("./views/registration/language.css")
public class NewPasswordView extends VerticalLayout implements HasDynamicTitle {

    private ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());
    private TranslationProvider transProv = new TranslationProvider();

    @Autowired
    private AuthServiceI authServiceI;

    /**
     * Dies ist der Konstruktor, zum Erzeugen der Neues-Passwort-wird-gesetzt Seite.
     */
    public NewPasswordView() {
        TextField name = new TextField(res.getString("view.new.password.field.username"));
        PasswordField newPassword = new PasswordField(res.getString("view.new.password.field.new.password"));
        PasswordField newPassword2 = new PasswordField(res.getString("view.new.password.field.new.password.repeat"));
        IntegerField code = new IntegerField(res.getString("view.new.password.field.code"));
        Button submit = new Button(res.getString("view.new.password.button.submit"));

        setSizeFull();
        setAlignItems(Alignment.CENTER);

        submit.addClickShortcut(Key.ENTER);
        submit.addClickListener(e -> {
            try {
                if (newPassword.getValue().equals(newPassword2.getValue())) {
                    authServiceI.changePassword(name.getValue(), newPassword.getValue(), code.getValue());
                    UI.getCurrent().navigate("logout");
                } else
                    Notification.show(res.getString("view.new.password.notification.password.not.equal"));
            } catch (FalseUserException falseUserException) {
                Notification.show(res.getString("view.new.password.notification.username.not.found"));
            }
        });

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
        languageDiv.addClassName("language-newpassword");
        languageDiv.add(menuBar);

        Div newpasswordDiv = new Div();
        newpasswordDiv.addClassName("view");
        VerticalLayout verticalLogin = new VerticalLayout();
        verticalLogin.setAlignItems(Alignment.CENTER);
        verticalLogin.add(
                new H1(res.getString("view.new.password.header")),
                new H4(res.getString("view.new.password.subheader")),
                name,
                newPassword,
                newPassword2,
                code,
                submit
        );
        newpasswordDiv.add(verticalLogin);

        add(
                languageDiv,
                newpasswordDiv
        );
        name.focus();
    }

    @Override
    public String getPageTitle() {
        return res.getString("view.new.password.pagetitle");
    }
}
