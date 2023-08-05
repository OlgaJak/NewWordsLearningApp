package com.newwordslearningapp.JsonReader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newwordslearningapp.APIconnection.WordAPIConnector;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class ReadingData {
    public static String readData() throws Exception {
        String dictionary;

        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;

        String jsonString = String.valueOf(WordAPIConnector.getWordAndExplanationFormApi(WordAPIConnector.getWordFromApi()));
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonString);

        String word = jsonNode.get("word").asText();
        String definition = jsonNode.get("meanings").get("definitions").get("definition").asText();
        String phonetic = jsonNode.get("phonetic").asText();



        return dictionary = word + definition + phonetic;
    }
}
