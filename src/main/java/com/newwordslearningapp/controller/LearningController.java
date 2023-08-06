package com.newwordslearningapp.controller;

import com.newwordslearningapp.service.ReadingDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.newwordslearningapp.APIconnection.WordAPIConnector.getWordAndExplanationFormApi;
import static com.newwordslearningapp.APIconnection.WordAPIConnector.getWordFromApi;

@Controller
public class LearningController {

    private final ReadingDataService readingDataService;

    @Autowired
    public LearningController(ReadingDataService readingDataService) {
        this.readingDataService = readingDataService;
    }

    @GetMapping("/learning")
    public String getData(Model model) {
        // Testing if 1 API giving random words is working
        try {
            String randomWord = getWordFromApi();

            // You can now use the 'randomWord' variable to do something with the word from the API.
            // For example, get the JSON data and pass it to the view:
            String jsonString = getWordAndExplanationFormApi(randomWord);
            readingDataService.readData(jsonString);

            model.addAttribute("word", readingDataService.getWord());
            model.addAttribute("partOfSpeech", readingDataService.getPartOfSpeech());
            model.addAttribute("definition", readingDataService.getDefinition());
            model.addAttribute("phonetic", readingDataService.getPhonetic());

        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception appropriately or show an error message
        }
        return "learning";
    }

    @PostMapping("/learning")
    public String showData(Model model, @RequestParam String randomWord) {
        // Here, you can handle the form submission
        // For example, if you want to get the data for the submitted word:

        try {
            String jsonString = getWordAndExplanationFormApi(randomWord);
            readingDataService.readData(jsonString);

            model.addAttribute("word", readingDataService.getWord());
            model.addAttribute("partOfSpeech", readingDataService.getPartOfSpeech());
            model.addAttribute("definition", readingDataService.getDefinition());
            model.addAttribute("phonetic", readingDataService.getPhonetic());

        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception appropriately or show an error message
        }

        return "learning";
    }
}
