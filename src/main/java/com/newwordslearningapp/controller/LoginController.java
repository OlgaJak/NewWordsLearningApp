package com.newwordslearningapp.controller;

import com.newwordslearningapp.entity.User;
import com.newwordslearningapp.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login-form";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("username") String email,
                               @RequestParam("password") String password,
                               HttpSession session, Model model) {
        // Perform user authentication manually (e.g., check credentials against the database)
        User user = userService.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            // User is authenticated, store user info in the session
            session.setAttribute("loggedInUser", user);
            // Add the user's email to the model
            model.addAttribute("email", email);
            return "redirect:/user-page";
        } else {
            // Invalid credentials, handle error or redirect back to login page
            model.addAttribute("errorMessage", "Invalid email or password.");
            return "login-form";
        }
    }
}