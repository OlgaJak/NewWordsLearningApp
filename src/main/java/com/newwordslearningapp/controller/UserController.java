package com.newwordslearningapp.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.newwordslearningapp.entity.User;

@Controller
public class UserController {

    @GetMapping("/user-page")
    public String showUserPage(Model model, HttpSession session) {
        String userDisplayName = (String) session.getAttribute("userDisplayName");

        if (userDisplayName != null) {
            model.addAttribute("displayName", userDisplayName);
        }

        return "user-page";
    }
}
