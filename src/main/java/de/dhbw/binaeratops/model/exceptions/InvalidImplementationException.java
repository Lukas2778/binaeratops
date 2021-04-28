package de.dhbw.binaeratops.model.exceptions;

/**
 * Exception für einen Implementierungsfehler.
 * <p>
 * Wird z.B. geworfen, wenn die Implementierung einer Schnittstelle das falsche Objekt beinhaltet.
 * <p>
 *
 * @author Nicolas Haug
 *
 */
public class InvalidImplementationException extends Exception {
    private static final long serialVersionUID = 1L;
    private final int errorCode;

    /**
     * Standard Exception mit ErrorCode-Information.
     * <p>
     *
     * @param AErrorCode ErrorCode.
     */
    public InvalidImplementationException(int AErrorCode) {
        super();
        errorCode = AErrorCode;
    }

    /**
     * Exception mit ErrorCode-Information, Fehlermeldung und Vorgängerexception.
     * <p>
     *
     * @param AErrorCode ErrorCode.
     * @param AMessage Fehlermeldung.
     * @param ACause Vorgängerexception.
     */
    public InvalidImplementationException(int AErrorCode, String AMessage, Throwable ACause) {
        super(AMessage, ACause);
        errorCode = AErrorCode;
    }

    /**
     * Exception mit ErrorCode-Information und Fehlermeldung.
     * <p>
     *
     * @param AErrorCode ErrorCode.
     * @param AMessage Fehlermeldung.
     */
    public InvalidImplementationException(int AErrorCode, String AMessage) {
        super(AMessage);
        errorCode = AErrorCode;
    }

    /**
     * Exception mit ErrorCode-Information und Vorgängerexception.
     * <p>
     *
     * @param AErrorCode ErrorCode.
     * @param ACause Vorgängerexception.
     */
    public InvalidImplementationException(int AErrorCode, Throwable ACause) {
        super(ACause);
        errorCode = AErrorCode;
    }

    /**
     * Gibt den DBMS-spezifischen Fehler-Code zurück.
     * <p>
     *
     * @return Fehler-Code.
     */
    public int getErrorCode() {
        return errorCode;
    }

}