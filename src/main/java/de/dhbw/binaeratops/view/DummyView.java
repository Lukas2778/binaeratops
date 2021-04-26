package de.dhbw.binaeratops.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Oberfläche einer Dummyview
 */
@Route(value = "dummy",layout = MainView.class)
@PageTitle("dummy")
public class DummyView extends Div {
    /**
     * Beispielskonstruktor zum Erzeugen einer View.
     */
    public DummyView() {
        add(new TextArea("Dummy"));
    }
}
