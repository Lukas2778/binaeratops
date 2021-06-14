package de.dhbw.binaeratops.model.api;

import de.dhbw.binaeratops.model.entitys.*;
import de.dhbw.binaeratops.model.enums.ActionType;

/**
 * Schnittstelle für eine Benutzeraktion.
 * <p>
 * Sie stellt alle Funktionalitäten zum Umgang mit einer Benutzeraktion bereit.
 * <p>
 * Für Datenbankoperationen siehe {@link de.dhbw.binaeratops.model.repository.UserActionRepositoryI}.
 * <p>
 * Für die Implementierung siehe {@link de.dhbw.binaeratops.model.entitys.UserAction}.
 *
 * @author Nicolas Haug
 */
public interface UserActionI {

    /**
     * Gibt die ID der Benutzeraktion zurück.
     *
     * @return ID der Benutzeraktion.
     */
    Long getActionId();

    /**
     * Setzt die ID der Benutzeraktion.
     *
     * @param AActionId ID der Benutzeraktion.
     */
    void setActionId(Long AActionId);

    /**
     * Gibt den Benutzer der Benutzeraktion zurück.
     *
     * @return Benutzer der Benutzeraktion.
     */
    User getUser();

    /**
     * Setzt den Benutzer der Benutzeraktion.
     *
     * @param AUser Benutzer der Benutzeraktion.
     */
    void setUser(User AUser);

    /**
     * Gibt den Avatar der Benutzeraktion zurück.
     *
     * @return Avatar der Benutzeraktion.
     */
    Avatar getAvatar();

    /**
     * Setzt den Avatar der Benutzeraktion.
     *
     * @param AAvatar Avatar der Benutzeraktion.
     */
    void setAvatar(Avatar AAvatar);

    /**
     * Gibt den Dungeon der Benutzeraktion zurück.
     *
     * @return Dungeon der Benutzeraktion.
     */
    Dungeon getDungeon();

    /**
     * Setzt den Dungeon der Benutzeraktion.
     *
     * @param ADungeon Dungeon der Benutzeraktion.
     */
    void setDungeon(Dungeon ADungeon);

    /**
     * Gibt den Aktionstyp der Benutzeraktion zurück.
     *
     * @return Aktionstyp der Benutzeraktion.
     */
    ActionType getActionType();

    /**
     * Setzt den Aktionstyp der Benutzeraktion.
     *
     * @param AActionType Aktionstyp der Benutzeraktion.
     */
    void setActionType(ActionType AActionType);

    /**
     * Gibt die Berechtigung der Benutzeraktion zurück.
     *
     * @return Berechtigung der Benutzeraktion.
     */
    Permission getPermission();

    /**
     * Setzt die Berechtigung der Benutzeraktion.
     *
     * @param APermission Berechtigung der Benutzeraktion.
     */
    void setPermission(Permission APermission);

    /**
     * Gibt die Nachricht der Benutzeraktion zurück.
     *
     * @return Nachricht der Benutzeraktion.
     */
    String getMessage();

    /**
     * Setzt die Nachricht der Benutzeraktion.
     *
     * @param AMessage Nachricht der Benutzeraktion.
     */
    void setMessage(String AMessage);

    /**
     * Gibt den NPC der Benutzeraktion zurück.
     *
     * @return NPC der Benutzeraktion.
     */
    NPCInstance getInteractedNpc();

    /**
     * Setzt den NPC der Benutzeraktion.
     *
     * @param AInteractedNPC NPC der Benutzeraktion.
     */
    void setInteractedNpc(NPCInstance AInteractedNPC);

    /**
     * Gibt den Gegenstand der Benutzeraktion zurück.
     *
     * @return Gegenstand der Benutzeraktion.
     */
    Item getInteractedItem();

    /**
     * Setzt den Gegenstand der Benutzeraktion.
     *
     * @param AInteractedItem Gegenstand der Benutzeraktion.
     */
    void setInteractedItem(Item AInteractedItem);

    /**
     * Gibt den Wahrheitswert zurück, ob angefragt wurde.
     *
     * @return Wahrheitswert, ob angefragt wurde.
     */
    Boolean getRequested();

    /**
     * Setzt den Wahrheitswert, ob angefragt wurde.
     *
     * @param ARequested Wahrheitswert, ob angefragt wurde.
     */
    void setRequested(Boolean ARequested);
}