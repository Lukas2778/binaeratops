package de.dhbw.binaeratops.service.api.parser;

import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;

/**
 * Callbacks des Scanners für die "In-Game"-Befehle.
 *
 * @author Nicolas Haug
 */
public interface InGameCmdHooksI {

    /**
     * Callback Befehl "join chat".
     *
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    void onCmdJoinChat() throws CmdScannerException;

    /**
     * Callback Befehl "join lobby".
     *
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    void onCmdJoinLobby() throws CmdScannerException;

    /**
     * Callback Befehl "logout".
     *
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    void onCmdLogout() throws CmdScannerException;

    /**
     * Callback Befehl "whisper".
     *
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    void onCmdWhisper(String AUserName, String AMessage) throws CmdScannerException;

}