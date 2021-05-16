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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.service.api.registration.AuthServiceI;
import de.dhbw.binaeratops.view.TranslationProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Fenster zur Validierung des Benutzeraccounts über den per E-Mail versandten Code.
 */
@Route("validateRegistration")
@CssImport("./views/registration/language.css")
public class ValdidateRegistrationView extends VerticalLayout implements HasDynamicTitle {

    private ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());
    private TranslationProvider transProv = new TranslationProvider();

    /**
     * Dies ist der Konstruktor, zum Erzeugen der Validierungs-Registrierungs Seite.
     *
     * @param AAuthServiceI AuthServiceI.
     */
    public ValdidateRegistrationView(@Autowired AuthServiceI AAuthServiceI) {
        TextField name = new TextField(res.getString("view.validate.registration.field.username"));
        IntegerField code = new IntegerField(res.getString("view.validate.registration.field.code"));
        Button submit = new Button(res.getString("view.validate.registration.button.submit"));

        setAlignItems(Alignment.CENTER);

        submit.addClickShortcut(Key.ENTER);
        submit.addClickListener(e -> {
            if (AAuthServiceI.confirm(name.getValue(), code.getValue())) {
                UI.getCurrent().getPage().setLocation("login");
            } else {
                Notification.show(res.getString("view.validate.registration.notification.check.code"));
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
        languageDiv.addClassName("language-validate");
        languageDiv.add(menuBar);

        Div validate = new Div();
        validate.addClassName("view");
        VerticalLayout verticalLogin = new VerticalLayout();
        verticalLogin.setAlignItems(Alignment.CENTER);
        verticalLogin.add(
                new H1(res.getString("view.validate.registration.header")),
                new H4(res.getString("view.validate.registration.subheader1")),
                new H4(res.getString("view.validate.registration.subheader2")),
                name,
                code,
                submit,
                new RouterLink(res.getString("view.validate.registration.link.login"), LogInView.class)
        );
        validate.add(verticalLogin);

        add(
                languageDiv,
                validate
        );
        name.focus();
    }

    @Override
    public String getPageTitle() {
        return res.getString("view.validate.registration.pagetitle");
    }
}
