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
 * Diese Seite wird aufgerufen, wenn der Benutzer auf den 'Passwort vergessen'-Link klickt.
 */
@Route("forgotPassword")
@CssImport("./views/registration/language.css")
public class ForgotPasswordView extends VerticalLayout implements HasDynamicTitle {

    private ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());
    private TranslationProvider transProv = new TranslationProvider();

    @Autowired
    AuthServiceI authServiceI;

    /**
     * Dies ist der Konstruktor, zum Erzeugen der Passwort-Vergessen Seite.
     */
    public ForgotPasswordView() {
        TextField name = new TextField(res.getString("view.forgot.password.field.username"));
        Button submit = new Button(res.getString("view.forgot.password.button.send.email"));

        setSizeFull();
        setAlignItems(Alignment.CENTER);

        submit.addClickShortcut(Key.ENTER);
        submit.addClickListener(e->
        {
            try {
                authServiceI.sendConfirmationEmail(name.getValue());
                UI.getCurrent().navigate("setNewPassword");
            } catch (FalseUserException falseUserException) {
                Notification.show(res.getString("view.forgot.password.notification.invalid.username"));
            }
        });

        /* Sprache */
        Select<Locale> languageSelect = new Select<>();
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
        languageDiv.addClassName("language-forgotpassword");
        languageDiv.add(menuBar);

        Div forgotPasswordDiv = new Div();
        forgotPasswordDiv.addClassName("view");
        VerticalLayout verticalLogin = new VerticalLayout();
        verticalLogin.setAlignItems(Alignment.CENTER);
        verticalLogin.add(
                new H1(res.getString("view.forgot.password.headline")),
                new H4(res.getString("view.forgot.password.h41")),
                new H4(res.getString("view.forgot.password.h42")),
                name,
                submit
        );
        forgotPasswordDiv.add(verticalLogin);


        add(
                languageDiv,
                forgotPasswordDiv
        );
        name.focus();
    }

    @Override
    public String getPageTitle() {
        return res.getString("view.forgot.password.pagetitle");
    }
}
