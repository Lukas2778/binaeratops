package de.dhbw.binaeratops.service.impl.parser.ingame;

import de.dhbw.binaeratops.service.api.parser.InGameCmdHooksI;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;
import de.dhbw.binaeratops.service.impl.parser.UserMessage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope(value = "session")
@Service
public class InGameCmdHooks implements InGameCmdHooksI {
    @Override
    public UserMessage onCmdWhisper(String AUserName, String AMessage) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onCmdWhisperMaster(String AMessage) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onCmdSpeak(String ARoomId, String AMessage) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onCmdNotifyRoom(String ARoomName, String AMessage) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onCmdNotifyAll(String AMessage) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onCmdWithdrawRole(String AUsername) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onCmdStop() throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onCmdExit() throws CmdScannerException {
        return null;
    }
}
