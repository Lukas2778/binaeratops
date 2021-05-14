package de.dhbw.binaeratops.service.impl.player.map;

import de.dhbw.binaeratops.groups.ImplGroup;
import de.dhbw.binaeratops.groups.Logger;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.Room;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import de.dhbw.binaeratops.service.impl.map.MapService;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@Category({ImplGroup.class})
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class MapServiceTest extends Logger {
    @Mock
    ConfiguratorServiceI configuratorServiceI;

    @Mock
    Dungeon testDungeon;

    MapService myMapService;

    List<Room> myTestList = new ArrayList<>();

    @Before
    public void setUp(){
        //new Dungeon("testDungeon");
        myMapService=new MapService();
        Room testRoom1=new Room("testRoom", "descr");
        testRoom1.setXCoordinate(0);
        testRoom1.setYCoordinate(0);
        testRoom1.setRoomId(1L);
        testRoom1.setEastRoomId(2L);
        testRoom1.setSouthRoomId(5L);

        Room testRoom2=new Room("testRoom", "descr");
        testRoom2.setXCoordinate(0);
        testRoom2.setYCoordinate(1);
        testRoom2.setRoomId(2L);
        testRoom2.setEastRoomId(3L);
        testRoom2.setSouthRoomId(6L);
        testRoom2.setWestRoomId(1L);

        Room testRoom3=new Room("testRoom", "descr");
        testRoom3.setXCoordinate(0);
        testRoom3.setYCoordinate(2);
        testRoom3.setRoomId(3L);
        testRoom3.setEastRoomId(4L);
        testRoom3.setSouthRoomId(7L);
        testRoom3.setWestRoomId(2L);

        Room testRoom4=new Room("testRoom", "descr");
        testRoom4.setXCoordinate(0);
        testRoom4.setYCoordinate(3);
        testRoom4.setRoomId(4L);
        testRoom4.setWestRoomId(3L);

        Room testRoom5=new Room("testRoom", "descr");
        testRoom5.setXCoordinate(1);
        testRoom5.setYCoordinate(0);
        testRoom5.setRoomId(5L);
        testRoom5.setEastRoomId(6L);
        testRoom5.setNorthRoomId(1L);

        Room testRoom6=new Room("testRoom", "descr");
        testRoom6.setXCoordinate(1);
        testRoom6.setYCoordinate(1);
        testRoom6.setRoomId(6L);
        testRoom6.setWestRoomId(5L);
        testRoom6.setNorthRoomId(2L);

        Room testRoom7=new Room("testRoom", "descr");
        testRoom7.setXCoordinate(1);
        testRoom7.setYCoordinate(2);
        testRoom7.setRoomId(7L);
        testRoom7.setNorthRoomId(3L);

        myTestList.add(testRoom1);
        myTestList.add(testRoom2);
        myTestList.add(testRoom3);
        myTestList.add(testRoom4);
        myTestList.add(testRoom5);
        myTestList.add(testRoom6);
        myTestList.add(testRoom7);

        Mockito.when(configuratorServiceI.getDungeon()).thenReturn(testDungeon);
        Mockito.when(testDungeon.getRooms()).thenReturn(myTestList);
        myMapService.initConfigure(configuratorServiceI);
    }

    @Test
    public void roomExistsTest(){
        Assert.assertTrue(myMapService.roomExists(0,0));
        Assert.assertFalse(myMapService.roomExists(2,1));
    }

    @Test
    public void canPlaceRoomTest(){
        Assert.assertTrue(myMapService.canPlaceRoom(2,1));
        Assert.assertFalse(myMapService.canPlaceRoom(3,2));
    }
    @Test
    public void canDeleteRoomTest(){
        Assert.assertTrue(myMapService.canDeleteRoom(0,0));
        Assert.assertTrue(myMapService.canDeleteRoom(1,1));
        Assert.assertFalse(myMapService.canDeleteRoom(0,2));
    }
    @Test
    public void canToggleWall1(){
        Assert.assertTrue(myMapService.canToggleWall(0,0,true));
    }
    @Test
    public void canToggleWall2(){
        Assert.assertTrue(myMapService.canToggleWall(0,0,false));
    }
    @Test
    public void canToggleWall3(){
        Assert.assertTrue(myMapService.canToggleWall(1,1,false));
    }
    @Test
    public void canToggleWall4(){
        Assert.assertFalse(myMapService.canToggleWall(0,2,false));
    }
    @Test
    public void canToggleWall5(){
        Assert.assertFalse(myMapService.canToggleWall(0,1,false));
    }

//    @Test
//    public void placeRoomTest(){
//        Assert.assertEquals(myMapService.placeRoom(2,1), );
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
