package de.dhbw.binaeratops.view.mainviewtabs;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.server.VaadinSession;

import java.util.ResourceBundle;

/**
 * Tab-Oberfläche für die Komponente "Mitteilungen" des Hauptmenüs.
 * <p>
 * Diese Ansicht stellt alle View-Komponenten für die Mitteilungen eines Benutzers bereit.
 * <p>
 * Dafür sendet sie die Benutzerangaben direkt an den entsprechenden Service.
 *
 * @author Pedro Treuer, Timon Gartung, Nicolas Haug, Lars Rösel, Mattias Rall, Lukas Göpel
 */
public class NotificationView extends HorizontalLayout implements HasDynamicTitle {
    private ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());

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

    @Override
    public String getPageTitle() {
        return res.getString("view.main.tab.notification.view");
    }
}
