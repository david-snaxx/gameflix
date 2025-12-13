package com.example.gameflixbackend.usermanagement.service;

import com.example.gameflixbackend.usermanagement.model.User;
import com.example.gameflixbackend.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<User> user = this.userRepository.findByUsername(username);
        if (user.isPresent()) {
            return user;
        }
        throw new RuntimeException("User " + username + " not found");
    }

    @Override
    public Optional<User> findById(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    /**
     * Checks to see if the input username exists in the database.
     * @param username The username to search for.
     * @return {@code true} if the username is in the database OR {@code false} if the username is not taken.
     */
    public boolean isUsernamePresent(String username) {
        return this.userRepository.existsByUsername(username);
    }
}
