package com.newwordslearningapp.controller;

import com.newwordslearningapp.service.ReadingDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import static com.newwordslearningapp.APIconnection.WordAPIConnector.getWordAndExplanationFormApi;
import static com.newwordslearningapp.APIconnection.WordAPIConnector.getWordFromApi;

@Controller
public class LearningController {

    private final ReadingDataService readingDataService;

    @Autowired
    public LearningController(ReadingDataService readingDataService) {
        this.readingDataService = readingDataService;
    }

    List<String> wordsList = new ArrayList<>();

    private int wordCount = 0;
    private boolean showNextButton = true;
    private boolean showTestButton = false;


    @GetMapping("/learning")
    public String getData(Model model) {
        // Testing if 1 API giving random words is working
        if (wordCount >= 5) {
            showNextButton = false;
            showTestButton = true;
        }

        if (showNextButton && wordCount < 5) {

            try {
                String randomWord = getWordFromApi();

                // You can now use the 'randomWord' variable to do something with the word from the API.
                // For example, get the JSON data and pass it to the view:
                String jsonString = getWordAndExplanationFormApi(randomWord);
                readingDataService.readData(jsonString);
                String upperCaseWord = readingDataService.getWord().substring(0, 1).toUpperCase() + readingDataService.getWord().substring(1);

                model.addAttribute("word", upperCaseWord);
                model.addAttribute("partOfSpeech", readingDataService.getPartOfSpeech());
                model.addAttribute("definition", readingDataService.getDefinition());
                model.addAttribute("phonetic", readingDataService.getPhonetic());

                // Add the current word to the previousWords list
                wordsList.add(upperCaseWord);
                model.addAttribute("previousWords", wordsList);

                wordCount++;

            } catch (Exception e) {
                e.printStackTrace();
                // Handle the exception appropriately or show an error message
            }
        }
        model.addAttribute("showNextButton", showNextButton);
        model.addAttribute("showTestButton", showTestButton);
        return "learning";
    }


    @PostMapping("/learning")
    public String showData(Model model, @RequestParam String action) {
        if ("next".equals(action)) {
            if (wordCount < 5) {
                try {
                    String randomWord = getWordFromApi();
                    String jsonString = getWordAndExplanationFormApi(randomWord);
                    readingDataService.readData(jsonString);
                    String upperCaseWord = readingDataService.getWord().substring(0, 1).toUpperCase() + readingDataService.getWord().substring(1);

                    if (!jsonString.contains("No Definitions Found")) {
                        model.addAttribute("word", upperCaseWord);
                        model.addAttribute("partOfSpeech", readingDataService.getPartOfSpeech());
                        model.addAttribute("definition", readingDataService.getDefinition());
                        model.addAttribute("phonetic", readingDataService.getPhonetic());

                        wordsList.add(upperCaseWord);
                        model.addAttribute("previousWords", wordsList);

                        wordCount++;
                    } else {

                        return showData(model, action);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        } else if ("test".equals(action)) {

            return "test";
        }

        if (wordCount >= 5) {
            showNextButton = false;
            showTestButton = true;
        }

        model.addAttribute("showNextButton", showNextButton);
        model.addAttribute("showTestButton", showTestButton);

        return "learning";
    }


    @GetMapping("test")
    public String TestPage() {
        return "test";
    }
}



