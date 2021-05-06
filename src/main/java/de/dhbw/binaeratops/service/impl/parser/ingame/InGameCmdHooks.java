package de.dhbw.binaeratops.service.impl.parser.ingame;

import de.dhbw.binaeratops.service.api.parser.InGameCmdHooksI;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope(value = "session")
@Service
public class InGameCmdHooks implements InGameCmdHooksI {
    @Override
    public void onCmdJoinChat() throws CmdScannerException {

    }

    @Override
    public void onCmdJoinLobby() throws CmdScannerException {

    }

    @Override
    public void onCmdLogout() throws CmdScannerException {

    }

    @Override
    public void onCmdWhisper(String AUserName, String AMessage) throws CmdScannerException {

    }
}
