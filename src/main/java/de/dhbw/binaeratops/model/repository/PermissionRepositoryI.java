package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.model.entitys.Dungeon;
import de.dhbw.binaeratops.model.entitys.Permission;
import de.dhbw.binaeratops.model.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepositoryI extends JpaRepository<Permission, Long> {

    /**
     * Sucht alle Rasseneinträge aus der Datenbank.
     *
     * @return Alle Rasseneinträge aus der Datenbank. TODO
     */
    @Override
    @NonNull
    List<Permission> findAll();

    List<Permission> findByAllowedDungeonAndUser(Dungeon AAllowedDungeon, User AUser);

    List<Permission> findByRequestedDungeonAndUser(Dungeon AAllowedDungeon, User AUser);

    List<Permission> findByBlockedDungeonAndUser(Dungeon AAllowedDungeon, User AUser);

    List<Permission> findByAllowedDungeon(Dungeon AAllowedDungeon);
}
