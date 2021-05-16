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
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.service.api.registration.AuthServiceI;
import de.dhbw.binaeratops.service.exceptions.AuthException;
import de.dhbw.binaeratops.service.exceptions.NotVerifiedException;
import de.dhbw.binaeratops.view.TranslationProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Login Fenster auf der Webapplikation.
 * Dies ist das erste Fenster, das der Benutzer sieht.
 */
//wenn keine Adresse zu einer bestimmten Seite in der URL eingegeben wird, wird sofort auf die Login-Seite verwiesen
@RouteAlias("")
@PWA(name = "Binäratops", shortName = "Binäratops", enableInstallPrompt = false)
@Route("login")
@CssImport("./views/registration/language.css")
public class LogInView extends VerticalLayout implements HasDynamicTitle {

    private ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());
    private TranslationProvider transProv = new TranslationProvider();


    /**
     * Dies ist der Konstruktor zum Erzeugen der Login Seite.
     *
     * @param authServiceI Übergabe des Authentifizierungsservices.
     */
    public LogInView(@Autowired AuthServiceI authServiceI) {
        //UI.getCurrent().addBeforeLeaveListener(e->System.out.println("test"));
        setId("SomeViewL");


        TextField name = new TextField(res.getString("view.login.field.username"));
        PasswordField password = new PasswordField(res.getString("view.login.field.password"));
        Button loginButton = new Button(res.getString("view.login.button.login"));

        HorizontalLayout changePasswRegisterLay = new HorizontalLayout();
        RouterLink changePasswLink = new RouterLink(res.getString("view.login.button.forgotpassword"), ForgotPasswordView.class);
        RouterLink registerLink = new RouterLink(res.getString("view.login.button.register"), RegisterView.class);
        changePasswRegisterLay.add(registerLink, new Label(" - "), changePasswLink);

        setSizeFull();
        //setJustifyContentMode(JustifyContentMode.CENTER);
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
        languageDiv.addClassName("language-login");
        languageDiv.add(menuBar);

        Div login = new Div();
        login.addClassName("view");
        VerticalLayout verticalLogin = new VerticalLayout();
        verticalLogin.setAlignItems(Alignment.CENTER);
        verticalLogin.add(
                new H1(res.getString("view.login.pagetitle")),
                new H4(res.getString("view.login.header")),
                name,
                password,
                loginButton,
                changePasswRegisterLay
        );
        login.add(verticalLogin);

        add(
                languageDiv,
                login
        );
        name.focus();
    }

    @Override
    public String getPageTitle() {
        return res.getString("view.login.pagetitle");
    }
}
