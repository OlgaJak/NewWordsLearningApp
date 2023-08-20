package com.newwordslearningapp.APIconnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WordAPIConnector {

    public static String getWordFromApi() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://random-words5.p.rapidapi.com/getRandom"))
                .header("X-RapidAPI-Key", "44454aefe7msh72364ecc04f75ecp146b32jsnb3e6a0977a7e")
                .header("X-RapidAPI-Host", "random-words5.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        return response.body();
    }

    public static String getWordAndExplanationFormApi(String word)throws Exception{
        URL url = new URL("https://api.dictionaryapi.dev/api/v2/entries/en/"+word);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        con.disconnect();

        return response.toString();
    }

    // Method for generating a new random word if current work explanation does not exist
    public static String getRandomWordAndExplanationFromApi() throws Exception {
        String randomWord = getWordFromApi();
        return getWordAndExplanationFormApi(randomWord);
    }

}
