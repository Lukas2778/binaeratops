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
import de.dhbw.binaeratops.service.api.parser.InGameCmdHooksI;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerException;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerSyntaxMissingException;
import de.dhbw.binaeratops.service.exceptions.parser.CmdScannerSyntaxUnexpectedException;
import de.dhbw.binaeratops.service.impl.parser.ingame.InGameCmdScanner;
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
public class InGameCmdScannerTest extends Logger {

    @Mock
    InGameCmdHooksI hooks;

    @Mock
    InGameCmdScanner scanner;

    private DungeonI dungeon;
    private UserI user;
    private AvatarI avatar;

    @Before
    public void setUp() {
        scanner = new InGameCmdScanner(hooks);
        dungeon = new Dungeon();
        user = new User();
        avatar = new Avatar();
    }

    @Test
    public void testHelpCommand() throws CmdScannerException, InvalidImplementationException {
        String input = "/HELP";
        Mockito.when(hooks.onCmdHelp(dungeon)).thenReturn(new UserMessage("view.game.cmd.help"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.cmd.help", um.getKey());
    }

    @Test
    public void testHelpAll() throws CmdScannerException, InvalidImplementationException {
        String input = "/HELP ALL";
        Mockito.when(hooks.onCmdHelpAll(dungeon)).thenReturn(new UserMessage("view.game.cmd.help.all"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.cmd.help.all", um.getKey());
    }

    @Test
    public void testHelpCommands() throws CmdScannerException, InvalidImplementationException {
        String input = "/HELP CMDS";
        Mockito.when(hooks.onCmdHelpCmds(dungeon)).thenReturn(new UserMessage("view.game.cmd.help.cmds"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.cmd.help.cmds", um.getKey());
    }

    @Test
    public void testHelpControl() throws CmdScannerException, InvalidImplementationException {
        String input = "/HELP CTRL";
        Mockito.when(hooks.onCmdHelpCtrl(dungeon)).thenReturn(new UserMessage("view.game.cmd.help.ctrl"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.cmd.help.ctrl", um.getKey());
    }

    @Test(expected = CmdScannerSyntaxUnexpectedException.class)
    public void testHelpFailure1() throws CmdScannerException, InvalidImplementationException {
        String input = "/HE";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test(expected = CmdScannerSyntaxMissingException.class)
    public void testMissingToken() throws CmdScannerException, InvalidImplementationException {
        String input = "/";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test
    public void testWhisperCommand() throws CmdScannerException, InvalidImplementationException {
        String input = "/WHISPER avh test";
        Mockito.when(hooks.onCmdWhisper(dungeon, avatar, "avh", "test")).thenReturn(new UserMessage("view.game.cmd.whisper"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.cmd.whisper", um.getKey());
    }

    @Test
    public void testWhisperMasterCommand() throws CmdScannerException, InvalidImplementationException {
        String input = "/WHISPER MASTER test";
        Mockito.when(hooks.onCmdWhisperMaster(dungeon, avatar, "test")).thenReturn(new UserMessage("view.game.cmd.whisper.master"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.cmd.whisper.master", um.getKey());
    }

    @Test(expected = CmdScannerSyntaxUnexpectedException.class)
    public void testWhisperCommandFailure() throws CmdScannerException, InvalidImplementationException {
        String input = "/WHISPE timon test";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test
    public void testSpeakCommand() throws CmdScannerException, InvalidImplementationException {
        String input = "/SPEAK test";
        Mockito.when(hooks.onCmdSpeak(dungeon, avatar, "test")).thenReturn(new UserMessage("view.game.cmd.speak"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.cmd.speak", um.getKey());
    }

    @Test(expected = CmdScannerSyntaxUnexpectedException.class)
    public void testSpeakCommandFail() throws CmdScannerException, InvalidImplementationException {
        String input = "/SPEK test";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test
    public void testNotifyAllCommand() throws CmdScannerException, InvalidImplementationException {
        String input = "/NOTIFY ALL test";
        Mockito.when(hooks.onCmdNotifyAll(dungeon, user, "test")).thenReturn(new UserMessage("view.game.cmd.notify.all"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.cmd.notify.all", um.getKey());
    }

    @Test
    public void testNotifyRoomCommand() throws CmdScannerException, InvalidImplementationException {
        String input = "/NOTIFY ROOM \"test\" test";
        Mockito.when(hooks.onCmdNotifyRoom(dungeon, user, "test", "test")).thenReturn(new UserMessage("view.game.cmd.notify.all"));
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
        Assert.assertEquals("view.game.cmd.notify.all", um.getKey());
    }

    @Test(expected = CmdScannerSyntaxMissingException.class)
    public void testNotifyRoomCommandFail() throws CmdScannerException, InvalidImplementationException {
        String input = "/NOTIFY ROOM \"test test";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }

    @Test(expected = CmdScannerSyntaxUnexpectedException.class)
    public void testNotifyCommandFail() throws CmdScannerException, InvalidImplementationException {
        String input = "/NOTIFY test test";
        UserMessage um = scanner.scan(input, dungeon, avatar, user);
    }
}
