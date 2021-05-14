package de.dhbw.binaeratops.service.impl.registration;

import com.vaadin.flow.component.Component;

/**
 * Klasse für die autorisierten Routen.
 * <p>
 * Stellt alle Funktionalitäten für die Bewegungsfreiheit des Benutzers bereit.
 * <p>
 * Es besteht aus der Route, dem Namen der Route und der View-Komponente.
 *
 * @author Matthias Rall, Nicolas Haug
 */
public class AuthorizedRoute {
    private String route;
    private String name;
    private Class<? extends Component> view;

    /**
     * Konstruktor zum Erzeugen einer autorisierten Route.
     *
     * @param ARoute Route, die autorisiert werden soll.
     * @param AName  Name der Route.
     * @param AView  View-Komponente der Route.
     */
    public AuthorizedRoute(String ARoute, String AName, Class<? extends Component> AView) {
        this.route = ARoute;
        this.name = AName;
        this.view = AView;
    }

    /**
     * Gibt die autorisierte Route zurück.
     *
     * @return Autorisierte Route.
     */
    public String getRoute() {
        return route;
    }

    /**
     * Setzt die autorisierte Route.
     *
     * @param ARoute Autorisierte Route.
     */
    public void setRoute(String ARoute) {
        this.route = ARoute;
    }

    /**
     * Gibt den Namen der Route zurück.
     *
     * @return Name der Route.
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen der Route.
     *
     * @param AName  Name der Route.
     */
    public void setName(String AName) {
        this.name = AName;
    }

    /**
     * Gibt die Klasse der View-Komponente zurück.
     *
     * @return Klasse der View-Komponente.
     */
    public Class<? extends Component> getView() {
        return view;
    }

    /**
     * Setzt die Klasse der View-Komponente.
     *
     * @param AView Klasse der View-Komponente.
     */
    public void setView(Class<? extends Component> AView) {
        this.view = AView;
    }
}
