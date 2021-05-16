package de.dhbw.binaeratops.service.exceptions.parser;

import de.dhbw.binaeratops.service.impl.parser.UserMessage;

/**
 * Unerwartetes Schlüsselwort beim beim Parsen gefunden.
 *
 *
 *
 * @author Nicolas Haug
 */
public class CmdScannerSyntaxUnexpectedException extends CmdScannerSyntaxException {
    private static final long serialVersionUID = 1L;

    private UserMessage um;
    /**
     * Exception mit fehlendem Token.
     * <p>
     *
     * @param AUnexpectedToken Fehlendem Token.
     * @param APosition Position, an der das Token aufgetreten ist.
     */
    public CmdScannerSyntaxUnexpectedException(String AUnexpectedToken, int APosition) {
        super(AUnexpectedToken);
        um = new UserMessage("error.parser.cmd.scanner.unexpected.token", AUnexpectedToken, String.valueOf(APosition+1));
    }

    /**
     * Gibt die lokale Fehlermeldung als Benutzernachricht zurück.
     * @return Benutzernachricht.
     */
    public UserMessage getUserMessage() {
        return um;
    }
}
