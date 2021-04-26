package de.dhbw.binaeratops.service.impl.registration;

import com.vaadin.flow.component.Component;

public class AuthorizedRoute {
    private String rout;
    private String name;
    private Class<? extends Component> view;

    public AuthorizedRoute(String rout, String name, Class<? extends Component> view) {
        this.rout = rout;
        this.name = name;
        this.view = view;
    }

    public String getRout() {
        return rout;
    }

    public void setRout(String rout) {
        this.rout = rout;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<? extends Component> getView() {
        return view;
    }

    public void setView(Class<? extends Component> view) {
        this.view = view;
    }
}
