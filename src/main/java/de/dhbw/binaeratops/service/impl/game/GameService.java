package de.dhbw.binaeratops.service.impl.game;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.repository.DungeonRepositoryI;
import de.dhbw.binaeratops.service.api.configuration.DungeonServiceI;
import de.dhbw.binaeratops.view.dungeonmaster.DungeonMasterView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author Lars Rösel
 * Date: 08.05.2021
 * Time: 16:35
 */
@Service
public class GameService {
    HashMap<Long, UI> dungeonUIHashMap = new HashMap<>();
    HashMap<Long, DungeonMasterView> dungeonDungeonMasterViewHashMap = new HashMap<>();

    @Autowired
    DungeonServiceI dungeonServiceI;

    @Autowired
    DungeonRepositoryI dungeonRepositoryI;

    public GameService() {}

    public void initialize(Dungeon ADungeon, UI ui, DungeonMasterView view) {
        dungeonUIHashMap.put(ADungeon.getDungeonId(), ui);
        dungeonDungeonMasterViewHashMap.put(ADungeon.getDungeonId(), view);
    }

    public void updateView(Long ADungeonId) {
        Dungeon dungeon = dungeonRepositoryI.findByDungeonId(ADungeonId);
        dungeonUIHashMap.get(dungeon.getDungeonId()).access(() -> dungeonDungeonMasterViewHashMap.get(dungeon.getDungeonId()).add(new Text("HEUREKA")));
    }

}
