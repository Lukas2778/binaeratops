package de.dhbw.binaeratops.service.impl.parser;

import de.dhbw.binaeratops.groups.ImplGroup;
import de.dhbw.binaeratops.groups.Logger;
import de.dhbw.binaeratops.model.api.AvatarI;
import de.dhbw.binaeratops.model.api.DungeonI;
import de.dhbw.binaeratops.model.api.UserI;
import de.dhbw.binaeratops.model.entitys.Avatar;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.exceptions.InvalidImplementationException;
import de.dhbw.binaeratops.service.api.parser.GameCtrlCmdHooksI;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerSyntaxMissingException;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerSyntaxUnexpectedException;
import de.dhbw.binaeratops.service.impl.parser.gamectrl.GameCtrlCmdScanner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

@RunWith(MockitoJUnitRunner.class)
@Category({ImplGroup.class})
@ActiveProfiles("test")
public class GameCtrlCmdScannerTest extends Logger {

    @Mock
    GameCtrlCmdHooksI hooks;

    @Mock
    GameCtrlCmdScanner scanner;

    private DungeonI dungeon;
    private UserI user;
    private AvatarI avatar;

    @Before
    public void setUp() {
        scanner = new GameCtrlCmdScanner(hooks);
        dungeon = new Dungeon();
        user = new User();
        avatar = new Avatar();
    }

