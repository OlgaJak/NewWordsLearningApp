package com.newwordslearningapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ReadingDataService {

    private String word;
    private String definition;
    private String phonetic;

    public void readData(String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(jsonString);

            JsonNode meaningsNodeWord = jsonNode.get(0).get("word");
            JsonNode meaningsNode = jsonNode.get(0).get("meanings");
            if (meaningsNode.isArray() && meaningsNode.size() > 0) {
                JsonNode definitionsNode = meaningsNode.get(0).get("definitions");
                if (definitionsNode.isArray() && definitionsNode.size() > 0) {
                    word = meaningsNodeWord.asText();
                    definition = definitionsNode.get(0).get("definition").asText();
                    phonetic = jsonNode.get(0).get("phonetics").get(0).get("text").asText();

                    System.out.println("Word: " + word);
                    System.out.println("Definition: " + definition);
                    System.out.println("Phonetic: " + phonetic);
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading data: " + e.getMessage());
            // Handle the exception appropriately or propagate it up the call stack
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
