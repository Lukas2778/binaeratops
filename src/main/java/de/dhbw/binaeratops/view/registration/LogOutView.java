package de.dhbw.binaeratops.view.registration;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

/**
 * Oberfläche für die Komponente "Abmeldung".
 * <p>
 * Diese Ansicht ist nicht sichtbar.
 * <p>
 * Sie beendet die aktuelle Benutzersitzung und leitet ihn auf die Seite zur Anmeldung weiter.
 *
 * @author Matthias Rall, Lukas Göpel, Nicolas Haug
 */
@Route("logout")
@PageTitle("logout")
public class LogOutView extends Div {
    /**
     * Dies ist der Konstruktor, zum Erzeugen der Logout Seite.
     */
    public LogOutView() {
        VaadinSession.getCurrent().getSession().invalidate();
        VaadinSession.getCurrent().close();
        UI.getCurrent().navigate("login");
    }
}
