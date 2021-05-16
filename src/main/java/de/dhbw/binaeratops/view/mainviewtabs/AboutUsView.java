package de.dhbw.binaeratops.view.mainviewtabs;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.server.VaadinSession;

import java.util.ResourceBundle;

/**
 * Oberfläche des Tabs 'Über uns'
 */
//@Route(value = "aboutUs",layout = MainView.class)
public class AboutUsView extends VerticalLayout implements HasDynamicTitle {

    private ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());

    H1 binTitle;
    String aboutText;
    Html html;

    /**
     * Konstruktor zum Erzeugen der View für den Tab 'Über uns'.
     */
    public AboutUsView() {
        binTitle = new H1(res.getString("view.about.us.headline"));
        aboutText = res.getString("view.about.us.text");
        html = new Html(aboutText);

        setSizeFull();
        add(binTitle, html);
    }

    @Override
    public String getPageTitle() {
        return res.getString("view.main.tab.about.us");
    }
}
