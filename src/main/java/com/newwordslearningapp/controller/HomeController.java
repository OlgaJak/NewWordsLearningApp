package com.newwordslearningapp.controller;

import com.newwordslearningapp.entity.User;
import com.newwordslearningapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final UserService userService;
@Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String showHomePage(Model model) {
        model.addAttribute("user", new User());
        return "home"; // Return the name of your HTML template
    }

    @PostMapping("/register")
    public String registrationForm(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "home";
        }
        if (userService.findByEmail(user.getEmail()) != null) {
            bindingResult.rejectValue("email", "email.duplicate", "Email is already registered");
            return "home"; // Return to the registration form with error message
        }

        userService.registerUser(user);
        session.setAttribute("loggedInUser", user);
        session.setAttribute("userDisplayName", user.getName());
        return "redirect:/user-page";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        // Delete attributes from the session
        session.removeAttribute("loggedInUser");

        // Closes the session
        session.invalidate();

        return "redirect:/home";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, Model model) {
        // Handle login logic here
        User user = userService.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("userDisplayName", user.getName());
            session.setAttribute("loggedInUser", user);

            return "redirect:/user-page";

        } else {
            model.addAttribute("errorMessage", "Invalid email or password.");
            return "home";
        }

    }
}