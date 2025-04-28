package org.example.wishlist6.Repository;

import org.example.wishlist6.Module.User;
import org.example.wishlist6.Module.Wishlist;
import org.example.wishlist6.Rowmappers.UserRowMapper;
import org.example.wishlist6.Rowmappers.WishlistRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM userlist";

        RowMapper<User> rowMapper = new UserRowMapper();

        return jdbcTemplate.query(sql, rowMapper);
    }

    public int addUser(User user) {
        String sql = "INSERT INTO userlist (user_name, user_email, user_password) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getUserEmail());
            ps.setString(3, user.getUserPassword());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }
    public void deleteUserById(int id) {
        String sql = "DELETE FROM userlist WHERE user_id = ?";
        jdbcTemplate.update(sql, id);
    }
    public User getUserById(int userId) {
        String sql = "SELECT * FROM userlist WHERE user_id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{userId}, new UserRowMapper());

    }
    public void updateUser(User user) {
        String sql = "UPDATE userlist SET user_name = ?, user_email = ?, user_password = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, user.getUserName(), user.getUserEmail(), user.getUserPassword(), user.getUserId());
    }

public User getUserByEmail(String email) {
        try {
            String sql = "SELECT * from userlist WHERE user_email = ? ";
            return jdbcTemplate.queryForObject(sql, new Object[] {email}, new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
}

}
