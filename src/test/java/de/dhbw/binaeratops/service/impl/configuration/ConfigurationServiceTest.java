package de.dhbw.binaeratops.service.impl.configuration;


import de.dhbw.binaeratops.groups.Logger;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;
import org.junit.Test;

public class ConfigurationServiceTest extends Logger {

    @Test
    public void checkCreateDungeon(){
        User TestUser = new User();
        String myDungeonName = "MyDungeon";
        String notMyDungeonName = "NotMyDungeon";

        Dungeon myTestDungeon = new Dungeon(myDungeonName);
        Dungeon NotMyTestDungeon = new Dungeon(notMyDungeonName);
        //Dungeon DungeonToTest = configuratorService.createDungeon(myDungeonName);

        //Assert.assertEquals(myTestDungeon.toString(), DungeonToTest.toString());
        //Assert.assertNotEquals(NotMyTestDungeon.toString(), DungeonToTest.toString());

    }
}