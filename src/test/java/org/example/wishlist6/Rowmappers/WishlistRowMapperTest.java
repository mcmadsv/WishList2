package org.example.wishlist6.Rowmappers;

import org.example.wishlist6.Module.Wishlist;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WishlistRowMapperTest {

    private final RowMapper<Wishlist> rowMapper = new WishlistRowMapper();

    @Test
    public void testMapRow() throws SQLException {
        // Arrange
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.getInt("wishlist_id")).thenReturn(1);
        Mockito.when(rs.getString("wishlist_name")).thenReturn("My Wishlist");

        // Act
        Wishlist wishlist = rowMapper.mapRow(rs, 1);

        // Assert
        assertEquals(1, wishlist.getWishListID());
        assertEquals("My Wishlist", wishlist.getWishListName());
    }
}
