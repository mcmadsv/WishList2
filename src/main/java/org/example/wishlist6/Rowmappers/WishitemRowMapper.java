package org.example.wishlist6.Rowmappers;

import org.example.wishlist6.Module.Wishitem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WishitemRowMapper implements RowMapper<Wishitem> {

    @Override
    public Wishitem mapRow(ResultSet rs, int rowNum) throws SQLException {
        Wishitem wish = new Wishitem();
        wish.setWishItemId(rs.getInt("wish_id"));
        wish.setWishItemName(rs.getString("wish_name"));
        wish.setWishItemDescription(rs.getString("wish_description"));
        wish.setWishUrl(rs.getString("wish_url"));
        wish.setWishlistId(rs.getInt("wishlist_id"));
        return wish;
    }
}
