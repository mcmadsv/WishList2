package org.example.wishlist6.Repository;

import org.example.wishlist6.Module.User;
import org.example.wishlist6.Rowmappers.UserRowMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("John Doe", "john@example.com", "password123", 1);
    }

    @Test
    public void testGetUserById() {
        // Arrange
        int userId = 1;
        String sql = "SELECT * FROM userlist WHERE user_id = ?";

        // Mocking the JdbcTemplate queryForObject method to return a user when called
        when(jdbcTemplate.queryForObject(eq(sql), any(Object[].class), any(UserRowMapper.class)))
                .thenReturn(user);

        // Act
        User result = userRepository.getUserById(userId);

        // Assert
        verify(jdbcTemplate, times(1)).queryForObject(eq(sql), any(Object[].class), any(UserRowMapper.class));
        assertNotNull(result);
        assertEquals(user.getUserId(), result.getUserId());
        assertEquals(user.getUserName(), result.getUserName());
        assertEquals(user.getUserEmail(), result.getUserEmail());
        assertEquals(user.getUserPassword(), result.getUserPassword());
    }

    @Test
    public void testAddUser() {
        // Arrange
        User user = new User("John Doe", "john@example.com", "password", 0);

        // Mock JdbcTemplate update method
        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class)))
                .thenAnswer(invocation -> {
                    KeyHolder keyHolder = invocation.getArgument(1);

                    // Simulate generated key by adding it to the keyHolder's keyList
                    Map<String, Object> generatedKey = new HashMap<>();
                    generatedKey.put("id", 123);  // Simulating the generated ID from the DB
                    keyHolder.getKeyList().add(generatedKey);

                    return 1;  // Simulate successful update (1 row affected)
                });

        // Act
        int generatedUserId = userRepository.addUser(user);

        // Assert
        assertEquals(123, generatedUserId);  // Verify the returned user ID is correct
        verify(jdbcTemplate, times(1)).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
    }

    @Test
    public void testDeleteUserById() {
        // Arrange
        int userId = 1;
        String sql = "DELETE FROM userlist WHERE user_id = ?";

        // Act
        userRepository.deleteUserById(userId);

        // Assert
        verify(jdbcTemplate, times(1)).update(eq(sql), eq(userId));
    }

    @Test
    public void testUpdateUser() {
        // Arrange
        String sql = "UPDATE userlist SET user_name = ?, user_email = ?, user_password = ? WHERE user_id = ?";
        when(jdbcTemplate.update(eq(sql), anyString(), anyString(), anyString(), eq(user.getUserId()))).thenReturn(1);

        // Act
        userRepository.updateUser(user);

        // Assert
        verify(jdbcTemplate, times(1)).update(eq(sql), anyString(), anyString(), anyString(), eq(user.getUserId()));
    }
}
