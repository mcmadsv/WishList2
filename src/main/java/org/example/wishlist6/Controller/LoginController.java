package org.example.wishlist6.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.wishlist6.Module.User;
import org.example.wishlist6.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(
            @RequestParam String userEmail,
            @RequestParam String userPassword,
            HttpSession session,
            Model model
    ) {
        System.out.println("Login attempt: " + userEmail + " / " + userPassword);

        User user = userService.authenticateAndGetUser(userEmail, userPassword);

        if (user != null) {
            System.out.println("Login successful for user: " + user.getUserName());
            session.setAttribute("userId", user.getUserId());
            return "redirect:/home";
        } else {
            System.out.println("Login failed.");
            model.addAttribute("errorMessage", "Forkert email eller kodeord.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        System.out.println("Logging out success");
        return "redirect:/";
    }


    @GetMapping("/home")
    public String showHome(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/";
        }
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        model.addAttribute("userName", user.getUserName());
        return "home";
    }

}
