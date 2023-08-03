package com.newwordslearningapp.controller;


import com.newwordslearningapp.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.newwordslearningapp.service.UserService;

@Controller
public class RegistrationController {
    private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration-form";
    }

    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        // Check if there are validation errors
        if (bindingResult.hasErrors()) {
            return "registration-form"; // Return to the registration form with error messages
        }

        // Check if password and passwordConfirm match
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "passwordConfirm.mismatch", "Passwords no not match");
            return "registration-form"; // Return to the registration form with error message
        }

        userService.registerUser(user);
        // Add the username to the model
        model.addAttribute("name", user.getName());
        return "redirect:/user-page"; // Redirect to the user page after successful registration
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        return "redirect:/home";
    }
}
