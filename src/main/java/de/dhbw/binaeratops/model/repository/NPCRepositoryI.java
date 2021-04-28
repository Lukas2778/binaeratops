package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.model.entitys.NPC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository für einen NPC.
 *
 * Es stellt alle Funktionalitäten zum Speichern, Löschen und Holen eines NPC aus der Datenbank bereit.
 *
 * @see NPC
 *
 * @author Nicolas Haug
 */
@Repository
public interface NPCRepositoryI extends JpaRepository<NPC, Long> {

    /**
     * Sucht alle NPC-Einträge aus der Datenbank.
     * @return Alle NPC-Einträge aus der Datenbank.
     */
    @Override
    @NonNull
    List<NPC> findAll();

    /**
     * Sucht den NPC mit der übergebenen ID in der Datenbank.
     * @param ANpcId ID des gesuchten NPC.
     * @return Gesuchter NPC.
     */
    NPC findByNpcId(Long ANpcId);
}