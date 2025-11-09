package com.example.gameflixbackend.gamemanagement.service;

import com.example.gameflixbackend.gamemanagement.model.Game;
import com.example.gameflixbackend.gamemanagement.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public void save(Game game) {
        this.gameRepository.save(game);
    }

    @Override
    public void deleteById(Long id) {
        this.gameRepository.deleteById(id);
    }

    @Override
    public Optional<Game> findById(Long id) {
        Optional<Game> game = this.gameRepository.findById(id);
        if (game.isPresent()) {
            return game;
        }
        throw new RuntimeException("Game with id " + id + " not found");
    }
}
