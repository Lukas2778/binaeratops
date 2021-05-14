package de.dhbw.binaeratops.service.impl.parser.gamectrl;

import de.dhbw.binaeratops.model.api.DungeonI;
import de.dhbw.binaeratops.service.api.parser.GameCtrlCmdHooksI;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;
import de.dhbw.binaeratops.service.impl.parser.UserMessage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Callbacks des Scanners für die Spielsteuerungs-Befehle.
 * <p>
 *     Für Schnittstelle siehe @{@link GameCtrlCmdHooksI}.
 * </p>
 *
 * @author Nicolas Haug, Lukas Göpel
 */
@Scope(value = "session")
@Service
public class GameCtrlCmdHooks implements GameCtrlCmdHooksI {


    @Override
    public UserMessage onWhereAmI(DungeonI ADungeon) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onInfoAll() throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onInfoRoom(DungeonI ADungeon) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onInfoPlayers(DungeonI ADungeon) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onMoveNorth(DungeonI ADungeon) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onMoveEast(DungeonI ADungeon) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onMoveSouth(DungeonI ADungeon) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onMoveWest(DungeonI ADungeon) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onLookAround(DungeonI ADungeon) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onExamine(DungeonI ADungeon, String AItemOrNpc) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onShowInventory(DungeonI ADungeon) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onShowEquipment(DungeonI ADungeon) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onTake(DungeonI ADungeon, String AItem) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onDrop(DungeonI ADungeon, String AItem) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onEat(DungeonI ADungeon, String AItem) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onDrink(DungeonI ADungeon, String AItem) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onEquip(DungeonI ADungeon, String AItem) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onLayDown(DungeonI ADungeon, String AItem) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onGetHealth(DungeonI ADungeon) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onTalk(DungeonI ADungeon, String ANpcName,String AMessage) throws CmdScannerException {
        return null;
    }
}
