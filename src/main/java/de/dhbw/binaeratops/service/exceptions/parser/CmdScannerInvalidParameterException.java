package de.dhbw.binaeratops.service.exceptions.parser;

import de.dhbw.binaeratops.service.impl.parser.UserMessage;
/**
 * Ungültigen Parameter übergeben, Parsefehler.
 *
 *
 * @author Nicolas Haug
 */
public class CmdScannerInvalidParameterException extends CmdScannerException {
    private static final long serialVersionUID = 1L;

    private UserMessage um;

    /**
     * Standard-Exception für den Fall, dass der übergebene Parameter nicht ausgewertet werden konnte.
     * @param AInvalidCommand Falsches Kommando das eingegeben wurde.
     */
    public CmdScannerInvalidParameterException(String AInvalidCommand) {
        super(AInvalidCommand);
        um = new UserMessage("error.parser.cmd.scanner.cmd.examine", AInvalidCommand);
    }

    /**
     * Gibt die lokale Fehlermeldung als Benutzernachricht zurück.
     * @return Benutzernachricht.
     */
    public UserMessage getUserMessage() {
        return um;
    }
}
