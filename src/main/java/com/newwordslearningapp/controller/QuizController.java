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

        for (UserLearnedWords userLearnedWords:fiveWordsForQuiz){
            List<UserLearnedWords>clonedFiveWordsForQuiz = new ArrayList<>(fiveWordsForQuiz);
            Collections.shuffle(clonedFiveWordsForQuiz);
            List<String> result = this.wordExplanationService.getFourExplanationsForWord(clonedFiveWordsForQuiz, userLearnedWords);
            quizOptions.add(new QuizScope(userLearnedWords.getWord(),result));

        }

//        System.out.println(quizWord);// Add the quiz result to the model to display it in the view
//        model.addAttribute("quizResult", result);
//        model.addAttribute("quizWord", quizWord.getWord());



         //.getWord can be changed to aa the word itself object

        model.addAttribute("quizOptions", quizOptions);

        return "quiz";
    }


}

