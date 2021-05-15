package de.dhbw.binaeratops.service.exceptions.parser;

import de.dhbw.binaeratops.service.impl.parser.UserMessage;

public class CmdScannerInsufficientPermissionException extends CmdScannerException {
    private static final long serialVersionUID = 1L;

    private UserMessage um;
    /**
     * Standard-Exception für den Fall, das die Berechtigung nicht ausreicht.
     * <p>
     *
     */
    public CmdScannerInsufficientPermissionException(String AInvalidCommand) {
        super(AInvalidCommand);
        um = new UserMessage("error.parser.cmd.scanner.insufficient.permission", AInvalidCommand);
    }
}
