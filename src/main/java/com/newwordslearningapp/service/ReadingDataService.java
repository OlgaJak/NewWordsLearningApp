package com.newwordslearningapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newwordslearningapp.APIconnection.WordAPIConnector;
import org.springframework.stereotype.Service;

@Service
public class ReadingDataService {

    private String word;
    private String definition;
    private String phonetic;
    private  String partOfSpeech;

    public void readData(String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(jsonString);

            JsonNode nodeWord = jsonNode.get(0).get("word");
            JsonNode meaningsNode = jsonNode.get(0).get("meanings");
            if (meaningsNode.isArray() && meaningsNode.size() > 0) {
                JsonNode definitionsNode = meaningsNode.get(0).get("definitions");
                if (definitionsNode.isArray() && definitionsNode.size() > 0 && nodeWord != null) {
                    word = nodeWord.asText();
                    definition = definitionsNode.get(0).get("definition").asText();
                    phonetic = jsonNode.get(0).get("phonetics").get(0).get("text").asText();
                    partOfSpeech = jsonNode.get(0).get("meanings").get(0).get("partOfSpeech").asText();

                    System.out.println("Word: " + word);
                    System.out.println("Part of speech: " + partOfSpeech);
                    System.out.println("Definition: " + definition);
                    System.out.println("Phonetic: " + phonetic);

                } else {
                    // If no definitions were found or nodeWord is null, fetch a new random word
                    System.out.println("No definitions found for the word: " + nodeWord.asText());
                    fetchNewRandomWordData();
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading data: " + e.getMessage());
            fetchNewRandomWordData();
        }
    }

    public void fetchNewRandomWordData() {
        try {
            String newRandomWord = WordAPIConnector.getWordFromApi();
            String newRandomWordData = WordAPIConnector.getWordAndExplanationFormApi(newRandomWord);
            readData(newRandomWordData); // Recursively call readData with new random word data
        } catch (Exception e) {
            System.err.println("Error fetching new random word data: " + e.getMessage());
            // Handle the exception appropriately or propagate it up the call stack
            fetchNewRandomWordData();
        }
    }

    public String getWord() {
        return word;
    }

    public String getDefinition() {
        return definition;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }
    private static String getFirstElement(JsonNode node, String... keys) {
        for (String key : keys) {
            JsonNode element = node.get(key);
            if (element != null && !element.isEmpty()) {
                return element.asText();
            }
        }
        return "";
    }
}
