package de.dhbw.binaeratops.model.api;

import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.model.enums.Gender;

import java.util.List;

/**
 * Schnittstelle für einen Avatar.
 * <p>
 * Sie stellt alle Funktionalitäten zum Umgang mit einem Avatar bereit.
 * <p>
 * Für Datenbankoperationen siehe {@link de.dhbw.binaeratops.model.repository.AvatarRepositoryI}.
 * <p>
 * Für Implementierung siehe {@link de.dhbw.binaeratops.model.entitys.Avatar}
 *
 * @author Nicolas Haug
 */
public interface AvatarI {

    /**
     * Gibt die ID des Avatars zurück.
     *
     * @return Avatar-ID des Avatars.
     */
    Long getAvatarId();

    /**
     * Setzt die ID eines Avatars.
     *
     * @param AAvatarId Avatar-ID, die gesetzt werden soll.
     */
    void setAvatarId(Long AAvatarId);

    /**
     * Gibt die Raum-ID des Raumes zurück, in dem der Avatar sich aktuell befindet.
     *
     * @return Raum-ID des Raumes, in dem der Avatar sich aktuell befindet.
     */
    Long getRoomId();

    /**
     * Setzt die Raum-ID des Raumes, in dem der Avatar sich aktuell befindet.
     *
     * @param ARoomId Raum-ID des Raumes, in dem der Avatar sich aktuell befindet.
     */
    void setRoomId(Long ARoomId);

    /**
     * Gibt das Geschlecht des Avatars zurück.
     *
     * @return Gechlecht des Avatars.
     * @see Gender
     */
    Gender getGender();

    /**
     * Setzt das Geschlecht des Avatars.
     *
     * @param AGender Geschlecht des Avatars.
     * @see Gender
     */
    void setGender(Gender AGender);

    /**
     * Gibt den Namen des Avatars zurück.
     *
     * @return Namen des Avatars.
     */
    String getName();

    /**
     * Setzt den Namen des Avatars.
     *
     * @param AName Name des Avatars.
     */
    void setName(String AName);

    /**
     * Gibt den Benutzer des Avatars zurück.
     *
     * @return Benutzer des Avatars.
     */
    User getUser();

    /**
     * Setzt den Benutzer des Avatars.
     *
     * @param AUser Benutzer des Avatars.
     */
    void setUser(User AUser);

    /**
     * Gibt den Dungeon des Avatars zurück.
     *
     * @return Dungeon des Avatars.
     */
    Dungeon getDungeon();

    /**
     * Setzt den Dungeon des Avatars.
     *
     * @param ADungeon Dungeon des Avatars.
     */
    void setDungeon(Dungeon ADungeon);

    /**
     * Gibt die Rasse des Avatars zurück.
     *
     * @return Rasse des Avatars.
     */
    Race getRace();

    /**
     * Setzt die Rasse des Avatars.
     *
     * @param ARace Zu setzende Rasse.
     */
    void setRace(Race ARace);

    /**
     * Gibt die Rolle des Avatars zurück.
     *
     * @return Rolle des Avatars.
     */
    Role getRole();

    /**
     * Setzt die Rolle des Avatars.
     *
     * @param ARole Zu setzende Rolle.
     */
    void setRole(Role ARole);

    /**
     * Gibt den aktuellen Raum des Avatars zurück.
     *
     * @return Aktueller Raum des Avatars.
     */
    Room getCurrentRoom();

    /**
     * Setzt den aktuellen Raum des Avatars.
     *
     * @param ACurrentRoom Aktueller Raum, der gesetzt werden soll.
     */
    void setCurrentRoom(Room ACurrentRoom);

    /**
     * Gibt das Inventar eines Avatars als Liste von Gegenständen zurück.
     *
     * @return Liste von Gegenständen (Inventar).
     */
    List<ItemInstance> getInventory();

    /**
     * Fügt dem Inventar des Avatars einen Gegenstand hinzu.
     *
     * @param AItem Gegenstand, der hinzugefügt werden soll.
     */
    void addInventoryItem(ItemInstance AItem);

    /**
     * Entfernt dem Inventar des Avatars einen Gegenstand.
     *
     * @param AItem Gegenstand, der entfernt werden soll.
     */
    void removeInventoryItem(ItemInstance AItem);

    /**
     * Gibt das ausgerüstete Equipment eines Avatars als Liste von Gegenständen zurück.
     * <p>
     * WICHTIG: Diese Liste darf jeden Gegenstandstyp nur 1x beinhalten.
     *
     * @return Liste von Gegenständen (Equipment)
     */
    List<ItemInstance> getEquipment();

    /**
     * Fügt der Ausrüstung des Avatars einen Gegenstand hinzu.
     *
     * @param AItem Gegenstand, der hinzugefügt werden soll.
     */
    void addEquipmentItem(ItemInstance AItem);

    /**
     * Entfernt der Ausrüstung des Avatars einen Gegenstand.
     *
     * @param AItem Gegenstand, der entfernt werden soll.
     */
    void removeEquipmentItem(ItemInstance AItem);

    /**
     * Gibt die Liste der besuchten Räume des Avatars zurück.
     *
     * @return Liste der besuchten Räume.
     */
    List<Attendance> getVisitedRooms();

    /**
     * Fügt der Liste der besuchten Räume den übergebenen Raum hinzu.
     *
     * @param AAttendance Raum, der hinzugefügt werden soll.
     */
    void addVisitedRoom(Attendance AAttendance);

    /**
     * Entfernt der Liste der besuchten Räume den übergebenen Raum.
     *
     * @param AAttendance Raum, der entfernt werden soll.
     */
    void removeVisitedRoom(Attendance AAttendance);

    /**
     * Gibt die Lebenspunkte des Avatars zurück.
     *
     * @return Lebenspunkte des Avatars.
     */
    Long getLifepoints();

    /**
     * Setzt die Lebenspunkte des Avatars.
     *
     * @param ALifepoints Lebenspunkte des Avatars.
     */
    void setLifepoints(Long ALifepoints);

    /**
     * Setzt die Lebenspunkte des Avatars inkl. Boni.
     *
     * @param ALifepoints          Lebenspunkte des Avatars.
     * @param ALifepointsBonusRace Bonus der Rasse.
     * @param ALifepointsBonusRole Bonus der Rolle.
     */
    void setLifepoints(Long ALifepoints, Long ALifepointsBonusRace, Long ALifepointsBonusRole);

    /**
     * Gibt den Wahrheitswert zurück, ob der Spieler aktiv ist.
     *
     * @return Wahrheitswert, ob Spieler aktiv ist.
     */
    boolean isActive();

    /**
     * Setzt den Wahrheitswert, ob der Avatar aktiv ist.
     *
     * @param AActive Wahrheitswert, ob Avatar aktiv ist oder nicht.
     */
    void setActive(boolean AActive);

    /**
     * Gibt den Wahrheitswert zurück, ob der Avatar eine Anfrage an den Dungeon-Master gestellt hat.
     *
     * @return Wahrheitswert, ob der Avatar eine Anfrage an den Dungeon-Master gestellt hat.
     */
    boolean hasRequested();

    /**
     * Setzt den Wahrheitswert, ob der Avatar eine Anfrage an den Dungeon-Master gestellt hat.
     *
     * @param ARequested Wahrheitswert, ob der Avatar eine Anfrage an den Dungeon-Master gestellt hat oder nicht.
     */
    void setRequested(boolean ARequested);
}
