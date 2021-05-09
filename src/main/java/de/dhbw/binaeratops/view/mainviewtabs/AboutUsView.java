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



        setSizeFull();
        add(binTitle);
    }
}
