package de.dhbw.binaeratops.model.service.impl.player.map;

import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.Room;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.repository.DungeonRepositoryI;
import de.dhbw.binaeratops.model.repository.RoomRepositoryI;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import de.dhbw.binaeratops.service.api.map.MapServiceI;
import de.dhbw.binaeratops.service.impl.map.MapService;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class MapServiceTest {
    @Mock
    ConfiguratorServiceI configuratorServiceI;

    @Mock
    Dungeon testDungeon;

    MapService myMapService;

    List<Room> myTestList = new ArrayList();

    @Before
    public void setUp(){
        //new Dungeon("testDungeon");
        myMapService=new MapService();
        Room testRoom=new Room("testRoom", "descr");
        testRoom.setXCoordinate(0);
        testRoom.setYCoordinate(0);
        myTestList.add(testRoom);

        Mockito.when(configuratorServiceI.getDungeon()).thenReturn(testDungeon);
        Mockito.when(testDungeon.getRooms()).thenReturn(myTestList);
        myMapService.init(8,configuratorServiceI);
    }

    @Test
    public void roomExistsTest(){
        Assert.assertTrue(myMapService.roomExists(0,0));
        Assert.assertFalse(myMapService.roomExists(0,1));
    }

    @Test
    public void canPlaceRoomTest(){
        Assert.assertTrue(myMapService.canPlaceRoom(0,1));
        Assert.assertFalse(myMapService.canPlaceRoom(0,0));
        Assert.assertFalse(myMapService.canPlaceRoom(0,2));
    }
//
//    @Test
//    public void placeRoomTest(){
//        //@TODO
//    }
//
//    @Test
//    public void canDeleteRoomTest(){
//        Assert.assertTrue(myMapService.canDeleteRoom(3,4));
//    }
//
//    @Test
//    public void canPlaceWallTest(){
//        Assert.assertTrue(myMapService.canToggleWall(3,4,true));
//    }
//
//    @Test
//    public void placeWallTest(){
//        //@TODO
//    }
//
//    @Test
//    public void canDeleteWallTest(){
//        Assert.assertTrue(true);
//    }
}
