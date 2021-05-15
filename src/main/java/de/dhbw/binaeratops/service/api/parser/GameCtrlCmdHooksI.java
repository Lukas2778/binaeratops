package de.dhbw.binaeratops.service.api.parser;

import de.dhbw.binaeratops.model.api.AvatarI;
import de.dhbw.binaeratops.model.api.DungeonI;
import de.dhbw.binaeratops.model.api.UserI;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
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
    // TODO JAVADOC Übergabeparameter ergänzen/hinzufügen
    /**
     * Callback Befehl "whereami".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onWhereAmI(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "whoami".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onWhoAmI(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "info all".
     *
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onInfoAll(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "info room".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onInfoRoom(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "info players".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onInfoPlayers(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "move north" und "move n".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onMoveNorth(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "move east" und "move e".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onMoveEast(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "move south" und "move s".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onMoveSouth(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "move west" und "move w".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onMoveWest(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "look around".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onLookAround(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "examine".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AItemOrNpc Gegenstand oder NPC der untersucht werden soll.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onExamineNpc(DungeonI ADungeon, String AItemOrNpc, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    UserMessage onExamineItem(DungeonI ADungeon, String AItemOrNpc, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "show inv".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onShowInventory(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "show equip".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onShowEquipment(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "take".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AItem Gegenstand der aufgenommen werden soll.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onTake(DungeonI ADungeon, String AItem, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "drop".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AItem Gegenstand, der fallen gelassen werden soll.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onDrop(DungeonI ADungeon, String AItem, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "eat".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AItem Gegenstand, der gegessen werden soll.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onEat(DungeonI ADungeon, String AItem, AvatarI AAvatar, UserI AUser) throws CmdScannerException;

    /**
     * Callback Befehl "drink".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AItem Gegenstand, der getrunken werden soll.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onDrink(DungeonI ADungeon, String AItem, AvatarI AAvatar, UserI AUser) throws CmdScannerException;

    /**
     * Callback Befehl "equip".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AItem Gegenstand, der ausgerüstet werden soll.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onEquip(DungeonI ADungeon, AvatarI AAvatar, UserI AUser, String AItem) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "laydown".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AItem Gegenstand, der hingelegt werden soll.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onLayDown(DungeonI ADungeon, AvatarI AAvatar, UserI AUser, String AItem) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "health".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onGetHealth(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "talk".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param ANpcName Name des NPCs.
     * @param AMessage Nachricht.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     */
    UserMessage onTalk(DungeonI ADungeon, AvatarI AAvatar, UserI AUser, String ANpcName, String AMessage) throws CmdScannerException;
}