package de.dhbw.binaeratops.model.api;

/**
 * Schnittstelle für einen Benutzer.
 *
 * Sie stellt alle Funktionalitäten zum Umgang mit einem Benutzer bereit.
 *
 * Für Datenbankoperationen siehe {@link de.dhbw.binaeratops.model.repository.UserRepository}.
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
    String getName();

    /**
     * Setzt den Benutzernamen des Benutzers.
     *
     * @param AName Zu setzender Benutzername.
     */
    void setName(String AName);

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
}
