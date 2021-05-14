package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.model.entitys.Dungeon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository für einen Dungeon.
 * <p>
 * Es stellt alle Funktionalitäten zum Speichern, Löschen und Holen eines Dungeon aus der Datenbank bereit.
 *
 * @author Nicolas Haug
 * @see Dungeon
 */
@Repository
public interface DungeonRepositoryI extends JpaRepository<Dungeon, Long> {

    /**
     * Sucht alle Dungeoneinträge aus der Datenbank.
     *
     * @return Alle Dungeoneinträge aus der Datenbank.
     */
    @Override
    @NonNull
    List<Dungeon> findAll();

    /**
     * Sucht den Dungeon mit der übergebenen ID in der Datenbank.
     *
     * @param ADungeonId ID des gesuchten Dungeons.
     * @return Gesuchter Dungeon.
     */
    Dungeon findByDungeonId(Long ADungeonId);
}