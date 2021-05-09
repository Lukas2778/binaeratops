package de.dhbw.binaeratops.view.mainviewtabs;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Oberfläche des Tabs 'Über uns'
 */
//@Route(value = "aboutUs",layout = MainView.class)
@PageTitle("Über Uns")
public class AboutUsView extends VerticalLayout {

    H1 binTitle;
    String aboutText;
    Html html;

    /**
     * Konstruktor zum Erzeugen der View für den Tab 'Über uns'.
     */
    public AboutUsView() {
        binTitle=new H1("Willkommen bei Binäratops");
        aboutText= "<div>Du hast dich bei unserem Binäratops Multi-User-Dungeon (MUD) registriert. Das Ziel" +
                " dieses Projekts ist es, eine Plattform zum Erstellen und Spielen personalisierter" +
                " MUDs zu schaffen. Du kannst deinen eigenen" +
                " Dungeon erstellen und mit anderen teilen.<br>Während des Spiels kannst du dich im Chat mit anderen" +
                " Dungeon-Enthusiasten unterhalten, die dir über den Weg laufen.<br>" +
                "Wir wünschen dir viel Spaß!</div>";
        html=new Html(aboutText);

        setSizeFull();
        add(binTitle,html);
    }
}
