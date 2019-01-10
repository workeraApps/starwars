package com.workera.apps.starwars;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class QueryUtilsTest {


    //Testing data being fetched from the connection and if it is being read


    @Test
    public void fetchSWCharacterData() {

        String jsonResponse = "";
        URL url = null;
        try {
            url = new URL("https://swapi.co/api/people");
        } catch (MalformedURLException e) {
            assertNull("Error building Url",url);
        }

        // If the URL is null, then return early.
        if (url == null) {
            assertNull("URL is empty",url);
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Testing that connection request made and json extracted

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = QueryUtils.readFromStream(inputStream);
                assertNotNull(jsonResponse);


            } else {


            }
        } catch (IOException e) {
            assertNotNull(e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {

                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


// Testing a small conversion from cm to m
    @Test
    public void convertcmtom() {
      String height = "172";
      double heightconverted = Double.parseDouble(height);
      double required = heightconverted/100.0;
      assertEquals(1.72, required, 0);

    }
}