package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.model.entitys.NPC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository für eine NPC-Blaupause.
 * <p>
 * Es stellt alle Funktionalitäten zum Speichern, Löschen und Holen einer NPC-Blaupause aus der Datenbank bereit.
 *
 * @author Nicolas Haug
 * @see NPC
 */
@Repository
public interface NPCRepositoryI extends JpaRepository<NPC, Long> {

    /**
     * Sucht alle NPC-Blaupauseneinträge aus der Datenbank.
     *
     * @return Alle NPC-Blaupauseneinträge aus der Datenbank.
     */
    @Override
    @NonNull
    List<NPC> findAll();

    /**
     * Sucht die NPC-Blaupause mit der übergebenen ID in der Datenbank.
     *
     * @param ANpcId ID der gesuchten NPC-Blaupause.
     * @return Gesuchte NPC-Blaupause.
     * @return Gesuchte NPC-Blaupause.
     */
    NPC findByNpcId(Long ANpcId);
}