package de.dhbw.binaeratops.service.exceptions.parser;

import de.dhbw.binaeratops.service.impl.parser.UserMessage;

/**
 * Unerwartetes Ende der Eingabe beim Parsen.
 *
 * TODO: schlampige Umsetzung, die Fehlermeldung sollte natürlich nicht
 * hier zusammengebastelt werden. Stattdessen die Informationen als
 * member mitgeben. Dann können auch in einer mehrsprachigen Applikation
 * vernünftige Meldungen ausgegeben werden.
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

    public UserMessage getUserMessage() {
        return um;
    }
}
