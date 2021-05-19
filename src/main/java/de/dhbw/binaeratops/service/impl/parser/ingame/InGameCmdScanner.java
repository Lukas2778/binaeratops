package de.dhbw.binaeratops.service.impl.parser.ingame;

import de.dhbw.binaeratops.model.api.AvatarI;
import de.dhbw.binaeratops.model.api.DungeonI;
import de.dhbw.binaeratops.model.api.UserI;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
import de.dhbw.binaeratops.service.api.parser.InGameCmdHooksI;
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
public class InGameCmdScanner extends AbstractCmdScanner {

    @Autowired
    private InGameCmdHooksI hooks;

    // Schlüsselwörter.
    private static final String CMD_HELP = "HELP";
    private static final String CMD_HELP_CONTROL = "CTRL";
    private static final String CMD_HELP_CMDS = "CMDS";
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
    private static final String CMD_EXIT_STOP_GAME = "GAME";
    private static final String CMD_STOP = "STOP";
    private static final String CMD_STOP_GAME = "GAME";

    /**
     * Konstruktor.
     */
    public InGameCmdScanner() {
    }

    /**
     * Konstruktor zum Ausführen von Tests mit Mocks.
     *
     * @param AInGameCmdHooks Hooks.
     */
    public InGameCmdScanner(InGameCmdHooksI AInGameCmdHooks) {
        this.hooks = AInGameCmdHooks;
    }

    /**
     * Scanner im Zustand "scanStart".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AAvatar  Avatar, der den Befehl ausführt.
     * @param AUser    Benutzer, der den Befehl ausführt.
     * @return Benutzernachricht.
     */
    protected UserMessage scanStart(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        startWithPos(1); // Überspringe Befehlszeichen

        String token = findNextToken();
        if (token == null) {
            onMissingToken("<Befehl>");
            return null;
        } else {
            switch (token.toUpperCase()) {
                case CMD_HELP:
                    return scanHelp1(ADungeon);
                case CMD_WHISPER:
                    return scanWhisper1(ADungeon, AAvatar);
                case CMD_SPEAK:
                    return scanSpeak(ADungeon, AAvatar);
                case CMD_NOTIFY:
                    return scanNotify(ADungeon, AUser);
                case CMD_WITHDRAW: // TODO WITHDRAW, EXIT, STOP ggf entfernen.
                    return scanWithdraw(ADungeon, AUser);
                case CMD_EXIT:
                    return scanGame(ADungeon, AUser, false);
                case CMD_STOP:
                    return scanGame(ADungeon, AUser, true);
                default:
                    onUnexpectedToken();
                    return null;
            }
        }
    }

    /**
     * Scanner im Zustand "scanHelp1".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     */
    private UserMessage scanHelp1(DungeonI ADungeon) throws CmdScannerException {
        String token = findRestOfInput();
        if (token == null) {
            return hooks.onCmdHelp(ADungeon);
        } else {
            switch (token.toUpperCase()) {
                case CMD_HELP_ALL:
                    return hooks.onCmdHelpAll(ADungeon);
                case CMD_HELP_CMDS:
                    return hooks.onCmdHelpCmds(ADungeon);
                case CMD_HELP_CONTROL:
                    return hooks.onCmdHelpCtrl(ADungeon);
                default:
                    onUnexpectedToken();
                    return null;
            }
        }
    }

    /**
     * Scanner im Zustand "scanWhisper1".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AAvatar  Avatar, der den Befehl ausführt.
     * @return Benutzernachricht.
     */
    private UserMessage scanWhisper1(DungeonI ADungeon, AvatarI AAvatar) throws CmdScannerException, InvalidImplementationException {
        String avatarname = findNextToken();
        if (avatarname == null) {
            onMissingToken("<Avatarname>");
            return null;
        } else {
            switch (avatarname.toUpperCase()) {
                case CMD_WHISPER_MASTER:
                    return scanWhisperMaster(ADungeon, AAvatar);
                default:
                    return scanWhisperAvatarName(ADungeon, AAvatar, avatarname);
            }
        }
    }

    /**
     * Scanner im Zustand "scanStart".
     *
     * @param ADungeon  Dungeon, in dem der Befehl ausgeführt wird.
     * @param AAvatar   Avatar, der den Befehl ausführt.
     * @param ARecipent Empfänger
     * @return Benutzernachricht.
     */
    private UserMessage scanWhisperAvatarName(DungeonI ADungeon, AvatarI AAvatar, String ARecipent) throws CmdScannerException, InvalidImplementationException {
        String message = findRestOfInput();
        if (message == null) {
            onMissingToken("<Nachricht>");
        } else {
            return hooks.onCmdWhisper(ADungeon, AAvatar, ARecipent, message);
        }
        return null;
    }

    /**
     * Scanner im Zustand "scanStart".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AAvatar  Avatar, der den Befehl ausführt.
     * @return Benutzernachricht.
     */
    private UserMessage scanWhisperMaster(DungeonI ADungeon, AvatarI AAvatar) throws CmdScannerException, InvalidImplementationException {
        String message = findRestOfInput();
        if (message == null) {
            onMissingToken("<Nachricht>");
            return null;
        } else {
            return hooks.onCmdWhisperMaster(ADungeon, AAvatar, message);
        }
    }

