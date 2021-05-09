package de.dhbw.binaeratops.service.impl;

import de.dhbw.binaeratops.groups.Logger;
import de.dhbw.binaeratops.model.api.AvatarI;
import de.dhbw.binaeratops.model.api.DungeonI;
import de.dhbw.binaeratops.model.api.UserI;
import de.dhbw.binaeratops.model.entitys.Avatar;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerSyntaxMissingException;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerSyntaxUnexpectedException;
import de.dhbw.binaeratops.service.impl.parser.ParserService;
import de.dhbw.binaeratops.service.impl.parser.UserMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class ParserServiceTest extends Logger {

//    private ParserService parser;
//
//    private DungeonI dungeon;
//    private UserI user;
//    private AvatarI avatar;
//
//    @Before
//    public void setUp() {
//        parser = new ParserService();
//
//        dungeon = new Dungeon();
//        dungeon.setDungeonId(123L);
//        dungeon.setCommandSymbol('/');
//
//        user = new User();
//        avatar = new Avatar();
//    }
//
//    //InGameCmdScanner
//    @Test
//    public void testHelpCommand() throws CmdScannerException, InvalidImplementationException {
//        UserMessage um = parser.parseCommand("/help", dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("view.game.cmd.help",um.getKey());
//    }
//
//    @Test
//    public void testHelpAll() throws CmdScannerException, InvalidImplementationException {
//        UserMessage um = parser.parseCommand("/help all",dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("view.game.cmd.help.all",um.getKey());
//    }
//
//    @Test
//    public void testHelpCommands() throws CmdScannerException, InvalidImplementationException {
//        UserMessage um = parser.parseCommand("/help commands",dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("view.game.cmd.help.cmds",um.getKey());
//    }
//
//    @Test
//    public void testHelpControl() throws CmdScannerException, InvalidImplementationException {
//        UserMessage um = parser.parseCommand("/help control",dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("view.game.cmd.help.ctrl",um.getKey());
//    }
//
//    @Test(expected = CmdScannerSyntaxUnexpectedException.class)
//    public void testHelpFailure1() throws CmdScannerException, InvalidImplementationException {
//        UserMessage um = parser.parseCommand("/help al",dungeon.getDungeonId(), avatar, user);
//    }
//
//    @Test(expected = CmdScannerSyntaxMissingException.class)
//    public void testMissingToken() throws CmdScannerException, InvalidImplementationException {
//        UserMessage um = parser.parseCommand("/",dungeon.getDungeonId(), avatar, user);
//    }
//
//
//
//
//    //GameCtrlCmdScanner
//    @Test
//    public void testInfoCommand() throws CmdScannerException, InvalidImplementationException{
//        UserMessage um = parser.parseCommand("info",dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("view.game.ctrl.cmd.info", um.getKey());
//    }
//
//    @Test
//    public void testInfoAll() throws CmdScannerException, InvalidImplementationException{
//        UserMessage um = parser.parseCommand("info all",dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("view.game.ctrl.cmd.info.all", um.getKey());
//    }
//
//    @Test
//    public void testInfoRoom() throws CmdScannerException, InvalidImplementationException{
//        UserMessage um = parser.parseCommand("info room",dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("view.game.ctrl.cmd.info.room", um.getKey());
//    }
//
//    @Test
//    public void testInfoPlayers() throws CmdScannerException, InvalidImplementationException{
//        UserMessage um = parser.parseCommand("info players",dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("view.game.ctrl.cmd.info.players", um.getKey());
//    }
//
//    @Test
//    public void testMoveNorth() throws CmdScannerException, InvalidImplementationException{
//        UserMessage um = parser.parseCommand("move north",dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("view.game.ctrl.cmd.move.north", um.getKey());
//        um = parser.parseCommand("move n",dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("view.game.ctrl.cmd.move.north.k", um.getKey());
//    }
//
//    @Test
//    public void testMoveEast() throws CmdScannerException, InvalidImplementationException{
//        UserMessage um = parser.parseCommand("move east",dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("view.game.ctrl.cmd.move.east", um.getKey());
//        um = parser.parseCommand("move e",dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("view.game.ctrl.cmd.move.east.k", um.getKey());
//    }
//
//    @Test
//    public void testMoveSouth() throws CmdScannerException, InvalidImplementationException{
//        UserMessage um = parser.parseCommand("move south",dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("view.game.ctrl.cmd.move.south", um.getKey());
//        um = parser.parseCommand("move s",dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("view.game.ctrl.cmd.move.south.k", um.getKey());
//    }
//
//    @Test
//    public void testMoveWest() throws CmdScannerException, InvalidImplementationException{
//        UserMessage um = parser.parseCommand("move west",dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("view.game.ctrl.cmd.move.west", um.getKey());
//        um = parser.parseCommand("move w",dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("view.game.ctrl.cmd.move.west.k", um.getKey());
//    }
//
//    @Test
//    public void testLookAround() throws CmdScannerException, InvalidImplementationException{
//        UserMessage um = parser.parseCommand("look around",dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("view.game.ctrl.cmd.look.around", um.getKey());
//    }
//
//    @Test
//    public void testExamine() throws CmdScannerException, InvalidImplementationException{
//        UserMessage um = parser.parseCommand("examine cool_apple",dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("cool_apple".toUpperCase(), um.getKey());
//    }
//
//    @Test
//    public void testShowInventory() throws CmdScannerException, InvalidImplementationException{
//        UserMessage um = parser.parseCommand("show inventory",dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("view.game.ctrl.cmd.show.inventory", um.getKey());
//        um = parser.parseCommand("show inv",dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("view.game.ctrl.cmd.show.inventory.k", um.getKey());
//    }
//
//    @Test
//    public void testShowEquipment() throws CmdScannerException, InvalidImplementationException{
//        UserMessage um = parser.parseCommand("show equipment",dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("view.game.ctrl.cmd.show.equipment", um.getKey());
//        um = parser.parseCommand("show eq",dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("view.game.ctrl.cmd.show.equipment.k", um.getKey());
//    }
//
//    @Test
//    public void testTake() throws CmdScannerException, InvalidImplementationException{
//        UserMessage um = parser.parseCommand("take cool_apple",dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("cool_apple".toUpperCase(), um.getKey());
//    }
//
//    @Test
//    public void testDrop() throws CmdScannerException, InvalidImplementationException{
//        UserMessage um = parser.parseCommand("drop cool_apple",dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("cool_apple".toUpperCase(), um.getKey());
//    }
//
//    @Test
//    public void testEat() throws CmdScannerException, InvalidImplementationException {
//        UserMessage um = parser.parseCommand("eat cool_apple",dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("cool_apple".toUpperCase(), um.getKey());
//    }
//
//    @Test
//    public void testDrink() throws CmdScannerException, InvalidImplementationException{
//        UserMessage um = parser.parseCommand("drink cold_water",dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("cold_water".toUpperCase(), um.getKey());
//    }
//
//    @Test
//    public void testEquip() throws CmdScannerException, InvalidImplementationException{
//        UserMessage um = parser.parseCommand("equip cool_shield",dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("cool_shield".toUpperCase(), um.getKey());
//    }
//
//    @Test
//    public void testLayDown() throws CmdScannerException, InvalidImplementationException{
//        UserMessage um = parser.parseCommand("lay down cool_shield",dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("cool_shield".toUpperCase(), um.getKey());
//    }
//
//    @Test
//    public void testTalk() throws CmdScannerException, InvalidImplementationException{
//        UserMessage um = parser.parseCommand("talk spongebob",dungeon.getDungeonId(), avatar, user);
//        Assert.assertEquals("spongebob".toUpperCase(), um.getKey());
//    }
}
