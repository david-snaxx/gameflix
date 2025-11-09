package com.example.gameflixbackend.gamemanagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;
    private String summary;
    private Double rating;
    private String coverUrl;
}
