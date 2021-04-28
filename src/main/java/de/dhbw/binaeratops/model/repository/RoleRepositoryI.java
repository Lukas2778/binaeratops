package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.model.entitys.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository für eine Rolle.
 *
 * Es stellt alle Funktionalitäten zum Speichern, Löschen und Holen einer Rolle aus der Datenbank bereit.
 *
 * @see Role
 *
 * @author Nicolas Haug
 */
@Repository
public interface RoleRepositoryI extends JpaRepository<Role, Long> {

    /**
     * Sucht alle Rolleneinträge aus der Datenbank.
     * @return Alle Rolleneinträge aus der Datenbank.
     */
    @Override
    @NonNull
    List<Role> findAll();

    /**
     * Sucht die Rolle mit der übergebenen ID in der Datenbank.
     * @param ARoleId ID der gesuchten Rolle.
     * @return Gesuchte Rolle.
     */
    Role findByRoleId(Long ARoleId);
}