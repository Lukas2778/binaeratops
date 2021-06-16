package de.dhbw.binaeratops.service.impl.game;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.model.enums.Gender;
import de.dhbw.binaeratops.model.enums.Status;
import de.dhbw.binaeratops.model.repository.*;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import de.dhbw.binaeratops.service.api.configuration.DungeonServiceI;
import de.dhbw.binaeratops.service.api.game.GameServiceI;
import de.dhbw.binaeratops.service.impl.configurator.ConfiguratorService;
import de.dhbw.binaeratops.view.dungeonmaster.DungeonMasterView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Komponente "GameService".
 * <p>
 * Dieser Service stellt alle Funktionalitäten für das Spiel bereit.
 * </p>
 * <p>
 * Für Schnittstelle dieser Komponente siehe {@link de.dhbw.binaeratops.service.api.game.GameServiceI}.
 * </p>
 *
 * @author Timon Gartung, Lukas Göpel, Matthias Rall, Lars Rösel
 */
@Service
public class GameService implements GameServiceI {
    HashMap<Long, UI> dungeonUIHashMap = new HashMap<>();
    HashMap<Long, DungeonMasterView> dungeonDungeonMasterViewHashMap = new HashMap<>();

    @Autowired
    DungeonServiceI dungeonServiceI;

    @Autowired
    ItemInstanceRepositoryI itemInstanceRepositoryI;

    @Autowired
    DungeonRepositoryI dungeonRepositoryI;

    @Autowired
    UserRepositoryI userRepositoryI;

    @Autowired
    RoomRepositoryI roomRepositoryI;

    @Autowired
    AvatarRepositoryI avatarRepositoryI;

    @Autowired
    PermissionRepositoryI permissionRepositoryI;

    @Autowired
    RoleRepositoryI roleRepositoryI;

    @Autowired
    RaceRepositoryI raceRepositoryI;

    @Autowired
    AttendanceRepositoryI attendanceRepositoryI;

    /**
     * Standardkonstruktor zum erzeugen des GameService.
     */
    public GameService() {
    }

    @Override
    public void initialize(Dungeon ADungeon, UI AUI, DungeonMasterView AView) {
        dungeonUIHashMap.put(ADungeon.getDungeonId(), AUI);
        dungeonDungeonMasterViewHashMap.put(ADungeon.getDungeonId(), AView);
    }

    @Override
    public void updateView(Long ADungeonId) {
        Dungeon dungeon = dungeonRepositoryI.findByDungeonId(ADungeonId);
        dungeonUIHashMap.get(dungeon.getDungeonId()).access(() -> dungeonDungeonMasterViewHashMap.get(dungeon.getDungeonId()).add(new Text("HEUREKA")));
    }

    @Override
    public void createNewAvatar(Long ADungeonId, Long AUserId, Long ACurrentRoomId, String AAvatarName,
                                Gender AAvatarGender, Long AAvatarRoleId, Long AAvatarRaceId, Long ALifepoints) {
        Dungeon dungeon = dungeonRepositoryI.findByDungeonId(ADungeonId);
        User user = userRepositoryI.findByUserId(AUserId);
        Room room = roomRepositoryI.findByRoomId(ACurrentRoomId);
        Role role = roleRepositoryI.findByRoleId(AAvatarRoleId);
        Race race = raceRepositoryI.findByRaceId(AAvatarRaceId);
        Avatar createAvatar = new Avatar(room, AAvatarGender, AAvatarName, race, role, ALifepoints);
        createAvatar.setDungeon(dungeon);
        createAvatar.setUser(user);
        //createAvatar.setCurrentRoom(ACurrentRoom);
        avatarRepositoryI.save(createAvatar);
        dungeon.addAvatar(createAvatar);
        user.addAvatar(createAvatar);
    }

