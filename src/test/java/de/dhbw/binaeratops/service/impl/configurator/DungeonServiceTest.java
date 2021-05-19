package de.dhbw.binaeratops.service.impl.configurator;

import de.dhbw.binaeratops.groups.ImplGroup;
import de.dhbw.binaeratops.groups.Logger;
import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.model.enums.Gender;
import de.dhbw.binaeratops.model.enums.Status;
import de.dhbw.binaeratops.model.repository.AvatarRepositoryI;
import de.dhbw.binaeratops.model.repository.DungeonRepositoryI;
import de.dhbw.binaeratops.model.repository.RoomRepositoryI;
import de.dhbw.binaeratops.model.repository.UserRepositoryI;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lars Rösel
 * Date: 19.05.2021
 * Time: 10:28
 */
@Category({ImplGroup.class})
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class DungeonServiceTest extends Logger {
    @Mock
    DungeonRepositoryI dungeonRepo;
    @Mock
    UserRepositoryI userRepo;
    @Mock
    RoomRepositoryI roomRepo;
    @Mock
    AvatarRepositoryI avatarRepo;

    DungeonService dungeonService;

    List<Dungeon> possibleDungeons;

    Dungeon d1;
    Dungeon d2;
    Dungeon d3;
    Dungeon d4;
    Dungeon d5;

    Room r1;
    Room r2;
    Room r3;
    Room r4;
    Room r5;

    Race ra1;
    Race ra2;
    Race ra3;
    Race ra4;
    Race ra5;

    Role ro1;
    Role ro2;
    Role ro3;
    Role ro4;
    Role ro5;

    User u1;
    User u2;
    User u3;
    User u4;
    User u5;

    Avatar a1;
    Avatar a2;
    Avatar a3;
    Avatar a4;
    Avatar a5;

    @Before
    public void before() {
        dungeonService = new DungeonService();
        dungeonService.setDungeonRepo(dungeonRepo);
        dungeonService.setUserRepo(userRepo);
        dungeonService.setRoomRepo(roomRepo);
        dungeonService.setAvatarRepo(avatarRepo);

        possibleDungeons = new ArrayList<>();

        ra1 = new Race("ra1", "Besch", 1L);
        ra2 = new Race("ra2", "Besch", 2L);
        ra3 = new Race("ra3", "Besch", 3L);
        ra4 = new Race("ra4", "Besch", 4L);
        ra5 = new Race("ra5", "Besch", 5L);

        ra1.setRaceId(5L);
        ra2.setRaceId(6L);
        ra3.setRaceId(7L);
        ra4.setRaceId(8L);
        ra5.setRaceId(9L);

        ro1 = new Role("ro1", "Besch", 1L);
        ro2 = new Role("ro2", "Besch", 2L);
        ro3 = new Role("ro3", "Besch", 3L);
        ro4 = new Role("ro4", "Besch", 4L);
        ro5 = new Role("ro5", "Besch", 5L);

        ro1.setRoleId(55L);
        ro2.setRoleId(66L);
        ro3.setRoleId(77L);
        ro4.setRoleId(88L);
        ro5.setRoleId(99L);

        r1 = new Room("r1", "TestRaum");
        r2 = new Room("r2", "TestRaum");
        r3 = new Room("r3", "TestRaum");
        r4 = new Room("r4", "TestRaum");
        r5 = new Room("r5", "TestRaum");

        r1.setRoomId(10L);
        r2.setRoomId(20L);
        r3.setRoomId(30L);
        r4.setRoomId(40L);
        r5.setRoomId(50L);

        u1 = new User("a", "a@a.a", "a", 1, true);
        u2 = new User("b", "a@a.a", "a", 1, true);
        u3 = new User("c", "a@a.a", "a", 1, true);
        u4 = new User("d", "a@a.a", "a", 1, true);
        u5 = new User("e", "a@a.a", "a", 1, true);

        u1.setUserId(11111L);
        u2.setUserId(22222L);
        u3.setUserId(33333L);
        u4.setUserId(44444L);
        u5.setUserId(55555L);

        d1 = new Dungeon("N1", u1.getUserId(), 1000L, 10000L, 100000L, '/', 1000000L);
        d2 = new Dungeon("N2", u2.getUserId(), 2000L, 20000L, 200000L, '/', 2000000L);
        d3 = new Dungeon("N3", u3.getUserId(), 3000L, 30000L, 300000L, '/', 3000000L);
        d4 = new Dungeon("N4", u4.getUserId(), 4000L, 40000L, 400000L, '/', 4000000L);
        d5 = new Dungeon("N5", u5.getUserId(), 5000L, 50000L, 500000L, '/', 5000000L);

        d1.setDungeonId(123L);
        d2.setDungeonId(456L);
        d3.setDungeonId(789L);
        d4.setDungeonId(123456L);
        d5.setDungeonId(456789L);

        a1 = new Avatar(r1, Gender.MALE, "a1", ra1, ro1, 10L);
        a2 = new Avatar(r2, Gender.FEMALE, "a2", ra2, ro2, 20L);
        a3 = new Avatar(r3, Gender.DIVERSE, "a3", ra3, ro3, 30L);
        a4 = new Avatar(r4, Gender.MALE, "a4", ra4, ro4, 40L);
        a5 = new Avatar(r5, Gender.FEMALE, "a5", ra5, ro5, 50L);

        a1.setAvatarId(147L);
        a2.setAvatarId(258L);
        a3.setAvatarId(369L);
        a4.setAvatarId(147258L);
        a5.setAvatarId(258369L);

        a1.setActive(true);
        a2.setActive(false);
        a3.setActive(true);
        a4.setActive(false);
        a5.setActive(false);

        a1.setDungeon(d1);
        a2.setDungeon(d2);
        a3.setDungeon(d3);
        a4.setDungeon(d4);
        a5.setDungeon(d1);

        d1.addAvatar(a1);
        d2.addAvatar(a2);
        d3.addAvatar(a3);
        d4.addAvatar(a4);
        d1.addAvatar(a5);

        possibleDungeons.add(d1);
        possibleDungeons.add(d2);
        possibleDungeons.add(d3);
        possibleDungeons.add(d4);
        possibleDungeons.add(d5);
    }

    @Test
    public void getAllDungeonsFromUserTest() {
        Mockito.when(dungeonRepo.findAll()).thenReturn(possibleDungeons);

        Assert.assertEquals(dungeonService.getAllDungeonsFromUser(u1).size(), 1);
    }

    @Test
    public void activateDungeonTest() {
        Mockito.when(dungeonRepo.findByDungeonId(d1.getDungeonId())).thenReturn(d1);
        Mockito.when(dungeonRepo.save(Mockito.any(Dungeon.class))).thenAnswer(i-> i.getArguments()[0]);
        dungeonService.activateDungeon(d1.getDungeonId());
        Assert.assertEquals(Status.ACTIVE, d1.getDungeonStatus());
    }

    @Test
    public void deactivateDungeonTest() {
        Mockito.when(dungeonRepo.findByDungeonId(d2.getDungeonId())).thenReturn(d2);
        Mockito.when(dungeonRepo.save(Mockito.any(Dungeon.class))).thenAnswer(i-> i.getArguments()[0]);
        dungeonService.deactivateDungeon(d2.getDungeonId());
        Assert.assertEquals(Status.INACTIVE, d2.getDungeonStatus());
    }

    @Test
    public void getCurrentAvatarsTest() {

        Mockito.when(dungeonRepo.findByDungeonId(d1.getDungeonId())).thenReturn(d1);
        int size = dungeonService.getCurrentAvatars(d1.getDungeonId()).size();
        Assert.assertEquals(1, size);
    }
}
