package de.dhbw.binaeratops.service.impl.game;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.model.enums.Gender;
import de.dhbw.binaeratops.model.repository.AvatarRepositoryI;
import de.dhbw.binaeratops.model.repository.DungeonRepositoryI;
import de.dhbw.binaeratops.model.repository.RoomRepositoryI;
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
 * Für Schnittstelle dieser Komponente siehe @{@link de.dhbw.binaeratops.service.api.game.GameServiceI}.
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
    DungeonRepositoryI dungeonRepositoryI;

    @Autowired
    RoomRepositoryI roomRepositoryI;

    @Autowired
    AvatarRepositoryI avatarRepositoryI;

    /**
     * Standardkonstruktor zum erzeugen des GameService.
     */
    public GameService() {}

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
    public void createNewAvatar(Dungeon ADungeon, User AUser, Long ACurrentRoomId, String AAvatarName, Gender AAvatarGender, Role AAvatarRole, Race AAvatarRace){
        Avatar createAvatar= new Avatar(roomRepositoryI.findByRoomId(ACurrentRoomId), AAvatarGender, AAvatarName, AAvatarRace, AAvatarRole);
        createAvatar.setDungeon(ADungeon);
        createAvatar.setUser(AUser);
        //createAvatar.setCurrentRoom(ACurrentRoom);
        avatarRepositoryI.save(createAvatar);
        ADungeon.addAvatar(createAvatar);
        AUser.addAvatar(createAvatar);
    }

    public List<Room> saveAvatarProgress(Avatar AAvatar, Room ACurrentRoom){
        AAvatar.addVisitedRoom(ACurrentRoom);
        AAvatar.setCurrentRoom(ACurrentRoom);
        avatarRepositoryI.save(AAvatar);
        return AAvatar.getVisitedRooms();
    }

}
