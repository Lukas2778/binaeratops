package de.dhbw.binaeratops.service.api.parser;

import de.dhbw.binaeratops.model.api.AvatarI;
import de.dhbw.binaeratops.model.api.DungeonI;
import de.dhbw.binaeratops.model.api.UserI;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;
import de.dhbw.binaeratops.service.impl.parser.UserMessage;

/**
 * Schnittstelle für die Callbacks des Scanners für die "In-Game"-Befehle.
 * <p>
 * Für Implementierung siehe {@link de.dhbw.binaeratops.service.impl.parser.ingame.InGameCmdHooks}.
 * </p>
 *
 * @author Nicolas Haug
 */
public interface InGameCmdHooksI {

    /**
     * Callback Befehl "help".
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     */
    UserMessage onCmdHelp(DungeonI ADungeon);


    /**
     * Callback Befeh "help all".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     */
    UserMessage onCmdHelpAll(DungeonI ADungeon);

    /**
     * Callback Befehl "help cmds".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     */
    UserMessage onCmdHelpCmds(DungeonI ADungeon);

    /**
     * Callback Befehl "help ctrl".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     */
    UserMessage onCmdHelpCtrl(DungeonI ADungeon);

    /**
     * Callback Befehl "whisper".
     *
     * @param AMessage  Nachricht.
     * @param AUserName Empfänger.
     * @param AAvatar   Avatar, der den Befehl ausführt.
     * @param ADungeon  Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * @throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onCmdWhisper(DungeonI ADungeon, AvatarI AAvatar, String AUserName, String AMessage) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "whisper master".
     *
     * @param AAvatar  Avatar, der den Befehl ausführt.
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AMessage Nachricht
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * @throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onCmdWhisperMaster(DungeonI ADungeon, AvatarI AAvatar, String AMessage) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "speak".
     *
     * @param AAvatar  Avatar, der den Befehl ausführt.
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AMessage Nachricht.
     * @return Benutzernachricht
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * @throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onCmdSpeak(DungeonI ADungeon, AvatarI AAvatar, String AMessage) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "notify room".
     *
     * @param ADungeon  Dungeon, in dem der Befehl ausgeführt wird.
     * @param AUser     Benutzer, der den Befehl ausführt.
     * @param ARoomName Raum, in den gesprochen wird.
     * @param AMessage  Nachricht.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * @throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onCmdNotifyRoom(DungeonI ADungeon, UserI AUser, String ARoomName, String AMessage) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "notify all".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AUser    Benutzer, der den Befehl ausführt.
     * @param AMessage Nachricht.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * @throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onCmdNotifyAll(DungeonI ADungeon, UserI AUser, String AMessage) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "withdraw role".
     *
     * @param ADungeon  Dungeon, in dem der Befehl ausgeführt wird.
     * @param AUser     Benutzer, der den Befehl ausführt.
     * @param ARecipent Benutzer, dem die Dungeon-Master Rolle übergeben werden soll.
     * @return Benutzernachricht.
     * @throws CmdScannerException            Fehler bei der Ausführung des Befehls.
     * @throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     * @throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onCmdWithdrawRole(DungeonI ADungeon, UserI AUser, String ARecipent) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "stop game".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AUser    Benutzer, der den Befehl ausführt.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * //@throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onCmdStop(DungeonI ADungeon, UserI AUser) throws CmdScannerException;

    /**
     * Callback Befehl "exit game".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AUser    Benutzer, der den Befehl ausführt.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * //@throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onCmdExit(DungeonI ADungeon, UserI AUser) throws CmdScannerException;
}