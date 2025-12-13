package com.example.gameflixbackend.pagerender.service;

import com.example.gameflixbackend.usermanagement.model.User;
import com.example.gameflixbackend.usermanagement.repository.UserRepository;
import com.example.gameflixbackend.usermanagement.service.UserServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Determines the role aka level of access the user has.
     * @param username The username for the account in question.
     * @return A UserDetails model that defines access for pages/
     * @throws UsernameNotFoundException If the username given does not have a record in the database.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        User user = optionalUser.get();

        List<String> roleList = new ArrayList<>();
        roleList.add("USER"); // default role

        // subscriber check
        if (user.getSubscribed()) {
            roleList.add("SUBSCRIBER");
        }

        // admin check
        if (user.getAdmin()) {
            roleList.add("ADMIN");
        }
        String[] roles = roleList.toArray(new String[0]);

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(roles)
                .build();
    }
}
