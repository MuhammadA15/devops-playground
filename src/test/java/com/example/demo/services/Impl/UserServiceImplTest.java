package com.example.demo.services.Impl;

import java.util.List;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.dto.UserPatchRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class UserServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void testCreateUser() {
        // Arrange
        User newUser = new User();
        newUser.setFirstName("John");
        newUser.setLastName("Doe");

        // Act
        userService.createUser(newUser);

        // Assert
        User savedUser = userRepository.findById(newUser.getUserId()).orElse(null);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getFirstName()).isEqualTo("John");
        assertThat(savedUser.getLastName()).isEqualTo("Doe");
    }

    @Test
    public void testGetUser() {
        // Arrange
        User user1 = new User();
        user1.setFirstName("Alice");
        user1.setLastName("Smith");
        User user2 = new User();
        user2.setFirstName("Bob");
        user2.setLastName("Johnson");
        userRepository.save(user1);
        userRepository.save(user2);

        // Act
        List<User> allUsers = userService.getUser(null, null, null);

        // Assert
        assertThat(allUsers).hasSize(2);
        assertThat(allUsers).contains(user1, user2);
    }

    @Test
    public void testPatchUser() {
        // Arrange
        User existingUser = new User();
        existingUser.setFirstName("Jane");
        existingUser.setLastName("Doe");
        userRepository.save(existingUser);

        UserPatchRequest patchRequest = new UserPatchRequest();
        patchRequest.setFirstName("Janet");

        // Act
        String result = userService.patchUser(existingUser.getUserId(), patchRequest);

        // Assert
        assertThat(result).isEqualTo("Successfully Updated User!");
        User updatedUser = userRepository.findById(existingUser.getUserId()).orElse(null);
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getFirstName()).isEqualTo("Janet");
    }

    @Test
    public void testDeleteUser() {
        // Arrange
        User userToDelete = new User();
        userToDelete.setFirstName("Eve");
        userToDelete.setLastName("Johnson");
        userRepository.save(userToDelete);

        // Act
        String result = userService.deleteUser(userToDelete.getUserId());

        // Assert
        assertThat(result).isEqualTo("User successfully deleted");
        assertThat(userRepository.findById(userToDelete.getUserId())).isEmpty();
    }
}
