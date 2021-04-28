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
     *
     * @return Wahrheitswert, ob Passwort übereinstimmt.
     */
    boolean checkPassword(String APassword);

    /**
     * Gibt die Benutzer-ID des Benutzers zurück.
     *
     * @return Benutzer-ID des Benutzers.
     */
    Long getId();

    /**
     * Setzt die Benutzer-ID des Benutzers.
     *
     * @param AId Zu setzende Benutzer-ID.
     */
    void setId(Long AId);

    /**
     * Gibt den Benutzernamen des Benutzers zurück.
     *
     * @return Benutzername des Benutzers.
     */
    String getUsername();

    /**
     * Setzt den Benutzernamen des Benutzers.
     *
     * @param AUsername Zu setzender Benutzername.
     */
    void setUsername(String AUsername);

    /**
     * Gibt die E-Mail des Benutzers zurück.
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
     * Setzt den Passwort-Hash des Benutzerpasswortes.
     *
     * @param APasswordHash Zu setzender Passwort-Hash des Benutzerpasswortes.
     */
    void setPasswordHash(String APasswordHash);

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
     * Gibt die Liste der Spieleravatare zurück.
     * @return Avatarliste.
     */
    List<Avatar> getAvatars();

    /**
     * Gibt die Liste der eigenen Dungeons zurück.
     * @return Dungeonliste.
     */
    List<Dungeon> getMyDungeons();
}
