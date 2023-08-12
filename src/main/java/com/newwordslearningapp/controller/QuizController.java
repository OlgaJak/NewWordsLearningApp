package com.newwordslearningapp.controller;

import com.newwordslearningapp.entity.User;
import com.newwordslearningapp.service.WordExplanationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class QuizController {
    private final WordExplanationService wordExplanationService;
@Autowired
    public QuizController(WordExplanationService wordExplanationService) {
        this.wordExplanationService = wordExplanationService;
    }



        //  User user = new User(2L,"user2", "email2@email.com", "Password2", null);
        // this.wordExplanationService.getFourExplanationsForWord("Wiry", user);
        //  System.out.println("Here we display the result:");
        // List<String> result = this.wordExplanationService.getFourExplanationsForWord("Wiry", user);

        @GetMapping("/quiz")
        public String quizPage(HttpSession session, Model model) {
            // Retrieve the user from the session
            User user = (User) session.getAttribute("loggedInUser");
            if (user == null) {
                // Handle the case when user is not logged in
                return "redirect:/login-form"; // Redirect to login page or handle as needed
            }

            List<String> result = this.wordExplanationService.getFourExplanationsForWord(user.getId());

            // Add the quiz result to the model to display it in the view
            model.addAttribute("quizResult", result);

            return "quiz";
        }


}

