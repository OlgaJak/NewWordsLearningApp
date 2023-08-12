package com.newwordslearningapp.controller;

import com.newwordslearningapp.APIconnection.WordAPIConnector;
import com.newwordslearningapp.entity.User;
import com.newwordslearningapp.entity.UserLearnedWords;
import com.newwordslearningapp.service.ReadingDataService;
import com.newwordslearningapp.service.UserLearnedWordsService;
import com.newwordslearningapp.service.WordExplanationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.newwordslearningapp.APIconnection.WordAPIConnector.getWordAndExplanationFormApi;
import static com.newwordslearningapp.APIconnection.WordAPIConnector.getWordFromApi;

@Controller
public class LearningController {

    private final ReadingDataService readingDataService;
    private final UserLearnedWordsService userLearnedWordsService;

    @Autowired
    public LearningController(ReadingDataService readingDataService, UserLearnedWordsService userLearnedWordsService) {
        this.readingDataService = readingDataService;
        this.userLearnedWordsService = userLearnedWordsService;

    }


    List<String> wordsList = new ArrayList<>();

    private int wordCount = 0;
    private boolean showNextButton = true;
    private boolean showTestButton = false;


    @GetMapping("/learning")
    public String getData(Model model, HttpSession session) {
        if (showNextButton && wordCount < 5) {
            try {
                String randomWord = getWordFromApi();
                String jsonString = getWordAndExplanationFormApi(randomWord);
                readingDataService.readData(jsonString);
                String upperCaseWord = readingDataService.getWord().substring(0, 1).toUpperCase() + readingDataService.getWord().substring(1);

                model.addAttribute("word", upperCaseWord);
                model.addAttribute("partOfSpeech", readingDataService.getPartOfSpeech());
                model.addAttribute("definition", readingDataService.getDefinition());
                model.addAttribute("phonetic", readingDataService.getPhonetic());

                wordsList.add(upperCaseWord);
                model.addAttribute("previousWords", wordsList);

                wordCount++;

                // UserLearnedWords object creation
                UserLearnedWords learnedWord = new UserLearnedWords();
                learnedWord.setWord(upperCaseWord);
                learnedWord.setDefinition(readingDataService.getDefinition());
                learnedWord.setPhonetic(readingDataService.getPhonetic());

                LocalDate currentDate = LocalDate.now();
                Date dateOfTask = Date.valueOf(currentDate);
                learnedWord.setDateOfTask(dateOfTask);

                learnedWord.setStatus(true);

                User loggedInUser = (User) session.getAttribute("loggedInUser");
                if (loggedInUser != null) {
                    learnedWord.setUser(loggedInUser);
                    userLearnedWordsService.saveLearnedWord(learnedWord);
                    System.out.println("Saving learned word for user: " + loggedInUser.getEmail());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (wordCount >= 5) {
            showNextButton = false;
            showTestButton = true;
        }

        model.addAttribute("showNextButton", showNextButton);
        model.addAttribute("showTestButton", showTestButton);
        return "learning";
    }


    @PostMapping("/learning")
    public String showData(Model model, @RequestParam String action, HttpSession session) {
        // Get user data through AuthenticationFilter
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if ("next".equals(action)) {
            if (wordCount < 5) {
                try {

                    String jsonString = WordAPIConnector.getRandomWordAndExplanationFromApi();
                    readingDataService.readData(jsonString);
                    String upperCaseWord = readingDataService.getWord().substring(0, 1).toUpperCase() + readingDataService.getWord().substring(1);

                    if (!jsonString.contains("No Definitions Found")) {
                        model.addAttribute("word", upperCaseWord);
                        model.addAttribute("partOfSpeech", readingDataService.getPartOfSpeech());
                        model.addAttribute("definition", readingDataService.getDefinition());
                        model.addAttribute("phonetic", readingDataService.getPhonetic());

                        wordsList.add(upperCaseWord);
                        model.addAttribute("previousWords", wordsList);

                        // UserLearnedWords object creation
                        UserLearnedWords learnedWord = new UserLearnedWords();
                        learnedWord.setWord(upperCaseWord);
                        learnedWord.setDefinition(readingDataService.getDefinition());
                        learnedWord.setPhonetic(readingDataService.getPhonetic());

                        LocalDate currentDate = LocalDate.now();
                        Date dateOfTask = Date.valueOf(currentDate);
                        learnedWord.setDateOfTask(dateOfTask);

                        learnedWord.setStatus(true);
                        learnedWord.setUser(loggedInUser);

                        // Save learned word through the service
                        userLearnedWordsService.saveLearnedWord(learnedWord);
                        System.out.println("Saving learned word for user: " + loggedInUser.getEmail());

                        wordCount++;
                    } else {
                        return showData(model, action, session);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if ("quiz".equals(action)) {
            return "quiz";
        }

        if (wordCount >= 5) {
            showNextButton = false;
            showTestButton = true;
        }

        model.addAttribute("showNextButton", showNextButton);
        model.addAttribute("showTestButton", showTestButton);

        return "learning";
    }


}



