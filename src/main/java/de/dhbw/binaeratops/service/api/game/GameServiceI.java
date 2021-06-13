package de.dhbw.binaeratops.service.api.game;

import com.vaadin.flow.component.UI;
import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.model.enums.Gender;
import de.dhbw.binaeratops.model.enums.Status;
import de.dhbw.binaeratops.view.dungeonmaster.DungeonMasterView;

import java.util.List;

/**
 * Interface für die Komponente "GameService".
 * <p>
 * Dieser Service stellt alle Funktionalitäten für das Spiel bereit.
 * </p>
 * <p>
 * Für Implementierung dieser Komponente siehe {@link de.dhbw.binaeratops.service.impl.game.GameService}.
 * </p>
 *
 * @author Timon Gartung, Lukas Göpel, Matthias Rall, Lars Rösel
 */
public interface GameServiceI {
    /**
     * Initialisiert den GameService mithilfe der übergebenen Parameter.
     *
     * @param ADungeon Dungeon.
     * @param AUI      UserInterface.
     * @param AView    Anzeige.
     */
    void initialize(Dungeon ADungeon, UI AUI, DungeonMasterView AView);

    /**
     * HashMap auf der Oberfläche aktualisieren.
     *
     * @param ADungeonId DunegonId.
     */
    void updateView(Long ADungeonId);

    /**
     * Neuen Avatar anlegen und in der Datenbank speichern.
     *
     * @param ADungeonId     Dungeon für den der Avatar erstellt werden soll.
     * @param AUserId        Nutzer für den der Avatar erstellt werden soll.
     * @param ACurrentRoomId Raum in dem der Avatar bei Start befinden soll.
     * @param AAvatarName    Name des Avatars.
     * @param AAvatarGender  Geschlecht des Avatars.
     * @param AAvatarRoleId  Rolle des Avatars.
     * @param AAvatarRaceId  Rasse des Avatars.
     * @param ALifepoints    Lebenspunkte des Avatars.
     */
    void createNewAvatar(Long ADungeonId, Long AUserId, Long ACurrentRoomId, String AAvatarName, Gender AAvatarGender, Long AAvatarRoleId, Long AAvatarRaceId, Long ALifepoints);

    void deleteAvatar(Long ADungeonId, Long AUserId, Long AAvatarId);

    Avatar getAvatarById(Long AAvatarId);

    /**
     * Avatarfortschritt im Dungeon speichern.
     *
     * @param AAvatarId      Avatar der aktualisiert werden soll.
     * @param ACurrentRoomId Raum der der Liste der vom Avatar besuchten Räume hinzugefügt werden soll.
     * @return Liste der schon besuchten Räume (inklusive dem aktuell übergebenen Raum).
     */
    List<Room> saveAvatarProgress(Long AAvatarId, Long ACurrentRoomId);

    /**
     * Spieler den aktiven Spielern hinzufügen.
     *
     * @param ADungeonId Dungeon dem der Spieler beitritt.
     * @param AUserId    Benutzer, der den Dungeon betritt.
     * @param AAvatarId  Avatar, der auf active gesetzt werden soll.
     */
    void addActivePlayer(Long ADungeonId, Long AUserId, Long AAvatarId);

    /**
     * Spieler von der Liste der aktiven Spieler löschen.
     *
     * @param ADungeonId Dungeon der verlassen wird.
     * @param AUserId    Benutzer, der den Dungeon verlässt.
     * @param AAvatarId  Avatar, der auf active gesetzt werden soll.
     */
    void removeActivePlayer(Long ADungeonId, Long AUserId, Long AAvatarId);

    /**
     * Gibt die Standard-Lebenspunkte eines Avatars in einem Dungeon zurück.
     *
     * @param ADungeonId Dungeon, zu welchem die Punkte geholt werden sollen.
     * @return Standard-Lebenspunkte eines Avatars.
     */
    Long getStandardAvatarLifepoints(Long ADungeonId);

    /**
     * Gibt den Wahrheitswert zurück, ob der eingegebene Benutzername gültig ist.
     *
     * @param ADungeonId  Dungeon für den gesucht werden soll, ob der Name eindeutig ist.
     * @param AAvatarName Gesuchter Avatarname.
     * @return Wahrheitswert.
     */
    boolean avatarNameIsValid(Long ADungeonId, String AAvatarName);

    /**
     * Gibt den Wahrheitswert zurück, ob das eingegebene Geschlecht gültig ist.
     *
     * @param AAvatarGender Eingegebenes Geschlecht.
     * @return Wahrheitswert.
     */
    boolean avatarGenderIsValid(Gender AAvatarGender);

    /**
     * Gibt den Wahrheitswert zurück, ob die eingegebene Rolle gültig ist.
     *
     * @param AAvatarRole Eingegebene Avatarrolle.
     * @return Wahrheitswert.
     */
    boolean avatarRoleIsValid(Role AAvatarRole);

    /**
     * Gibt den Wahrheitswert zurück, ob die eingegebene Rasse gültig ist.
     *
     * @param AAvatarRace Eingegebene Avatarrasse.
     * @return Wahrheitswert.
     */
    boolean avatarRaceIsValid(Race AAvatarRace);

    /**
     * Gibt zurück, ob der Dungeon noch aktiv ist.
     * @param ADungeonId Der gewünschte Dungeon.
     * @return Boolean, ob es aktiv ist oder nicht.
     */
    Status getDungeonStatus(Long ADungeonId);

    /**
     * Setzt die Lebenspunkte des Avatars
     * @param AAvatarId der gewünschte Avatar
     * @param AValue der gewünschte Wert
     */
    void setLifePoints(Long AAvatarId, Long AValue);
}
