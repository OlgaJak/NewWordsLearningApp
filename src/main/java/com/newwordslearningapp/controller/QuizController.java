package com.newwordslearningapp.controller;

import com.newwordslearningapp.DTO.QuizScope;
import com.newwordslearningapp.entity.User;
import com.newwordslearningapp.entity.UserLearnedWords;
import com.newwordslearningapp.service.WordExplanationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

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

        List<UserLearnedWords> fiveWordsForQuiz = this.wordExplanationService.getFiveWordsForQuiz(user.getId());
        // we take the word object so we can track the id and other info to identify which word was selected
        // we can still return the word string below in attribute
//        UserLearnedWords quizWord = this.wordExplanationService.getWordForQuiz(fiveWordsForQuiz);


        ArrayList<QuizScope> quizOptions = new ArrayList<>();

        for (UserLearnedWords userLearnedWords : fiveWordsForQuiz) {
            List<UserLearnedWords> clonedFiveWordsForQuiz = new ArrayList<>(fiveWordsForQuiz);
            Collections.shuffle(clonedFiveWordsForQuiz);
            List<String> result = wordExplanationService.getFourExplanationsForWord(clonedFiveWordsForQuiz, userLearnedWords);
            String correctAnswer = userLearnedWords.getDefinition(); // Get the correct answer
            quizOptions.add(new QuizScope(userLearnedWords.getWord(), result, correctAnswer));
        }

        model.addAttribute("quizOptions", quizOptions);

        return "quiz";
    }


    @PostMapping("/submitQuiz")
    public String submitQuiz(@RequestParam Map<String, String> quizOptions, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login-form";
        }

        int score = calculateScore(quizOptions, user.getId());
        session.setAttribute("quizOptions", getQuizOptionsFromSession(session)); // Store quiz options in session

        // Redirect to the quiz result page with the score parameter
        return "redirect:/quiz-result?score=" + score;
    }

    @GetMapping("/quiz-result")
    public String quizResult(@RequestParam(name = "score", required = true) int score, HttpSession session, Model model) {
        // Retrieve quizOptions from session and set it in the model
        List<QuizScope> quizOptions = getQuizOptionsFromSession(session);
        model.addAttribute("quizOptions", quizOptions);
        model.addAttribute("score", score);

        return "quiz-result"; // Return the quiz result page
    }



    private int calculateScore(Map<String, String> quizOptions, Long userId) {
        int score = 0;

        List<UserLearnedWords> fiveWordsForQuiz = wordExplanationService.getFiveWordsForQuiz(userId);

        for (UserLearnedWords word : fiveWordsForQuiz) {
            String selectedOption = quizOptions.get(String.valueOf(word.getId()));
            if (selectedOption != null && selectedOption.equals(word.getDefinition())) {
                score++;
            }
        }

        return score;
    }

    private List<QuizScope> getQuizOptionsFromSession(HttpSession session) {
        List<QuizScope> quizOptions = new ArrayList<>();
        User user = (User) session.getAttribute("loggedInUser");

        if (user != null) {
            List<UserLearnedWords> fiveWordsForQuiz = wordExplanationService.getFiveWordsForQuiz(user.getId());

            for (UserLearnedWords userLearnedWords : fiveWordsForQuiz) {
                List<UserLearnedWords> clonedFiveWordsForQuiz = new ArrayList<>(fiveWordsForQuiz);
                Collections.shuffle(clonedFiveWordsForQuiz);
                List<String> result = wordExplanationService.getFourExplanationsForWord(clonedFiveWordsForQuiz, userLearnedWords);
                quizOptions.add(new QuizScope(userLearnedWords.getWord(), result, userLearnedWords.getDefinition()));
            }
        }

        return quizOptions;
    }








}

