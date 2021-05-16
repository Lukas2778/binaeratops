package de.dhbw.binaeratops.service.exceptions.parser;

import de.dhbw.binaeratops.service.impl.parser.UserMessage;

/**
 * Exception für den Fall, dass der Empfänger der Nachricht offline ist.
 *
 *
 * @author Nicolas Haug
 */
public class CmdScannerRecipientOfflineException extends CmdScannerException {
    private static final long serialVersionUID = 1L;

    private UserMessage um;

    /**
     * Standard-Exception für den Fall, dass der Empfänger offline ist.
     */
    public CmdScannerRecipientOfflineException(String ARecipent) {
        um = new UserMessage("error.parser.cmd.scanner.cmd.whisper.offline", ARecipent);
    }

    /**
     * Gibt die lokale Fehlermeldung als Benutzernachricht zurück.
     * @return Benutzernachricht.
     */
    public UserMessage getUserMessage() {
        return um;
    }
}
