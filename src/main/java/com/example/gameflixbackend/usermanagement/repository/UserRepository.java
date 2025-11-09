package com.example.gameflixbackend.usermanagement.repository;

import com.example.gameflixbackend.usermanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Gets a {@link User} from the gameflix database matching the given username.
     * @param username The username to search for.
     * @return All info of a gameflix user in an {@link User} object.
     */
    @Query("SELECT x FROM User x WHERE x.username = :username")
    Optional<User> findByUsername(@Param("username") String username);
}
