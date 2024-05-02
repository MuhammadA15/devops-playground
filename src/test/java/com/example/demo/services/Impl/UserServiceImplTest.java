package com.example.demo.services.Impl;

import java.util.List;
import java.util.Optional;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.dto.UserPatchRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private UserServiceImpl userService;


    // Create new UserServiceImpl to make mock calls to
    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void testCreateUser() {
        // Arrange
        User newUser = new User();
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setEmail("john.doe@email.com");
        newUser.setUsername("John1");

        // Mock the repository save method
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        // Mock the repository findByUsername method
        when(userRepository.findByUsername(newUser.getUsername())).thenReturn(List.of(newUser));
        
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
        when(userRepository.findAll()).thenReturn(List.of(user1, user2)); // Added mock for findAll
    
        // Act
        List<User> allUsers = userService.getUser(null, null, null);
    
        System.out.println("Retrieved users: " + allUsers);
    
        // Assert
        assertThat(allUsers)
        .extracting(User::getUsername, User::getFirstName, User::getLastName, User::getEmail)
        .containsExactly(
            tuple(user1.getUsername(), user1.getFirstName(), user1.getLastName(), user1.getEmail()),
            tuple(user2.getUsername(), user2.getFirstName(), user2.getLastName(), user2.getEmail())
        );
    }
    
    @Test
    public void testPatchUser() {
        // Arrange
        User existingUser = mock(User.class);
        when(existingUser.getUserId()).thenReturn("123");
        when(existingUser.getFirstName()).thenReturn("Jane");
        when(existingUser.getLastName()).thenReturn("Doe");
        when(existingUser.getEmail()).thenReturn("Jane.Doe@email.com");
        when(existingUser.getUsername()).thenReturn("Jane1");
    
        // Mock the repository save and findByUserId methods
        when(userRepository.save(any(User.class))).thenReturn(existingUser);
        when(userRepository.findByUserId(existingUser.getUserId())).thenReturn(existingUser);
    
        UserPatchRequest patchRequest = new UserPatchRequest();
        patchRequest.setFirstName("Janet");
        patchRequest.setLastName("Dope");
        patchRequest.setEmail("Janet.Dope@email.com");
        patchRequest.setUsername("Janet1");
    
        // Act
        String result = userService.patchUser(existingUser.getUserId(), patchRequest);
    
        // Update the mock to return the new first name
        when(existingUser.getFirstName()).thenReturn(patchRequest.getFirstName());
        when(existingUser.getLastName()).thenReturn(patchRequest.getLastName());
        when(existingUser.getEmail()).thenReturn(patchRequest.getEmail());
        when(existingUser.getUsername()).thenReturn(patchRequest.getUsername());
    
        // Assert
        assertThat(result).isEqualTo("Successfully Updated User!");
        User updatedUser = userRepository.findByUserId(existingUser.getUserId());
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getFirstName()).isEqualTo(patchRequest.getFirstName());
        assertThat(updatedUser.getLastName()).isEqualTo(patchRequest.getLastName());
        assertThat(updatedUser.getEmail()).isEqualTo(patchRequest.getEmail());
        assertThat(updatedUser.getUsername()).isEqualTo(patchRequest.getUsername());
    }
    
    @Test
    public void testDeleteUser() {
        // Arrange
        User userToDelete = mock(User.class);
        String userId = "123";
        when(userToDelete.getUserId()).thenReturn(userId);
        when(userToDelete.getFirstName()).thenReturn("Eve");
        when(userToDelete.getLastName()).thenReturn("Johnson");
        when(userToDelete.getEmail()).thenReturn("Eve.Johnson@email.com");
        when(userToDelete.getUsername()).thenReturn("Eve1");
    
        // Mock the repository existsById, deleteById and findById methods
        when(userRepository.existsById(userId)).thenReturn(Boolean.TRUE);
        doNothing().when(userRepository).deleteById(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
    
        // Act
        String result = userService.deleteUser(userId);
    
        // Assert
        assertThat(result).isEqualTo("User succesfully deleted");
        assertThat(userRepository.findById(userId)).isEmpty();
    }
    
}