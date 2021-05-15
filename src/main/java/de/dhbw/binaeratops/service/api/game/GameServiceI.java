package de.dhbw.binaeratops.service.api.game;

import com.vaadin.flow.component.UI;
import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.model.enums.Gender;
import de.dhbw.binaeratops.view.dungeonmaster.DungeonMasterView;

/**
 * Interface für die Komponente "GameService".
 * <p>
 * Dieser Service stellt alle Funktionalitäten für das Spiel bereit.
 * </p>
 * <p>
 * Für Implementierung dieser Komponente siehe @{@link de.dhbw.binaeratops.service.impl.game.GameService}.
 * </p>
 *
 * @author Timon Gartung, Lukas Göpel, Matthias Rall, Lars Rösel
 */
public interface GameServiceI {
    /**
     *
     * @param ADungeon
     * @param AUI
     * @param AView
     */
    void initialize(Dungeon ADungeon, UI AUI, DungeonMasterView AView);

    /**
     *
     * @param ADungeonId
     */
    void updateView(Long ADungeonId);

    /**
     *
     * @param ADungeon
     * @param AUser
     * @param ACurrentRoomId
     * @param AAvatarName
     * @param AAvatarGender
     * @param AAvatarRole
     * @param AAvatarRace
     */
    void createNewAvatar(Dungeon ADungeon, User AUser, Long ACurrentRoomId, String AAvatarName, Gender AAvatarGender, Role AAvatarRole, Race AAvatarRace);

}
