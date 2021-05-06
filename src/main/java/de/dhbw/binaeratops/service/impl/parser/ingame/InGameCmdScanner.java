package de.dhbw.binaeratops.service.impl.parser.ingame;

import de.dhbw.binaeratops.service.api.parser.InGameCmdHooksI;
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
public class InGameCmdScanner extends AbstractCmdScanner {

    private final InGameCmdHooksI hooks;

    // Schlüsselwörter.
    private static final String CMD_HELP = "HELP";
    private static final String CMD_HELP_CONTROL = "CONTROL";
    private static final String CMD_HELP_CMDS = "COMMANDS";
    private static final String CMD_HELP_ALL = "ALL";
    private static final String CMD_WHISPER = "WHISPER";
    private static final String CMD_WHISPER_MASTER = "MASTER";
    private static final String CMD_SPEAK = "SPEAK";
    private static final String CMD_NOTIFY = "NOTIFY";
    private static final String CMD_NOTIFY_ALL = "ALL";
    private static final String CMD_NOTIFY_ROOM = "ROOM";
    private static final String CMD_REPORT = "REPORT";
    private static final String CMD_WITHDRAW = "WITHDRAW";
    private static final String CMD_WITHDRAW_ROLE = "ROLE";
    private static final String CMD_NPC = "NPC";
    private static final String CMD_NPC_SPEAK = "SPEAK";
    private static final String CMD_NPC_ATTACK = "ATTACK";
    private static final String CMD_NPC_TELEPORT = "TELEPORT";
    private static final String CMD_NPC_DROP = "DROP";
    private static final String CMD_EXIT = "EXIT";
    private static final String CMD_EXIT_GAME = "GAME";
    private static final String CMD_STOP = "STOP";
    private static final String CMD_STOP_GAME = "GAME";

    /**
     * Konstruktor.
     *
     * @param AHooks Callbacks für die gefundenen Befehle.
     */
    public InGameCmdScanner(InGameCmdHooksI AHooks) {
        hooks = AHooks;
    }

    /**
     * Scanner im Zustand "Start".
     */
    protected UserMessage scanStart() throws CmdScannerException {
        startWithPos(1); // Überspringe Befehlszeichen

        String token = findNextToken();
        if (token == null) {
            onMissingToken("<Befehl>");
            return  null;
        } else {
            switch (token.toUpperCase()) {
                case CMD_HELP:
                    return scanHelp1();
                case CMD_WHISPER:
                    return scanWhisper1();
                case CMD_SPEAK:
                    return scanSpeak();
                case CMD_NOTIFY:
                    return scanNotify();
                default :
                    onUnexpectedToken();
                    return null;
            }
        }
    }

    private UserMessage scanHelp1() throws CmdScannerException {
        String token = findNextToken();
        if (token == null) {
            return new UserMessage("view.game.cmd.help");
        } else {
            switch (token.toUpperCase()) {
                case CMD_HELP_ALL:
                    return new UserMessage("view.game.cmd.help.all");
                case CMD_HELP_CMDS:
                    return new UserMessage("view.game.cmd.help.cmds");
                case CMD_HELP_CONTROL:
                    return new UserMessage("view.game.cmd.help.ctrl");
                default:
                    onUnexpectedToken();
                    return null;
            }
        }
    }

    private UserMessage scanWhisper1() throws CmdScannerException {
        String avatarname = findNextToken();
        if (avatarname == null) {
            onMissingToken("<Avatarname>");
            return null;
        } else {
            switch (avatarname.toUpperCase()) {
                case CMD_WHISPER_MASTER:
                    return scanWhisperMaster();
                default:
                    return scanWhisperAvatarName(avatarname);
            }
        }
    }

    private UserMessage scanWhisperAvatarName(String AAvatarname) throws CmdScannerException {
        String message = findRestOfInput();
        if (message == null) {
            onMissingToken("<Nachricht>");
        } else {
            //TODO hooks.onCmdWhisper(AAvatarname, message);
        }
        return null;
    }

    private UserMessage scanWhisperMaster() throws CmdScannerException {
        String message = findRestOfInput();
        if (message == null) {
            onMissingToken("<Nachricht>");
        } else {
            // TODO hooks.onCmdWhisperMaster()
        }
        return null;
    }

    private UserMessage scanSpeak() throws CmdScannerException {
        String message = findRestOfInput();
        if (message == null) {
            onMissingToken("<Nachricht>");
        } else {
            // TODO hooks.onCmdSpeak(ARoomId, message); --> Woher kriegen wir die RaumID?
        }
        return null;
    }

    private UserMessage scanNotify() throws CmdScannerException {
        String token = findNextToken();
        if (token == null) {
            onMissingToken("<ALL|ROOM>");
            return null;
        } else {
            switch (token.toUpperCase()) {
                case CMD_NOTIFY_ROOM:
                    return scanNotifyRoom();
                case CMD_NOTIFY_ALL:
                    return scanNotifyAll();
                default:
                    onUnexpectedToken();
                    return null;
            }
        }
    }

    private UserMessage scanNotifyRoom() throws CmdScannerException {
        String roomName = findNextToken();
        if (roomName == null) {
            onMissingToken("<Raumname>");
            return null;
        } else {
            return scanNotifyRoomMessage(roomName);
        }
    }

    private UserMessage scanNotifyRoomMessage(String ARoomName) throws CmdScannerException {
        String message = findRestOfInput();
        if (message == null) {
            onMissingToken("<Nachricht>");
            return null;
        } else {
            // TODO return hooks.onCmdNotifyRoom(ARoomName, message);
        }
        return null;
    }

    private UserMessage scanNotifyAll() throws CmdScannerException {
        String message = findRestOfInput();
        if(message == null) {
            onMissingToken("<Nachricht>");
            return null;
        } else {
            // TODO return hooks.onCmdNotifyAll(message);
        }
        return null;
    }

    /**
     * Scanner Zustand "Join" Level 1.
     */
    private void _scanJoin1() throws CmdScannerException {
        String token = findNextToken();
        if (token == null) {
            onMissingToken("chat|lobby");
        } else {
            switch (token.toLowerCase()) {
                case "chat" :
                    _scanJoinChat1();
                    break;
                case "lobby" :
                    _scanJoinLobby1();
                    break;
                default :
                    onUnexpectedToken();
                    break;
            }
        }
    }

    private void _scanJoinChat1() throws CmdScannerException {
        hooks.onCmdJoinChat();
    }
    private void _scanJoinLobby1() throws CmdScannerException {
        hooks.onCmdJoinLobby();
    }

    /**
     * Scanner Zustand "Leave" Level 1.
     */
    private void _scanLogout1() throws CmdScannerException {
        hooks.onCmdLogout();
    }

    /**
     * Scanner Zustand "Whisper" Level 1.
     */
    private void _scanWhisper1() throws CmdScannerException {
        String userName = findNextToken();
        if (userName == null) {
            onMissingToken("Benutzername");
        } else {
            _scanWhisper2(userName);
        }
    }
    /**
     * Scanner Zustand "Whisper" Level 2.
     */
    private void _scanWhisper2(String AUserName) throws CmdScannerException {
        String message = findRestOfInput();
        if (message == null) {
            onMissingToken("Meldung");
        } else {
            hooks.onCmdWhisper(AUserName, message);
        }
    }
}
