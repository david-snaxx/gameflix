package com.example.gameflixbackend.gamemanagement.repository;

import com.example.gameflixbackend.gamemanagement.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game,Long> {

    boolean existsByName(String name);
}
