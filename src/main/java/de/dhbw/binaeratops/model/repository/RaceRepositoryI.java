package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.model.entitys.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository für eine Rasse.
 * <p>
 * Es stellt alle Funktionalitäten zum Speichern, Löschen und Holen einer Rasse aus der Datenbank bereit.
 *
 * @author Nicolas Haug
 * @see Race
 */
@Repository
public interface RaceRepositoryI extends JpaRepository<Race, Long> {

    /**
     * Sucht alle Rasseneinträge aus der Datenbank.
     *
     * @return Alle Rasseneinträge aus der Datenbank.
     */
    @Override
    @NonNull
    List<Race> findAll();

    /**
     * Sucht die Rasse mit der übergebenen ID in der Datenbank.
     *
     * @param ARaceId ID der gesuchten Rasse.
     * @return Gesuchte Rasse.
     */
    Race findByRaceId(Long ARaceId);
}