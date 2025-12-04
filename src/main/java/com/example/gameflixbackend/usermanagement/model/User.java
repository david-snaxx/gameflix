package com.example.gameflixbackend.usermanagement.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }
}
