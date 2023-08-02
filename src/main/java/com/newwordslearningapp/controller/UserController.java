package com.newwordslearningapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.newwordslearningapp.entity.User;

@Controller
public class UserController {

    @GetMapping("/user-page")
    public String showUserPage(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("name", user.getName());
        return "user-page";
    }
}
