package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.model.entitys.NpcInstance;
import de.dhbw.binaeratops.model.entitys.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Matthias Rall
 * Date: 09.05.2021
 * Time: 18:39
 */

@Repository
public interface NpcInstanceRepositoryI extends JpaRepository<NpcInstance,Long> {
    @Override
    List<NpcInstance> findAll();

    NpcInstance findByNpcInstanceId(Long ANpcId);

    List<NpcInstance> findByRoom(Room ARoom);
}
