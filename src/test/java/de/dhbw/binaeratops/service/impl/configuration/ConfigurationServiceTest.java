package de.dhbw.binaeratops.service.impl.configuration;


import de.dhbw.binaeratops.groups.Logger;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.enums.Visibility;
import de.dhbw.binaeratops.model.repository.DungeonRepositoryI;
import de.dhbw.binaeratops.service.impl.configurator.ConfiguratorService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class ConfigurationServiceTest extends Logger {



    ConfiguratorService configuratorService;
    @Mock
    DungeonRepositoryI dungeonRepositoryI;


    Dungeon testDungeon;

    @Before
    public void setUp(){
        testDungeon = new Dungeon();
        configuratorService = new ConfiguratorService();
        Mockito.when(dungeonRepositoryI.save(Mockito.any(Dungeon.class))).thenAnswer(i-> i.getArguments()[0]);
        configuratorService.dungeonRepo = dungeonRepositoryI;
    }

    @Test
    public void checkCreateDungeon(){
        User testUser = new User();
        String myDungeonName = "MyDungeon";
        String notMyDungeonName = "NotMyDungeon";
        long numberOfPlayers = 5;
        Visibility dungeonVisibility = Visibility.PUBLIC;

        Dungeon myTestDungeon = new Dungeon(myDungeonName, testUser.getUserId(), numberOfPlayers, dungeonVisibility);
        Dungeon NotMyTestDungeon = new Dungeon(notMyDungeonName, testUser.getUserId(), numberOfPlayers, dungeonVisibility);

        Dungeon DungeonToTest = configuratorService.createDungeon(myDungeonName, testUser, numberOfPlayers, dungeonVisibility);

        Assert.assertEquals(myTestDungeon.toString(), DungeonToTest.toString());
        Assert.assertNotEquals(NotMyTestDungeon.toString(), DungeonToTest.toString());

    }
}
