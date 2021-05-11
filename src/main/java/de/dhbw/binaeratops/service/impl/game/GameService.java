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
public class GameService {
    HashMap<Long, UI> dungeonUIHashMap = new HashMap<>();
    HashMap<Long, DungeonMasterView> dungeonDungeonMasterViewHashMap = new HashMap<>();

    @Autowired
    DungeonServiceI dungeonServiceI;

    @Autowired
    DungeonRepositoryI dungeonRepositoryI;

    /**
     * Standardkonstruktor zum erzeugen des GameService.
     */
    public GameService() {}

    public void initialize(Dungeon ADungeon, UI AUI, DungeonMasterView AView) {
        dungeonUIHashMap.put(ADungeon.getDungeonId(), AUI);
        dungeonDungeonMasterViewHashMap.put(ADungeon.getDungeonId(), AView);
    }

    public void updateView(Long ADungeonId) {
        Dungeon dungeon = dungeonRepositoryI.findByDungeonId(ADungeonId);
        dungeonUIHashMap.get(dungeon.getDungeonId()).access(() -> dungeonDungeonMasterViewHashMap.get(dungeon.getDungeonId()).add(new Text("HEUREKA")));
    }

}
