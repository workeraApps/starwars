package com.workera.apps.starwars;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    /** Tag for the log messages */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Creating a private constructor
     */
    private QueryUtils() {
    }

    /**
     * Query the STAR WARS API and return a list of {@link SWCharacter} objects.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static List<SWCharacter> fetchSWCharacterData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "There was a problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link SWCharacter}
        List<SWCharacter> swCharacters = extractSWCharacters(jsonResponse);

        // Return a list of {@link SWCharacter}
        return swCharacters;

    }

    /**
     * Returns a URL object using a string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "There was a problem building the URL ", e);
        }
        return url;
    }

    /**
     * Making an HTTP request to the given URL and returning a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000); // milliseconds
            urlConnection.setConnectTimeout(15000); //milliseconds
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            // For a successful response (200)
            // then read the input stream and parse the response.
            // Otherwise log error code

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the star wars JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Converting the {@link InputStream} into a String containing the entire JSON.
     */
    public static String readFromStream(InputStream inStream) throws IOException {
        StringBuilder entireoutput = new StringBuilder();
        if (inStream != null) {
            InputStreamReader inReader = new InputStreamReader(inStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inReader);
            String line = reader.readLine();
            while (line != null) {
                entireoutput.append(line);
                line = reader.readLine();
            }
        }
        return entireoutput.toString();
    }


    /**
     * Return a list of {@link SWCharacter} objects that has been built up from
     * parsing a JSON response.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<SWCharacter> extractSWCharacters(String swJSON) {


        ArrayList<SWCharacter> swcharacters = new ArrayList<>();

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(swJSON)) {
            return null;
        }

        try {

            // Create a JSONObject from the SWAPI_JSON_RESPONSE string
            JSONObject baseJson = new JSONObject(swJSON);

            // Extract the JSONArray associated with the key called "results",
            // which represents a list of characters.
            JSONArray swArray = baseJson.getJSONArray("results");

            // For each character in the Array, create an {@link SWCharacter} object
            for (int i = 0; i < swArray.length(); i++) {

                // Get a single character at position i within the list of characters
                JSONObject currentcharacter= swArray.getJSONObject(i);

                // Extract the value for the key called "height"
                double height = convertcmtom(currentcharacter.getString("height"));

                // Extract the value for the key called "mass"
                double mass = Double.parseDouble(currentcharacter.getString("mass"));

                // Extract the value for the key called "name"
                String name = currentcharacter.getString("name");

                // Extract the value for the key called "created"
                String date_time = currentcharacter.getString("created");

                // Convert the date and string to a readable format from the given (ISO 8601)
                String replaced = date_time.replace("T", " at ");
                String dtime = replaced.replace("Z", "");
               // StringBuilder dt = new StringBuilder(replaced);
                //dt.deleteCharAt(date_time.length()-1);





                // Create a new {@link SWCharacter} object with the mass in kg, height in m, date and time,
                // from the JSON response.
                SWCharacter character = new SWCharacter( mass, height, name, dtime);

                // Add the new {@link SWCharacter} to the list of star war characters.
                swcharacters.add(character);
            }

        } catch (JSONException e) {

            Log.e("QueryUtils", "There was a problem parsing the JSON results", e);
        }

        // Return the list of star war characters
        return swcharacters;
    }


    public static Double convertcmtom(String height){

        double heightincm = Double.parseDouble(height);
        return (heightincm/100.0);

    }

}
