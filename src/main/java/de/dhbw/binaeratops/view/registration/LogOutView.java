package de.dhbw.binaeratops.view.registration;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

/**
 * Diese Seite ist nicht síchtbar.
 * Hier wird der Benutzer ausgeloggt und auf die Login-Seite weitergeleitet.
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
