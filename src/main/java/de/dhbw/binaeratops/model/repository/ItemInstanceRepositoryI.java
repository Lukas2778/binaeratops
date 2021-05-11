package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.model.entitys.ItemInstance;
import de.dhbw.binaeratops.model.entitys.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository für eine Gegenstand-Blaupause.
 * <p>
 * Es stellt alle Funktionalitäten zum Speichern, Löschen und Holen einer Gegenstand-Blaupause aus der Datenbank bereit.
 *
 * @author Lars Rösel, Nicolas Haug
 * Date: 08.05.2021
 * Time: 20:23
 * @see ItemInstance
 */
@Repository
public interface ItemInstanceRepositoryI extends JpaRepository<ItemInstance, Long> {

    /**
     * Sucht alle Gegenstand-Blaupauseneinträge aus der Datenbank.
     *
     * @return Alle Gegenstand-Blaupauseneinträge aus der Datenbank.
     */
    @Override
    @NonNull
    List<ItemInstance> findAll();

    /**
     * Sucht die Gegenstand-Blaupausen mit der übergebenen ID in der Datenbank.
     *
     * @param AItemId ID der gesuchten Gegenstand-Blaupause.
     * @return Gesuchte Gegenstand-Blaupause.
     */
    ItemInstance findByItemInstanceId(Long AItemId);

    /**
     * Sucht die Gegenstand-Blaupausen für den übergebenen Raum.
     *
     * @param ARoom Raum, für den alle Gegenstand Blaupausen gesucht sind.
     * @return Gegenstand-Blaupausen des Raumes.
     */
    List<ItemInstance> findByRoom(Room ARoom);
}
