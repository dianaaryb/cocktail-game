package com.ridango.game;

import lombok.Getter;

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
        System.out.println("Guess the cocktail");

        System.out.println("START");
        System.out.println("QUIT");

        System.out.println("START - s\nQUIT - q");

        userResponse = scanner.nextLine();
        startEventListener.run();
    }

    public void drawField(String displayableName, String instructions) {
        System.out.println("Instructions: " + instructions);
        System.out.println(displayableName);
        System.out.println("Enter cocktail name: " + displayableName);
        userResponse = scanner.nextLine();
        cocktailNameEntryEventListener.run();
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
