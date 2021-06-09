package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository für eine Aktion.
 * <p>
 * Es stellt alle Funktionalitäten zum Speichern, Löschen und Holen einer Aktion aus der Datenbank bereit.
 *
 * @author Nicolas Haug
 * @see UserAction
 */
@Repository
public interface UserActionRepositoryI extends JpaRepository<UserAction, Long> {

    /**
     * Gibt alle Aktionseinträge aus der Datenbank zurück.
     *
     * @return Alle Aktionseinträge aus der Datenbank.
     */
    @Override
    @NonNull
    List<UserAction> findAll();

    /**
     * Sucht die Aktion mit der übergebenen ID in der Datenbank.
     *
     * @param AActionId ID der gesuchten Aktion.
     * @return Gesuchte Aktion.
     */
    UserAction findByActionId(Long AActionId);

    /**
     * Sucht alle Aktionen zu einem Dungeon in der Datenbank.
     *
     * @param ADungeon Dungeon zu dem alle Aktionen gesucht werden sollen.
     * @return Alle Aktionen des Dungeons.
     */
    List<UserAction> findByDungeon(Dungeon ADungeon);
}