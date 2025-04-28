package org.example.wishlist6.Service;

import org.example.wishlist6.Module.User;
import org.example.wishlist6.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("John Doe", "john@example.com", "password123", 1);
    }

    @Test
    void testAddUser() {
        // Arrange
        User user = new User("testUser", "test@example.com", "password123", 0);
        int generatedId = 1;

        when(userRepository.addUser(any(User.class))).thenReturn(generatedId);

        // Act
        userService.addUser(user);

        // Assert
        verify(userRepository, times(1)).addUser(user);
    }

    @Test
    public void testGetAllUsers() {
        // Arrange
        User user2 = new User("Jane Smith", "jane@example.com", "password456", 2);
        List<User> users = Arrays.asList(user, user2);
        when(userRepository.getAllUsers()).thenReturn(users);

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(user));
        assertTrue(result.contains(user2));
    }

    @Test
    public void testDeleteUserById() {
        // Arrange
        doNothing().when(userRepository).deleteUserById(anyInt());

        // Act
        userService.deleteUserById(1);

        // Assert
        verify(userRepository, times(1)).deleteUserById(1);
    }

    @Test
    public void testUpdateUser() {
        // Arrange
        doNothing().when(userRepository).updateUser(any(User.class));

        // Act
        userService.updateUser(user);

        // Assert
        verify(userRepository, times(1)).updateUser(any(User.class));
    }

    @Test
    public void testGetUserById() {
        // Arrange
        int userId = 1;
        when(userRepository.getUserById(userId)).thenReturn(user);

        // Act
        User result = userService.getUserById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(user.getUserId(), result.getUserId());
        assertEquals(user.getUserName(), result.getUserName());
        assertEquals(user.getUserEmail(), result.getUserEmail());
        assertEquals(user.getUserPassword(), result.getUserPassword());
    }

    @Test
    public void testAuthenticateUser_InvalidEmail() {
        // Arrange
        when(userRepository.getUserByEmail(anyString())).thenReturn(null);

        // Act
        User result = userService.authenticateAndGetUser("invalid@example.com", "password123");

        // Assert
        assertNull(result);
    }

    @Test
    public void testAuthenticateUser_InvalidPassword() {
        // Arrange
        User user = new User("John Doe", "john@example.com", "correctpassword", 1);
        when(userRepository.getUserByEmail("john@example.com")).thenReturn(user);

        // Act
        User result = userService.authenticateAndGetUser("john@example.com", "wrongpassword");

        // Assert
        assertNull(result);
    }

    @Test
    public void testAuthenticateUser_ValidCredentials() {
        // Arrange
        User user = new User("John Doe", "john@example.com", "password123", 1);
        when(userRepository.getUserByEmail("john@example.com")).thenReturn(user);

        // Act
        User result = userService.authenticateAndGetUser("john@example.com", "password123");

        // Assert
        assertNotNull(result);
        assertEquals(user.getUserId(), result.getUserId());
        assertEquals(user.getUserName(), result.getUserName());
        assertEquals(user.getUserEmail(), result.getUserEmail());
    }
}
