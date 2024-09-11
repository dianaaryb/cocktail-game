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
    @Getter
    private int currentCocktailIndex = 0;
    @Getter
    private int highScore;

    public GameState(List<Cocktail> cocktails, int highScore) {
        this.cocktails = cocktails;
        currentCocktail = cocktails.get(currentCocktailIndex);
        this.highScore = highScore;
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

    public int cocktailCount() {
        return cocktails.size();
    }

    public void addNewCocktails(List<Cocktail> newCocktails) {
        currentCocktailIndex = 0;
        cocktails.addAll(newCocktails);
    }
}
