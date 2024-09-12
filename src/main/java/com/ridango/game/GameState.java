package com.ridango.game;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Random;

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
    @Getter @Setter
    private int roundNumber = 0;
    @Getter
    private String cocktailNameToDisplay;


    public GameState(List<Cocktail> cocktails, int highScore) {
        this.cocktails = cocktails;
        currentCocktail = cocktails.get(currentCocktailIndex);
        this.highScore = highScore;
        this.cocktailNameToDisplay = getHiddenCocktailName();
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
        cocktailNameToDisplay = getHiddenCocktailName();
    }

    public int cocktailCount() {
        return cocktails.size();
    }

    public void addNewCocktails(List<Cocktail> newCocktails) {
        currentCocktailIndex = 0;
        cocktails.addAll(newCocktails);
    }

    public void increaseRoundNumber() {
        roundNumber++;
    }

    public void revealLetter() {
        Random random = new Random();
        while (true) {
            int charPosition = random.nextInt(currentCocktail.getName().length());
            char charOnPosition = currentCocktail.getName().charAt(charPosition);
            if (cocktailNameToDisplay.charAt(charPosition) == '_') {
                cocktailNameToDisplay = cocktailNameToDisplay.substring(0, charPosition) + charOnPosition + cocktailNameToDisplay.substring(charPosition + 1);
                return;
            }
        }
    }

    private String getHiddenCocktailName() {
        return currentCocktail.getName().replaceAll("\\S", "_");
    }
}
