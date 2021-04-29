package de.dhbw.binaeratops.model.service.impl.player.map;

import de.dhbw.binaeratops.service.impl.player.map.MapService;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class MapServiceTest {
    MapService myMapService=new MapService(10);

    @Test
    public void canPlaceRoomTest(){
        Assert.assertTrue(myMapService.canPlaceRoom(3,4));
    }

    @Test
    public void placeRoomTest(){
        //@TODO
    }

    @Test
    public void canDeleteRoomTest(){
        Assert.assertTrue(myMapService.canDeleteRoom(3,4));
    }

    @Test
    public void canPlaceWallTest(){
        Assert.assertTrue(myMapService.canPlaceWall(3,4));
    }

    @Test
    public void placeWallTest(){
        //@TODO
    }

    @Test
    public void canDeleteWallTest(){
        Assert.assertTrue(myMapService.canDeleteWall(3,4));
    }
}
