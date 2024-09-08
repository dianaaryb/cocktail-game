package com.ridango.game;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    public Cocktail parseJson(JSONObject json) {
        String name = (String) json.get("strDrink");
        String category = (String) json.get("strCategory");
        String glass = (String) json.get("strGlass");
        String instructions = (String) json.get("strInstructions");
        List<String> ingredients = new ArrayList<>();
        for (int i = 1; ;i++) {
            String ingredient = (String) json.get("strIngredient" + i);
            if (ingredient == null) {
                break;
            }
            ingredients.add(ingredient);
        }
        return new Cocktail(name, instructions, category, glass, ingredients);
    }
}
