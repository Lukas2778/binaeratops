package de.dhbw.binaeratops.model;

import de.dhbw.binaeratops.model.entitys.User;

public class KickUser {

    User user;
    boolean kick;
    public KickUser(User user, boolean kick) {
        this.user = user;
        this.kick= kick;
    }

    public User getUser() {
        return user;
    }

    public boolean getKick(){return kick;}
}
