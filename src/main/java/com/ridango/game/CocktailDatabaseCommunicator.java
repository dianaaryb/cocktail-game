package com.ridango.game;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CocktailDatabaseCommunicator {

    private static final String RANDOM_COCKTAILS_URL = "https://www.thecocktaildb.com/api/json/v1/1/random.php";
    private static final String GET_REQUEST = "GET";

    public static List<Cocktail> getTenRandomCocktails() {
        List<Cocktail> cocktails = new ArrayList<>();
        try {
            URL url = new URL(RANDOM_COCKTAILS_URL);
            HttpURLConnection urlConnection;
            while(cocktails.size() < 10) {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod(GET_REQUEST);
                if(urlConnection.getResponseCode() != 200){
                    System.out.println("Error: Could not connect to API");
                    return null;
                }
                String jsonResponse = readApiResponse(urlConnection);
                if (jsonResponse == null || jsonResponse.isEmpty()) {
                    System.out.println("Error: Empty response from API");
                    return cocktails;
                }
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);
                JSONArray drinksArray = (JSONArray) jsonObject.get("drinks");
                if (drinksArray == null || drinksArray.isEmpty()) {
                    System.out.println("Error: No cocktails available from API");
                    return cocktails;
                }
                String drinkData = drinksArray.get(0).toString();
                JSONObject jsonObject2 = (JSONObject) parser.parse(drinkData);
                JsonParser.parseJson(jsonObject2, cocktails);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return cocktails;
    }

    private static String readApiResponse(HttpURLConnection apiConnection) {
        try {
            StringBuilder resultJson = new StringBuilder();
            Scanner scanner = new Scanner(apiConnection.getInputStream());
            while (scanner.hasNext()) {
                resultJson.append(scanner.nextLine());
            }
            scanner.close();
            return resultJson.toString();
        } catch (Exception e) {
            System.out.println("Failed to get response");;
        }
        return null;
    }
}