    /**
     * Scanner im Zustand "scanSpeak".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AAvatar  Avatar, der den Befehl ausführt.
     * @return Benutzernachricht.
     */
    private UserMessage scanSpeak(DungeonI ADungeon, AvatarI AAvatar) throws CmdScannerException, InvalidImplementationException {
        String message = findRestOfInput();
        if (message == null) {
            onMissingToken("<Nachricht>");
            return null;
        } else {
            return hooks.onCmdSpeak(ADungeon, AAvatar, message); //--> Woher kriegen wir die RaumID?
        }
    }

    /**
     * Scanner im Zustand "scanNotify".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AUser    Benutzer, der den Befehl ausführt.
     * @return Benutzernachricht.
     */
    private UserMessage scanNotify(DungeonI ADungeon, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        String token = findNextToken();
        if (token == null) {
            onMissingToken("<ALL|ROOM>");
            return null;
        } else {
            switch (token.toUpperCase()) {
                case CMD_NOTIFY_ROOM:
                    return scanNotifyRoom(ADungeon, AUser);
                case CMD_NOTIFY_ALL:
                    return scanNotifyAll(ADungeon, AUser);
                default:
                    onUnexpectedToken();
                    return null;
            }
        }
    }

    /**
     * Scanner im Zustand "scanNotifyRoom".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AUser    Benutzer, der den Befehl ausführt.
     * @return Benutzernachricht.
     */
    private UserMessage scanNotifyRoom(DungeonI ADungeon, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        String roomName = findParenthesesToken();
        if (roomName == null) {
            onMissingToken("\"<Raumname>\"");
            return null;
        } else {
            return scanNotifyRoomMessage(ADungeon, AUser, roomName);
        }
    }

    /**
     * Scanner im Zustand "scanStart".
     *
     * @param ADungeon  Dungeon, in dem der Befehl ausgeführt wird.
     * @param AUser     Benutzer, der den Befehl ausführt.
     * @param ARoomName Name des Raumes.
     * @return Benutzernachricht.
     */
    private UserMessage scanNotifyRoomMessage(DungeonI ADungeon, UserI AUser, String ARoomName) throws CmdScannerException, InvalidImplementationException {
        String message = findRestOfInput();
        if (message == null) {
            onMissingToken("<Nachricht>");
            return null;
        } else {
            return hooks.onCmdNotifyRoom(ADungeon, AUser, ARoomName, message);
        }
    }

    /**
     * Scanner im Zustand "scanStart".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AUser    Benutzer, der den Befehl ausführt.
     * @return Benutzernachricht.
     */
    private UserMessage scanNotifyAll(DungeonI ADungeon, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        String message = findRestOfInput();
        if (message == null) {
            onMissingToken("<Nachricht>");
            return null;
        } else {
            return hooks.onCmdNotifyAll(ADungeon, AUser, message);
        }
    }

    /**
     * Scanner im Zustand "scanWithdraw".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AUser    Benutzer, der den Befehl ausführt.
     * @return Benutzernachricht.
     */
    private UserMessage scanWithdraw(DungeonI ADungeon, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        String token = findNextToken();
        if (token == null) {
            onMissingToken("<ROLE>");
            return null;
        } else {
            switch (token.toUpperCase()) {
                case CMD_WITHDRAW_ROLE:
                    return scanWithdrawRole(ADungeon, AUser);
                default:
                    onUnexpectedToken();
                    return null;
            }
        }
    }

    /**
     * Scanner im Zustand "scanWithdrawRole".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AUser    Benutzer, der den Befehl ausführt.
     * @return Benutzernachricht.
     */
    private UserMessage scanWithdrawRole(DungeonI ADungeon, UserI AUser) throws CmdScannerException, InvalidImplementationException {
        String username = findRestOfInput();
        if (username == null) {
            onMissingToken("<Benutzername>");
            return null;
        } else {
            return hooks.onCmdWithdrawRole(ADungeon, AUser, username);
        }
    }

    /**
     * Scanner im Zustand "scanStart".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AUser    Benutzer, der den Befehl ausführt.
     * @param AStop    Wahrheitswert, ob gestoppt oder pausiert werden soll.
     * @return Benutzernachricht.
     */
    private UserMessage scanGame(DungeonI ADungeon, UserI AUser, Boolean AStop) throws CmdScannerException {
        String game = findRestOfInput();
        if (game == null) {
            onMissingToken("<GAME>");
        } else {
            switch (game.toUpperCase()) {
                case CMD_EXIT_STOP_GAME:
                    if (AStop) {
                        return hooks.onCmdStop(ADungeon, AUser);
                    } else {
                        return hooks.onCmdExit(ADungeon, AUser);
                    }
                default:
                    onUnexpectedToken();
                    return null;
            }
        }
        return null;
    }
}
