package de.dhbw.binaeratops.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("dummy")
@PageTitle("dummy")
public class DummyView extends Div {
    public DummyView() {
        add(new TextArea("Dummy"));
    }
}
