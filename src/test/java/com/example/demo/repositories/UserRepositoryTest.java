package com.example.demo.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.Impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Test
    public void testFindByUsername() {
        // Arrange
        User user1 = new User();
        user1.setUsername("testUser");
        List<User> expectedUsers = Arrays.asList(user1);

        when(userRepository.findByUsername("testUser")).thenReturn(expectedUsers);

        // Act
        List<User> actualUsers = userService.getUser("testUser", null, null);

        // Assert
        assertEquals(expectedUsers, actualUsers);
        verify(userRepository, times(1)).findByUsername("testUser");
    }

    @Test
    public void testFindByFirstName() {
        //Arrange
        User user2 = new User();
        user2.setFirstName("testFirstName");
        List<User> expectedUsers = Arrays.asList(user2);

        when(userRepository.findByFirstName("testFirstName")).thenReturn(expectedUsers);

        //Act
        List<User> actualUsers = userService.getUser(null, "testFirstName", null);

        //Assert
        assertEquals(expectedUsers, actualUsers);
        verify(userRepository, times(1)).findByFirstName("testFirstName");
    }

    @Test
    public void testFindByLastName() {
        //Arange
        User user3 = new User();
        user3.setLastName("testLastName");
        List<User> expectedUsers = Arrays.asList(user3);

        when(userRepository.findByLastName("testLastName")).thenReturn(expectedUsers);

        //Act
        List<User> actualUsers = userService.getUser(null, null, "testLastName");

        //Assert
        assertEquals(expectedUsers, actualUsers);
        verify(userRepository, times(1)).findByLastName("testLastName");
    }
}