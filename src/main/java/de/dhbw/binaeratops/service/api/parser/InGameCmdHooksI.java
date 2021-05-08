package de.dhbw.binaeratops.service.api.parser;

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
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onCmdWhisper(String AUserName, String AMessage) throws CmdScannerException;

    /**
     * Callback Befehl "whisper master".
     *
     * @param AMessage Nachricht
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onCmdWhisperMaster(String AMessage) throws CmdScannerException;

    /**
     * Callback Befehl "speak".
     *
     * @param ARoomId Raum, in den gesprochen wird.
     * @param AMessage Nachricht.
     * @return Benutzernachricht
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onCmdSpeak(String ARoomId, String AMessage) throws CmdScannerException;

    /**
     * Callback Befehl "notify room".
     *
     * @param ARoomName Raum, in den gesprochen wird.
     * @param AMessage Nachricht.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onCmdNotifyRoom(String ARoomName, String AMessage) throws CmdScannerException;

    /**
     * Callback Befehl "notify all".
     * @param AMessage Nachricht.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onCmdNotifyAll(String AMessage) throws CmdScannerException;

    /**
     * Callback Befehl "withdraw role".
     *
     * @param AUsername Benutzer, dem die Dungeon-Master Rolle übergeben werden soll.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onCmdWithdrawRole(String AUsername) throws CmdScannerException;

    /**
     * Callback Befehl "stop game".
     *
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onCmdStop() throws CmdScannerException;

    /**
     * Callback Befehl "exit game".
     *
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onCmdExit() throws CmdScannerException;

}