package de.dhbw.binaeratops.service.api.registration;

import de.dhbw.binaeratops.service.impl.registration.AuthException;
import de.dhbw.binaeratops.service.impl.registration.RegistrationException;


public interface AuthServiceI {

    void authenticate(String name, String password) throws AuthException;

    void register(String name, String password, String eMail) throws RegistrationException;

    boolean confirm(String userName, int code);

    void sendConfirmationEmail(long userID);

    void changePassword(long userID, String newPassword, int code);

    void changePassword(long userID, String newPassword, String oldPassword);
}
