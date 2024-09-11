package com.ridango.game;

import lombok.Getter;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class UI {

    Scanner scanner = new Scanner(System.in);
    Runnable startEventListener;
    Runnable skipEventListener;
    Runnable cocktailNameEntryEventListener;
    @Getter
    private String userResponse;

    public void displayGameStart(){
        for (int i = 0; i < 3; i++) {
            System.out.println();
        }
        System.out.println("***GUESS THE COCKTAIL***");

        System.out.println("START - s");
        System.out.println("QUIT - q");

        userResponse = scanner.nextLine();
        startEventListener.run();
    }

    public void drawField(String displayableName, GameState state, Engine engine) {
//        for (int i = 0; i < 3; i++) {
//            System.out.println();
//        }
        System.out.println();
        System.out.println("ˆˆˆˆˆPress 5 for skip roundˆˆˆˆˆ");
        System.out.println("ˆˆˆˆˆPress 0 to quit the gameˆˆˆˆˆ");
        System.out.println();
        try{
            System.out.println("Best Result: " + HighScoreFileHandler.readNumberFromFile(HighScoreFileHandler.FILE_PATH));
        }catch (Exception e){
            System.out.println("Failed to read from file");
        }
        System.out.println("Current score: " + state.getScore());
        System.out.println("Round number " + engine.getRoundNumber()); //for debug
        System.out.println("Attempts for round: " + state.getAttempts());
        System.out.println("Instructions: " + state.getCurrentCocktail().getInstructions());
        if(engine.isShowAdditionalInfo()){
            displayAdditionalInfo(state);
        }
        System.out.println(state.getCurrentCocktail().getName());
        System.out.println("Enter cocktail name: " + displayableName);
        userResponse = scanner.nextLine();
        cocktailNameEntryEventListener.run();
    }

    public void displayAdditionalInfo(GameState state){
        for (int i = 0; i < 3; i++) {
            System.out.println();
        }
        System.out.println("Category: " + state.getCurrentCocktail().getCategory());
        System.out.println("Glass: " + state.getCurrentCocktail().getGlass());
        System.out.print("Ingredients: ");
        for (int i = 0; i < state.getCurrentCocktail().getIngredients().size(); i++) {
            System.out.print(state.getCurrentCocktail().getIngredients().get(i));
            if(i < state.getCurrentCocktail().getIngredients().size() - 1){
                System.out.print(", ");
            }
        }
    }

    public void addStartEventListener(Runnable runnable) {
        startEventListener = runnable;
    }

    public void addSkipEventListener(Runnable runnable) {
        skipEventListener = runnable;
    }

    public void addCocktailNameEntryEventListener(Runnable runnable) {
        cocktailNameEntryEventListener = runnable;
    }
}
