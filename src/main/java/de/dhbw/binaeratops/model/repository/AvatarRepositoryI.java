package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.model.entitys.Avatar;
import de.dhbw.binaeratops.model.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository für einen Avatar.
 * <p>
 * Es stellt alle Funktionalitäten zum Speichern, Löschen und Holen eines Avatars aus der Datenbank bereit.
 *
 * @author Nicolas Haug
 * @see Avatar
 */
@Repository
public interface AvatarRepositoryI extends JpaRepository<Avatar, Long> {

    /**
     * Gibt alle Avatareinträge aus der Datenbank zurück.
     *
     * @return Alle Avatareinträge aus der Datenbank.
     */
    @Override
    @NonNull
    List<Avatar> findAll();

    /**
     * Sucht den Avatar mit der übergebenen ID in der Datenbank.
     *
     * @param AAvatarId ID des gesuchten Avatars.
     * @return Gesuchter Avatar.
     */
    Avatar findByAvatarId(Long AAvatarId);

    List<Avatar> findByUserAndActive(User AUser, boolean AActive);
}