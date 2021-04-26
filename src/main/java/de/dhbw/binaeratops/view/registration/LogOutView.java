package de.dhbw.binaeratops.view.registration;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;


@Route("logout")
@PageTitle("logout")
public class LogOutView extends Div {
    public LogOutView() {
        VaadinSession.getCurrent().getSession().invalidate();
        VaadinSession.getCurrent().close();
        UI.getCurrent().getPage().setLocation("login");
        UI.getCurrent().navigate("login");
    }
}
