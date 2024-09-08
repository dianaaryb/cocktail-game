package com.ridango.game;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CocktailDatabaseCommunicator {

    private static final String RANDOM_COCKTAILS_URL = "https://www.thecocktaildb.com/api/json/v1/1/random.php";
    private static final String GET_REQUEST = "GET";

    public static List<Cocktail> getTenRandomCocktails() throws IOException, ParseException {
        List<Cocktail> cocktails = new ArrayList<>();
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
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);
            String drinkData = ((JSONArray) jsonObject.get("drinks")).get(0).toString();
            JSONObject jsonObject2 = (JSONObject) parser.parse(drinkData);
            String name = (String) jsonObject2.get("strDrink");
            String category = (String) jsonObject2.get("strCategory");
            String glass = (String) jsonObject2.get("strGlass");
            String instructions = (String) jsonObject2.get("strInstructions");
            List<String> ingredients = new ArrayList<>();
            for (int i = 1; ;i++) {
                String ingredient = (String) jsonObject2.get("strIngredient" + i);
                if (ingredient == null) {
                    break;
                }
                ingredients.add(ingredient);
            }
            cocktails.add(new Cocktail(name, instructions, category, glass, ingredients));
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
