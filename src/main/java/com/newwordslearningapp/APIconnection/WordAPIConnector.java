package com.newwordslearningapp.APIconnection;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WordAPIConnector {

    public static String getWordFromApi() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://random-words5.p.rapidapi.com/getRandom"))
                .header("X-RapidAPI-Key", "0cbfe2a365mshd5ffef77528df59p1516acjsn76ccbc42f94c")
                .header("X-RapidAPI-Host", "random-words5.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        return response.body();
    }

}



//https://api.dictionaryapi.dev/api/v2/entries/en/<word>
//https://dictionaryapi.dev/
//    URL url = new URL("https://api.dictionaryapi.dev/api/v2/entries/en/hello");