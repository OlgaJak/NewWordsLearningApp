package com.newwordslearningapp.APIconnection;

import java.net.MalformedURLException;
import java.net.URL;

public class PictureAPIConnector {
//https://api.dictionaryapi.dev/api/v2/entries/en/<word>
    //https://dictionaryapi.dev/
    URL url = new URL("https://api.dictionaryapi.dev/api/v2/entries/en/hello");


    public PictureAPIConnector() throws MalformedURLException {
    }
}
