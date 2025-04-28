package org.example.wishlist6.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.wishlist6.Module.User;
import org.example.wishlist6.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testShowCreateForm() throws Exception {
        // Act
        mockMvc.perform(get("/user/create"))
                // Assert
                .andExpect(status().isOk())
                .andExpect(view().name("add-user"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void testAddUser() throws Exception {
        // Act
        mockMvc.perform(post("/user/create")
                        .param("userName", "Alice Johnson")
                        .param("userEmail", "alice@example.com")
                        .param("userPassword", "password789"))
                // Assert
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        // Arrange: Assuming user deletion should return a redirection (302 status) after a successful delete.
        int userId = 1;  // Example user ID to delete
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", userId);  // Simulating a logged-in session.

        // Act: Perform a DELETE request to the user deletion endpoint
        mockMvc.perform(post("/user/delete/{id}", userId)  // Use POST, not DELETE, because of the way the controller is mapped.
                        .session(session))  // Include the session for authentication
                // Assert: Expect HTTP 3xx redirection (302) to the login page after successful deletion
                .andExpect(status().is3xxRedirection())  // Expecting 302 redirect to the home page
                .andExpect(view().name("redirect:/"));  // Ensure the redirect goes to the homepage
    }


    @Test
    void testShowEditUserFormWithValidSession() throws Exception {
        int userId = 1;
        User user = new User("John Doe", "john@example.com", "password123", userId);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", userId);

        // Arrange
        when(userService.getUserById(userId)).thenReturn(user);

        // Act
        mockMvc.perform(get("/user/{id}/edit", userId).session(session))
                // Assert
                .andExpect(status().isOk())
                .andExpect(view().name("edit-user"))
                .andExpect(model().attribute("user", user));
    }

    @Test
    void testShowEditUserFormWithInvalidSession() throws Exception {
        int userId = 1;

        // Act
        mockMvc.perform(get("/user/{id}/edit", userId))
                // Assert
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    void testUpdateUserWithValidSession() throws Exception {
        int userId = 1;
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", userId);

        // Act
        mockMvc.perform(post("/user/{id}/edit", userId)
                        .param("userName", "Updated Name")
                        .param("userEmail", "updated@example.com")
                        .param("userPassword", "newpassword")
                        .session(session))
                // Assert
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/home"));
    }

    @Test
    void testUpdateUserWithInvalidSession() throws Exception {
        int userId = 1;

        // Act
        mockMvc.perform(post("/user/{id}/edit", userId)
                        .param("userName", "Intruder")
                        .param("userEmail", "intruder@example.com")
                        .param("userPassword", "wrongpass"))
                // Assert
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }
}