package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.model.entitys.Dungeon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository für einen Dungeon.
 *
 * Es stellt alle Funktionalitäten zum Speichern, Löschen und Holen eines Dungeon aus der Datenbank bereit.
 *
 * @see Dungeon
 *
 * @author Nicolas Haug
 */
@Repository
public interface DungeonRepositoryI extends JpaRepository<Dungeon, Long> {

    /**
     * Sucht alle Dungeoneinträge aus der Datenbank.
     * @return Alle Dungeoneinträge aus der Datenbank.
     */
    @Override
    @NonNull
    List<Dungeon> findAll();

    /**
     * Sucht den Dungeon mit der übergebenen ID in der Datenbank.
     * @param ADungeonId ID des gesuchten Dungeons.
     * @return Gesuchter Dungeon.
     */
    Dungeon findByDungeonId(Long ADungeonId);

    @Query(value = "SELECT dungeon.* FROM dungeon RIGHT JOIN user_my_dungeons ON dungeon.dungeon_id = user_my_dungeons.my_dungeons_dungeon_id WHERE user_my_dungeons.user_user_id = :userId", nativeQuery = true)
    List<Dungeon> findByUserId(@Param("userId")Long AUserId);
}