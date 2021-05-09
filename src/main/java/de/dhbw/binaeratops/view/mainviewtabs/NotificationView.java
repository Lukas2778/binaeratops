package de.dhbw.binaeratops.view.mainviewtabs;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Oberfläche des Tabs 'Mitteilungen'
 */
//@Route(value = "notification",layout = MainView.class)
@PageTitle("Mitteilungen")
public class NotificationView extends HorizontalLayout {
    /**
     * Konstruktor zum Erzeugen der View für den Tab 'Mitteilungen'.
     */
    public NotificationView() {
        com.vaadin.flow.component.html.H1 l = new H1("Coming Soon ...");
        this.add ( l );

        this.setSizeFull ();
        this.setJustifyContentMode ( FlexComponent.JustifyContentMode.CENTER ); // Put content in the middle horizontally.
        this.setDefaultVerticalComponentAlignment ( FlexComponent.Alignment.CENTER ); // Put content in the middle vertically.
    }
}
