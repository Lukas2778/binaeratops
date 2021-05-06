package de.dhbw.binaeratops.service.impl;

import de.dhbw.binaeratops.groups.Logger;
import de.dhbw.binaeratops.model.api.DungeonI;
import de.dhbw.binaeratops.model.entitys.Dungeon;
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

    private ParserService parser;

    private DungeonI dungeon;

    @Before
    public void setUp() {
        parser = new ParserService();

        dungeon = new Dungeon();
        dungeon.setCommandSymbol('/');
    }
    @Test
    public void testHelpCommand() throws CmdScannerException {
        UserMessage um = parser.parseCommand("/help", dungeon);
        Assert.assertEquals("view.game.cmd.help",um.getKey());
    }

    @Test
    public void testHelpAll() throws CmdScannerException {
        UserMessage um = parser.parseCommand("/help all",dungeon);
        Assert.assertEquals("view.game.cmd.help.all",um.getKey());
    }

    @Test
    public void testHelpCommands() throws CmdScannerException {
        UserMessage um = parser.parseCommand("/help commands",dungeon);
        Assert.assertEquals("view.game.cmd.help.cmds",um.getKey());
    }

    @Test
    public void testHelpControl() throws CmdScannerException {
        UserMessage um = parser.parseCommand("/help control",dungeon);
        Assert.assertEquals("view.game.cmd.help.ctrl",um.getKey());
    }

    @Test(expected = CmdScannerSyntaxUnexpectedException.class)
    public void testHelpFailure1() throws CmdScannerException {
        UserMessage um = parser.parseCommand("/help al",dungeon);
    }

    @Test(expected = CmdScannerSyntaxMissingException.class)
    public void testMissingToken() throws CmdScannerException {
        UserMessage um = parser.parseCommand("/",dungeon);
    }
}
