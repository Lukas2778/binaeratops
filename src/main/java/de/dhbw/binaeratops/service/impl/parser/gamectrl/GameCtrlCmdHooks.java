package de.dhbw.binaeratops.service.impl.parser.gamectrl;

import de.dhbw.binaeratops.model.api.AvatarI;
import de.dhbw.binaeratops.model.api.DungeonI;
import de.dhbw.binaeratops.model.api.UserI;
import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
import de.dhbw.binaeratops.model.repository.UserRepositoryI;
import de.dhbw.binaeratops.service.api.parser.GameCtrlCmdHooksI;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerInsufficientPermissionException;
import de.dhbw.binaeratops.service.impl.parser.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    UserRepositoryI userRepo;

    @Override
    public UserMessage onWhereAmI(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            User dungeonMaster = userRepo.findByUserId(dungeon.getDungeonMasterId());
            return new UserMessage("view.game.ctrl.cmd.whereami", avatar.getCurrentRoom().getRoomName(), dungeon.getDungeonName(), dungeonMaster.getName(), avatar.getCurrentRoom().getDescription());
        } else {
            throw new CmdScannerInsufficientPermissionException("WHEREAMI");
        }
    }

    @Override
    public UserMessage onWhoAmI(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            Race race = avatar.getRace();
            Role role = avatar.getRole();
            return new UserMessage("view.game.ctrl.cmd.whoami", avatar.getName(), race.getRaceName(), race.getDescription(), role.getRoleName(), role.getDescription() /*, TODO Lebenspunkte hinzufügen*/);
        } else {
            throw new CmdScannerInsufficientPermissionException("WHOAMI");
        }
    }

    @Override
    public UserMessage onInfoAll(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            User dungeonMaster = userRepo.findByUserId(dungeon.getDungeonMasterId());
            Room currentRoom = avatar.getCurrentRoom();
            boolean alldiscorved = false;
            if (avatar.getVisitedRooms().size() == dungeon.getRooms().size()) {
                alldiscorved = true;
            }
            if (alldiscorved) {
                return new UserMessage("view.game.ctrl.cmd.info.all", dungeon.getDungeonName(), dungeonMaster.getName(),
                        String.valueOf(dungeon.getCurrentUsers().size()), getCurrentUsers(dungeon), currentRoom.getRoomName(),
                        currentRoom.getDescription(), String.valueOf(avatar.getVisitedRooms().size()), String.valueOf(dungeon.getRooms().size()));
            } else {
                return new UserMessage("view.game.ctrl.cmd.info.all", dungeon.getDungeonName(), dungeonMaster.getName(),
                        String.valueOf(dungeon.getCurrentUsers().size()), getCurrentUsers(dungeon), currentRoom.getRoomName(),
                        currentRoom.getDescription(), String.valueOf(avatar.getVisitedRooms().size()), "???");
            }
        } else {
            throw new CmdScannerInsufficientPermissionException("INFO ALL");
        }
    }

    @Override
    public UserMessage onInfoRoom(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            Room currentRoom = avatar.getCurrentRoom();
            boolean alldiscorved = false;
            if (avatar.getVisitedRooms().size() == dungeon.getRooms().size()) {
                alldiscorved = true;
            }
            if (alldiscorved) {
                return new UserMessage("view.game.ctrl.cmd.info.room", currentRoom.getRoomName(),
                        currentRoom.getDescription(), String.valueOf(avatar.getVisitedRooms().size()), String.valueOf(dungeon.getRooms().size()));
            } else {
                return new UserMessage("view.game.ctrl.cmd.info.room", currentRoom.getRoomName(),
                        currentRoom.getDescription(), String.valueOf(avatar.getVisitedRooms().size()), "???");
            }
        } else {
            throw new CmdScannerInsufficientPermissionException("INFO ROOM");
        }
    }

    @Override
    public UserMessage onInfoPlayers(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar avatar = Avatar.check(AAvatar);
        if (avatar.getUser().getUserId() != dungeon.getDungeonMasterId()) {
            User dungeonMaster = userRepo.findByUserId(dungeon.getDungeonMasterId());
            Room currentRoom = avatar.getCurrentRoom();
            return new UserMessage("view.game.ctrl.cmd.info.players", String.valueOf(dungeon.getCurrentUsers().size()),
                        getCurrentUsers(dungeon));
        } else {
            throw new CmdScannerInsufficientPermissionException("INFO PLAYERS");
        }
    }

    @Override
    public UserMessage onMoveNorth(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onMoveEast(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onMoveSouth(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onMoveWest(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onLookAround(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onExamine(DungeonI ADungeon, String AItemOrNpc, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onShowInventory(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onShowEquipment(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onTake(DungeonI ADungeon, String AItem, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onDrop(DungeonI ADungeon, String AItem, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onEat(DungeonI ADungeon, String AItem, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onDrink(DungeonI ADungeon, String AItem, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onEquip(DungeonI ADungeon, AvatarI AAvatar, UserI AUser, String AItem) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onLayDown(DungeonI ADungeon, AvatarI AAvatar, UserI AUser, String AItem) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onGetHealth(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onTalk(DungeonI ADungeon, AvatarI AAvatar, UserI AUser, String ANpcName, String AMessage) throws CmdScannerException {
        return null;
    }

    private String getCurrentUsers(Dungeon ADungeon) {
        List<Avatar> avatars = new ArrayList<>(); // TODO Produktiv-Liste einsetzen...
        StringBuilder s = new StringBuilder();
        s.append("<ol>");
        for (Avatar tempAvatar : avatars) {
            s.append("<li>").append(tempAvatar.getName()).append("</li>");
        }
        s.append("</ol>");
        return s.toString();
    }
}
