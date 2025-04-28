package org.example.wishlist6.Repository;

import org.example.wishlist6.Module.Wishitem;
import org.example.wishlist6.Module.Wishlist;
import org.example.wishlist6.Rowmappers.WishitemRowMapper;
import org.example.wishlist6.Rowmappers.WishlistRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishListRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Gemmer ønskeseddel og returnerer genereret ID
    public void addWishlist(Wishlist wishlist) {
        String sql = "INSERT INTO wishlist (wishlist_name, user_id) VALUES (?, ?)";

        System.out.println("Executing SQL: " + sql + " with values " + wishlist.getWishListName() + ", " + wishlist.getUserId());

        jdbcTemplate.update(sql, wishlist.getWishListName(), wishlist.getUserId());

        System.out.println("Wishlist saved in database.");
    }

    // Gemmer et ønske til en ønskeseddel
    public void addWish(Wishitem wish) {
        String sql = "INSERT INTO wish (wish_name, wish_description, wish_url, wishlist_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                wish.getWishItemName(),
                wish.getWishItemDescription(),
                wish.getWishUrl(),
                wish.getWishlistId()
        );
    }

    public Wishlist getWishlistById(int id) {
        String sql = "SELECT * FROM wishlist WHERE wishlist_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new WishlistRowMapper());
    }
    public Wishitem getWishById(int wishId) {
        String sql = "SELECT * FROM wish WHERE wish_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{wishId}, new WishitemRowMapper()); // Assuming you have a WishitemRowMapper
    }
    public List<Wishlist> getWishlistsByUserId(Integer userId) {
        String sql = "SELECT * FROM wishlist WHERE user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, new WishlistRowMapper());
    }

    public List<Wishitem> getWishesByWishlistId(int wishlistId) {
        String sql = "SELECT * FROM wish WHERE wishlist_id = ?";
        return jdbcTemplate.query(sql, new Object[]{wishlistId}, new WishitemRowMapper());
    }

    public void deleteWishlistById(int id) {
        String sql = "DELETE FROM wishlist WHERE wishlist_id = ?";
        jdbcTemplate.update(sql, id);
    }
    public void deleteWishById(int wishId) {
        String sql = "DELETE FROM wish WHERE wish_id = ?";
        jdbcTemplate.update(sql, wishId);
    }

    public void updateWish(Wishitem wish) {
        String sql = "UPDATE wish SET wish_name = ?, wish_description = ?, wish_url = ? WHERE wish_id = ?";
        jdbcTemplate.update(sql,
                wish.getWishItemName(),
                wish.getWishItemDescription(),
                wish.getWishUrl(),
                wish.getWishItemId());
    }
    public void updateWishlist(Wishlist wishlist) {
        String sql = "UPDATE wishlist SET wishlist_name = ? WHERE wishlist_id = ?";
        jdbcTemplate.update(sql, wishlist.getWishListName(), wishlist.getWishListID());
    }




}
