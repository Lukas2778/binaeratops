package de.dhbw.binaeratops.model.repository;

import de.dhbw.binaeratops.model.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Override
    public List<User> findAll();

    public List<User> findByName(String name);
}
