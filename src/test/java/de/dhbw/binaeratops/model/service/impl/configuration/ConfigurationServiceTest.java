package de.dhbw.binaeratops.model.service.impl.configuration;


import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.service.impl.configurator.ConfiguratorService;
import org.junit.Assert;
import org.junit.Test;

public class ConfigurationServiceTest {

    @Test
    public void checkCreateDungeon(){
        User TestUser = new User();
        String myDungeonName = "MyDungeon";
        String notMyDungeonName = "NotMyDungeon";
        ConfiguratorService configuratorService = new ConfiguratorService(TestUser);
        Dungeon myTestDungeon = new Dungeon(myDungeonName);
        Dungeon NotMyTestDungeon = new Dungeon(notMyDungeonName);
        Dungeon DungeonToTest = configuratorService.createDungeon(myDungeonName);

        Assert.assertEquals(myTestDungeon.toString(), DungeonToTest.toString());
        Assert.assertNotEquals(NotMyTestDungeon.toString(), DungeonToTest.toString());
    }
}
