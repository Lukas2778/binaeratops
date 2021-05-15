package de.dhbw.binaeratops.service.exceptions.parser;

import de.dhbw.binaeratops.service.impl.parser.UserMessage;

public class CmdScannerInvalidItemTypeException extends CmdScannerException {
    private static final long serialVersionUID = 1L;

    private UserMessage um;
    /**
     * Standard-Exception für den Fall, das der übergebene Parameter nicht ausgewertet werden konnte.
     * <p>
     *
     */
    public CmdScannerInvalidItemTypeException(String AInvalidCommand, String AActualType) {
        super(AInvalidCommand);
        um = new UserMessage("error.parser.cmd.scanner.cmd.equip", AInvalidCommand, AActualType);
    }
}
