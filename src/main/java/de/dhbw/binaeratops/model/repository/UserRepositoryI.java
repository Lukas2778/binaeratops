package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.model.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository für einen Benutzer.
 *
 * Es stellt alle Funktionalitäten zum Speichern, Löschen und Holen eines Benutzers aus der Datenbank bereit.
 *
 * @see User
 *
 * @author Nicolas Haug
 */
@Repository
public interface UserRepositoryI extends JpaRepository<User,Long> {

    /**
     * Sucht alle Benutzereinträge aus der Datenbank zurück.
     * @return Alle Benutzereinträge aus der Datenbank.
     */
    @Override
    @NonNull
    List<User> findAll();

    /**
     * Sucht den Benutzer mit dem übergebenen Benutzernamen.
     * @param AUsername Zu suchender Benutzername.
     * @return Benutzer mit diesem Benutzernamen.
     */
    User findByUsername(String AUsername);
}
