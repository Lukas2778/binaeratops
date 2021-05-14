package de.dhbw.binaeratops.service.api.permission;

import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;

import java.util.List;

/**
 * Interface für die Komponente "PermissionService".
 * <p>
 * Dieser Service stellt alle Funktionalitäten Berechtigungsverwaltung bereit.
 * </p>
 * <p>
 * Für Implementierung dieser Komponente siehe @{@link de.dhbw.binaeratops.service.impl.permission.PermissionService}.
 * </p>
 *
 * @author Timon Gartung
 */
public interface PermissionServiceI {

    /**
     * Spielberechtigungsanfrage wird an Dungeon-Master geschickt.
     * @param AUser Benutzer.
     * @param ADungeon Dungeon.
     */
    public void requestPermission(User AUser, Dungeon ADungeon);

    /**
     * Antwort auf die Spielberechtigungsanfrage eines Benutzers antworten.
     * @param AUser Benutzer.
     * @param ADungeon Dungeon.
     * @param APermission Boolean ob der Benutzer mitspielen darf.
     */
    public void answerRequest(User AUser, Dungeon ADungeon, boolean APermission);

    /**
     * Erteile em Spieler eine Spielberechtigung.
     * @param AUser Benutzer.
     * @param ADungeon Dungeon.
     */
    public void givePermission(User AUser, Dungeon ADungeon);
}
