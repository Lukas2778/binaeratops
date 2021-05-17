package de.dhbw.binaeratops.service.impl.parser;

import de.dhbw.binaeratops.model.api.AvatarI;
import de.dhbw.binaeratops.model.api.DungeonI;
import de.dhbw.binaeratops.model.api.UserI;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
import de.dhbw.binaeratops.model.repository.DungeonRepositoryI;
import de.dhbw.binaeratops.service.api.parser.ParserServiceI;
import de.dhbw.binaeratops.service.api.parser.UserMessageI;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;
import de.dhbw.binaeratops.service.impl.parser.gamectrl.GameCtrlCmdScanner;
import de.dhbw.binaeratops.service.impl.parser.ingame.InGameCmdScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Komponente "ParserService".
 * <p>
 * Dieser Service stellt alle Funktionalitäten zum Parsen der Spielereingabe bereit.
 * </p>
 * <p>
 * Für Schnittstelle dieser Komponente siehe {@link ParserServiceI}.
 * </p>
 *
 * @author Nicolas Haug, Lukas Göpel
 */
@Scope(value = "session")
@Service
public class ParserService implements ParserServiceI {
    @Autowired
    InGameCmdScanner ingameCmdScanner;

    @Autowired
    GameCtrlCmdScanner gameCtrlCmdScanner;

    @Autowired
    DungeonRepositoryI myDungeonRepo;

    /**
     * Standardkonstruktor zum Erzeugen des ParserService.
     */
    public ParserService() {

    }

    public UserMessage parseCommand(String AInput, Long ADungeonId, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        DungeonI myDungeon = myDungeonRepo.findByDungeonId(ADungeonId);
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

    public Character getCommandSymbol(Long ADungeonId) {
        return myDungeonRepo.findByDungeonId(ADungeonId).getCommandSymbol();
    }

    /**
     * Überprüft, ob die Spielereingabe mit dem Befehlssymbol startet.
     *
     * @param AInput   Spielereingabe.
     * @param ADungeon Dungeon, in dem der Befehl abgesetzt wird.
     * @return Wahrheitswert.
     */
    private boolean checkPrefix(String AInput, DungeonI ADungeon) {
        return AInput.startsWith(ADungeon.getCommandSymbol().toString());
    }
}
