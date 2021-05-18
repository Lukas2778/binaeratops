package de.dhbw.binaeratops.model.actions;

import de.dhbw.binaeratops.model.entitys.User;

/**
 * Transportklasse für die KickUserAction.
 *
 * @author Timon Gartung, Matthias Rall, Nicolas Haug
 */
public class KickUserAction {

    User user;
    boolean kick;

    /**
     * Standardkonstruktor zum Erzeugen der KickUserAction mit dem Spieler und dem Wahrheitswert, ob gekickt werden soll.
     *
     * @param user Spieler
     * @param kick Wahrheitswert, ob Spieler gekickt werden soll.
     */
    public KickUserAction(User user, boolean kick) {
        this.user = user;
        this.kick = kick;
    }

    /**
     * Gibt den Benutzer zurück.
     *
     * @return Benutzer.
     */
    public User getUser() {
        return user;
    }

    /**
     * Gibt den Wahrheitswert zurück, ob Spieler gekickt werden soll.
     *
     * @return Wahrheitswert, ob Spieler gekickt werden soll.
     */
    public boolean getKick() {
        return kick;
    }
}
