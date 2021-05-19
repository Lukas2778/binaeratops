package de.dhbw.binaeratops.service.impl.game;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.model.enums.Gender;
import de.dhbw.binaeratops.model.repository.*;
import de.dhbw.binaeratops.service.api.configuration.DungeonServiceI;
import de.dhbw.binaeratops.service.api.game.GameServiceI;
import de.dhbw.binaeratops.view.dungeonmaster.DungeonMasterView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

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
    public void createNewAvatar(Dungeon ADungeon, User AUser, Long ACurrentRoomId, String AAvatarName, Gender AAvatarGender, Role AAvatarRole, Race AAvatarRace, Long ALifepoints) {
        Avatar createAvatar = new Avatar(roomRepositoryI.findByRoomId(ACurrentRoomId), AAvatarGender, AAvatarName, AAvatarRace, AAvatarRole,
                ALifepoints);
        createAvatar.setDungeon(ADungeon);
        createAvatar.setUser(AUser);
        //createAvatar.setCurrentRoom(ACurrentRoom);
        avatarRepositoryI.save(createAvatar);
        ADungeon.addAvatar(createAvatar);
        AUser.addAvatar(createAvatar);
    }

    @Override
    public void deleteAvatar(Dungeon ADungeon, User AUser, Avatar AAvatar) {
        if (avatarRepositoryI.findByAvatarId(AAvatar.getAvatarId()) != null) {
            for (int i = 0; i <= AAvatar.getInventory().size(); i++) {
                try {
                    for (ItemInstance inventItem : AAvatar.getInventory()) {
                        AAvatar.removeInventoryItem(inventItem);
                        itemInstanceRepositoryI.delete(inventItem);
                    }
                } catch (Exception e) {
                }
            }
            for (int i = 0; i <= AAvatar.getEquipment().size(); i++) {
                try {
                    for (ItemInstance equipItem : AAvatar.getEquipment()) {
                        AAvatar.removeEquipmentItem(equipItem);
                        itemInstanceRepositoryI.delete(equipItem);
                    }
                } catch (Exception e) {
                }
            }
            try {
                ADungeon.removeAvatar(AAvatar);
                AUser.removeAvatar(AAvatar);
                userRepositoryI.save(AUser);
                dungeonRepositoryI.save(ADungeon);
                avatarRepositoryI.delete(AAvatar);
            }catch (Exception e){}
        }
    }

    @Override
    public List<Room> saveAvatarProgress(Avatar AAvatar, Room ACurrentRoom) {
        AAvatar.addVisitedRoom(ACurrentRoom);
        AAvatar.setCurrentRoom(ACurrentRoom);
        avatarRepositoryI.save(AAvatar);
        return AAvatar.getVisitedRooms();
    }

    @Override
    public void addActivePlayer(Dungeon ADungeon, User AUser, Avatar AAvatar) {
        Dungeon dungeon = dungeonRepositoryI.findByDungeonId(ADungeon.getDungeonId());
        User user = userRepositoryI.findByUserId(AUser.getUserId());
        Avatar avatar = avatarRepositoryI.findByAvatarId(AAvatar.getAvatarId());
        if (!dungeon.getCurrentUsers().contains(user)) {
            dungeon.addCurrentUser(user);
            avatar.setActive(true);
            avatarRepositoryI.save(avatar);
            user.setCurrentDungeon(dungeon);
            dungeonRepositoryI.save(dungeon);
            userRepositoryI.save(user);
        }
    }

    @Override
    public Avatar getAvatarById(Long AAvatarId) {
        return avatarRepositoryI.findByAvatarId(AAvatarId);
    }

    @Override
    public void removeActivePlayer(Dungeon ADungeon, User AUser, Avatar AAvatar) {
        Dungeon dungeon = dungeonRepositoryI.findByDungeonId(ADungeon.getDungeonId());
        User user = userRepositoryI.findByUserId(AUser.getUserId());
        Avatar avatar = avatarRepositoryI.findByAvatarId(AAvatar.getAvatarId());
        if (dungeon.getCurrentUsers().contains(user)) {
            dungeon.removeCurrentUser(user);
            avatar.setActive(false);
            avatarRepositoryI.save(avatar);
            user.removeCurrentDungeon();
            dungeonRepositoryI.save(dungeon);
            userRepositoryI.save(user);
        }
    }

    public Long getStandardAvatarLifepoints(Dungeon ADungeon) {
        return ADungeon.getStandardAvatarLifepoints();
    }

    @Override
    public boolean avatarNameIsValid(Dungeon ADungeon, String AAvatarName) {
        if (AAvatarName.isEmpty()||AAvatarName.contains(" "))
            return false;
        for (Avatar myAvatar : ADungeon.getAvatars()) {
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
}
