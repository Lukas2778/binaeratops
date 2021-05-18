package de.dhbw.binaeratops.service.exceptions.parser;

import de.dhbw.binaeratops.service.impl.parser.UserMessage;

/**
 * Exception für den Fall, dass noch die Antwort einer Anfrage aussteht.
 *
 *
 * @author Nicolas Haug
 */
public class CmdScannerAlreadyRequestedException extends CmdScannerException {
    private static final long serialVersionUID = 1L;

    private UserMessage um;

    /**
     * Standard-Exception für den Fall, dass noch die Antwort einer Anfrage aussteht.
     */
    public CmdScannerAlreadyRequestedException() {
        um = new UserMessage("error.parser.cmd.scanner.already.requested");
    }

    /**
     * Gibt die lokale Fehlermeldung als Benutzernachricht zurück.
     * @return Benutzernachricht.
     */
    public UserMessage getUserMessage() {
        return um;
    }
}
