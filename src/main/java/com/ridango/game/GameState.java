package com.ridango.game;

import lombok.Getter;

import java.util.List;

public class GameState {

    @Getter
    private int attempts = 5;
    @Getter
    private int score = 0;
    private List<Cocktail> cocktails;
    @Getter
    private Cocktail currentCocktail;
    private int currentCocktailIndex = 0;

    public GameState(List<Cocktail> cocktails) {
        this.cocktails = cocktails;
        currentCocktail = cocktails.get(currentCocktailIndex);
    }

    public void increaseScore() {
        score += attempts;
    }

    public void decreaseAttempts() {
        attempts--;
    }

    public void restoreAttempts() {
        attempts = 5;
    }

    public void nextCocktail() {
        currentCocktail = cocktails.get(++currentCocktailIndex);
    }
}
