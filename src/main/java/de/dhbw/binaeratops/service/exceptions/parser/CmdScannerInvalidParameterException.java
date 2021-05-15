package de.dhbw.binaeratops.service.exceptions.parser;

import de.dhbw.binaeratops.service.impl.parser.UserMessage;

public class CmdScannerInvalidParameterException extends CmdScannerException {
    private static final long serialVersionUID = 1L;

    private UserMessage um;
    /**
     * Standard-Exception für den Fall, das der übergebene Parameter nicht ausgewertet werden konnte.
     * <p>
     *
     */
    public CmdScannerInvalidParameterException(String AInvalidCommand) {
        super(AInvalidCommand);
        um = new UserMessage("error.parser.cmd.scanner.cmd.examine", AInvalidCommand);
    }

    public UserMessage getUserMessage() {
        return um;
    }
}
