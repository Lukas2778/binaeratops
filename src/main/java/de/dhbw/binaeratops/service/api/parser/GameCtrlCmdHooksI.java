package de.dhbw.binaeratops.service.api.parser;

import de.dhbw.binaeratops.model.api.DungeonI;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.Item;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.enums.Direction;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;
import de.dhbw.binaeratops.service.impl.parser.UserMessage;

/**
 * Callbacks des Scanners für die Spielsteuerungs-Befehle.
 *
 * @author Nicolas Haug
 */
public interface GameCtrlCmdHooksI {

    UserMessage onWhereAmI(DungeonI ADungeon) throws CmdScannerException;

    UserMessage onInfoAll() throws CmdScannerException;
    UserMessage onInfoRoom(DungeonI ADungeon) throws CmdScannerException;
    UserMessage onInfoPlayers(DungeonI ADungeon) throws CmdScannerException;

    UserMessage onMoveNorth(DungeonI ADungeon) throws CmdScannerException;
    UserMessage onMoveEast(DungeonI ADungeon) throws CmdScannerException;
    UserMessage onMoveSouth(DungeonI ADungeon) throws CmdScannerException;
    UserMessage onMoveWest(DungeonI ADungeon) throws CmdScannerException;

    UserMessage onLookAround(DungeonI ADungeon) throws CmdScannerException;
    UserMessage onExamine(DungeonI ADungeon, String AItemOrNpc) throws CmdScannerException;
    UserMessage onShowInventory(DungeonI ADungeon) throws CmdScannerException;
    UserMessage onShowEquipment(DungeonI ADungeon) throws CmdScannerException;
    UserMessage onTake(DungeonI ADungeon, String AItem) throws CmdScannerException;
    UserMessage onDrop(DungeonI ADungeon, String AItem) throws CmdScannerException;
    UserMessage onEat(DungeonI ADungeon, String AItem) throws CmdScannerException;
    UserMessage onDrink(DungeonI ADungeon, String AItem) throws CmdScannerException;
    UserMessage onEquip(DungeonI ADungeon, String AItem) throws CmdScannerException;
    UserMessage onLayDown(DungeonI ADungeon, String AItem) throws CmdScannerException;
    UserMessage onGetHealth(DungeonI ADungeon) throws CmdScannerException;
    UserMessage onTalk(DungeonI ADungeon, String ANpcName,String AMessage) throws CmdScannerException;


}