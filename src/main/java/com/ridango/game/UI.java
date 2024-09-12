package com.ridango.game;

import lombok.Getter;

import java.util.Scanner;

public class UI {

    private final Scanner scanner = new Scanner(System.in);
    private Runnable startEventListener;
    private Runnable cocktailNameEntryEventListener;
    @Getter
    private String userResponse;
    private boolean showAdditionalInfo = false;

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

    public void drawField(GameState state) {
        printEmptyLine(4);
        System.out.println("ˆˆˆˆˆPress 5 for skip roundˆˆˆˆˆ");
        System.out.println("ˆˆˆˆˆPress 0 to quit the gameˆˆˆˆˆ");
        printEmptyLine(1);
        System.out.println("Best Result: " + state.getHighScore());
        System.out.println("Current score: " + state.getScore());
        System.out.println("Attempts for round: " + state.getAttempts());
        System.out.println("Instructions: " + state.getCurrentCocktail().getInstructions());
        if(showAdditionalInfo){
            displayAdditionalInfo(state);
        }
        System.out.println("Enter cocktail name: " + state.getCocktailNameToDisplay());
        userResponse = scanner.nextLine();
        cocktailNameEntryEventListener.run();
    }

    public void displayAdditionalInfo(GameState state){
        System.out.println("Category: " + state.getCurrentCocktail().getCategory());
        System.out.println("Glass: " + state.getCurrentCocktail().getGlass());
        System.out.print("Ingredients: ");
        for (int i = 0; i < state.getCurrentCocktail().getIngredients().size(); i++) {
            System.out.print(state.getCurrentCocktail().getIngredients().get(i));
            if(i < state.getCurrentCocktail().getIngredients().size() - 1){
                System.out.print(", ");
            }
        }
        System.out.print("\n");
    }

    public void printGameOverMessage() {
        System.out.println("Game is over!");
    }

    public void printEmptyLine(int times) {
        for (int i = 0; i < times; i++) {
            System.out.println();
        }
    }

    public void addStartEventListener(Runnable runnable) {
        startEventListener = runnable;
    }

    public void addCocktailNameEntryEventListener(Runnable runnable) {
        cocktailNameEntryEventListener = runnable;
    }

    public void enableAdditionalInfo() {
        showAdditionalInfo = true;
    }

    public void disableAdditionalInfo() {
        showAdditionalInfo = false;
    }
}
