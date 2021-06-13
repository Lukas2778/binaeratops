package de.dhbw.binaeratops.service.impl.game;

import de.dhbw.binaeratops.groups.ImplGroup;
import de.dhbw.binaeratops.groups.Logger;
import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.model.enums.Gender;
import de.dhbw.binaeratops.model.repository.*;
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
public class GameServiceTest extends Logger {
    @Mock
    DungeonRepositoryI dungeonRepo;
    @Mock
    UserRepositoryI userRepo;
    @Mock
    RoomRepositoryI roomRepo;
    @Mock
    AvatarRepositoryI avatarRepo;
    @Mock
    ItemInstanceRepositoryI itemInstanceRepo;
    @Mock
    PermissionRepositoryI permissionRepo;

    GameService gameService;

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

    Item i1;
    Item i2;
    Item i3;
    Item i4;
    Item i5;

    ItemInstance ii1;
    ItemInstance ii2;
    ItemInstance ii3;
    ItemInstance ii4;
    ItemInstance ii5;

    @Before
    public void before() {
        gameService = new GameService();
        gameService.setDungeonRepositoryI(dungeonRepo);
        gameService.setUserRepositoryI(userRepo);
        gameService.setRoomRepositoryI(roomRepo);
        gameService.setAvatarRepositoryI(avatarRepo);
        gameService.setItemInstanceRepositoryI(itemInstanceRepo);
        gameService.setPermissionRepositoryI(permissionRepo);

        possibleDungeons = new ArrayList<>();

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

        a1.setActive(false);
        a2.setActive(false);
        a3.setActive(true);
        a4.setActive(true);
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

        ii1 = new ItemInstance();
        ii2 = new ItemInstance();
        ii3 = new ItemInstance();
        ii4 = new ItemInstance();
        ii5 = new ItemInstance();

        ii1.setItem(i1);
        ii2.setItem(i2);
        ii3.setItem(i3);
        ii4.setItem(i4);
        ii5.setItem(i5);

        ii1.setInventoryAvatar(a1);
        ii2.setInventoryAvatar(a2);
        ii3.setInventoryAvatar(a3);
        ii4.setInventoryAvatar(a4);
        ii5.setInventoryAvatar(a5);

        possibleDungeons.add(d1);
        possibleDungeons.add(d2);
        possibleDungeons.add(d3);
        possibleDungeons.add(d4);
        possibleDungeons.add(d5);
    }

    @Test
    public void addActivePlayer() {
        Mockito.when(dungeonRepo.findByDungeonId(d1.getDungeonId())).thenReturn(d1);
        Mockito.when(userRepo.findByUserId(u1.getUserId())).thenReturn(u1);
        Mockito.when(avatarRepo.findByAvatarId(a1.getAvatarId())).thenReturn(a1);
        Mockito.when(avatarRepo.save(Mockito.any(Avatar.class))).thenAnswer(i -> i.getArguments()[0]);
        ArrayList<Permission> permissions = new ArrayList<>();
        Permission p1 = new Permission(u1);
        permissions.add(p1);

        Mockito.when(permissionRepo.findByAllowedDungeonAndUser(d1, u1)).thenReturn(permissions);
        Mockito.when(permissionRepo.save(Mockito.any(Permission.class))).thenAnswer(i -> i.getArguments()[0]);
        Mockito.when(dungeonRepo.save(Mockito.any(Dungeon.class))).thenAnswer(i -> i.getArguments()[0]);
        Mockito.when(userRepo.save(Mockito.any(User.class))).thenAnswer(i -> i.getArguments()[0]);
        gameService.addActivePlayer(d1.getDungeonId(), u1.getUserId(), a1.getAvatarId());
        Assert.assertTrue(a1.isActive());
    }
}
