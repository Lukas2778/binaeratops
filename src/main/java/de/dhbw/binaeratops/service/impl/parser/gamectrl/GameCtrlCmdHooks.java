package de.dhbw.binaeratops.service.impl.parser.gamectrl;

import de.dhbw.binaeratops.model.enums.Direction;
import de.dhbw.binaeratops.service.api.parser.GameCtrlCmdHooksI;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope(value = "session")
@Service
public class GameCtrlCmdHooks implements GameCtrlCmdHooksI {
    @Override
    public void onCmdMove(Direction ADirection, int ASteps) throws CmdScannerException {

    }

    @Override
    public void onCmdPunch() throws CmdScannerException {

    }
}
