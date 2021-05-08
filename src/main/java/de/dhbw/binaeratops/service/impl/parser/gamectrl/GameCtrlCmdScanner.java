package de.dhbw.binaeratops.service.impl.parser.gamectrl;

import de.dhbw.binaeratops.model.enums.Direction;
import de.dhbw.binaeratops.service.api.parser.GameCtrlCmdHooksI;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;
import de.dhbw.binaeratops.service.impl.parser.AbstractCmdScanner;
import de.dhbw.binaeratops.service.impl.parser.UserMessage;
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

    private final GameCtrlCmdHooksI hooks;

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
    private static final String CMD_LAY = "LAY";
    private static final String CMD_LAY_DOWN = "DOWN";
    private static final String CMD_HEALTH = "HEALTH";
    private static final String CMD_TALK = "TALK";


    /**
     * Konstruktor.
     *
     * @param AHooks Callbacks für die gefundenen Befehle.
     */
    public GameCtrlCmdScanner(GameCtrlCmdHooksI AHooks) {
        hooks = AHooks;
    }

    /**
     * Scanner im Zustand "Start".
     */
    protected UserMessage scanStart() throws CmdScannerException {
        String token = findNextToken();
        if (token == null) {
            onMissingToken("<Control>");
            return null;
        } else {
            switch (token.toUpperCase()) {
                case CMD_INFO:
                    return scanInfo1();
                case CMD_MOVE:
                    return scanMove1();
                case CMD_LOOK:
                    return scanLook1();
                case CMD_WHOAMI:
                    return new UserMessage("whoami");//TODO
                case CMD_WHEREAMI:
                    return new UserMessage("whereami");//TODO
                case CMD_EXAMINE:
                    return scanExamine1();
                case CMD_SHOW:
                    return scanShow1();
                case CMD_TAKE:
                    return scanTake1();
                case CMD_DROP:
                    return scanDrop1();
                case CMD_EAT:
                    return scanEat1();
                case CMD_DRINK:
                    return scanDrink1();
                case CMD_EQUIP:
                    return scanEquip1();
                case CMD_LAY:
                    return scanLayDown1();
                case CMD_HEALTH:
                    return new UserMessage("1");//TODO
                case CMD_TALK:
                    return scanTalk1();
                default:
                    onUnexpectedToken();
                    return null;
            }
        }
    }


    private UserMessage scanInfo1() throws CmdScannerException {
        String token = findRestOfInput();
        if (token == null) {
            return new UserMessage("view.game.ctrl.cmd.info");
        } else {
            switch (token.toUpperCase()) {
                case CMD_INFO_ALL:
                    return new UserMessage("view.game.ctrl.cmd.info.all");
                case CMD_INFO_ROOM:
                    return new UserMessage("view.game.ctrl.cmd.info.room");
                case CMD_INFO_PLAYERS:
                    return new UserMessage("view.game.ctrl.cmd.info.players");
                default:
                    onUnexpectedToken();
                    return null;
            }
        }
    }

    private UserMessage scanMove1() throws CmdScannerException {
        String direction = findRestOfInput();
        if (direction == null) {
            onMissingToken("<Richtung>");
            return null;
        } else {
            switch (direction.toUpperCase()) {
                case CMD_MOVE_NORTH:
                    return new UserMessage("view.game.ctrl.cmd.move.north");
                case CMD_MOVE_NORTH_K:
                    return new UserMessage("view.game.ctrl.cmd.move.north.k");
                case CMD_MOVE_EAST:
                    return new UserMessage("view.game.ctrl.cmd.move.east");
                case CMD_MOVE_EAST_K:
                    return new UserMessage("view.game.ctrl.cmd.move.east.k");
                case CMD_MOVE_SOUTH:
                    return new UserMessage("view.game.ctrl.cmd.move.south");
                case CMD_MOVE_SOUTH_K:
                    return new UserMessage("view.game.ctrl.cmd.move.south.k");
                case CMD_MOVE_WEST:
                    return new UserMessage("view.game.ctrl.cmd.move.west");
                case CMD_MOVE_WEST_K:
                    return new UserMessage("view.game.ctrl.cmd.move.west.k");
                default:
                    onUnexpectedToken();
                    return null;
            }
        }
    }

    private UserMessage scanLook1() throws CmdScannerException {
        String token = findRestOfInput();
        if (token == null) {
            onMissingToken("<Schlüsselwort>");
            return null;
        } else {
            switch (token.toUpperCase()) {
                case CMD_LOOK_AROUND:
                    return new UserMessage("view.game.ctrl.cmd.look.around");
                default:
                    onUnexpectedToken();
                    return null;
            }
        }
    }

    private UserMessage scanExamine1() throws CmdScannerException {
        String name = findRestOfInput();
        if (name == null) {
            onMissingToken("<NPC_NAME> | <ITEM_NAME>");
            return null;
        } else {
            name = name.toUpperCase();
            //TODO
            return new UserMessage(name);
        }
    }


    private UserMessage scanShow1() throws CmdScannerException {
        String token = findRestOfInput();
        if (token == null) {
            onMissingToken("<Schlüsselwort>");
            return null;
        } else {
            switch (token.toUpperCase()) {
                case CMD_SHOW_INVENTORY:
                    return new UserMessage("view.game.ctrl.cmd.show.inventory");
                case CMD_SHOW_INVENTORY_K:
                    return new UserMessage("view.game.ctrl.cmd.show.inventory.k");
                case CMD_SHOW_EQUIPMENT:
                    return new UserMessage("view.game.ctrl.cmd.show.equipment");
                case CMD_SHOW_EQUIPMENT_K:
                    return new UserMessage("view.game.ctrl.cmd.show.equipment.k");
                default:
                    onUnexpectedToken();
                    return null;
            }
        }
    }


    private UserMessage scanTake1() throws CmdScannerException {
        String item = findRestOfInput();
        if (item == null) {
            onMissingToken("<ITEM>");
            return null;
        } else {
            item = item.toUpperCase();
            //TODO
            return new UserMessage(item);
        }
    }

    private UserMessage scanDrop1() throws CmdScannerException {
        String item = findRestOfInput();
        if (item == null) {
            onMissingToken("<ITEM>");
            return null;
        } else {
            item = item.toUpperCase();
            //TODO
            return new UserMessage(item);
        }
    }

    private UserMessage scanEat1() throws CmdScannerException {
        String eatableItem = findRestOfInput();
        if (eatableItem == null) {
            onMissingToken("<EATABLE_ITEM>");
            return null;
        } else {
            eatableItem = eatableItem.toUpperCase();
            //TODO
            return new UserMessage(eatableItem);
        }
    }

    private UserMessage scanDrink1() throws CmdScannerException {
        String drinkableItem = findRestOfInput();
        if (drinkableItem == null) {
            onMissingToken("<DRINKABLE_ITEM>");
            return null;
        } else {
            drinkableItem = drinkableItem.toUpperCase();
            //TODO
            return new UserMessage(drinkableItem);
        }
    }

    private UserMessage scanEquip1() throws CmdScannerException {
        String equipableItem = findRestOfInput();
        if (equipableItem == null) {
            onMissingToken("<EQUIPABLE_ITEM>");
            return null;
        } else {
            equipableItem = equipableItem.toUpperCase();
            //TODO
            return new UserMessage(equipableItem);
        }
    }

    private UserMessage scanLayDown1() throws CmdScannerException {
        String down = findNextToken();
        if (down == null) {
            onMissingToken("<Schlüsselwort>");
            return null;
        } else {
            switch (down.toUpperCase()) {
                case CMD_LAY_DOWN:
                    return scanLayDown2();
                default:
                    onUnexpectedToken();
                    return null;
            }
        }
    }

    private UserMessage scanLayDown2() throws CmdScannerException {
        String item = findRestOfInput();
        if (item == null) {
            onMissingToken("<ITEM>");
            return null;
        } else {
            item = item.toUpperCase();
            //TODO
            return new UserMessage(item);
        }
    }

    private UserMessage scanTalk1() throws CmdScannerException {
        String name = findRestOfInput();
        if (name == null) {
            onMissingToken("<NPC_NAME>");
            return null;
        } else {
            name = name.toUpperCase();
            //TODO
            return new UserMessage(name);
        }
    }

}