    @Test
    public void testInfoAllCommand() throws CmdScannerException, InvalidImplementationException {
        String input = "INFO ALL";
        Mockito.when(hooks.onInfoAll(dungeon, avatar, user)).thenReturn(new UserMessage("view.game.ctrl.cmd.info.all"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.ctrl.cmd.info.all", um.getKey());
    }

    @Test
    public void testInfoPlayersCommand() throws CmdScannerException, InvalidImplementationException {
        String input = "INFO PLAYERS";
        Mockito.when(hooks.onInfoPlayers(dungeon, avatar, user)).thenReturn(new UserMessage("view.game.ctrl.cmd.info.players"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.ctrl.cmd.info.players", um.getKey());
    }

    @Test
    public void testInfoRoomCommand() throws CmdScannerException, InvalidImplementationException {
        String input = "INFO ROOM";
        Mockito.when(hooks.onInfoRoom(dungeon, avatar, user)).thenReturn(new UserMessage("view.game.ctrl.cmd.info.room"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.ctrl.cmd.info.room", um.getKey());
    }

    @Test(expected = CmdScannerSyntaxMissingException.class)
    public void testInfoCommandFailure() throws CmdScannerException, InvalidImplementationException {
        String input = "INFO";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test(expected = CmdScannerSyntaxUnexpectedException.class)
    public void testInfoCommandFailure2() throws CmdScannerException, InvalidImplementationException {
        String input = "INFO AL";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test
    public void testMoveNorthCommand() throws CmdScannerException, InvalidImplementationException {
        String input = "MOVE N";
        Mockito.when(hooks.onMoveNorth(dungeon, avatar, user)).thenReturn(new UserMessage("view.game.ctrl.cmd.move.north"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.ctrl.cmd.move.north", um.getKey());
        String input2 = "MOVE NORTH";
        Mockito.when(hooks.onMoveNorth(dungeon, avatar, user)).thenReturn(new UserMessage("view.game.ctrl.cmd.move.north"));
        UserMessage um2 = scanner.scan(input2, dungeon, avatar, user);
        Assert.assertEquals("view.game.ctrl.cmd.move.north", um2.getKey());
    }

    @Test
    public void testMoveSouthCommand() throws CmdScannerException, InvalidImplementationException {
        String input = "MOVE s";
        Mockito.when(hooks.onMoveSouth(dungeon, avatar, user)).thenReturn(new UserMessage("view.game.ctrl.cmd.move.south"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.ctrl.cmd.move.south", um.getKey());
        String input2 = "MOVE south";
        Mockito.when(hooks.onMoveSouth(dungeon, avatar, user)).thenReturn(new UserMessage("view.game.ctrl.cmd.move.south"));
        UserMessage um2 = scanner.scan(input2, dungeon, avatar, user);
        Assert.assertEquals("view.game.ctrl.cmd.move.south", um2.getKey());
    }

    @Test
    public void testMoveEastCommand() throws CmdScannerException, InvalidImplementationException {
        String input = "MOVE e";
        Mockito.when(hooks.onMoveEast(dungeon, avatar, user)).thenReturn(new UserMessage("view.game.ctrl.cmd.move.east"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.ctrl.cmd.move.east", um.getKey());
        String input2 = "MOVE east";
        Mockito.when(hooks.onMoveEast(dungeon, avatar, user)).thenReturn(new UserMessage("view.game.ctrl.cmd.move.east"));
        UserMessage um2 = scanner.scan(input2, dungeon, avatar, user);
        Assert.assertEquals("view.game.ctrl.cmd.move.east", um2.getKey());
    }

    @Test
    public void testMoveWestCommand() throws CmdScannerException, InvalidImplementationException {
        String input = "MOVE w";
        Mockito.when(hooks.onMoveWest(dungeon, avatar, user)).thenReturn(new UserMessage("view.game.ctrl.cmd.move.west"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.ctrl.cmd.move.west", um.getKey());
        String input2 = "MOVE west";
        Mockito.when(hooks.onMoveWest(dungeon, avatar, user)).thenReturn(new UserMessage("view.game.ctrl.cmd.move.west"));
        UserMessage um2 = scanner.scan(input2, dungeon, avatar, user);
        Assert.assertEquals("view.game.ctrl.cmd.move.west", um2.getKey());
    }

    @Test(expected = CmdScannerSyntaxMissingException.class)
    public void testMoveCommandFailure() throws CmdScannerException, InvalidImplementationException {
        String input = "MOVE";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test(expected = CmdScannerSyntaxUnexpectedException.class)
    public void testMoveCommandFailure2() throws CmdScannerException, InvalidImplementationException {
        String input = "MOVE d";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test
    public void testLookAroundCommand() throws CmdScannerException, InvalidImplementationException {
        String input = "LOOK AROUND";
        Mockito.when(hooks.onLookAround(dungeon, avatar, user)).thenReturn(new UserMessage("view.game.ctrl.cmd.look.around.n.e.s.w"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.ctrl.cmd.look.around.n.e.s.w", um.getKey());
    }

    @Test(expected = CmdScannerSyntaxMissingException.class)
    public void testLookAroundCommandFailure1() throws CmdScannerException, InvalidImplementationException {
        String input = "LOOK";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test(expected = CmdScannerSyntaxUnexpectedException.class)
    public void testLookAroundCommandFailure2() throws CmdScannerException, InvalidImplementationException {
        String input = "LOOK ARUND";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test
    public void testWhereAmICommand() throws CmdScannerException, InvalidImplementationException {
        String input = "WHEREAMI";
        Mockito.when(hooks.onWhereAmI(dungeon, avatar, user)).thenReturn(new UserMessage("view.game.ctrl.cmd.whereami"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.ctrl.cmd.whereami", um.getKey());
    }

    @Test(expected = CmdScannerSyntaxUnexpectedException.class)
    public void testWhereAmICommandFailure2() throws CmdScannerException, InvalidImplementationException {
        String input = "WHEREAM";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test
    public void testWhoAmICommand() throws CmdScannerException, InvalidImplementationException {
        String input = "WHOAMI";
        Mockito.when(hooks.onWhoAmI(dungeon, avatar, user)).thenReturn(new UserMessage("view.game.ctrl.cmd.whoami"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.ctrl.cmd.whoami", um.getKey());
    }

    @Test(expected = CmdScannerSyntaxUnexpectedException.class)
    public void testWhoAmICommandFailure2() throws CmdScannerException, InvalidImplementationException {
        String input = "WHOAM";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test
    public void testExamineNpcCommand() throws CmdScannerException, InvalidImplementationException {
        String input = "EXAMINE NPC test";
        Mockito.when(hooks.onExamineNpc(dungeon, "test", avatar, user)).thenReturn(new UserMessage("view.game.ctrl.cmd.examine.npc"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.ctrl.cmd.examine.npc", um.getKey());
    }

    @Test
    public void testExamineItemCommand() throws CmdScannerException, InvalidImplementationException {
        String input = "EXAMINE item test";
        Mockito.when(hooks.onExamineItem(dungeon, "test", avatar, user)).thenReturn(new UserMessage("view.game.ctrl.cmd.examine.item"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.ctrl.cmd.examine.item", um.getKey());
    }

    @Test(expected = CmdScannerSyntaxMissingException.class)
    public void testExamineCommandFailure1() throws CmdScannerException, InvalidImplementationException {
        String input = "EXAMINE";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test(expected = CmdScannerSyntaxUnexpectedException.class)
    public void testExamineCommandFailure2() throws CmdScannerException, InvalidImplementationException {
        String input = "EXAMINE ITM";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test
    public void testShowInventoryCommand() throws CmdScannerException, InvalidImplementationException {
        String input = "SHOW inventory";
        Mockito.when(hooks.onShowInventory(dungeon, avatar, user)).thenReturn(new UserMessage("view.game.ctrl.cmd.show.inventory"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.ctrl.cmd.show.inventory", um.getKey());
        String input2 = "SHOW inv";
        Mockito.when(hooks.onShowInventory(dungeon, avatar, user)).thenReturn(new UserMessage("view.game.ctrl.cmd.show.inventory"));
        UserMessage um2 = scanner.scan(input2, dungeon, avatar, user);
        Assert.assertEquals("view.game.ctrl.cmd.show.inventory", um2.getKey());
    }

    @Test(expected = CmdScannerSyntaxMissingException.class)
    public void testShowInventoryCommandFailure1() throws CmdScannerException, InvalidImplementationException {
        String input = "SHOW";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test(expected = CmdScannerSyntaxUnexpectedException.class)
    public void testShowInventoryCommandFailure2() throws CmdScannerException, InvalidImplementationException {
        String input = "SHOW INVEN";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test
    public void testShowEquipmentCommand() throws CmdScannerException, InvalidImplementationException {
        String input = "SHOW equipment";
        Mockito.when(hooks.onShowEquipment(dungeon, avatar, user)).thenReturn(new UserMessage("view.game.ctrl.cmd.show.equipment"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.ctrl.cmd.show.equipment", um.getKey());
        String input2 = "SHOW equip";
        Mockito.when(hooks.onShowEquipment(dungeon, avatar, user)).thenReturn(new UserMessage("view.game.ctrl.cmd.show.equipment"));
        UserMessage um2 = scanner.scan(input2, dungeon, avatar, user);
        Assert.assertEquals("view.game.ctrl.cmd.show.equipment", um2.getKey());
    }

    @Test(expected = CmdScannerSyntaxMissingException.class)
    public void testShowEquipmentCommandFailure1() throws CmdScannerException, InvalidImplementationException {
        String input = "SHOW";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test(expected = CmdScannerSyntaxUnexpectedException.class)
    public void testShowEquipmentCommandFailure2() throws CmdScannerException, InvalidImplementationException {
        String input = "SHOW EQUI";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test
    public void testTakeItemCommand() throws CmdScannerException, InvalidImplementationException {
        String input = "take test";
        Mockito.when(hooks.onTake(dungeon, "test", avatar, user)).thenReturn(new UserMessage("view.game.ctrl.cmd.take"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.ctrl.cmd.take", um.getKey());
    }

    @Test(expected = CmdScannerSyntaxMissingException.class)
    public void testTakeItemCommandFailure1() throws CmdScannerException, InvalidImplementationException {
        String input = "take";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test(expected = CmdScannerSyntaxUnexpectedException.class)
    public void testTakeItemCommandFailure2() throws CmdScannerException, InvalidImplementationException {
        String input = "tak";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test
    public void testDropItemCommand() throws CmdScannerException, InvalidImplementationException {
        String input = "drop test";
        Mockito.when(hooks.onDrop(dungeon, "test", avatar, user)).thenReturn(new UserMessage("view.game.ctrl.cmd.drop"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.ctrl.cmd.drop", um.getKey());
    }

    @Test(expected = CmdScannerSyntaxMissingException.class)
    public void testDropItemCommandFailure1() throws CmdScannerException, InvalidImplementationException {
        String input = "drop";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test(expected = CmdScannerSyntaxUnexpectedException.class)
    public void testDropItemCommandFailure2() throws CmdScannerException, InvalidImplementationException {
        String input = "drp";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test
    public void testConsumeItemCommand() throws CmdScannerException, InvalidImplementationException {
        String input = "consume test";
        Mockito.when(hooks.onConsume(dungeon, "test", avatar, user)).thenReturn(new UserMessage("view.game.ctrl.cmd.consume"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.ctrl.cmd.consume", um.getKey());
    }

    @Test(expected = CmdScannerSyntaxMissingException.class)
    public void testConsumeItemCommandFailure1() throws CmdScannerException, InvalidImplementationException {
        String input = "consume";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test(expected = CmdScannerSyntaxUnexpectedException.class)
    public void testConsumeItemCommandFailure2() throws CmdScannerException, InvalidImplementationException {
        String input = "cnsm";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test
    public void testEquipItemCommand() throws CmdScannerException, InvalidImplementationException {
        String input = "equip test";
        Mockito.when(hooks.onEquip(dungeon, avatar, user, "test")).thenReturn(new UserMessage("view.game.ctrl.cmd.equip"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.ctrl.cmd.equip", um.getKey());
    }

    @Test(expected = CmdScannerSyntaxMissingException.class)
    public void testEquipItemCommandFailure1() throws CmdScannerException, InvalidImplementationException {
        String input = "equip";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test(expected = CmdScannerSyntaxUnexpectedException.class)
    public void testEquipItemCommandFailure2() throws CmdScannerException, InvalidImplementationException {
        String input = "eq";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test
    public void testLayDownItemCommand() throws CmdScannerException, InvalidImplementationException {
        String input = "laydown test";
        Mockito.when(hooks.onLayDown(dungeon, avatar, user, "test")).thenReturn(new UserMessage("view.game.ctrl.cmd.laydown"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.ctrl.cmd.laydown", um.getKey());
    }

    @Test(expected = CmdScannerSyntaxMissingException.class)
    public void testLayDownItemCommandFailure1() throws CmdScannerException, InvalidImplementationException {
        String input = "equip";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test(expected = CmdScannerSyntaxUnexpectedException.class)
    public void testLayDownItemCommandFailure2() throws CmdScannerException, InvalidImplementationException {
        String input = "eq";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test
    public void testHealthItemCommand() throws CmdScannerException, InvalidImplementationException {
        String input = "health";
        Mockito.when(hooks.onGetHealth(dungeon, avatar, user)).thenReturn(new UserMessage("view.game.ctrl.cmd.health"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.ctrl.cmd.health", um.getKey());
    }

    @Test(expected = CmdScannerSyntaxUnexpectedException.class)
    public void testHealthCommandFailure() throws CmdScannerException, InvalidImplementationException {
        String input = "healt";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test
    public void testTalkCommand() throws CmdScannerException, InvalidImplementationException {
        String input = "talk \"test\" hi";
        Mockito.when(hooks.onTalk(dungeon, avatar, user, "test", "hi")).thenReturn(new UserMessage("view.game.ctrl.cmd.talk"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.ctrl.cmd.talk", um.getKey());
    }

    @Test(expected = CmdScannerSyntaxMissingException.class)
    public void testTalkCommandFailure1() throws CmdScannerException, InvalidImplementationException {
        String input = "talk \"tes";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test(expected = CmdScannerSyntaxMissingException.class)
    public void testTalkCommandFailure2() throws CmdScannerException, InvalidImplementationException {
        String input = "talk \"test\"";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test(expected = CmdScannerSyntaxMissingException.class)
    public void testTalkCommandFailure3() throws CmdScannerException, InvalidImplementationException {
        String input = "talk";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test(expected = CmdScannerSyntaxUnexpectedException.class)
    public void testTalkCommandFailure4() throws CmdScannerException, InvalidImplementationException {
        String input = "tlk";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test
    public void testHitCommand() throws CmdScannerException, InvalidImplementationException {
        String input = "hit test";
        Mockito.when(hooks.onHit(dungeon, avatar, user, "test")).thenReturn(new UserMessage("view.game.ctrl.cmd.hit"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.ctrl.cmd.hit", um.getKey());
    }

    @Test(expected = CmdScannerSyntaxMissingException.class)
    public void testHitCommandFailure1() throws CmdScannerException, InvalidImplementationException {
        String input = "hit";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test(expected = CmdScannerSyntaxUnexpectedException.class)
    public void testHitCommandFailure2() throws CmdScannerException, InvalidImplementationException {
        String input = "ht";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }
}
