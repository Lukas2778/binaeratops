package de.dhbw.binaeratops.service.api.parser;

import de.dhbw.binaeratops.model.enums.Direction;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;

/**
 * Callbacks des Scanners für die Spielsteuerungs-Befehle.
 *
 * @author Nicolas Haug
 */
public interface GameCtrlCmdHooksI {

    /**
     * Callback Befehl "[move] north|south|east|west [steps]".
     *
     * @param ADirection Richtung.
     * @param ASteps Schritte.
     *
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    void onCmdMove(Direction ADirection, int ASteps) throws CmdScannerException;

    /**
     * Callback Befehl "punch".
     *
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    void onCmdPunch() throws CmdScannerException;
}