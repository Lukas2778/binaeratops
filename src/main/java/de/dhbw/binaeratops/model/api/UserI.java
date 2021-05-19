package de.dhbw.binaeratops.model.api;

import de.dhbw.binaeratops.model.entitys.Avatar;
import de.dhbw.binaeratops.model.entitys.Dungeon;

import java.util.List;

/**
 * Schnittstelle für einen Benutzer.
 * <p>
 * Sie stellt alle Funktionalitäten zum Umgang mit einem Benutzer bereit.
 * <p>
 * Für Datenbankoperationen siehe {@link de.dhbw.binaeratops.model.repository.UserRepositoryI}.
 * <p>
 * Für die Implementierung siehe {@link de.dhbw.binaeratops.model.entitys.User}.
 *
 * @author Nicolas Haug
 */
public interface UserI {

    /**
     * Überprüft, ob das eingegebene Passwort gehast dem des Benutzers entspricht.
     *
     * @param APassword Passwort, das zu überpüfen ist.
     * @return Wahrheitswert, ob Passwort übereinstimmt.
     */
    boolean checkPassword(String APassword);

    /**
     * Gibt die Benutzer-ID des Benutzers zurück.
     *
     * @return Benutzer-ID des Benutzers.
     */
    Long getUserId();

    /**
     * Setzt die Benutzer-ID des Benutzers.
     *
     * @param AUserId Zu setzende Benutzer-ID.
     */
    void setUserId(Long AUserId);

    /**
     * Gibt den Benutzernamen des Benutzers zurück.
     *
     * @return Benutzername des Benutzers.
     */
    String getName();

    /**
     * Setzt den Benutzernamen des Benutzers.
     *
     * @param AUsername Zu setzender Benutzername.
     */
    void setName(String AUsername);

    /**
     * Gibt die E-Mail des Benutzers zurück.
     *
     * @return E-Mail des Benutzers.
     */
    String getEmail();

    /**
     * Setzt die E-Mail des Benutzers.
     *
     * @param AEmail Zu setzende E-Mail.
     */
    void setEmail(String AEmail);

    /**
     * Gibt den Passwort-Hash des Benutzerpasswortes zurück.
     *
     * @return Passwort-Hash des Benutzerpasswortes.
     */
    String getPasswordHash();

    /**
     * Setzt das Benutzerpasswortes des Benutzers.
     *
     * @param APassword Zu setzendes Passwirt des Benutzerpasswortes.
     */
    void setPassword(String APassword);

    /**
     * Gibt den Verifizierungscode des Benutzers zurück.
     *
     * @return Verifizierungscode des Benutzers.
     */
    Integer getCode();

    /**
     * Setzt den Verifizierungscode des Benutzers.
     *
     * @param ACode Verifizierungscode des Benutzers.
     */
    void setCode(Integer ACode);

    /**
     * Gibt den Verifizierungsstatus des Benutzers zurück.
     *
     * @return Verifizierungsstatus des Benutzers.
     */
    Boolean isVerified();

    /**
     * Setzt den Verifizierungsstatus des Benutzers.
     *
     * @param AIsVerified Zu setzender Verifizierungsstatus.
     */
    void setVerified(Boolean AIsVerified);


    /**
     * Dungeon, indem sich der Benutzer zurzeit aufhält.
     *
     * @return Aktueller Dungeon.
     */
    Dungeon getCurrentDungeon();

    /**
     * Aktuellen Dunegon setzen.
     *
     * @param ACurrentDungeon Aktuellen Dungeon der gesetzt werden soll.
     */
    void setCurrentDungeon(Dungeon ACurrentDungeon);

    /**
     * Aktuellen Dungeon zurücksetzen.
     */
    void removeCurrentDungeon();

    /**
     * Gibt die Liste der Spieleravatare zurück.
     *
     * @return Avatarliste.
     */
    List<Avatar> getAvatars();

    /**
     * Fügt einen Avatar dem Benutzer hinzu.
     *
     * @param AAvatar Avatar, der hinzugefügt werden soll.
     */
    void addAvatar(Avatar AAvatar);

    /**
     * Entfernt einen Avatar des Benutzers.
     *
     * @param AAvatar Avatar, der entfernt werden soll.
     */
    void removeAvatar(Avatar AAvatar);

    /**
     * Gibt die Liste der eigenen Dungeons zurück.
     *
     * @return Dungeonliste.
     */
    List<Dungeon> getMyDungeons();

    /**
     * Fügt dem Benutzer einen Dungeon hinzu.
     *
     * @param ADungeon Dungeon, der hinzugefügt werden soll.
     */
    void addDungeon(Dungeon ADungeon);

    /**
     * Entfernt den Dungeon des Benutzers.
     *
     * @param ADungeon Dungeon des Benutzers.
     */
    void removeDungeon(Dungeon ADungeon);
}
