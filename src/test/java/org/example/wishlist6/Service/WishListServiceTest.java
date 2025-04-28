package org.example.wishlist6.Service;

import org.example.wishlist6.Module.Wishitem;
import org.example.wishlist6.Module.Wishlist;
import org.example.wishlist6.Repository.WishListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

public class WishListServiceTest {

    @Mock
    private WishListRepository wishListRepository;

    @InjectMocks
    private WishListService wishListService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddWishlist() {
        // Arrange: Create a Wishlist object with the attributes of wishListName, wishListID, and userId
        Wishlist wishlist = new Wishlist("New Wishlist", 1, 1);  // No createdAt attribute

        // Act: Call the addWishlist method of the service
        wishListService.addWishlist(wishlist);

        // Assert: Verify that the addWishlist method was called exactly once
        verify(wishListRepository, times(1)).addWishlist(wishlist);
    }

    @Test
    public void testGetAllWishlists() {
        // Arrange: Prepare a list of expected wishlists with the correct attributes (wishListName, wishListID, userId)
        List<Wishlist> expectedWishlists = List.of(
                new Wishlist("Wishlist 1", 1, 1),
                new Wishlist("Wishlist 2", 2, 1)
        );
        when(wishListRepository.getWishlistsByUserId(1)).thenReturn(expectedWishlists);

        // Act: Retrieve the wishlists for userId 1
        List<Wishlist> wishlists = wishListService.getWishlistsByUserId(1);

        // Assert: Verify that the getWishlistsByUserId method was called exactly once and check if the results match
        verify(wishListRepository, times(1)).getWishlistsByUserId(1);
        assert(wishlists.equals(expectedWishlists));  // Check if the wishlists match the expected list
    }

    @Test
    public void testSaveWishToWishlist() {
        // Arrange: Create a Wishitem object to be added to a wishlist
        Wishitem wish = new Wishitem("Wish Item", "Description", "url", 1);

        // Act: Add the wish to the wishlist with ID 1
        wishListService.addWishToWishlist(1, wish);

        // Assert: Verify that the addWish method of the repository was called exactly once
        verify(wishListRepository, times(1)).addWish(wish);
    }

    @Test
    public void testDeleteWishlistById() {
        // Arrange: Prepare a wishlist ID to be deleted
        int wishlistId = 1;

        // Act: Call the deleteWishlistById method
        wishListService.deleteWishlistById(wishlistId);

        // Assert: Verify that the deleteWishlistById method was called exactly once
        verify(wishListRepository, times(1)).deleteWishlistById(wishlistId);
    }
}
