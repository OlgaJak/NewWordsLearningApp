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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //Creating lists for saving user data
    private Map<User, List<String>> userWordsListMap = new HashMap<>();
    private Map<User, Integer> userWordCountMap = new HashMap<>();
    private Map<User, Boolean> userShowNextButtonMap = new HashMap<>();
    private Map<User, Boolean> userShowTestButtonMap = new HashMap<>();


    @GetMapping("/learning")
    public String getData(Model model, HttpSession session) {
        // Check if user is authenticated
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        // Initializing user data
        if (!userWordsListMap.containsKey(loggedInUser)) {
            userWordsListMap.put(loggedInUser, new ArrayList<>());
            userWordCountMap.put(loggedInUser, 0);
            userShowNextButtonMap.put(loggedInUser, true);
            userShowTestButtonMap.put(loggedInUser, false);
        }

        // Get user data
        List<String> wordsList = userWordsListMap.get(loggedInUser);
        int wordCount = userWordCountMap.get(loggedInUser);
        boolean showNextButton = userShowNextButtonMap.get(loggedInUser);
        boolean showTestButton = userShowTestButtonMap.get(loggedInUser);

        if (showNextButton && wordCount < 5) {
            try {
                String randomWord = getWordFromApi();
                String jsonString = getWordAndExplanationFormApi(randomWord);
                readingDataService.readData(jsonString);
                String upperCaseWord = readingDataService.getWord().substring(0, 1).toUpperCase() + readingDataService.getWord().substring(1);

                model.addAttribute("showNextButton", true);
                model.addAttribute("showTestButton", false);

                model.addAttribute("word", upperCaseWord);
                model.addAttribute("partOfSpeech", readingDataService.getPartOfSpeech());
                model.addAttribute("definition", readingDataService.getDefinition());
                model.addAttribute("phonetic", readingDataService.getPhonetic());

                wordsList.add(upperCaseWord);
                model.addAttribute("previousWords", wordsList);

                wordCount++;

                // Update user data
                userWordCountMap.put(loggedInUser, wordCount);
                userShowNextButtonMap.put(loggedInUser, showNextButton);
                userShowTestButtonMap.put(loggedInUser, showTestButton);

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

                // Save learned word
                userLearnedWordsService.saveLearnedWord(learnedWord);
                System.out.println("Saving learned word for user: " + loggedInUser.getEmail());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (wordCount >= 5) {
            model.addAttribute("showNextButton", false);
            model.addAttribute("showTestButton", true);
        }

        return "learning";
    }


    @PostMapping("/learning")
    public String showData(Model model, @RequestParam String action, HttpSession session) {
        // Check if user is authenticated
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        // Initializing user data
        if (!userWordsListMap.containsKey(loggedInUser)) {
            userWordsListMap.put(loggedInUser, new ArrayList<>());
            userWordCountMap.put(loggedInUser, 0);
            userShowNextButtonMap.put(loggedInUser, true);
            userShowTestButtonMap.put(loggedInUser, false);
        }

        // Get user data
        List<String> wordsList = userWordsListMap.get(loggedInUser);
        int wordCount = userWordCountMap.get(loggedInUser);
        boolean showNextButton = userShowNextButtonMap.get(loggedInUser);
        boolean showTestButton = userShowTestButtonMap.get(loggedInUser);

        if ("next".equals(action)) {
            if (wordCount < 5) {
                try {
                    String jsonString = WordAPIConnector.getRandomWordAndExplanationFromApi();
                    readingDataService.readData(jsonString);
                    String upperCaseWord = readingDataService.getWord().substring(0, 1).toUpperCase() + readingDataService.getWord().substring(1);

                    if (!jsonString.contains("No Definitions Found")) {
                        model.addAttribute("showNextButton", true);
                        model.addAttribute("showTestButton", false);

                        model.addAttribute("word", upperCaseWord);
                        model.addAttribute("partOfSpeech", readingDataService.getPartOfSpeech());
                        model.addAttribute("definition", readingDataService.getDefinition());
                        model.addAttribute("phonetic", readingDataService.getPhonetic());

                        wordsList.add(upperCaseWord);
                        model.addAttribute("previousWords", wordsList);

                        wordCount++;

                        // Update user data
                        userWordCountMap.put(loggedInUser, wordCount);
                        userShowNextButtonMap.put(loggedInUser, showNextButton);
                        userShowTestButtonMap.put(loggedInUser, showTestButton);

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

                        // Save learned word
                        userLearnedWordsService.saveLearnedWord(learnedWord);
                        System.out.println("Saving learned word for user: " + loggedInUser.getEmail());
                    } else {
                        return "redirect:/learning";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if ("quiz".equals(action)) {
            return "quiz";
        }

        if (wordCount >= 5) {
            model.addAttribute("showNextButton", false);
            model.addAttribute("showTestButton", true);
        }

        return "learning";
    }

}





