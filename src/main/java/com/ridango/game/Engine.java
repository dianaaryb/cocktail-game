package com.ridango.game;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Random;

public class Engine {

    private UI ui = new UI();
    private GameState state;
    private String cocktailName;

    public void run() throws IOException, ParseException {
        state = new GameState(CocktailDatabaseCommunicator.getTenRandomCocktails());
        ui.addStartEventListener(() -> startGame());
        ui.addSkipEventListener(() -> skipRound());
        ui.addCocktailNameEntryEventListener(() -> checkCocktailName());
        ui.displayGameStart();
    }

    public void startGame() {
        if (ui.getUserResponse().equals("s")) {
            cocktailName = "_".repeat(state.getCurrentCocktail().getName().length());
            ui.drawField(cocktailName, state.getCurrentCocktail().getInstructions());
        } else {
            System.out.println("Goodbye!");
            System.exit(1);
        }
    }

    public void skipRound() {

    }

    public void checkCocktailName() {
        if (ui.getUserResponse().toLowerCase().equals(state.getCurrentCocktail().getName().toLowerCase())) {
            state.increaseScore();
            state.restoreAttempts();
            state.nextCocktail();
            cocktailName = "_".repeat(state.getCurrentCocktail().getName().length());
            ui.drawField(cocktailName, state.getCurrentCocktail().getInstructions());
        } else {
            state.decreaseAttempts();
            revealLetter();
            ui.drawField(cocktailName, state.getCurrentCocktail().getInstructions());
        }
    }

    public void revealLetter() {
        Random random = new Random();
        while (true) {
            int charPosition = random.nextInt(state.getCurrentCocktail().getName().length());
            char charOnPosition = state.getCurrentCocktail().getName().charAt(charPosition);
            if (cocktailName.charAt(charPosition) != '_') {
                cocktailName = cocktailName.replace('_', charOnPosition);
                return;
            }
        }
    }

}
