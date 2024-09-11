package com.ridango.game;

import lombok.Getter;

import java.util.List;
@Getter
public class Cocktail {

    private String name;
    private String instructions;
    private String category;
    private String glass;
    private List<String> ingredients;

    public Cocktail(String name, String instructions, String category, String glass, List<String> ingredients) {
        this.name = name;
        this.instructions = instructions;
        this.category = category;
        this.glass = glass;
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, Ingredients: %s", name, ingredients);
    }
}
