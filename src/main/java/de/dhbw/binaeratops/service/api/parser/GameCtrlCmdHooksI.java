package de.dhbw.binaeratops.service.api.parser;

import de.dhbw.binaeratops.model.api.DungeonI;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;
import de.dhbw.binaeratops.service.impl.parser.UserMessage;

/**
 * Schnittstelle für die Callbacks des Scanners für die Spielsteuerungs-Befehle.
 * <p>
 * Für Implementierung siehe @{@link de.dhbw.binaeratops.service.impl.parser.gamectrl.GameCtrlCmdHooks}.
 * </p>
 *
 * @author Nicolas Haug, Lukas Göpel
 */
public interface GameCtrlCmdHooksI {

    /**
     * Callback Befehl "whereami".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onWhereAmI(DungeonI ADungeon) throws CmdScannerException;

    /**
     * Callback Befehl "info all".
     *
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onInfoAll() throws CmdScannerException;

    /**
     * Callback Befehl "info room".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onInfoRoom(DungeonI ADungeon) throws CmdScannerException;

    /**
     * Callback Befehl "info players".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onInfoPlayers(DungeonI ADungeon) throws CmdScannerException;

    /**
     * Callback Befehl "move north" und "move n".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onMoveNorth(DungeonI ADungeon) throws CmdScannerException;

    /**
     * Callback Befehl "move east" und "move e".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onMoveEast(DungeonI ADungeon) throws CmdScannerException;

    /**
     * Callback Befehl "move south" und "move s".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onMoveSouth(DungeonI ADungeon) throws CmdScannerException;

    /**
     * Callback Befehl "move west" und "move w".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onMoveWest(DungeonI ADungeon) throws CmdScannerException;

    /**
     * Callback Befehl "look around".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onLookAround(DungeonI ADungeon) throws CmdScannerException;

    /**
     * Callback Befehl "examine".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AItemOrNpc Gegenstand oder NPC der untersucht werden soll.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onExamine(DungeonI ADungeon, String AItemOrNpc) throws CmdScannerException;

    /**
     * Callback Befehl "show inv".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onShowInventory(DungeonI ADungeon) throws CmdScannerException;

    /**
     * Callback Befehl "show equip".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onShowEquipment(DungeonI ADungeon) throws CmdScannerException;

    /**
     * Callback Befehl "take".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AItem Gegenstand der aufgenommen werden soll.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onTake(DungeonI ADungeon, String AItem) throws CmdScannerException;

    /**
     * Callback Befehl "drop".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AItem Gegenstand, der fallen gelassen werden soll.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onDrop(DungeonI ADungeon, String AItem) throws CmdScannerException;

    /**
     * Callback Befehl "eat".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AItem Gegenstand, der gegessen werden soll.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onEat(DungeonI ADungeon, String AItem) throws CmdScannerException;

    /**
     * Callback Befehl "drink".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AItem Gegenstand, der getrunken werden soll.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onDrink(DungeonI ADungeon, String AItem) throws CmdScannerException;

    /**
     * Callback Befehl "equip".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AItem Gegenstand, der ausgerüstet werden soll.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onEquip(DungeonI ADungeon, String AItem) throws CmdScannerException;

    /**
     * Callback Befehl "laydown".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AItem Gegenstand, der hingelegt werden soll.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onLayDown(DungeonI ADungeon, String AItem) throws CmdScannerException;

    /**
     * Callback Befehl "health".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onGetHealth(DungeonI ADungeon) throws CmdScannerException;

    /**
     * Callback Befehl "talk".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param ANpcName Name des NPCs.
     * @param AMessage Nachricht.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onTalk(DungeonI ADungeon, String ANpcName, String AMessage) throws CmdScannerException;
}