package de.dhbw.binaeratops.service.exceptions.parser;

import de.dhbw.binaeratops.service.impl.parser.UserMessage;

/**
 * Unerwartetes Ende der Eingabe beim Parsen.
 *
 *
 * @author Nicolas Haug
 */
public class CmdScannerSyntaxMissingException extends CmdScannerSyntaxException {
    private static final long serialVersionUID = 1L;

    private UserMessage um;

    /**
     * Exception mit fehlendem Token.
     * <p>
     *
     * @param AMissingToken Fehlendem Token.
     */
    public CmdScannerSyntaxMissingException(String AMissingToken) {
        super(AMissingToken);
        um = new UserMessage("error.parser.cmd.scanner.missing.token", AMissingToken);
    }

    /**
     * Gibt die lokale Fehlermeldung als Benutzernachricht zurück.
     * @return Benutzernachricht.
     */
    public UserMessage getUserMessage() {
        return um;
    }
}
