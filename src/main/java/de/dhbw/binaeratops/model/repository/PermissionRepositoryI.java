package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.Permission;
import de.dhbw.binaeratops.model.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository für eine Berechtigung.
 * <p>
 * Es stellt alle Funktionalitäten zum Speichern, Löschen und Holen einer Berechtigung aus der Datenbank bereit.
 *
 * @author Nicolas Haug
 * @see Permission
 */
@Repository
public interface PermissionRepositoryI extends JpaRepository<Permission, Long> {

    /**
     * Sucht alle Berechtigungseinträge aus der Datenbank.
     *
     * @return Alle Berechtigungseinträge aus der Datenbank.
     */
    @Override
    @NonNull
    List<Permission> findAll();

    /**
     * Sucht den Berechtigungseintrag zu einem bestimmten erlaubten Dungeon und Benutzer.
     * @param AAllowedDungeon Erlaubter Dungeon, zu dem die Berechtigung gesucht werden soll.
     * @param AUser Benutzer, zu dem die Berechtigung gesucht werden soll.
     * @return Berechtigung.
     */
    List<Permission> findByAllowedDungeonAndUser(Dungeon AAllowedDungeon, User AUser);

    /**
     * Sucht den Berechtigungseintrag zu einem bestimmten angefragten Dungeon und Benutzer.
     * @param ARequestedDungeon Angefragter Dungeon, zu dem die Berechtigung gesucht werden soll.
     * @param AUser Benutzer, zu dem die Berechtigung gesucht werden soll.
     * @return Berechtigung.
     */
    List<Permission> findByRequestedDungeonAndUser(Dungeon ARequestedDungeon, User AUser);

    /**
     * Sucht den Berechtigungseintrag zu einem bestimmten blockierten Dungeon und Benutzer.
     * @param ABlockedDungeon Blockierter Dungeon, zu dem die Berechtigung gesucht werden soll.
     * @param AUser Benutzer, zu dem die Berechtigung gesucht werden soll.
     * @return Berechtigung.
     */
    List<Permission> findByBlockedDungeonAndUser(Dungeon ABlockedDungeon, User AUser);

    /**
     * Gibt alle erlaubten Berechtigungen zu einem Dungeon zurück.
     * @param AAllowedDungeon Dungeon, zu dem die Berechtigungen gesucht werden sollen.
     * @return Alle erlaubten Berechtigungen.
     */
    List<Permission> findByAllowedDungeon(Dungeon AAllowedDungeon);

    /**
     * Gibt alle blockierten Berechtigungen zu einem Dungeon zurück.
     * @param ABlockedDungeon Dungeon, zu dem die Berechtigungen gesucht werden sollen.
     * @return Alle blockierten Berechtigungen.
     */
    List<Permission> findByBlockedDungeon(Dungeon ABlockedDungeon);
}
