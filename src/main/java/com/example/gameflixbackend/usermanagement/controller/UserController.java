package com.example.gameflixbackend.usermanagement.controller;

import com.example.gameflixbackend.usermanagement.model.LoginInfo;
import com.example.gameflixbackend.usermanagement.model.User;
import com.example.gameflixbackend.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Checks to see if given login information is correct for an existing gameflix account.
     * @param loginInfo The {@link LoginInfo} needed to validate a user.
     * @return Success/failure response for the given information.
     */
    @RequestMapping(value = "/login/validate", method = RequestMethod.POST)
    public ResponseEntity<String> validateLogin(@RequestBody LoginInfo loginInfo) {
        User user;

        try {
            user = this.getUserByUsername(loginInfo.username).getBody();
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }

        if (user == null) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }

        if (!passwordEncoder.matches(loginInfo.password, user.password)) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>("Login Successful", HttpStatus.OK);
    }

    /**
     * Processes the registering of a new GameFlix user account.
     * The username must not be in use already.
     * @param user The user object detailing the new account to attempt to create.
     * @return A response of successful or failed account creation.
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            user.password = this.passwordEncoder.encode(user.password);
            this.userService.save(user);
            return ResponseEntity
                    .ok("User registered successfully");
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    /**
     * Gets all details of an active user's account corresponding to the given username.
     * @param username The username to search for.
     * @return A response containing the full details of an {@link User}, or null if no user exists by the given name.
     */
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        try {
            Optional<User> user = userService.findByUsername(username);
            return ResponseEntity
                    .ok(user.get());
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    /**
     * Deletes an existing gameflix user account based on the login info given.
     * @param loginInfo The login info to verify the deletion request and identify what user record to delete.
     * @return A response of deletion success / faliure.
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@RequestBody LoginInfo loginInfo) {
        // was the login info valid?
        ResponseEntity<String> authenticationResponse = this.validateLogin(loginInfo);
        if (authenticationResponse.getStatusCode() != HttpStatus.OK) {
            return authenticationResponse;
        }

        try {
            Long userId = Objects.requireNonNull(this.getUserByUsername(loginInfo.username).getBody()).id;
            this.userService.delete(userId);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("User delete failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

