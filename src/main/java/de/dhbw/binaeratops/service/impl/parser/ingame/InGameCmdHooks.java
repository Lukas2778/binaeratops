package de.dhbw.binaeratops.service.impl.parser.ingame;

import de.dhbw.binaeratops.model.api.AvatarI;
import de.dhbw.binaeratops.model.api.DungeonI;
import de.dhbw.binaeratops.model.api.UserI;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
import de.dhbw.binaeratops.model.repository.AvatarRepositoryI;
import de.dhbw.binaeratops.model.repository.DungeonRepositoryI;
import de.dhbw.binaeratops.model.repository.UserRepositoryI;
import de.dhbw.binaeratops.service.api.chat.ChatServiceI;
import de.dhbw.binaeratops.service.api.parser.InGameCmdHooksI;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerInsufficientPermissionException;
import de.dhbw.binaeratops.service.impl.parser.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Callbacks des Scanners für die "In-Game"-Befehle.
 * <p>
 * Für Implementierung siehe @{@link de.dhbw.binaeratops.service.impl.parser.ingame.InGameCmdHooks}.
 * </p>
 *
 * @author Nicolas Haug
 */
@Scope(value = "session")
@Service
public class InGameCmdHooks implements InGameCmdHooksI {

    @Autowired
    UserRepositoryI userRepo;

    @Autowired
    AvatarRepositoryI avatarRepo;

    @Autowired
    DungeonRepositoryI dungeonRepo;

    @Autowired
    ChatServiceI myChatService;

    @Override
    public UserMessage onCmdHelp(DungeonI ADungeon) {
        return new UserMessage("view.game.cmd.help", String.valueOf(ADungeon.getCommandSymbol()), String.valueOf(ADungeon.getCommandSymbol()));
    }

    @Override
    public UserMessage onCmdHelpCmds(DungeonI ADungeon) {
        return new UserMessage("view.game.cmd.help.cmds", String.valueOf(ADungeon.getCommandSymbol()), String.valueOf(ADungeon.getCommandSymbol()));
    }

    @Override
    public UserMessage onCmdHelpCtrl(DungeonI ADungeon) {
        return new UserMessage("view.game.cmd.help.ctrl");
    }

    @Override
    public UserMessage onCmdWhisper(DungeonI ADungeon, AvatarI AAvatar, String AUserName, String AMessage) throws CmdScannerException {
        // TODO
        return null;
    }

    @Override
    public UserMessage onCmdWhisperMaster(DungeonI ADungeon, AvatarI AAvatar, String AMessage) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onCmdSpeak(DungeonI ADungeon, AvatarI AAvatar, String AMessage) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onCmdNotifyRoom(DungeonI ADungeon, UserI AUser, String ARoomName, String AMessage) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onCmdNotifyAll(DungeonI ADungeon, UserI AUser, String AMessage) throws CmdScannerException {
        //myChatService.sendMessage(AMessage,ADungeon.getCurrentUsers());
        if (ADungeon.getDungeonMasterId() == AUser.getUserId()) {
            myChatService.notifyAll(AMessage,ADungeon.getCurrentUsers(), ADungeon.getUser());
            System.out.println("Anzahl aktuelle User: "+ ADungeon.getCurrentUsers().size());
            return new UserMessage("view.game.ingame.cmd.notify.all", AMessage);
        } else {
            throw new CmdScannerInsufficientPermissionException("NOTIFY ALL");
        }
    }

    @Override
    public UserMessage onCmdWithdrawRole(DungeonI ADungeon, UserI AUser, String ARecipent) throws CmdScannerException, InvalidImplementationException {
        Dungeon dungeon = Dungeon.check(ADungeon);
        if (ADungeon.getDungeonMasterId() == AUser.getUserId()) {
            if (userRepo.findByName(ARecipent) != null) {
                User user = userRepo.findByName(ARecipent);
                ADungeon.setDungeonMasterId(user.getUserId());
                dungeonRepo.save(dungeon);
                // TODO return new UserMessage("", user.getUsername())
            } else {
                // return new UserMessage(User XY nicht gefunden.)
            }
        }
        if (userRepo.findByName(ARecipent) != null) {
            User user = userRepo.findByName(ARecipent);
            ADungeon.setDungeonMasterId(user.getUserId());
            dungeonRepo.save(dungeon);
            // TODO return new UserMessage("", user.getUsername())
        } else {
            // return new UserMessage(User XY nicht gefunden.)
        }
        return null;
    }

    @Override
    public UserMessage onCmdStop(DungeonI ADungeon, UserI AUser) throws CmdScannerException {
        return null;
    }

    @Override
    public UserMessage onCmdExit(DungeonI ADungeon, UserI AUser) throws CmdScannerException {
        return null;
    }
}
