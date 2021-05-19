package de.dhbw.binaeratops.service.impl.parser.ingame;

import de.dhbw.binaeratops.model.api.AvatarI;
import de.dhbw.binaeratops.model.api.DungeonI;
import de.dhbw.binaeratops.model.api.UserI;
import de.dhbw.binaeratops.model.entitys.Avatar;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.Room;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
import de.dhbw.binaeratops.model.repository.AvatarRepositoryI;
import de.dhbw.binaeratops.model.repository.DungeonRepositoryI;
import de.dhbw.binaeratops.model.repository.RoomRepositoryI;
import de.dhbw.binaeratops.model.repository.UserRepositoryI;
import de.dhbw.binaeratops.service.api.chat.ChatServiceI;
import de.dhbw.binaeratops.service.api.parser.InGameCmdHooksI;
import de.dhbw.binaeratops.service.exceptions.parser.*;
import de.dhbw.binaeratops.service.impl.parser.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Callbacks des Scanners für die "In-Game"-Befehle.
 * <p>
 * Für Implementierung siehe {@link de.dhbw.binaeratops.service.impl.parser.ingame.InGameCmdHooks}.
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

    @Autowired
    RoomRepositoryI roomRepo;

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
    public UserMessage onCmdWhisper(DungeonI ADungeon, AvatarI AAvatar, String AUserName, String AMessage) throws CmdScannerException, InvalidImplementationException {
        Avatar avatar = Avatar.check(AAvatar);
        Dungeon dungeon = Dungeon.check(ADungeon);
        Avatar recipient = new Avatar();
        for (Avatar a : dungeon.getAvatars()) {
            if (a.getName().equalsIgnoreCase(AUserName)) {
                recipient = a;
                break;
            }
        }

        if (recipient == null) {
            throw new CmdScannerInvalidParameterException(AUserName);
        }

        if (!recipient.isActive()) {
            throw new CmdScannerRecipientOfflineException(AUserName);
        }

        if (avatar.getAvatarId() != recipient.getAvatarId()) {
            if (avatar.getName() != null) {
                myChatService.whisper(AMessage, recipient.getUser(), avatar);
            } else {
                User dungeonMaster = userRepo.findByUserId(dungeon.getDungeonMasterId());
                myChatService.whisperDungeonMaster(AMessage, recipient.getUser(), dungeonMaster);
            }
            return new UserMessage("view.game.ingame.cmd.whisper", AMessage, AUserName);
        } else {
            throw new CmdScannerInvalidRecipientException();
        }
    }

    @Override
    public UserMessage onCmdWhisperMaster(DungeonI ADungeon, AvatarI AAvatar, String AMessage) throws CmdScannerException, InvalidImplementationException {
        Avatar avatar = Avatar.check(AAvatar);
        Dungeon dungeon = Dungeon.check(ADungeon);
        if (avatar == null) {
            throw new CmdScannerInvalidRecipientException();
        }
        User dungeonMaster = userRepo.findByUserId(dungeon.getDungeonMasterId());
        myChatService.whisper(AMessage, dungeonMaster, avatar);
        return new UserMessage("view.game.ingame.cmd.whisper.master", AMessage);
    }

    @Override
    public UserMessage onCmdSpeak(DungeonI ADungeon, AvatarI AAvatar, String AMessage) throws CmdScannerException, InvalidImplementationException {
        Avatar avatar = Avatar.check(AAvatar);
        Dungeon dungeon = Dungeon.check(ADungeon);
        Room currentRoom = avatar.getCurrentRoom();
        List<User> recipients = getUsersOfRoom(dungeon, currentRoom);
        User dungeonMaster = userRepo.findByUserId(dungeon.getDungeonMasterId());
        recipients.add(dungeonMaster);
        for (User user : recipients) {
            myChatService.whisperRoom(AMessage, user, avatar, currentRoom.getRoomName());
        }
        //myChatService.sendRoomMessage(AMessage, recipients, avatar, currentRoom);
        return new UserMessage("view.game.ingame.cmd.speak", AMessage, currentRoom.getRoomName());
    }

    @Override
    public UserMessage onCmdNotifyRoom(DungeonI ADungeon, UserI AUser, String ARoomName, String AMessage) throws CmdScannerException, InvalidImplementationException {
        User user = User.check(AUser);
        Dungeon dungeon = Dungeon.check(ADungeon);
        if (ADungeon.getDungeonMasterId() == user.getUserId()) {
            for (Room room : dungeon.getRooms()) {
                if (room.getRoomName().equalsIgnoreCase(ARoomName)) {
                    List<User> users = getUsersOfRoom(dungeon, room);
                    User dungeonMaster = userRepo.findByUserId(dungeon.getDungeonMasterId());
                    for (User tempUser : users) {
                        myChatService.whisperDungeonMasterRoom(AMessage, tempUser, dungeonMaster, room.getRoomName());
                    }
                    //myChatService.sendRoomMessage(AMessage, users, dungeonMaster, room);
                    return new UserMessage("view.game.ingame.cmd.speak", AMessage, room.getRoomName());
                }
            }
            throw new CmdScannerInvalidParameterException(ARoomName);
        } else {
            throw new CmdScannerInsufficientPermissionException("NOTIFY ROOM");
        }
    }

    @Override
    public UserMessage onCmdNotifyAll(DungeonI ADungeon, UserI AUser, String AMessage) throws CmdScannerException, InvalidImplementationException {
        User user = User.check(AUser);
        Dungeon dungeon = Dungeon.check(ADungeon);
        if (ADungeon.getDungeonMasterId() == AUser.getUserId()) {
            for (User tempUser : dungeon.getCurrentUsers()) {
                User dungeonMaster = userRepo.findByUserId(dungeon.getDungeonMasterId());
                myChatService.whisperDungeonMaster(AMessage, tempUser, dungeonMaster);
            }
            //myChatService.notifyAll(AMessage, ADungeon.getCurrentUsers(), ADungeon.getUser());
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

    // TODO Kommentare schreiben

    private List<User> getUsersOfRoom(Dungeon ADungeon, Room ARoom) {
        List<User> users = new ArrayList<>();
        for (Avatar avatar : getCurrentAvatars(ADungeon)) {
            if (avatar.getCurrentRoom().equals(ARoom)) {
                users.add(avatar.getUser());
            }
        }
        return users;
    }

    // TODO Kommentare schreiben

    private List<Avatar> getCurrentAvatars(Dungeon ADungeon) {
        List<Avatar> avatars = new ArrayList<>();
        ADungeon.getAvatars().forEach(avatar -> {
            if (avatar.isActive()) {
                avatars.add(avatar);
            }
        });
        return avatars;
    }
}
