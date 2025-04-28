package org.example.wishlist6.Rowmappers;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.example.wishlist6.Module.User;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUserName(rs.getString("user_name"));
        user.setUserEmail(rs.getString("user_email"));
        user.setUserPassword(rs.getString("user_password"));
        return user;
    }
}

