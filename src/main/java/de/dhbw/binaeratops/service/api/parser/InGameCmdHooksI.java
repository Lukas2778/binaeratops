package de.dhbw.binaeratops.service.api.parser;

import de.dhbw.binaeratops.model.api.AvatarI;
import de.dhbw.binaeratops.model.api.DungeonI;
import de.dhbw.binaeratops.model.api.UserI;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;
import de.dhbw.binaeratops.service.impl.parser.UserMessage;

/**
 * Callbacks des Scanners für die "In-Game"-Befehle.
 *
 * @author Nicolas Haug
 */
public interface InGameCmdHooksI {
    /**
     * Callback Befehl "whisper".
     *
     * @param AMessage Nachricht.
     * @param AUserName Empfänger.
     * @param AAvatar Avatar, der den Befehl ausführt.
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onCmdWhisper(DungeonI ADungeon, AvatarI AAvatar, String AUserName, String AMessage) throws CmdScannerException;

    /**
     * Callback Befehl "whisper master".
     *
     * @param AAvatar Avatar, der den Befehl ausführt.
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AMessage Nachricht
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onCmdWhisperMaster(DungeonI ADungeon, AvatarI AAvatar, String AMessage) throws CmdScannerException;

    /**
     * Callback Befehl "speak".
     *
     * @param AAvatar Avatar, der den Befehl ausführt.
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AMessage Nachricht.
     * @return Benutzernachricht
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onCmdSpeak(DungeonI ADungeon, AvatarI AAvatar, String AMessage) throws CmdScannerException;

    /**
     * Callback Befehl "notify room".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AUser Benutzer, der den Befehl ausführt.
     * @param ARoomName Raum, in den gesprochen wird.
     * @param AMessage Nachricht.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onCmdNotifyRoom(DungeonI ADungeon, UserI AUser, String ARoomName, String AMessage) throws CmdScannerException;

    /**
     * Callback Befehl "notify all".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AUser Benutzer, der den Befehl ausführt.
     * @param AMessage Nachricht.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onCmdNotifyAll(DungeonI ADungeon, UserI AUser, String AMessage) throws CmdScannerException;

    /**
     * Callback Befehl "withdraw role".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AUser Benutzer, der den Befehl ausführt.
     * @param ARecipent Benutzer, dem die Dungeon-Master Rolle übergeben werden soll.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onCmdWithdrawRole(DungeonI ADungeon, UserI AUser, String ARecipent) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "stop game".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AUser Benutzer, der den Befehl ausführt.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onCmdStop(DungeonI ADungeon, UserI AUser) throws CmdScannerException;

    /**
     * Callback Befehl "exit game".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AUser Benutzer, der den Befehl ausführt.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onCmdExit(DungeonI ADungeon, UserI AUser) throws CmdScannerException;
}