package de.dhbw.binaeratops.service.impl.configurator;

import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.enums.Status;
import de.dhbw.binaeratops.model.enums.Visibility;
import de.dhbw.binaeratops.model.repository.DungeonRepositoryI;
import de.dhbw.binaeratops.service.api.configuration.DungeonServiceI;
import org.apache.http.auth.AUTH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DungeonService implements DungeonServiceI {
    @Autowired
    DungeonRepositoryI dungeonRepo;

    @Override
    public List<Dungeon> getAllDungeonsFromUser(User AUser){
        List<Dungeon> userDungeons = new ArrayList<>();

        for(Dungeon myDungeon: dungeonRepo.findAll()){
            if (myDungeon.getDungeonMasterId().equals(AUser.getUserId())){
                userDungeons.add(myDungeon);
            }
        }
        return userDungeons;
    }

    @Override
    public List<Dungeon> getDungeonsLobby(User AUser) {
        List<Dungeon> userDungeons = new ArrayList<>();

        for(Dungeon myDungeon: dungeonRepo.findAll()){
            if(
                    myDungeon.getDungeonVisibility()!=null
                    && myDungeon.getDungeonStatus()!=null
                    && myDungeon.getDungeonStatus().equals(Status.ACTIVE)
                    && !myDungeon.getDungeonMasterId().equals(AUser.getUserId())
                    && (myDungeon.getDungeonVisibility().equals(Visibility.PUBLIC)
                        || (myDungeon.getDungeonVisibility().equals(Visibility.IN_CONFIGURATION)
                            && myDungeon.getAllowedUsers().contains(AUser)
                            && !myDungeon.getBlockedUsers().contains(AUser)
                            )
                    )
            ){
                userDungeons.add(myDungeon);
            }
        }
        return userDungeons;
    }
}
