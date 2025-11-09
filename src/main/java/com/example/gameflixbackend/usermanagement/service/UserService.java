package com.example.gameflixbackend.usermanagement.service;

import com.example.gameflixbackend.usermanagement.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    /**
     * Save a new {@link User} to the GameFlix database.
     * @param user The new {@link User} to save.
     */
    public void save(User user);

    /**
     * Remove a {@link User} from the GameFlix database that corresponds to the given id.
     * @param id The id of the user to remove.
     */
    public void delete(Long id);

    /**
     * Get an {@link User} from the GameFlix database that has the given username if one exists.
     * @param username The username to search for.
     * @return An {@link Optional} holding either a {@link User} with the given username, or an empty optional if
     * none exist in the database.
     */
    public Optional<User> findByUsername(String username);

    /**
     * Get an {@link User} from the GameFlix database that has the given id if one exists.
     * @param id The id to search for.
     * @return An {@link Optional} holding either a {@link User} with the given id, or an empty optional if
     * none exist in the database.
     */
    public Optional<User> findById(Long id);

    /**
     * Get all {@link User}s currently stored in the GameFlix database.
     * @return A list of all {@link User}s in the database.
     */
    public List<User> findAll();
}
