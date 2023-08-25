package com.newwordslearningapp.controller;

import com.newwordslearningapp.entity.User;
import com.newwordslearningapp.entity.UserProgress;
import com.newwordslearningapp.service.UserProgressService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserProgressController {

    private UserProgressService userProgressService;

    @Autowired
    public UserProgressController(UserProgressService userProgressService) {
        this.userProgressService = userProgressService;
    }

    @PostMapping("/progress")
    public String userProgressPage(Model model, HttpSession session) {
        // Get user from session
        User user = (User) session.getAttribute("loggedInUser");

        // Get user progress data from database
        List<UserProgress> userProgressList = userProgressService.getUserProgressByUser(user);

        // Pass data to the model to display on the page
        model.addAttribute("userProgressList", userProgressList);

        return "progress";
    }
}
