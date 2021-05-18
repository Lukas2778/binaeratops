package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.model.entitys.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository für eine Gegenstand-Blaupause.
 * <p>
 * Es stellt alle Funktionalitäten zum Speichern, Löschen und Holen einer Gegenstand-Blaupause aus der Datenbank bereit.
 *
 * @author Nicolas Haug
 * @see Item
 */
@Repository
public interface ItemRepositoryI extends JpaRepository<Item, Long> {

    /**
     * Sucht alle Gegenstand-Blaupauseneinträge aus der Datenbank.
     *
     * @return Alle Gegenstand-Blaupauseneinträge aus der Datenbank.
     */
    @Override
    @NonNull
    List<Item> findAll();

    /**
     * Sucht die Gegenstand-Blaupausen mit der übergebenen ID in der Datenbank.
     *
     * @param AItemId ID der gesuchten Gegenstand-Blaupause.
     * @return Gesuchte Gegenstand-Blaupause.
     */
    Item findByItemId(Long AItemId);
}