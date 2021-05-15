package de.dhbw.binaeratops.service.impl.parser.gamectrl;

import de.dhbw.binaeratops.model.api.AvatarI;
import de.dhbw.binaeratops.model.api.DungeonI;
import de.dhbw.binaeratops.model.api.UserI;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
import de.dhbw.binaeratops.service.api.parser.GameCtrlCmdHooksI;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;
import de.dhbw.binaeratops.service.impl.parser.AbstractCmdScanner;
import de.dhbw.binaeratops.service.impl.parser.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Simpler zustandsbehafteter Scanner zur Interpretation der "In-Game"-Befehle.
 *
 * @author Nicolas Haug
 */
@Scope(value = "session")
@Service
public class GameCtrlCmdScanner extends AbstractCmdScanner {

    @Autowired
    private GameCtrlCmdHooksI hooks;

    // Schlüsselwörter
    private static final String CMD_INFO = "INFO";
    private static final String CMD_INFO_PLAYERS = "PLAYERS";
    private static final String CMD_INFO_ROOM = "ROOM";
    private static final String CMD_INFO_ALL = "ALL";
    private static final String CMD_MOVE = "MOVE";
    private static final String CMD_MOVE_NORTH = "NORTH";
    private static final String CMD_MOVE_NORTH_K = "N";
    private static final String CMD_MOVE_EAST = "EAST";
    private static final String CMD_MOVE_EAST_K = "E";
    private static final String CMD_MOVE_SOUTH = "SOUTH";
    private static final String CMD_MOVE_SOUTH_K = "S";
    private static final String CMD_MOVE_WEST = "WEST";
    private static final String CMD_MOVE_WEST_K = "W";
    private static final String CMD_LOOK = "LOOK";
    private static final String CMD_LOOK_AROUND = "AROUND";
    private static final String CMD_WHOAMI = "WHOAMI";
    private static final String CMD_WHEREAMI = "WHEREAMI";
    private static final String CMD_EXAMINE = "EXAMINE";
    private static final String CMD_EXAMINE_NPC = "NPC";
    private static final String CMD_EXAMINE_ITEM = "ITEM";
    private static final String CMD_SHOW = "SHOW";
    private static final String CMD_SHOW_INVENTORY = "INVENTORY";
    private static final String CMD_SHOW_INVENTORY_K = "INV";
    private static final String CMD_SHOW_EQUIPMENT = "EQUIPMENT";
    private static final String CMD_SHOW_EQUIPMENT_K = "EQ";
    private static final String CMD_TAKE = "TAKE";
    private static final String CMD_DROP = "DROP";
    private static final String CMD_EAT = "EAT";
    private static final String CMD_DRINK = "DRINK";
    private static final String CMD_EQUIP = "EQUIP";
    private static final String CMD_LAY_DOWN = "LAYDOWN";
    private static final String CMD_HEALTH = "HEALTH";
    private static final String CMD_TALK = "TALK";


    /**
     * Konstruktor.
     *
     */
    public GameCtrlCmdScanner() {

    }

    /**
     * Scanner im Zustand "Start".
     */
    @Override
    protected UserMessage scanStart(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        String token = findNextToken();
        if (token == null) {
            onMissingToken("<Control>");
            return null;
        } else {
            switch (token.toUpperCase()) {
                case CMD_INFO:
                    return scanInfo1(ADungeon, AAvatar, AUser);
                case CMD_MOVE:
                    return scanMove1(ADungeon, AAvatar, AUser);
                case CMD_LOOK:
                    return scanLook1(ADungeon, AAvatar, AUser);
                case CMD_WHOAMI:
                    return hooks.onWhoAmI(ADungeon, AAvatar, AUser);
                case CMD_WHEREAMI:
                    return hooks.onWhereAmI(ADungeon, AAvatar, AUser);
                case CMD_EXAMINE:
                    return scanExamine1(ADungeon, AAvatar, AUser);
                case CMD_SHOW:
                    return scanShow1(ADungeon, AAvatar, AUser);
                case CMD_TAKE:
                    return scanTake1(ADungeon, AAvatar, AUser);
                case CMD_DROP:
                    return scanDrop1(ADungeon, AAvatar, AUser);
                case CMD_EAT:
                    return scanEat1(ADungeon, AAvatar, AUser);
                case CMD_DRINK:
                    return scanDrink1(ADungeon, AAvatar, AUser);
                case CMD_EQUIP:
                    return scanEquip1(ADungeon, AAvatar, AUser);
                case CMD_LAY_DOWN:
                    return scanLayDown1(ADungeon, AAvatar, AUser);
                case CMD_HEALTH:
                    return hooks.onGetHealth(ADungeon, AAvatar, AUser);
                case CMD_TALK:
                    return scanTalk1(ADungeon, AAvatar, AUser);
                default:
                    onUnexpectedToken();
                    return null;
            }
        }
    }


    private UserMessage scanInfo1(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        String token = findRestOfInput();
        if (token == null) {
            onMissingToken("<ALL | ROOM | PLAYERS>");
            return null;
        } else {
            switch (token.toUpperCase()) {
                case CMD_INFO_ALL:
                    return hooks.onInfoAll(ADungeon, AAvatar, AUser);
                case CMD_INFO_ROOM:
                    return hooks.onInfoRoom(ADungeon, AAvatar, AUser);
                case CMD_INFO_PLAYERS:
                    return hooks.onInfoPlayers(ADungeon, AAvatar, AUser);
                default:
                    onUnexpectedToken();
                    return null;
            }
        }
    }

