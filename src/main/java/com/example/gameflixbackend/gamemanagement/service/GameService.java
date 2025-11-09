package com.example.gameflixbackend.gamemanagement.service;

import com.example.gameflixbackend.gamemanagement.model.Game;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface GameService {

    /**
     * Save a new {@link Game} to the GameFlix database.
     * @param game The new {@link Game} to save.
     */
    public void save(Game game);

    /**
     * Remove a {@link Game} from the GameFlix database that corresponds to the GameFlix id given.
     * @param id The ID of the {@link Game} to remove.
     */
    public void deleteById(Long id);

    /**
     * Get a {@link Game} from the database by its GameFlix id.
     * @param id The GameFlix id of the game.
     * @return The {@link Game} corresponding to the input GameFlix id if one exists.
     */
    public Optional<Game> findById(Long id);
}
