package de.dhbw.binaeratops.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Oberfläche des Tabs 'Über uns'
 */
//@Route(value = "aboutUs",layout = MainView.class)
@PageTitle("Über Uns")
public class AboutUsView extends HorizontalLayout {

    /**
     * Konstruktor zum Erzeugen der View für den Tab 'Über uns'.
     */
    public AboutUsView() {
        super ();

        com.vaadin.flow.component.html.H1 l = new H1("Coming Soon ...");
        this.add ( l );

        this.setSizeFull ();
        this.setJustifyContentMode ( FlexComponent.JustifyContentMode.CENTER ); // Put content in the middle horizontally.
        this.setDefaultVerticalComponentAlignment ( FlexComponent.Alignment.CENTER ); // Put content in the middle vertically.
    }
}
