package de.dhbw.binaeratops.view.mychat;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

/**
 * @author Lukas Göpel
 * Date: 10.06.2021
 * Time: 09:33
 */

@Route("mychat")
public class TestView extends VerticalLayout {
    public TestView(){
        Button lol = new Button("uff");
        add(lol);
    }
}
