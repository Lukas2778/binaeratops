package de.dhbw.binaeratops.model.mychat;

import java.security.Principal;

/**
 * @author Lukas Göpel
 * Date: 10.06.2021
 * Time: 10:20
 */
public class User implements Principal {
    String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
