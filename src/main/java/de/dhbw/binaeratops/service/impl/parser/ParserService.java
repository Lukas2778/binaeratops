package de.dhbw.binaeratops.service.impl.parser;

import de.dhbw.binaeratops.model.api.AvatarI;
import de.dhbw.binaeratops.model.api.DungeonI;
import de.dhbw.binaeratops.model.api.UserI;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
import de.dhbw.binaeratops.service.api.parser.UserMessageI;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;
import de.dhbw.binaeratops.service.impl.parser.gamectrl.GameCtrlCmdHooks;
import de.dhbw.binaeratops.service.impl.parser.gamectrl.GameCtrlCmdScanner;
import de.dhbw.binaeratops.service.impl.parser.ingame.InGameCmdHooks;
import de.dhbw.binaeratops.service.impl.parser.ingame.InGameCmdScanner;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * TODO
 */
@Scope(value = "session")
@Service
public class ParserService {

    InGameCmdScanner ingameCmdScanner;

    GameCtrlCmdScanner gameCtrlCmdScanner;

    public ParserService() {
        this.ingameCmdScanner = new InGameCmdScanner(new InGameCmdHooks());
        this.gameCtrlCmdScanner = new GameCtrlCmdScanner(new GameCtrlCmdHooks());
    }

    /**
     * TODO Avatarname muss eindeutig sein!
     * @param AInput
     * @return
     */
    public UserMessage parseCommand(String AInput, DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        // TODO Scanner hinzufügen
        AInput = AInput.trim();
        AInput = AInput.toUpperCase();
        UserMessageI userMessage;
        if (checkPrefix(AInput, ADungeon)) {
            userMessage = ingameCmdScanner.scan(AInput, ADungeon, AAvatar, AUser);
        } else {
            userMessage = gameCtrlCmdScanner.scan(AInput, ADungeon, AAvatar, AUser);
        }
        return (UserMessage) userMessage;
    }

    private boolean checkPrefix(String AInput, DungeonI  ADungeon) {
        return AInput.startsWith(ADungeon.getCommandSymbol().toString());
    }
}