    @Override
    public void deleteAvatar(Long ADungeonId, Long AUserId, Long AAvatarId) {
        Dungeon dungeon = dungeonRepositoryI.findByDungeonId(ADungeonId);
        User user = userRepositoryI.findByUserId(AUserId);
        Avatar avatar = avatarRepositoryI.findByAvatarId(AAvatarId);
        if (avatar != null) {
            for (int i = 0; i <= avatar.getInventory().size(); i++) {
                try {
                    for (ItemInstance inventItem : avatar.getInventory()) {
                        avatar.removeInventoryItem(inventItem);
                        itemInstanceRepositoryI.delete(inventItem);
                    }
                } catch (Exception e) {
                }
            }
            for (int i = 0; i <= avatar.getEquipment().size(); i++) {
                try {
                    for (ItemInstance equipItem : avatar.getEquipment()) {
                        avatar.removeEquipmentItem(equipItem);
                        itemInstanceRepositoryI.delete(equipItem);
                    }
                } catch (Exception e) {
                }
            }
            try {
                dungeon.removeAvatar(avatar);
                user.removeAvatar(avatar);
                userRepositoryI.save(user);
                dungeonRepositoryI.save(dungeon);
                avatarRepositoryI.delete(avatar);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public List<Room> saveAvatarProgress(Long ADungeonId, Long AAvatarId, Long ACurrentRoomId) {
        Dungeon dungeon = dungeonRepositoryI.findByDungeonId(ADungeonId);
        Avatar avatar = avatarRepositoryI.findByAvatarId(AAvatarId);
        Room room = roomRepositoryI.findByRoomId(ACurrentRoomId);

        Attendance newAttendance = new Attendance(dungeon, room);
        boolean duplicate=false;

        //falls der Raum nicht schon hinzugefügt wurde
        for (Attendance visitedR : attendanceRepositoryI.findByAvatarAndDungeon(avatar, dungeon)) {
            if (newAttendance.getRoom().getRoomId().equals(visitedR.getRoom().getRoomId())) {
                duplicate=true;
                break;
            }
        }
        if(!duplicate){
            avatar.addVisitedRoom(newAttendance);
            attendanceRepositoryI.save(newAttendance);
        }

        avatar.setCurrentRoom(room);
        avatarRepositoryI.save(avatar);

        return attendanceToRooms(ADungeonId, AAvatarId);
    }

    @Override
    public List<Room> attendanceToRooms(Long ADungeonId, Long AAvatarId){
        Dungeon dungeon = dungeonRepositoryI.findByDungeonId(ADungeonId);
        Avatar avatar = avatarRepositoryI.findByAvatarId(AAvatarId);

        List<Attendance> myAttendances = attendanceRepositoryI.findByAvatarAndDungeon(avatar, dungeon);

        List<Room> returnRooms = new ArrayList<>();
        for(Attendance roomAtt : myAttendances){
            returnRooms.add(roomAtt.getRoom());
        }
        return returnRooms;
    }

    @Override
    public void addActivePlayer(Long ADungeonId, Long AUserId, Long AAvatarId) {
        Dungeon dungeon = dungeonRepositoryI.findByDungeonId(ADungeonId);
        User user = userRepositoryI.findByUserId(AUserId);
        Avatar avatar = avatarRepositoryI.findByAvatarId(AAvatarId);
        if (!dungeon.getCurrentUsers().contains(user)) {
            dungeon.addCurrentUser(user);
            avatar.setActive(true);
            avatarRepositoryI.save(avatar);
            user.setCurrentDungeon(dungeon);
            List<Permission> permissions = permissionRepositoryI.findByAllowedDungeonAndUser(dungeon, user);
            dungeon.removeRequestUser(permissions.get(0));
            dungeon.addAllowedUser(permissions.get(0));
            permissionRepositoryI.save(permissions.get(0));
            dungeonRepositoryI.save(dungeon);
            userRepositoryI.save(user);
        }
    }

    @Override
    public Avatar getAvatarById(Long AAvatarId) {
        return avatarRepositoryI.findByAvatarId(AAvatarId);
    }

    @Override
    public void removeActivePlayer(Long ADungeonId, Long AUserId, Long AAvatarId, boolean ALobbyRequest) {
        Dungeon dungeon = dungeonRepositoryI.findByDungeonId(ADungeonId);
        User user = userRepositoryI.findByUserId(AUserId);
        Avatar avatar = avatarRepositoryI.findByAvatarId(AAvatarId);
        if (dungeon.getCurrentUsers().contains(user)) {
            dungeon.removeCurrentUser(user);
            avatar.setActive(false);
            avatarRepositoryI.save(avatar);
            user.removeCurrentDungeon();
            dungeonRepositoryI.save(dungeon);
            userRepositoryI.save(user);
        }
        else if (ALobbyRequest){
            avatar.setActive(false);
            avatarRepositoryI.save(avatar);
            userRepositoryI.save(user);
        }
    }

    @Override
    public Long getStandardAvatarLifepoints(Long ADungeonId) {
        Dungeon dungeon = dungeonRepositoryI.findByDungeonId(ADungeonId);
        return dungeon.getStandardAvatarLifepoints();
    }

    @Override
    public boolean avatarNameIsValid(Long ADungeonId, String AAvatarName) {
        if (AAvatarName.isEmpty() || AAvatarName.contains(" "))
            return false;
        Dungeon dungeon = dungeonRepositoryI.findByDungeonId(ADungeonId);
        for (Avatar myAvatar : dungeon.getAvatars()) {
            if (myAvatar.getName().equals(AAvatarName))
                return false;
        }
        return true;
    }

    @Override
    public boolean avatarGenderIsValid(Gender AAvatarGender) {
        return AAvatarGender != null;
    }

    @Override
    public boolean avatarRoleIsValid(Role AAvatarRole) {
        return AAvatarRole != null;
    }

    @Override
    public boolean avatarRaceIsValid(Race AAvatarRace) {
        return AAvatarRace != null;
    }

    @Override
    public Status getDungeonStatus(Long ADungeonId) {
        return dungeonRepositoryI.findByDungeonId(ADungeonId).getDungeonStatus();
    }

    @Override
    public void setLifePoints(Long AAvatarId, Long AValue) {
        Avatar avatar = avatarRepositoryI.findByAvatarId(AAvatarId);
        avatar.setLifepoints(AValue);
        avatarRepositoryI.save(avatar);
    }

    @Override
    public void setPlayersInactive(Long ADungeonId) {
        Dungeon dungeon = dungeonRepositoryI.findByDungeonId(ADungeonId);

        List<Avatar> avatars = dungeonServiceI.getCurrentAvatars(ADungeonId);
        for (Avatar avatar : avatars) {
            dungeonServiceI.setAvatarInactive(avatar.getAvatarId());
        }

        while (dungeon.getCurrentUsers().size() > 0) {
            User user = dungeon.getCurrentUsers().get(0);
            user.removeCurrentDungeon();
            userRepositoryI.save(user);
            dungeon.removeCurrentUser(user);
        }
        dungeonRepositoryI.save(dungeon);
    }

    @Override
    public User getUser(Long AUserId) {
        return userRepositoryI.findByUserId(AUserId);
    }

    public void removeItemFromInventory(Long AAvatarId, Long AItemId) {
        Avatar avatar = avatarRepositoryI.findByAvatarId(AAvatarId);
        ItemInstance item = itemInstanceRepositoryI.findByItemInstanceId(AItemId);
        avatar.removeInventoryItem(item);
        avatarRepositoryI.save(avatar);
        itemInstanceRepositoryI.delete(item);
    }

    public List<ItemInstance> getInventory(Long AAvatarId) {
        Avatar avatar = avatarRepositoryI.findByAvatarId(AAvatarId);
        return avatar.getInventory();
    }

    public List<ItemInstance> getEquipment(Long AAvatarId) {
        Avatar avatar = avatarRepositoryI.findByAvatarId(AAvatarId);
        return avatar.getEquipment();
    }

    //Wird für Tests benötigt
    public void setItemInstanceRepositoryI(ItemInstanceRepositoryI itemInstanceRepositoryI) {
        this.itemInstanceRepositoryI = itemInstanceRepositoryI;
    }

    public void setDungeonRepositoryI(DungeonRepositoryI dungeonRepositoryI) {
        this.dungeonRepositoryI = dungeonRepositoryI;
    }

    public void setUserRepositoryI(UserRepositoryI userRepositoryI) {
        this.userRepositoryI = userRepositoryI;
    }

    public void setRoomRepositoryI(RoomRepositoryI roomRepositoryI) {
        this.roomRepositoryI = roomRepositoryI;
    }

    public void setAvatarRepositoryI(AvatarRepositoryI avatarRepositoryI) {
        this.avatarRepositoryI = avatarRepositoryI;
    }

    public void setPermissionRepositoryI(PermissionRepositoryI permissionRepositoryI) {
        this.permissionRepositoryI = permissionRepositoryI;
    }
}
