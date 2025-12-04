package com.example.gameflixbackend.usermanagement;

import com.example.gameflixbackend.usermanagement.model.User;
import com.example.gameflixbackend.usermanagement.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

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
    @Transactional
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
    @Transactional
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
    @Transactional
    void saveUser_throwsExceptionOnDuplicateUser() {
        this.userService.save(testUserOne); // this is defined in setup

        User duplicateUser = new User();
        duplicateUser.username = "testUserOne";
        duplicateUser.password = "duplicatePassword";

        // "testUserOne" username was already taken
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> this.userService.save(duplicateUser));
    }

    @Test
    @Transactional
    void findByUsername_throwsExceptionOnUserNotFound() {
        this.userService.save(testUserOne);
        Optional<User> realUser = this.userService.findByUsername("testUserOne");

        Assertions.assertThrows(RuntimeException.class, () -> this.userService.findByUsername("nonExistentUser"));
        Assertions.assertTrue(realUser.isPresent()); // ensuring real users are still being found
    }

    @Test
    @Transactional
    void findById_throwsExceptionOnUserNotFound() {
        this.userService.save(testUserOne);
        Optional<User> realUser = this.userService.findById(testUserOne.id);
        Optional<User> nonExistentUser = this.userService.findById(12312312312L);

        Assertions.assertTrue(realUser.isPresent());
        Assertions.assertFalse(nonExistentUser.isPresent());
    }
}
