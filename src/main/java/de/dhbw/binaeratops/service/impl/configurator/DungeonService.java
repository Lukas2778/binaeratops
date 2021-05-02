package de.dhbw.binaeratops.service.impl.configurator;

import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;
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

    public List<Dungeon> getAllDungeonsFromUser(User AUser){

        List<Dungeon> userDungeons = new ArrayList<Dungeon>();

        for(Dungeon myDungeon: dungeonRepo.findAll()){
            if (myDungeon.getDungeonMasterId() == AUser.getUserId()){
                userDungeons.add(myDungeon);
            }
        }
        return userDungeons;
    }
}
