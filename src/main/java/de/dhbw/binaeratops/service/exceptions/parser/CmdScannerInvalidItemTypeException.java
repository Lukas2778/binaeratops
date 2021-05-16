package de.dhbw.binaeratops.service.exceptions.parser;

import de.dhbw.binaeratops.service.impl.parser.UserMessage;

/**
 * Syntaxfehler beim Parsen der Eingabe.
 * <p>
 * Eingegebener ItemTyp entspricht nicht dem erwarteten.
 *
 * @author Nicolas Haug
 */
public class CmdScannerInvalidItemTypeException extends CmdScannerException {
    private static final long serialVersionUID = 1L;

    private UserMessage um;

    /**
     * Standard-Exception für den Fall, dass der übergebene Parameter nicht ausgewertet werden konnte.
     *
     * @param AInvalidCommand Falsches Kommando das eingegeben wurde.
     * @param AActualType Eigentlicher Gegenstandstyp.
     */
    public CmdScannerInvalidItemTypeException(String AInvalidCommand, String AActualType) {
        super(AInvalidCommand);
        um = new UserMessage("error.parser.cmd.scanner.cmd.equip", AInvalidCommand, AActualType);
    }

    /**
     * Gibt die lokale Fehlermeldung als Benutzernachricht zurück.
     *
     * @return Benutzernachricht.
     */
    public UserMessage getUserMessage() {
        return um;
    }
}
