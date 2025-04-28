package org.example.wishlist6.Database;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//public class DatabaseConnectionTest {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Test
//    void testDatabaseConnectionAndSchema() {
//        // Verify database connection by executing a simple query
//        Integer result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'users'", Integer.class);
//
//        // Assert that the 'users' table exists (replace 'users' with a table from your schema.sql)
//        assertEquals(1, result, "The 'users' table should exist in the database.");
//    }
//}