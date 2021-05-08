package de.dhbw.binaeratops.service.impl.configurator;

import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.enums.Status;
import de.dhbw.binaeratops.model.repository.DungeonRepositoryI;
import de.dhbw.binaeratops.service.api.configuration.DungeonServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DungeonService implements DungeonServiceI {

    @Autowired
    DungeonRepositoryI dungeonRepo;

    public List<Dungeon> getAllDungeonsFromUser(User AUser) {

        List<Dungeon> userDungeons = new ArrayList<>();

        for (Dungeon myDungeon : dungeonRepo.findAll()) {
            if (myDungeon.getDungeonMasterId().equals(AUser.getUserId())) {
                userDungeons.add(myDungeon);
            }
        }
        return userDungeons;
    }

    @Override
    public void activateDungeon(long ADungeonId) {
        Dungeon dungeon = dungeonRepo.findByDungeonId(ADungeonId);
        dungeon.setDungeonStatus(Status.ACTIVE);
        dungeonRepo.save(dungeon);
    }

    @Override
    public void deactivateDungeon(long ADungeonId) {
        Dungeon dungeon = dungeonRepo.findByDungeonId(ADungeonId);
        dungeon.setDungeonStatus(Status.INACTIVE);
        dungeonRepo.save(dungeon);
    }
}
