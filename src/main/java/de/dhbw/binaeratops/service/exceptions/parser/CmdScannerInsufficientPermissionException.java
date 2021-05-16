package de.dhbw.binaeratops.service.exceptions.parser;

import de.dhbw.binaeratops.service.impl.parser.UserMessage;

/**
 * Fehler beim Parsen, da keine Berechtiung diesen Befehl auszuführen.
 *
 * @author Nicolas Haug
 */
public class CmdScannerInsufficientPermissionException extends CmdScannerException {
    private static final long serialVersionUID = 1L;

    private UserMessage um;

    /**
     * Standard-Exception für den Fall, dass die Berechtigung nicht ausreicht.
     * @param AInvalidCommand
     */
    public CmdScannerInsufficientPermissionException(String AInvalidCommand) {
        super(AInvalidCommand);
        um = new UserMessage("error.parser.cmd.scanner.insufficient.permission", AInvalidCommand);
    }

    /**
     * Gibt die lokale Fehlermeldung als Benutzernachricht zurück.
     * @return Benutzernachricht.
     */
    public UserMessage getUserMessage() {
        return um;
    }
}
