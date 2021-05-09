package de.dhbw.binaeratops.service.impl.parser;

import de.dhbw.binaeratops.model.api.AvatarI;
import de.dhbw.binaeratops.model.api.DungeonI;
import de.dhbw.binaeratops.model.api.UserI;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
import de.dhbw.binaeratops.model.repository.DungeonRepositoryI;
import de.dhbw.binaeratops.service.api.parser.UserMessageI;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;
import de.dhbw.binaeratops.service.impl.parser.gamectrl.GameCtrlCmdScanner;
import de.dhbw.binaeratops.service.impl.parser.ingame.InGameCmdScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * TODO
 */
@Scope(value = "session")
@Service
public class ParserService {
    @Autowired
    InGameCmdScanner ingameCmdScanner;

    @Autowired
    GameCtrlCmdScanner gameCtrlCmdScanner;

    @Autowired
    DungeonRepositoryI myDungeonRepo;

    public ParserService() {

    }

    /**
     * TODO Avatarname muss eindeutig sein!
     * @param AInput
     * @return
     */
    public UserMessage parseCommand(String AInput, Long ADungeonId, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        DungeonI myDungeon=myDungeonRepo.findByDungeonId(ADungeonId);
        User user = User.check(AUser); // TODO UNSCHÖN, muss raus hier
        myDungeon.addCurrentUser(user);
        // TODO Scanner hinzufügen
        AInput = AInput.trim();
        UserMessageI userMessage;
        if (checkPrefix(AInput, myDungeon)) {
            userMessage = ingameCmdScanner.scan(AInput, myDungeon, AAvatar, AUser);
        } else {
            userMessage = gameCtrlCmdScanner.scan(AInput, myDungeon, AAvatar, AUser);
        }
        return (UserMessage) userMessage;
    }

    private boolean checkPrefix(String AInput, DungeonI  ADungeon) {
        return AInput.startsWith(ADungeon.getCommandSymbol().toString());
    }
}
