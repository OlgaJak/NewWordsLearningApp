package com.newwordslearningapp.JsonReader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReadingData {
    public static void readData(String jsonString) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(jsonString);

            String word = jsonNode.get(0).get("word").asText();

            JsonNode meaningsNode = jsonNode.get(0).get("meanings");
            if (meaningsNode.isArray() && meaningsNode.size() > 0) {
                JsonNode definitionsNode = meaningsNode.get(0).get("definitions");
                if (definitionsNode.isArray() && definitionsNode.size() > 0) {
                    String definition = definitionsNode.get(0).get("definition").asText();
                    String phonetic = jsonNode.get(0).get("phonetics").get(0).get("text").asText();

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
