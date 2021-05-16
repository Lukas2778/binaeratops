package de.dhbw.binaeratops.model;

import de.dhbw.binaeratops.model.entitys.User;

public class KickUser {

    public KickUser(User user) {
        this.user = user;
    }

    User user;

    public User getUser() {
        return user;
    }
}
