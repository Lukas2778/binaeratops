package de.dhbw.binaeratops.service.exceptions.parser;

/**
 * Unerwartetes Schlüsselwort beim beim Parsen gefunden.
 *
 * TODO: schlampige Umsetzung, die Fehlermeldung sollte natürlich nicht
 * hier zusammengebastelt werden. Stattdessen die Informationen als
 * member mitgeben. Dann können auch in einer mehrsprachigen Applikation
 * vernünftige Meldungen ausgegeben werden.
 *
 * @author Nicolas Haug
 */
public class CmdScannerSyntaxUnexpectedException extends CmdScannerSyntaxException {
    private static final long serialVersionUID = 1L;

    /**
     * Exception mit fehlendem Token.
     * <p>
     *
     * @param AUnexpectedToken Fehlendem Token.
     * @param APosition Position, an der das Token aufgetreten ist.
     */
    public CmdScannerSyntaxUnexpectedException(String AUnexpectedToken, int APosition) {
        super(String.format("Unerwartetes Schlüsselwort '%s' an Position %d",
                AUnexpectedToken, APosition + 1));
    }

}