    private UserMessage scanMove1(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        String direction = findRestOfInput();
        if (direction == null) {
            onMissingToken("<Direction>");
            return null;
        } else {
            switch (direction.toUpperCase()) {
                case CMD_MOVE_NORTH:
                case CMD_MOVE_NORTH_K:
                    return hooks.onMoveNorth(ADungeon, AAvatar, AUser);
                case CMD_MOVE_EAST:
                case CMD_MOVE_EAST_K:
                    return hooks.onMoveEast(ADungeon, AAvatar, AUser);
                    case CMD_MOVE_SOUTH:
                case CMD_MOVE_SOUTH_K:
                    return hooks.onMoveSouth(ADungeon, AAvatar, AUser);
                case CMD_MOVE_WEST:
                case CMD_MOVE_WEST_K:
                    return hooks.onMoveWest(ADungeon, AAvatar, AUser);
                default:
                    onUnexpectedToken();
                    return null;
            }
        }
    }

    private UserMessage scanLook1(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        String token = findRestOfInput();
        if (token == null) {
            onMissingToken("<AROUND>");
            return null;
        } else {
            switch (token.toUpperCase()) {
                case CMD_LOOK_AROUND:
                    return hooks.onLookAround(ADungeon, AAvatar, AUser);
                default:
                    onUnexpectedToken();
                    return null;
            }
        }
    }

    private UserMessage scanExamine1(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        String token = findNextToken();
        if (token == null) {
            onMissingToken("<NPC> | <ITEM>");
            return null;
        } else {
            switch (token.toUpperCase()) {
                case CMD_EXAMINE_NPC:
                    return scanExamine2(ADungeon, AAvatar, AUser, true);
                case CMD_EXAMINE_ITEM:
                    return scanExamine2(ADungeon, AAvatar, AUser, false);
                default:
                    onUnexpectedToken();
                    return null;
            }
        }
    }

    private UserMessage scanExamine2(DungeonI ADungeon, AvatarI AAvatar, UserI AUser, boolean ANpcOrItem) throws CmdScannerException, InvalidImplementationException {
        String name = findRestOfInput();
        if (name == null) {
            onMissingToken("<NAME>");
            return null;
        } else {
            if (ANpcOrItem) {
                return hooks.onExamineNpc(ADungeon, name, AAvatar, AUser);
            } else {
                return hooks.onExamineItem(ADungeon, name, AAvatar, AUser);
            }
        }
    }


    private UserMessage scanShow1(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        String token = findRestOfInput();
        if (token == null) {
            onMissingToken("<INVENTORY | INV | EQUIPMENT | EQUIP>");
            return null;
        } else {
            switch (token.toUpperCase()) {
                case CMD_SHOW_INVENTORY:
                case CMD_SHOW_INVENTORY_K:
                    return hooks.onShowInventory(ADungeon, AAvatar, AUser);
                case CMD_SHOW_EQUIPMENT:
                case CMD_SHOW_EQUIPMENT_K:
                    return hooks.onShowEquipment(ADungeon, AAvatar, AUser);
                default:
                    onUnexpectedToken();
                    return null;
            }
        }
    }


    private UserMessage scanTake1(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        String item = findRestOfInput();
        if (item == null) {
            onMissingToken("<ITEM>");
            return null;
        } else {
            return hooks.onTake(ADungeon, item.toUpperCase(), AAvatar, AUser);
        }
    }

    private UserMessage scanDrop1(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        String item = findRestOfInput();
        if (item == null) {
            onMissingToken("<ITEM>");
            return null;
        } else {
            return hooks.onDrop(ADungeon, item, AAvatar, AUser);
        }
    }

    private UserMessage scanEat1(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        String eatableItem = findRestOfInput();
        if (eatableItem == null) {
            onMissingToken("<EATABLE_ITEM>");
            return null;
        } else {
            return hooks.onEat(ADungeon, eatableItem.toUpperCase(), AAvatar, AUser);
        }
    }

    private UserMessage scanDrink1(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        String drinkableItem = findRestOfInput();
        if (drinkableItem == null) {
            onMissingToken("<DRINKABLE_ITEM>");
            return null;
        } else {
            return hooks.onDrink(ADungeon, drinkableItem.toUpperCase(), AAvatar, AUser);
        }
    }

    private UserMessage scanEquip1(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        String equipableItem = findRestOfInput();
        if (equipableItem == null) {
            onMissingToken("<EQUIPABLE_ITEM>");
            return null;
        } else {
            return hooks.onEquip(ADungeon, AAvatar, AUser, equipableItem.toUpperCase());
        }
    }

    private UserMessage scanLayDown1(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        String item = findRestOfInput();
        if (item == null) {
            onMissingToken("<ITEM>");
            return null;
        } else {
            return hooks.onLayDown(ADungeon, AAvatar, AUser, item.toUpperCase());
        }
    }

    private UserMessage scanTalk1(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        String name = findNextToken();
        if (name == null) {
            onMissingToken("<NPC_NAME>");
            return null;
        } else {
            return scanTalk2(ADungeon, AAvatar, AUser, name.toUpperCase());
        }
    }

    private UserMessage scanTalk2(DungeonI ADungeon, AvatarI AAvatar, UserI AUser, String ANpcName) throws CmdScannerException {
        String message = findRestOfInput();
        if (message == null) {
            onMissingToken("<MESSAGE>");
            return null;
        } else {
            return hooks.onTalk(ADungeon, AAvatar, AUser, ANpcName, message);
        }
    }
}
