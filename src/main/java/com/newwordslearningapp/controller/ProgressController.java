package com.newwordslearningapp.controller;

import com.newwordslearningapp.DTO.QuizScope;
import com.newwordslearningapp.entity.User;
import com.newwordslearningapp.entity.UserProgress;
import com.newwordslearningapp.service.UserProgressService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class ProgressController {
    private final UserProgressService userProgressService;

    @Autowired
    public ProgressController(UserProgressService userProgressService) {
        this.userProgressService = userProgressService;
    }

    @GetMapping("/progress")
    public String viewProgress(Model model, Principal principal, HttpSession session) {
        // Get the currently logged-in user's ID
        Long userId = getUserIdFromPrincipal(principal);

        // Retrieve the user's progress using UserProgressService
        UserProgress userProgress = userProgressService.getUserProgress(userId);

        // Add the progress data to the model for rendering
        model.addAttribute("userProgress", userProgress);

        // Retrieve the quiz results and score from the session
        List<QuizScope> quizResults = (List<QuizScope>) session.getAttribute("quizResults");
        Integer quizScore = (Integer) session.getAttribute("quizScore");
        int actualQuizScore = quizScore != null ? quizScore.intValue() : 0;

        // Add quiz results and score to the model
        model.addAttribute("quizResults", quizResults);
        model.addAttribute("quizScore", quizScore);

        return "progress"; // Create a progress.html Thymeleaf template for rendering
    }

    private Long getUserIdFromPrincipal(Principal principal) {
        if (principal != null) {
            // Assuming your User object has a getId() method to retrieve the user's ID
            User user = (User) principal; // No need for casting to Authentication
            return user.getId();
        }
        return null; // Handle the case where the principal is null or the user is not authenticated
    }
}
