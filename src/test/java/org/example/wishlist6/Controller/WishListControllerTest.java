//
package org.example.wishlist6.Controller;

import org.example.wishlist6.Module.Wishlist;
import org.example.wishlist6.Module.Wishitem;
import org.example.wishlist6.Service.WishListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class WishListControllerTest {

    @Mock
    private WishListService wishListService;

    @InjectMocks
    private WishListController wishListController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(wishListController).build();
    }

    @Test
    public void testShowCreateWishlistForm() throws Exception {
        // Arrange: Create a mock user and set the URL.
        MvcResult result = mockMvc.perform(get("/wishlist/create")
                        .with(user("testuser").password("password").roles("USER")))
                .andReturn();

        // Act: Perform the request and capture the result.
        System.out.println("Response Status: " + result.getResponse().getStatus());
        System.out.println("Redirected to: " + result.getResponse().getHeader("Location"));

        // Assert: Validate the response (just log the output here).
    }

    @Test
    public void testAddWishlist() throws Exception {
        // Arrange: Set up wishlist name to be passed to the controller.
        String wishListName = "New Wishlist";

        // Act: Perform the POST request to create a new wishlist.
        mockMvc.perform(post("/wishlist/create")
                        .param("wishListName", wishListName))

                // Assert: Verify the redirection status after successful creation.
                .andExpect(status().is3xxRedirection())  // Expect a redirection
                .andExpect(redirectedUrl("/"));  // Expect redirection to the root URL ("/")
    }

    @Test
    public void testViewWishlist() throws Exception {
        // Arrange: Create mock wishlist and wish items.
        int wishlistId = 1;
        Wishlist wishlist = new Wishlist("Wishlist 1", wishlistId, 1);
        List<Wishitem> wishitems = List.of(new Wishitem("Wish 1", "Description", "url", wishlistId));

        when(wishListService.getWishlistById(wishlistId)).thenReturn(wishlist);
        when(wishListService.getWishesByWishlistId(wishlistId)).thenReturn(wishitems);

        // Act: Perform GET request to view the specific wishlist.
        mockMvc.perform(get("/wishlist/{id}", wishlistId))

                // Assert: Verify that the response status is 3xx redirection if it's a redirect.
                .andExpect(status().is3xxRedirection())  // Expect a redirect if needed

                // If it's a redirect, check the redirection URL.
                .andExpect(redirectedUrl("/login"));  // Assuming redirect goes to /login (adjust based on your app logic)
    }

    @Test
    public void testDeleteWishlist() throws Exception {
        // Arrange: Set up the wishlist ID to delete.
        int wishlistId = 1;

        // Act: Perform POST request to delete the wishlist.
        mockMvc.perform(post("/wishlist/delete/{id}", wishlistId))

                // Assert: Verify that the response is a redirection after deletion.
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist"));

        // Assert: Ensure that the delete method was invoked once for the given wishlistId.
        verify(wishListService, times(1)).deleteWishlistById(wishlistId);
    }

    @Test
    public void testDeleteWish() throws Exception {
        // Arrange: Set up wishlist ID and wish ID for deletion.
        int wishlistId = 1;
        int wishId = 1;

        // Act: Perform GET request to delete a specific wish.
        mockMvc.perform(get("/wishlist/{wishlistId}/delete-wish/{wishId}", wishlistId, wishId))

                // Assert: Verify that the response is a redirection after deletion.
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist/" + wishlistId));

        // Assert: Verify that the delete wish method was called once for the given wishId.
        verify(wishListService, times(1)).deleteWishById(wishId);
    }

    @Test
    public void testEditWish() throws Exception {
        // Arrange: Mock the session to simulate a logged-in user
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", 1); // Simulate a logged-in user with ID 1

        // Mock the WishListService to return a valid Wishitem
        Wishitem wish = new Wishitem("Wish 1", "Description", "url", 1);
        when(wishListService.getWishById(1)).thenReturn(wish);

        // Act: Perform the GET request to edit the wish with the mocked session
        MvcResult result = mockMvc.perform(get("/wishlist/1/edit-wish/1")
                        .session(session)) // Add the mocked session
                .andExpect(status().isOk()) // Expect status 200, because this is the edit form
                .andExpect(view().name("edit-wish")) // Ensure it returns the correct view name for editing
                .andExpect(model().attributeExists("wish")) // Ensure "wish" attribute exists in the model
                .andReturn();

        // Assert: Verify the correct view name and the wish details in the model
        assertThat(result.getModelAndView().getViewName()).isEqualTo("edit-wish");
        assertThat(result.getModelAndView().getModel().get("wish")).isEqualTo(wish);
    }

    @Test
    public void testUpdateWish() throws Exception {
        // Arrange: Create the initial wish that we will update
        int wishlistId = 1;
        int wishId = 1;

        // Mock the WishListService to do nothing when updateWishItem is called
        doNothing().when(wishListService).updateWishItem(any(Wishitem.class));

        // Act: Perform POST request to update the wish with parameters
        mockMvc.perform(post("/wishlist/{wishlistId}/edit-wish/{wishId}", wishlistId, wishId)
                        .param("wishItemName", "Updated Wish")
                        .param("wishItemDescription", "Updated Description")
                        .param("wishUrl", "url")
                        .param("wishlistId", String.valueOf(wishlistId)))  // Pass the fields as request parameters
                .andExpect(status().is3xxRedirection())  // Expect redirection after the update
                .andExpect(redirectedUrl("/wishlist/" + wishlistId));  // Verify redirection URL

        // Assert: Verify that the updateWishItem method was called with the correct wish
        ArgumentCaptor<Wishitem> captor = ArgumentCaptor.forClass(Wishitem.class);
        verify(wishListService, times(1)).updateWishItem(captor.capture());

        // Verify the captured wish item matches the expected values
        Wishitem capturedWish = captor.getValue();
        assertThat(capturedWish).isNotNull(); // Ensure the captured wish is not null
        assertThat(capturedWish.getWishItemName()).isEqualTo("Updated Wish");
        assertThat(capturedWish.getWishItemDescription()).isEqualTo("Updated Description");
        assertThat(capturedWish.getWishUrl()).isEqualTo("url");
        assertThat(capturedWish.getWishlistId()).isEqualTo(wishlistId);
    }

    @Test
    public void testEditWishlist() throws Exception {
        // Arrange: Create a mock session with a userId attribute
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", 1); // Simulate a logged-in user with ID 1

        int wishlistId = 1;
        Wishlist wishlist = new Wishlist("Wishlist 1", wishlistId, 1);
        when(wishListService.getWishlistById(wishlistId)).thenReturn(wishlist);

        // Act: Perform GET request to edit the specific wishlist with the mocked session
        mockMvc.perform(get("/wishlist/{id}/edit", wishlistId).session(session))
                .andDo(print()) // Print the request and response for debugging
                // Assert: Verify that the response status, view name, and model attributes are correct
                .andExpect(status().isOk())
                .andExpect(view().name("edit-wishlist"))
                .andExpect(model().attribute("wishlist", wishlist))
                .andExpect(model().attribute("wishlistId", wishlistId));
    }

    @Test
    public void testUpdateWishlist() throws Exception {
        // Arrange: Set up the wishlist data for updating.
        int wishlistId = 1;
        Wishlist wishlist = new Wishlist("Updated Wishlist", wishlistId, 1);

        // Act: Perform POST request to update the wishlist.
        mockMvc.perform(post("/wishlist/{id}/edit", wishlistId)
                        .flashAttr("wishlist", wishlist))

                // Assert: Verify that the response is a redirection after updating.
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist"));

        // Assert: Ensure that the update wishlist method was called once for the given wishlist.
        verify(wishListService, times(1)).updateWishlist(wishlist);
    }

    @Test
    public void testAddWishToWishList() throws Exception {
        // Arrange: Set up the wishlist ID and wish details
        int wishlistId = 1;
        Wishitem wish = new Wishitem("New Wish", "Description", "http://example.com", wishlistId);

        // Mock the WishListService to do nothing when addWishToWishlist is called
        doNothing().when(wishListService).addWishToWishlist(eq(wishlistId), any(Wishitem.class));

        // Act: Perform POST request to add the wish to the wishlist
        mockMvc.perform(post("/wishlist/{id}/add-wish", wishlistId)
                        .param("wishItemName", wish.getWishItemName())
                        .param("wishItemDescription", wish.getWishItemDescription())
                        .param("wishUrl", wish.getWishUrl())
                        .param("wishlistId", String.valueOf(wishlistId)))
                .andExpect(status().is3xxRedirection())  // Expect redirection after adding the wish
                .andExpect(redirectedUrl("/wishlist/" + wishlistId));  // Verify redirection URL

        // Assert: Verify that the addWishToWishlist method was called with the correct parameters
        verify(wishListService, times(1)).addWishToWishlist(eq(wishlistId), any(Wishitem.class));
    }
}
