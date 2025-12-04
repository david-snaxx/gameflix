package com.example.gameflixbackend.usermanagement;

import com.example.gameflixbackend.usermanagement.model.User;
import com.example.gameflixbackend.usermanagement.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    UserService userService;

    User testUserOne;
    User testUserTwo;

    @BeforeEach
    void setup() {
        testUserOne = new User();
        testUserOne.username = "testUserOne";
        testUserOne.password = "passwordOne";

        testUserTwo = new User();
        testUserTwo.username = "testUserTwo";
        testUserTwo.password = "twoPassword";
    }

    @Test
    @DisplayName("Given user login data, when that data is saved, then that user login data should persist and be retrievable")
    void saveUser_savesNewUser() {
        this.userService.save(testUserOne);
        this.userService.save(testUserTwo);

        Optional<User> foundUserOne = userService.findByUsername(testUserOne.username);
        Optional<User> foundUserTwo = userService.findByUsername(testUserTwo.username);

        // we expect both users to exist now
        Assertions.assertTrue(foundUserOne.isPresent());
        Assertions.assertEquals(testUserOne.username, foundUserOne.get().username);
        Assertions.assertTrue(foundUserTwo.isPresent());
        Assertions.assertEquals(testUserTwo.username, foundUserTwo.get().username);
    }

    @Test
    @DisplayName("Given we target a known user, when a delete by id request is made, that user should stop existing " +
            "in storage")
    void deleteUser_deletesExistingUser() {
        this.userService.save(testUserOne);
        this.userService.save(testUserTwo);

        // we expect both test users to exist for now
        Optional<User> foundUserOne = this.userService.findByUsername(testUserOne.username);
        Optional<User> foundUserTwo = this.userService.findByUsername(testUserTwo.username);
        Assertions.assertTrue(foundUserOne.isPresent());
        Assertions.assertEquals(testUserOne.username, foundUserOne.get().username);
        Assertions.assertTrue(foundUserTwo.isPresent());
        Assertions.assertEquals(testUserTwo.username, foundUserTwo.get().username);

        // only user one should be gone now
        this.userService.delete(foundUserOne.get().id);
        Assertions.assertThrows(RuntimeException.class, () -> this.userService.findByUsername(testUserOne.username));
        Assertions.assertTrue(this.userService.findByUsername(testUserTwo.username).isPresent());

        // now user two should be gone as well
        this.userService.delete(foundUserTwo.get().id);
        Assertions.assertThrows(RuntimeException.class, () -> this.userService.findByUsername(testUserTwo.username));
    }

    @Test
    @DisplayName("Given a username has already been taken, when a new registration attempt is made with that username, " +
            "then there should be an exception thrown")
    void saveUser_throwsExceptionOnDuplicateUser() {
        this.userService.save(testUserOne); // this is defined in setup

        User duplicateUser = new User();
        duplicateUser.username = "testUserOne";
        duplicateUser.password = "duplicatePassword";

        // "testUserOne" username was already taken
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> this.userService.save(duplicateUser));
    }

    @Test
    @DisplayName("Given we search for a username that is not registered, when that search request is made, then an " +
            "exception should be thrown")
    void findByUsername_throwsExceptionOnUserNotFound() {
        this.userService.save(testUserOne);
        Optional<User> realUser = this.userService.findByUsername("testUserOne");

        Assertions.assertThrows(RuntimeException.class, () -> this.userService.findByUsername("nonExistentUser"));
        Assertions.assertTrue(realUser.isPresent()); // ensuring real users are still being found
    }

    @Test
    @DisplayName("Given we search for a user id that is not taken, when that search request is made, then an empty" +
            "user optional should be returned")
    void findById_throwsExceptionOnUserNotFound() {
        this.userService.save(testUserOne);
        Optional<User> realUser = this.userService.findById(testUserOne.id);
        Optional<User> nonExistentUser = this.userService.findById(12312312312L);

        Assertions.assertTrue(realUser.isPresent());
        Assertions.assertFalse(nonExistentUser.isPresent());
    }

    @Test
    @DisplayName("Given we request all registered users from the database, when that request is made, we should be given" +
            "a list containing all registered users")
    void findAll_providesAllUsers() {
        this.userService.save(testUserOne);
        this.userService.save(testUserTwo);

        List<User> users = this.userService.findAll();

        Assertions.assertFalse(users.isEmpty());
        Assertions.assertTrue(users.contains(testUserOne));
        Assertions.assertTrue(users.contains(testUserTwo));
    }

    @Test
    @DisplayName("Given we change the password of an existing user and the username AND id match, when we resave that " +
            "user, then the user information is updated in the database")
    void save_changesPasswordGivenMatchingIds() {
        this.userService.save(testUserOne);
        Long testUserOneId = this.userService.findByUsername(testUserOne.username).get().id; // for confirming id later

        // change the password
        this.testUserOne.password = "thisIsANewPassword";
        this.userService.save(testUserTwo);

        User alteredTestUserOne = this.userService.findByUsername(testUserOne.username).get();

        // the id didn't change so it should have allowed us to save
        Assertions.assertEquals(testUserOneId, alteredTestUserOne.id);
        // the new password should therefore be reflected
        Assertions.assertEquals(testUserOne.password, "thisIsANewPassword");
    }
}
