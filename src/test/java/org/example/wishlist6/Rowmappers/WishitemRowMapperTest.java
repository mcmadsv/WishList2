package org.example.wishlist6.Rowmappers;

import org.example.wishlist6.Module.Wishitem;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WishitemRowMapperTest {

    private final RowMapper<Wishitem> rowMapper = new WishitemRowMapper();

    @Test
    public void testMapRow() throws SQLException {
        // Arrange
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.getInt("wish_id")).thenReturn(1);
        Mockito.when(rs.getString("wish_name")).thenReturn("New Phone");
        Mockito.when(rs.getString("wish_description")).thenReturn("The latest model of phone");
        Mockito.when(rs.getString("wish_url")).thenReturn("http://newphone.com");
        Mockito.when(rs.getInt("wishlist_id")).thenReturn(1);

        // Act
        Wishitem wishitem = rowMapper.mapRow(rs, 1);

        // Assert
        assertEquals(1, wishitem.getWishItemId());
        assertEquals("New Phone", wishitem.getWishItemName());
        assertEquals("The latest model of phone", wishitem.getWishItemDescription());
        assertEquals("http://newphone.com", wishitem.getWishUrl());
        assertEquals(1, wishitem.getWishlistId());
    }
}
