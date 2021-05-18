package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.model.entitys.NPCInstance;
import de.dhbw.binaeratops.model.entitys.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository für eine NPC-Instanz.
 * <p>
 * Es stellt alle Funktionalitäten zum Speichern, Löschen und Holen einer NPC-Instanz aus der Datenbank bereit.
 *
 * @author Matthias Rall, Nicolas Haug
 * Date: 09.05.2021
 * Time: 18:39
 * @see NPCInstance
 */
@Repository
public interface NpcInstanceRepositoryI extends JpaRepository<NPCInstance, Long> {

    /**
     * Sucht alle NPC-Instanzeinträge aus der Datenbank.
     *
     * @return Alle NPC-Instanzeinträge aus der Datenbank.
     */
    @Override
    List<NPCInstance> findAll();

    /**
     * Sucht die NPC-Instanzen mit der übergebenen ID in der Datenbank.
     *
     * @param ANpcId ID der gesuchten NPC-Instanz.
     * @return Gesuchte NPC-Instanz.
     */
    NPCInstance findByNpcInstanceId(Long ANpcId);

    /**
     * Sucht die NPC-Instanzen für den übergebenen Raum.
     *
     * @param ARoom Raum, für den alle NPC-Instanzen gesucht sind.
     * @return Gegenstand-Instanzen des Raumes.
     */
    List<NPCInstance> findByRoom(Room ARoom);
}
