package org.example.wishlist6.Repository;

import org.example.wishlist6.Module.Wishitem;
import org.example.wishlist6.Module.Wishlist;
import org.example.wishlist6.Rowmappers.WishitemRowMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

public class WishListRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private WishListRepository wishListRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveWish() {
        // Arrange: Setup the Wishitem to be saved
        Wishitem wishitem = new Wishitem("Wish Item 1", "Description", "url", 1);

        // Act: Call the saveWish method to save the wish item
        wishListRepository.addWish(wishitem);

        // Assert: Verify that JdbcTemplate's update method was called once with the correct SQL and parameters
        verify(jdbcTemplate, times(1)).update(
                anyString(), // Match any string (SQL query)
                eq(wishitem.getWishItemName()),       // Match the wish_name parameter
                eq(wishitem.getWishItemDescription()), // Match the wish_description parameter
                eq(wishitem.getWishUrl()),           // Match the wish_url parameter
                eq(wishitem.getWishlistId())         // Match the wishlist_id parameter
        );
    }

    @Test
    public void testDeleteWishlistById() {
        // Arrange: Define the wishlist ID to be deleted
        int wishlistId = 1;

        // Act: Call the deleteWishlistById method to delete the wishlist
        wishListRepository.deleteWishlistById(wishlistId);

        // Assert: Verify that JdbcTemplate's update method was called once with the correct SQL and parameters
        verify(jdbcTemplate, times(1)).update(
                anyString(),  // Match any string (SQL query)
                eq(wishlistId) // Match the wishlistId parameter specifically
        );
    }

    @Test
    public void testGetWishlistById() {
        // Arrange: Define the wishlist ID and the expected result
        int wishlistId = 1;
        Wishlist wishlist = new Wishlist("Wishlist 1", wishlistId, 1);

        // Act: Mock the queryForObject method to return the wishlist when it's called
        when(jdbcTemplate.queryForObject(
                anyString(),
                any(Object[].class),
                any(RowMapper.class)
        )).thenReturn(wishlist);

        // Act: Call the getWishlistById method to retrieve the wishlist by its ID
        Wishlist result = wishListRepository.getWishlistById(wishlistId);

        // Assert: Verify that queryForObject was called once with the expected arguments
        verify(jdbcTemplate, times(1)).queryForObject(
                anyString(),
                any(Object[].class),
                any(RowMapper.class)
        );
        // Assert the wishlist is not null and has the expected name
        assert(result != null);
        assert(result.getWishListName().equals("Wishlist 1"));
    }

    @Test
    public void testGetWishesByWishlistId() {
        // Arrange: Define the expected list of wishes for a given wishlist ID
        List<Wishitem> wishList = List.of(new Wishitem("Wish Item", "Description", "url", 1));
        when(jdbcTemplate.query(
                eq("SELECT * FROM wish WHERE wishlist_id = ?"),
                eq(new Object[]{1}),
                any(WishitemRowMapper.class)
        )).thenReturn(wishList);

        // Act: Call the getWishesByWishlistId method to get the wishes for a specific wishlist
        List<Wishitem> result = wishListRepository.getWishesByWishlistId(1);

        // Assert: Verify that jdbcTemplate.query was called with the correct parameters
        verify(jdbcTemplate).query(
                eq("SELECT * FROM wish WHERE wishlist_id = ?"),
                eq(new Object[]{1}),
                any(WishitemRowMapper.class)
        );
        // Assert that the result is not null and contains the expected wish
        assert(result != null);
        assert(result.size() > 0);
        assert(result.get(0).getWishItemName().equals("Wish Item"));
    }

    @Test
    public void testDeleteWishById() {
        // Arrange: Define the wish ID to be deleted
        int wishId = 1;

        // Act: Call the deleteWishById method to delete the wish
        wishListRepository.deleteWishById(wishId);

        // Assert: Verify that JdbcTemplate's update method was called once with the correct SQL and parameters
        verify(jdbcTemplate, times(1)).update(
                anyString(), // Match any string (SQL query)
                eq(wishId)   // Match the wishId parameter specifically
        );
    }

    @Test
    public void testUpdateWish() {
        // Arrange: Create a wish item with updated details
        Wishitem wishitem = new Wishitem("Updated Wish", "Updated Description", "updated_url", 1);
        wishitem.setWishItemId(1);  // Assuming `setWishItemId()` is available to set the wish ID

        // Act: Call the updateWish method to update the wish
        wishListRepository.updateWish(wishitem);

        // Assert: Verify that JdbcTemplate's update method was called once with the correct SQL and parameters
        verify(jdbcTemplate, times(1)).update(
                anyString(),  // Match any string (SQL query)
                eq(wishitem.getWishItemName()), // Match the updated wish_name
                eq(wishitem.getWishItemDescription()), // Match the updated wish_description
                eq(wishitem.getWishUrl()), // Match the updated wish_url
                eq(wishitem.getWishItemId()) // Match the updated wish_id
        );
    }

    @Test
    public void testUpdateWishlist() {
        // Arrange: Create a wishlist with updated details
        Wishlist wishlist = new Wishlist("Updated Wishlist", 1, 1);

        // Act: Call the updateWishlist method to update the wishlist
        wishListRepository.updateWishlist(wishlist);

        // Assert: Verify that JdbcTemplate's update method was called once with the correct SQL and parameters
        verify(jdbcTemplate, times(1)).update(
                anyString(), // Match any string (SQL query)
                eq(wishlist.getWishListName()), // Match the updated wishlist_name
                eq(wishlist.getWishListID()) // Match the updated wishlist_id
        );
    }
}
