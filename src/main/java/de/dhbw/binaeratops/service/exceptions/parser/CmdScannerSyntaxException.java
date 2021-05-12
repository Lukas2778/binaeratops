package de.dhbw.binaeratops.service.exceptions.parser;
/**
 * Syntaxfehler beim Parsen der Eingabe.
 *
 * @author Nicolas Haug
 */
public class CmdScannerSyntaxException extends CmdScannerException {
    private static final long serialVersionUID = 1L;

    /**
     * Exception mit Fehlermeldung.
     * <p>
     *
     * @param AMessage Fehlermeldung.
     */
    public CmdScannerSyntaxException(String AMessage) {
        super(AMessage);
    }
}