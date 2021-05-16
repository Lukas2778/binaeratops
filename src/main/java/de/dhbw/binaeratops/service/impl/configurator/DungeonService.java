package de.dhbw.binaeratops.service.impl.configurator;

import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.model.enums.Status;
import de.dhbw.binaeratops.model.enums.Visibility;
import de.dhbw.binaeratops.model.repository.AvatarRepositoryI;
import de.dhbw.binaeratops.model.repository.DungeonRepositoryI;
import de.dhbw.binaeratops.model.repository.RoomRepositoryI;
import de.dhbw.binaeratops.model.repository.UserRepositoryI;
import de.dhbw.binaeratops.service.api.configuration.DungeonServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Komponente "DungeonService".
 * <p>
 * Dieser Service stellt alle Funktionalitäten zum Umgang mit einem Dungen bereit.
 * </p>
 * <p>
 * Für Schnittstelle dieser Komponente siehe @{@link DungeonServiceI}.
 * </p>
 *
 * @author Timon Gartung, Pedro Treuer, Nicolas Haug, Lukas Göpel, Matthias Rall, Lars Rösel
 */
@Service
public class DungeonService implements DungeonServiceI {
    @Autowired
    DungeonRepositoryI dungeonRepo;

    @Autowired
    UserRepositoryI userRepo;

    @Autowired
    RoomRepositoryI roomRepo;

    @Autowired
    AvatarRepositoryI avatarRepo;

    @Override
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
    public List<Dungeon> getDungeonsLobby(User AUser) {
        List<Dungeon> userDungeons = new ArrayList<>();

        for (Dungeon myDungeon : dungeonRepo.findAll()) {
            if (
                    myDungeon.getDungeonVisibility() != null
                            && myDungeon.getDungeonStatus() != null
                            && myDungeon.getDungeonStatus().equals(Status.ACTIVE)
                            && !myDungeon.getDungeonMasterId().equals(AUser.getUserId())
                            && (
                                    myDungeon.getDungeonVisibility().equals(Visibility.PUBLIC)
                            || (
                                    myDungeon.getDungeonVisibility().equals(Visibility.PRIVATE)
                                    && myDungeon.getAllowedUsers().contains(AUser)
                                    && !myDungeon.getBlockedUsers().contains(AUser)
                                )
                    )
            ) {
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

    @Override
    public List<Avatar> getCurrentAvatars(long ADungeonId) {
        Dungeon dungeon = dungeonRepo.findByDungeonId(ADungeonId);

        List<Avatar> avatars = new ArrayList<>();
        dungeon.getAvatars().forEach(avatar -> {
            if (avatar.isActive())
                avatars.add(avatar);
        });
        return avatars;
    }

    @Override
    public Room getRoomOfAvatar(Avatar AAvatar) {
        return roomRepo.findByRoomId(AAvatar.getCurrentRoom().getRoomId());
    }

    @Override
    public Room getRoomByPosition(Dungeon ADungeon, int AX, int AY) {
        List<Room> room=roomRepo.findByDungeonAndXcoordinateAndYcoordinate(ADungeon, AX, AY);
        if(room.size() == 0)
            return null;
        else
            return  room.get(0);
    }

    @Override
    public void saveDungeon(Dungeon ADungeon) {
        dungeonRepo.save(ADungeon);
    }

    @Override
    public void saveUser(User AUser) {
        userRepo.save(AUser);
    }

    @Override
    public void setDungeonMaster(Dungeon ADungeon, Long AUserId) {
        ADungeon.setDungeonMasterId(AUserId);
        dungeonRepo.save(ADungeon);
    }

    @Override
    public List<User> getCurrentUsers(Dungeon ADungeon) {
        return ADungeon.getCurrentUsers();
    }

    @Override
    public Dungeon getDungeon(Long ADungeonId){
        return dungeonRepo.findByDungeonId(ADungeonId);
    }

    @Override
    public void kickPlayer(Long ADungeonId, Long AUserId){
        Dungeon dungeon = dungeonRepo.findByDungeonId(ADungeonId);
        User user = userRepo.findByUserId(AUserId);
        Avatar avatar = avatarRepo.findByUserAndActive(user, true).get(0);
        avatar.setActive(false);
        avatarRepo.save(avatar);
        dungeon.addBlockedUser(user);
        dungeonRepo.save(dungeon);
    }

    @Override
    public Room getRoomById(Long ARoomId) {
        return roomRepo.findByRoomId(ARoomId);
    }

    public void setAvatarNotRequested(Long AAvatarId) {
        Avatar avatar = avatarRepo.findByAvatarId(AAvatarId);
        avatar.setRequested(false);
        avatarRepo.save(avatar);
    }
}
