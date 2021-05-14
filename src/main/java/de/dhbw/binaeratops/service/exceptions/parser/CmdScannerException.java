package de.dhbw.binaeratops.service.exceptions.parser;

/**
 * Basisklasse für alle Exceptions innerhalb eines Scanners.
 *
 * @author Nicolas Haug
 */
public abstract class CmdScannerException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Standardexception.
     */
    public CmdScannerException() {
    }

    /**
     * Exception mit Fehlermeldung.
     * <p>
     *
     * @param AMessage Fehlermeldung.
     */
    public CmdScannerException(String AMessage) {
        super(AMessage);
    }
}