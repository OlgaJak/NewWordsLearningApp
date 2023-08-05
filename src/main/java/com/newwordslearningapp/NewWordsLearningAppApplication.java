package com.newwordslearningapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.newwordslearningapp.APIconnection.WordAPIConnector.getWordAndExplanationFormApi;
import static com.newwordslearningapp.APIconnection.WordAPIConnector.getWordFromApi;

@SpringBootApplication
public class NewWordsLearningAppApplication {

    public static void main(String[] args) {

        SpringApplication.run(NewWordsLearningAppApplication.class, args);


        //testing if 1 api giving random words is working
        try {
            String randomWord = getWordFromApi();
            // You can now use the 'randomWord' variable to do something with the word from the API.
            // For example, print it:

            String description = String.valueOf(getWordAndExplanationFormApi(randomWord));
            System.out.println("Random word from API: " + randomWord +description);
        } catch (Exception e) {
            e.printStackTrace();
        }










    }

}
