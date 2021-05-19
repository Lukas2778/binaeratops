package de.dhbw.binaeratops.service.api.configuration;

import de.dhbw.binaeratops.model.entitys.*;

import java.util.List;

/**
 * Interface für die Komponente "DungeonService".
 * <p>
 * Dieser Service stellt alle Funktionalitäten zum Umgang mit einem Dungen bereit.
 * </p>
 * <p>
 * Für Implementierung dieser Komponente siehe {@link de.dhbw.binaeratops.service.impl.configurator.DungeonService}.
 * </p>
 *
 * @author Timon Gartung, Pedro Treuer, Nicolas Haug, Lukas Göpel, Matthias Rall, Lars Rösel
 */
public interface DungeonServiceI {
    /**
     * Sucht in der Datenbank nach allen Dungeons, die ein Benutzer erstellt hat.
     *
     * @param AUser Benutzer.
     * @return Liste von Dungeons, die der übergebene Benutzer erstellt hat.
     */
    List<Dungeon> getAllDungeonsFromUser(User AUser);

    /**
     * Sucht nach Dungeons, die dem Benutzer in der Lobby-Ansicht angezeigt werden sollen.
     *
     * @param AUser Benutzer.
     * @return Liste der Dungeons, die dem übergebenen Benutzer angezeigt werden sollen.
     */
    List<Dungeon> getDungeonsLobby(User AUser);

    /**
     * Setzt einen Dungeon auf aktiv.
     *
     * @param ADungeonId Id des Dungeon.
     */
    void activateDungeon(long ADungeonId);

    /**
     * Setzt einen Dungeon auf inaktiv.
     *
     * @param ADungeonId Id des Dungeon.
     */
    void deactivateDungeon(long ADungeonId);

    /**
     * Speichert den übergebenen Dungeon.
     *
     * @param ADungeon Dungeon, der gespeichert werden soll.
     */
    void saveDungeon(Dungeon ADungeon);

    /**
     * Speichert den übergebenen Benutzer.
     *
     * @param AUser Benutzer, der gespeichert werden soll.
     */
    void saveUser(User AUser);

    /**
     * Gibt die aktuellen Avatare zurück.
     *
     * @param ADungeonId ID des Dungeon.
     * @return Liste der activen Spieleravatare.
     */
    List<Avatar> getCurrentAvatars(long ADungeonId);

    /**
     * Setzt den gewünschten Avatar auf inaktiv
     * @param AAvatarId der gewünschte Avatar.
     */
    void setAvatarInactive(long AAvatarId);

    /**
     * Gibt den Raum des übergebenen Avatars zurück.
     *
     * @param AAvatar den gesucheten Avatar.
     * @return den aktuellen Raum des Avatars.
     */
    Room getRoomOfAvatar(Avatar AAvatar);

    /**
     * Gibt den Raum an der übergebenen Position zurück.
     *
     * @param ADungeon     Dungeon des Raumes.
     * @param AXCoordinate X-Koordinate des Raumes.
     * @param AYCoordinate Y-Koordinate des Raumes.
     * @return Gesuchter Raum.
     */
    Room getRoomByPosition(Dungeon ADungeon, int AXCoordinate, int AYCoordinate);

    /**
     * Setzt den Dungeon-Master des übergebenen Dungeons.
     *
     * @param ADungeonId Dungeon, für den der Dungeon-Master gesetzt werden soll.
     * @param AUserId  Benutzer ID des Benutzers, der Dungeon-Master werden soll.
     */
    void setDungeonMaster(Long ADungeonId, Long AUserId);

    /**
     * Gibt die aktuellen Benutzer zurück.
     *
     * @param ADungeon Dungeon, für den die aktuellen Benutzer zurückgegeben werden soll.
     * @return Aktuelle Benutzer.
     */
    List<User> getCurrentUsers(Dungeon ADungeon);

    /**
     * Gibt den Dungeon mit der übergebenen ID zurück.
     *
     * @param ADungeonId ID, des gesuchten Dungeons.
     * @return Gesuchter Dungeon.
     */
    Dungeon getDungeon(Long ADungeonId);

    /**
     * Kickt den übergebenen Benutzer aus dem übergebenen Dungeon.
     *
     * @param ADungeonId Dungeon aus dem der Benutzer entfernt werden soll.
     * @param AUserId    Benutzer, der entfernt werden soll.
     */
    void kickPlayer(Long ADungeonId, Long AUserId);

    void declinePlayer(Long ADungeonId, Long AUserId, Permission APermission);

    /**
     * Gibt den Raum der übergebenen ID zurück.
     *
     * @param ARoomId ID des gesuchten Raumes.
     * @return Gesuchter Raum.
     */
    Room getRoomById(Long ARoomId);

    /**
     * Gibt den User aus der DB zurück.
     *
     * @param AUserId Der gewünschte User.
     * @return
     */
    User getUser(Long AUserId);

    /**
     * Erlaubt übergebenen Benutzer dem Dungeon beizutreten.
     *
     * @param ADungeonId Dungeon, dem der Benutzer beitreten können soll.
     * @param AUserId    Benutzer, der beitreten können soll.
     * @param APermission Aktuelle Berechtigung.
     */
    void allowUser(Long ADungeonId, Long AUserId, Permission APermission);

    /**
     * Setzt den Avatar in Status "Keine ausstehende Anfrage vorhanden".
     *
     * @param AAvatarId Avatar, der den Status geändert bekommen soll.
     */
    void setAvatarNotRequested(Long AAvatarId);

    void removeRequestedUser(Long ADungeonId, Long AUserId);

    Permission getPermissionRequest(User AUser, Dungeon ADungeon);

    Permission getPermissionGranted(User AUser,Dungeon ADungeon);

    Permission getPermissionBlocked(User AUser,Dungeon ADungeon);

    void savePermission(Permission APermission);
}

