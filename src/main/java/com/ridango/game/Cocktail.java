package com.ridango.game;

import lombok.Getter;

import java.util.List;

@Getter
public class Cocktail {

    private final String name;
    private final String instructions;
    private final String category;
    private final String glass;
    private final List<String> ingredients;

    public Cocktail(String name, String instructions, String category, String glass, List<String> ingredients) {
        this.name = name;
        this.instructions = instructions;
        this.category = category;
        this.glass = glass;
        this.ingredients = ingredients;
    }

    @Override
    public String toString() { // for developing purposes
        return String.format("Name: %s, Ingredients: %s", name, ingredients);
    }
}
