package de.dhbw.binaeratops.service.exceptions.parser;

import de.dhbw.binaeratops.service.impl.parser.UserMessage;

/**
 * Exception für den Fall, dass der Empfänger der Nachricht ungültig ist.
 *
 *
 * @author Nicolas Haug
 */
public class CmdScannerInvalidRecipientException extends CmdScannerException {
    private static final long serialVersionUID = 1L;

    private UserMessage um;

    /**
     * Standard-Exception für den Fall, dass der Empfänger ungültig ist.
     */
    public CmdScannerInvalidRecipientException() {
        um = new UserMessage("error.parser.cmd.scanner.cmd.whisper");
    }

    /**
     * Gibt die lokale Fehlermeldung als Benutzernachricht zurück.
     * @return Benutzernachricht.
     */
    public UserMessage getUserMessage() {
        return um;
    }
}
