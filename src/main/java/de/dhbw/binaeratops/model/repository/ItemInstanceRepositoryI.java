package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.model.entitys.Item;
import de.dhbw.binaeratops.model.entitys.ItemInstance;
import de.dhbw.binaeratops.model.entitys.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Lars Rösel
 * Date: 08.05.2021
 * Time: 20:23
 */
@Repository
public interface ItemInstanceRepositoryI extends JpaRepository<ItemInstance, Long> {

    @Override
    @NonNull
    List<ItemInstance> findAll();

    /**
     * Sucht den Gegenstand mit der übergebenen ID in der Datenbank.
     * @param AItemId ID des gesuchten Gegenstandes.
     * @return Gesuchter Gegenstand.
     */
    ItemInstance findByItemInstanceId(Long AItemId);

    List<ItemInstance> findByRoom(Room ARoom);
}
