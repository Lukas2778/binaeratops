package de.dhbw.binaeratops.service.impl.configurator;

import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.model.enums.ActionType;
import de.dhbw.binaeratops.model.enums.Status;
import de.dhbw.binaeratops.model.enums.Visibility;
import de.dhbw.binaeratops.model.repository.*;
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
 * Für Schnittstelle dieser Komponente siehe {@link DungeonServiceI}.
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

    @Autowired
    PermissionRepositoryI permissionRepo;

    @Autowired
    UserActionRepositoryI userActionRepo;

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
    public void setAvatarInactive(long AAvatarId) {
        Avatar avatar = avatarRepo.findByAvatarId(AAvatarId);
        avatar.setActive(false);
        avatarRepo.save(avatar);
    }

    @Override
    public Room getRoomOfAvatar(Avatar AAvatar) {
        return roomRepo.findByRoomId(AAvatar.getCurrentRoom().getRoomId());
    }

    @Override
    public Room getRoomByPosition(Dungeon ADungeon, int AXCoordinate, int AYCoordinate) {
        List<Room> room = roomRepo.findByDungeonAndXcoordinateAndYcoordinate(ADungeon, AXCoordinate, AYCoordinate);
        if (room.size() == 0)
            return null;
        else
            return room.get(0);
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
    public void changeDungeonMaster(Long ADungeonId, Long ANewUserId, Long AOldUserId) {
        Dungeon dungeon = dungeonRepo.findByDungeonId(ADungeonId);
        User newUser = userRepo.findByUserId(ANewUserId);
        User oldUser = userRepo.findByUserId(AOldUserId);

        oldUser.removeDungeon(dungeon);
        newUser.addDungeon(dungeon);
        dungeon.setDungeonMasterId(ANewUserId);

        dungeonRepo.save(dungeon);
    }

    @Override
    public List<User> getCurrentUsers(Dungeon ADungeon) {
        return ADungeon.getCurrentUsers();
    }

    @Override
    public Dungeon getDungeon(Long ADungeonId) {
        return dungeonRepo.findByDungeonId(ADungeonId);
    }

    @Override
    public void kickPlayer(Long ADungeonId, Long AUserId) {
        Dungeon dungeon = dungeonRepo.findByDungeonId(ADungeonId);
        User user = userRepo.findByUserId(AUserId);
        List<Avatar> avatars = avatarRepo.findByUserAndActive(user, true);
        if (avatars.size() > 0) {
            Avatar avatar = avatars.get(0);
            avatar.setActive(false);
            avatarRepo.save(avatar);
            user.removeCurrentDungeon();
            userRepo.save(user);
            dungeon.removeCurrentUser(user);
            dungeonRepo.save(dungeon);
        }
    }

    public void declinePlayer(Long ADungeonId, Long AUserId, Permission APermission) {
        Dungeon dungeon = dungeonRepo.findByDungeonId(ADungeonId);
        dungeon.addBlockedUser(APermission);
        dungeon.removeRequestUser(APermission);
        permissionRepo.save(APermission);
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

    public User getUser(Long AUserId) {
        return userRepo.findByUserId(AUserId);
    }

    @Override
    public void allowUser(Long ADungeonId, Long AUserId, Permission APermission) {
        Dungeon dungeon = dungeonRepo.findByDungeonId(ADungeonId);
        User user = userRepo.findByUserId(AUserId);
        dungeon.addAllowedUser(APermission);
        dungeon.removeRequestUser(APermission);
//        user.setAllowedDungeon(dungeon);
//        dungeon.getAllowedUsers().add(user);
        permissionRepo.save(APermission);
        userRepo.save(user);
        dungeonRepo.save(dungeon);
    }

    public Permission getPermissionRequest(User AUser, Dungeon ADungeon) {
        List<Permission> p = permissionRepo.findByRequestedDungeonAndUser(ADungeon, AUser);
        if (p.size() > 0) {
            return p.get(0);
        } else {
            return null;
        }
    }

    public Permission getPermissionGranted(User AUser, Dungeon ADungeon) {
        List<Permission> p = permissionRepo.findByAllowedDungeonAndUser(ADungeon, AUser);
        if (p.size() > 0) {
            return p.get(0);
        } else {
            return null;
        }
    }

    public Permission getPermissionBlocked(User AUser, Dungeon ADungeon) {
        List<Permission> p = permissionRepo.findByBlockedDungeonAndUser(ADungeon, AUser);
        if (p.size() > 0) {
            return p.get(0);
        } else {
            return null;
        }
    }

    public void savePermission(Permission APermission) {
        permissionRepo.save(APermission);
    }

    //Diese Setter werden für Tests benötigt
    public void setDungeonRepo(DungeonRepositoryI dungeonRepo) {
        this.dungeonRepo = dungeonRepo;
    }

    public void setUserRepo(UserRepositoryI userRepo) {
        this.userRepo = userRepo;
    }

    public void setRoomRepo(RoomRepositoryI roomRepo) {
        this.roomRepo = roomRepo;
    }

    public void setAvatarRepo(AvatarRepositoryI avatarRepo) {
        this.avatarRepo = avatarRepo;
    }

    public void saveUserAction(UserAction AUserAction) {
        userActionRepo.save(AUserAction);
    }

    public List<Permission> getRequestedPermissions(Dungeon ADungeon) {
        return permissionRepo.findByRequestedDungeon(ADungeon);
    }

    public void deleteUserAction(UserAction AUserAction) {
        userActionRepo.delete(AUserAction);
    }

    public List<UserAction> getUserActions(Dungeon ADungeon) {
        List<UserAction> userActions = userActionRepo.findByDungeon(ADungeon);
        List<UserAction> filtered = new ArrayList<>();
        for (UserAction temp : userActions) {
            if (!temp.getActionType().equals(ActionType.ENTRY_REQUEST)) {
                filtered.add(temp);
            }
        }
        return filtered;
    }
}
