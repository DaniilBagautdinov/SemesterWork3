package ru.kpfu.itis.bagautdinov.repositories;

import ru.kpfu.itis.bagautdinov.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.username = ?1")
    Optional<User> findByUsername(String username);


}
