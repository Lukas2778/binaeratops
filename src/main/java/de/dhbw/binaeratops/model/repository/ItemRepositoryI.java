package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.model.entitys.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository für einen Gegenstand.
 * <p>
 * Es stellt alle Funktionalitäten zum Speichern, Löschen und Holen eines Gegenstandes aus der Datenbank bereit.
 *
 * @author Nicolas Haug
 * @see Item
 */
@Repository
public interface ItemRepositoryI extends JpaRepository<Item, Long> {

    /**
     * Sucht alle Gegenstandeinträge aus der Datenbank.
     *
     * @return Alle Gegenstandeinträge aus der Datenbank.
     */
    @Override
    @NonNull
    List<Item> findAll();

    /**
     * Sucht den Gegenstand mit der übergebenen ID in der Datenbank.
     *
     * @param AItemId ID des gesuchten Gegenstandes.
     * @return Gesuchter Gegenstand.
     */
    Item findByItemId(Long AItemId);
}