package de.dhbw.binaeratops.model.repository;


import de.dhbw.binaeratops.model.entitys.Permission;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

/**
 * Repository für eine Permission
 * <p>
 * Es stellt alle Funktionalitäten zum Speichern, Löschen und Holen eines Avatars aus der Datenbank bereit.
 *
 * @author Pedro Treuer
 * @see de.dhbw.binaeratops.model.entitys.Permission
 */
@Repository
public interface PermissionRepositoryI
        extends JpaRepository<Permission, Long> {

    /**
     * Sucht alle Berechtigungen aus der Datenbank zurück.
     *
     * @return Alle Berechtigungen aus der Datenbank.
     */
    @Override
    @NonNull
    List<Permission> findAll();

    /**
     * Sucht den Avatar mit der übergebenen ID in der Datenbank.
     *
     * @param APermissionId der gesuchten Berechtigung.
     * @return Gesuchte Berechtigung.
     */
    Permission findByPermissionId(Long APermissionId);
}