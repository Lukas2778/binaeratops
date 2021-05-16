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
     * @param AAvatar Avatar, der den Befehl ausführt.
     * @param AUser Benutzer, der den Befehl ausführt.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * @throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onWhereAmI(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "whoami".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AAvatar Avatar, der den Befehl ausführt.
     * @param AUser Benutzer, der den Befehl ausführt.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * @throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onWhoAmI(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "info all".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AAvatar Avatar, der den Befehl ausführt.
     * @param AUser Benutzer, der den Befehl ausführt.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * @throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onInfoAll(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "info room".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AAvatar Avatar, der den Befehl ausführt.
     * @param AUser Benutzer, der den Befehl ausführt.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * @throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onInfoRoom(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "info players".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AAvatar Avatar, der den Befehl ausführt.
     * @param AUser Benutzer, der den Befehl ausführt.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * @throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onInfoPlayers(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "move north" und "move n".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AAvatar Avatar, der den Befehl ausführt.
     * @param AUser Benutzer, der den Befehl ausführt.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * @throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onMoveNorth(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "move east" und "move e".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AAvatar Avatar, der den Befehl ausführt.
     * @param AUser Benutzer, der den Befehl ausführt.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * @throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onMoveEast(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "move south" und "move s".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AAvatar Avatar, der den Befehl ausführt.
     * @param AUser Benutzer, der den Befehl ausführt.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * @throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onMoveSouth(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "move west" und "move w".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AAvatar Avatar, der den Befehl ausführt.
     * @param AUser Benutzer, der den Befehl ausführt.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * @throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onMoveWest(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "look around".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AAvatar Avatar, der den Befehl ausführt.
     * @param AUser Benutzer, der den Befehl ausführt.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * @throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onLookAround(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "examine npc".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AAvatar Avatar, der den Befehl ausführt.
     * @param AUser Benutzer, der den Befehl ausführt.
     * @param ANpc NPC der untersucht werden soll.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * @throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onExamineNpc(DungeonI ADungeon, String ANpc, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "examine item".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AAvatar Avatar, der den Befehl ausführt.
     * @param AUser Benutzer, der den Befehl ausführt.
     * @param AItem Gegenstand der untersucht werden soll.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * @throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onExamineItem(DungeonI ADungeon, String AItem, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "show inv".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AAvatar Avatar, der den Befehl ausführt.
     * @param AUser Benutzer, der den Befehl ausführt.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * @throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onShowInventory(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "show equip".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AAvatar Avatar, der den Befehl ausführt.
     * @param AUser Benutzer, der den Befehl ausführt.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * @throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onShowEquipment(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "take".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AAvatar Avatar, der den Befehl ausführt.
     * @param AUser Benutzer, der den Befehl ausführt.
     * @param AItem Gegenstand der aufgenommen werden soll.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * @throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onTake(DungeonI ADungeon, String AItem, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "drop".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AAvatar Avatar, der den Befehl ausführt.
     * @param AUser Benutzer, der den Befehl ausführt.
     * @param AItem Gegenstand, der fallen gelassen werden soll.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * @throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onDrop(DungeonI ADungeon, String AItem, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "consume".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AAvatar Avatar, der den Befehl ausführt.
     * @param AUser Benutzer, der den Befehl ausführt.
     * @param AItem Gegenstand, der konsumiert werden soll.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * @throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onConsume(DungeonI ADungeon, String AItem, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "equip".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AAvatar Avatar, der den Befehl ausführt.
     * @param AUser Benutzer, der den Befehl ausführt.
     * @param AItem Gegenstand, der ausgerüstet werden soll.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * @throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onEquip(DungeonI ADungeon, AvatarI AAvatar, UserI AUser, String AItem) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "laydown".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AAvatar Avatar, der den Befehl ausführt.
     * @param AUser Benutzer, der den Befehl ausführt.
     * @param AItem Gegenstand, der hingelegt werden soll.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * @throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onLayDown(DungeonI ADungeon, AvatarI AAvatar, UserI AUser, String AItem) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "health".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AAvatar Avatar, der den Befehl ausführt.
     * @param AUser Benutzer, der den Befehl ausführt.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * @throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onGetHealth(DungeonI ADungeon, AvatarI AAvatar, UserI AUser) throws CmdScannerException, InvalidImplementationException;

    /**
     * Callback Befehl "talk".
     *
     * @param ADungeon Dungeon, in dem der Befehl ausgeführt wird.
     * @param AAvatar Avatar, der den Befehl ausführt.
     * @param AUser Benutzer, der den Befehl ausführt.
     * @param ANpcName Name des NPCs.
     * @param AMessage Nachricht.
     * @return Benutzernachricht.
     * @throws CmdScannerException Fehler bei der Ausführung des Befehls.
     * //@throws InvalidImplementationException Fehler, bei der Übergabe des Interface Objektes.
     */
    UserMessage onTalk(DungeonI ADungeon, AvatarI AAvatar, UserI AUser, String ANpcName, String AMessage) throws CmdScannerException;
}