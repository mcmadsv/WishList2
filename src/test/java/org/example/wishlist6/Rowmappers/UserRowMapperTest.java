package org.example.wishlist6.Rowmappers;

import org.example.wishlist6.Module.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRowMapperTest {

    private final RowMapper<User> rowMapper = new UserRowMapper();

    @Test
    public void testMapRow() throws SQLException {
        // Arrange
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.getInt("user_id")).thenReturn(1);
        Mockito.when(rs.getString("user_name")).thenReturn("John Doe");
        Mockito.when(rs.getString("user_email")).thenReturn("john.doe@example.com");
        Mockito.when(rs.getString("user_password")).thenReturn("password123");

        // Act
        User user = rowMapper.mapRow(rs, 1);

        // Assert
        assertEquals(1, user.getUserId());
        assertEquals("John Doe", user.getUserName());
        assertEquals("john.doe@example.com", user.getUserEmail());
        assertEquals("password123", user.getUserPassword());
    }
}
