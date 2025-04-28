package org.example.wishlist6.Rowmappers;

import org.example.wishlist6.Module.Wishlist;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WishlistRowMapper implements RowMapper<Wishlist> {

    @Override
    public Wishlist mapRow(ResultSet rs, int rowNum) throws SQLException {
        Wishlist wishlist = new Wishlist();
        wishlist.setWishListID(rs.getInt("wishlist_id"));
        wishlist.setWishListName(rs.getString("wishlist_name"));
        wishlist.setUserId(rs.getInt("user_id"));
        return wishlist;
    }
}
