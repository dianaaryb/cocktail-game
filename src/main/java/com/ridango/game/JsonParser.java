package com.ridango.game;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    static void parseJson(JSONObject jsonObject2, List<Cocktail> cocktails) {
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
}
