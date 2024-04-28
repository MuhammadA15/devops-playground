package com.example.demo.services.Impl;

import java.util.List;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.dto.UserPatchRequest;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void testCreateUser() {
        // Arrange
        User newUser = new User();
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setEmail("john.doe@email.com");
        newUser.setUsername("John1");
    
        // Act
        userService.createUser(newUser);
    
        // Assert
        List<User> retrievedUsers = userService.getUser(newUser.getUsername(), null, null);
        assertThat(retrievedUsers).isNotEmpty(); // Ensure at least one user is retrieved
        User savedUser = retrievedUsers.get(0);
        assertThat(savedUser.getUsername()).isEqualTo(newUser.getUsername());
        assertThat(savedUser.getFirstName()).isEqualTo(newUser.getFirstName());
        assertThat(savedUser.getLastName()).isEqualTo(newUser.getLastName());
        assertThat(savedUser.getEmail()).isEqualTo(newUser.getEmail());
    }
    
    @Test
    public void testGetUser() {
    // Arrange
    User user1 = mock(User.class);
    when(user1.getFirstName()).thenReturn("Alice");
    when(user1.getLastName()).thenReturn("Smith");
    when(user1.getEmail()).thenReturn("Alice.Smith@email.com");
    when(user1.getUsername()).thenReturn("Alice1");

    User user2 = mock(User.class);
    when(user2.getFirstName()).thenReturn("Bob");
    when(user2.getLastName()).thenReturn("Johnson");
    when(user2.getEmail()).thenReturn("bob.johnson@email.com");
    when(user2.getUsername()).thenReturn("Bob1");

    // Mock
    when(userRepository.findByFirstName("Alice")).thenReturn(List.of(user1));
    when(userRepository.findByLastName("Smith")).thenReturn(List.of(user1));
    when(userRepository.findByLastName("Johnson")).thenReturn(List.of(user2));

    // Act
    List<User> allUsers = userService.getUser(null, null, null);

    System.out.println("Retrieved users: " + allUsers);

    // Assert
    assertThat(allUsers).containsExactly(user1, user2);
    }
    
    @Test
    public void testPatchUser() {
        // Arrange
        User existingUser = new User();
        existingUser.setFirstName("Jane");
        existingUser.setLastName("Doe");
        existingUser.setEmail("Jane.Doe@email.com");
        existingUser.setUsername("Jane1");
        userRepository.save(existingUser);

        // Mock
        when(userRepository.findById(anyString())).thenReturn(java.util.Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        UserPatchRequest patchRequest = new UserPatchRequest();
        patchRequest.setFirstName("Janet");
        patchRequest.setLastName("Dope");
        patchRequest.setEmail("Janet.Dope@email.com");
        patchRequest.setUsername("Janet1");

        // Act
        String result = userService.patchUser(existingUser.getUserId(), patchRequest);

        // Assert
        assertThat(result).isEqualTo("Successfully Updated User!");
        User updatedUser = userRepository.findById(existingUser.getUserId()).orElse(null);
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getFirstName()).isEqualTo("Janet");
        assertThat(updatedUser.getLastName()).isEqualTo("Dope");
        assertThat(updatedUser.getEmail()).isEqualTo("Janet.Dope@email.com");
        assertThat(updatedUser.getUsername()).isEqualTo("Janet1");

        userRepository.delete(updatedUser);
    }

    @Test
    public void testDeleteUser() {
        // Arrange
        User userToDelete = new User();
        userToDelete.setFirstName("Eve");
        userToDelete.setLastName("Johnson");
        userToDelete.setEmail("Eve.Johnson@email.com");
        userToDelete.setUsername("Eve1");
        userRepository.save(userToDelete);

        // Mock
        when(userRepository.existsById(anyString())).thenReturn(Boolean.valueOf(true));
        doNothing().when(userRepository).deleteById(anyString());

        // Act
        String result = userService.deleteUser(userToDelete.getUserId());

        // Assert
        assertThat(result).isEqualTo("User succesfully deleted");
        assertThat(userRepository.findById(userToDelete.getUserId())).isEmpty();
    }
}
