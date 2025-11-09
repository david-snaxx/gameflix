package com.example.gameflixbackend.usermanagement.model;

import jakarta.persistence.*;

import java.io.Serializable;

/**
 * Represents all information tied to a gameflix user.
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "username", unique = true, nullable = false, length = 50)
    public String username;

    @Column(name = "password", nullable = false, length = 255)
    public String password;
}
