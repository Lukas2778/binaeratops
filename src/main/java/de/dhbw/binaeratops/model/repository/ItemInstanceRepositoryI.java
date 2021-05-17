package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.model.entitys.ItemInstance;
import de.dhbw.binaeratops.model.entitys.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository für eine Gegenstand-Instanz.
 * <p>
 * Es stellt alle Funktionalitäten zum Speichern, Löschen und Holen einer Gegenstand-Instanz aus der Datenbank bereit.
 *
 * @author Lars Rösel, Nicolas Haug
 * Date: 08.05.2021
 * Time: 20:23
 * @see ItemInstance
 */
@Repository
public interface ItemInstanceRepositoryI extends JpaRepository<ItemInstance, Long> {

    /**
     * Sucht alle Gegenstand-Instanzeinträge aus der Datenbank.
     *
     * @return Alle Gegenstand-Instanzeinträge aus der Datenbank.
     */
    @Override
    @NonNull
    List<ItemInstance> findAll();

    /**
     * Sucht die Gegenstand-Instanzen mit der übergebenen ID in der Datenbank.
     *
     * @param AItemId ID der gesuchten Gegenstand-Instanz.
     * @return Gesuchte Gegenstand-Instanz.
     */
    ItemInstance findByItemInstanceId(Long AItemId);

    /**
     * Sucht die Gegenstand-Instanzen für den übergebenen Raum.
     *
     * @param ARoom Raum, für den alle Gegenstand-Instanzen gesucht sind.
     * @return Gegenstand-Instanzen des Raumes.
     */
    List<ItemInstance> findByRoom(Room ARoom);
}
