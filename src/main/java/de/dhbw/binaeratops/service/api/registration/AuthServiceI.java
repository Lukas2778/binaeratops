package de.dhbw.binaeratops.service.api.registration;

import de.dhbw.binaeratops.service.impl.registration.AuthException;
import de.dhbw.binaeratops.service.impl.registration.NotVerifiedException;
import de.dhbw.binaeratops.service.impl.registration.RegistrationException;

/**
 * Stellt die Schnittstelle zwischen der User-Authentifizierung und der Benutzeroberfläche bereit.
 */
public interface AuthServiceI {

    /**
     * Ist ein Benutzer an der Webapplikation registriert, kann er sich authentifizieren.
     * @param AName Hier muss der Benutzername eingegeben werden, mit dem der Benutzer sich registriert hat.
     * @param APassword Hier muss das Benutzerpasswort eingegeben werden, mit dem der Benutzer sich registriert hat.
     * @throws AuthException Ist der Benutzer nicht registriert, wird eine Exception geworfen.
     */
    void authenticate(String AName, String APassword) throws AuthException, NotVerifiedException;

    /**
     * Registrierung eines Benutzers an der Webapplikation.
     * @param AName Name, der für den Benutzer verwendet werden soll.
     * @param APassword Passwort, das für den Benutzer verwendet werden soll.
     * @param AEMail E-Mail Adresse, die für den Benutzer verwendet werden soll.
     * @throws RegistrationException Ist der eingegebene Benutzername schon vergeben, die E-Mail Adresse nicht valide oder das Passwort nicht möglich, so wird eine Exception geworfen.
     */
    void register(String AName, String APassword, String AEMail) throws RegistrationException;

    /**
     * Überprüfung des Bestätigungscodes, welcher per E-Mail an den Benutzer gesandt wurde.
     * @param AUserName Eingabe Benutzername, mit welchem sich der Benutzer an der Weboberfläche registriert hat.
     * @param ACode Eingabe des Codes, welcher per E-Mail an den Benutzer gesandt wurde.
     * @return Rückmeldung über die Codeübereinstimmung.
     */
    boolean confirm(String AUserName, int ACode);

    /**
     * Senden der E-Mail zur Bestätigung nach der Codeeingabe zur erfolgreichen Registrierung an der Webapplikation.
     * @param AUserId Übergabe der BenutzerID
     */
    void sendConfirmationEmail(long AUserId);

    /**
     * Bestätigung zur Passwortänderung eines Benutzers.
     * @param AUserId Übergabe der BenutzerID.
     * @param ANewPassword Neu zu setzendes Passwort.
     * @param ACode Bestätigungscode, welcher per E-Mail gesendet wurde.
     */
    void changePassword(long AUserId, String ANewPassword, int ACode);

    /**
     * Passwortänderung eines Benutzers.
     * @param AUserId Übergabe der BenutzerID.
     * @param ANewPassword Neu zu setzendes Passwort.
     * @param AOldPassword Altes Passwort zur Bestätigung.
     */
    void changePassword(long AUserId, String ANewPassword, String AOldPassword);
}
