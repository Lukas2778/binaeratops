package de.dhbw.binaeratops.model.service.impl.player.map;

import de.dhbw.binaeratops.service.impl.player.map.MapService;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class MapServiceTest {
    @Autowired
    MapService myMapService;

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
        Assert.assertTrue(myMapService.canToggleWall(3,4,true));
    }

    @Test
    public void placeWallTest(){
        //@TODO
    }

    @Test
    public void canDeleteWallTest(){
        Assert.assertTrue(true);
    }
}
