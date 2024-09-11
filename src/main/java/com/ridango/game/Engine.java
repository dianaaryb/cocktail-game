package com.ridango.game;

import lombok.Getter;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Random;

public class Engine {

    private UI ui = new UI();
    private GameState state;
    private String cocktailName;
    @Getter
    private int roundNumber = 0;
    @Getter
    private boolean showAdditionalInfo = false;

    public void run() throws IOException, ParseException {
        state = new GameState(CocktailDatabaseCommunicator.getTenRandomCocktails());
        ui.addStartEventListener(() -> startGame());
        ui.addSkipEventListener(() -> skipRound());
        ui.addCocktailNameEntryEventListener(() -> checkCocktailName());
        ui.displayGameStart();
    }

    public void startGame() {
        if (ui.getUserResponse().equals("s")) {
            cocktailName = state.getCurrentCocktail().getName().replaceAll("[a-zA-Z]", "_");
            while(roundNumber < 5)
            {
                if(ui.getUserResponse().equals("0")){
                    gameIsOver();
                }
                ui.drawField(cocktailName, state, this);
//                showAdditionalInfo = false;
                if (showAdditionalInfo) {
                    ui.displayAdditionalInfo(state);
                    showAdditionalInfo = false;
                }
            }
            try{
                HighScoreFileHandler.updateNumberInFile(state.getScore());
            }catch (Exception e){
                System.out.println("Failed to check");
            }
            gameIsOver();
        } else {
            gameIsOver();
        }
    }

    private static void gameIsOver() {
        System.out.println("Game is over!");
        System.exit(1);
    }

    public void skipRound() {
        state.restoreAttempts();
        state.nextCocktail();
        cocktailName = "_".repeat(state.getCurrentCocktail().getName().length());
        if (roundNumber >= 5) {
            gameIsOver();
        } else {
            roundNumber = 0;
            ui.drawField(cocktailName, state, this);
        }
    }

    public void checkCocktailName() {
        if(ui.getUserResponse().equals("5")){
            ui.skipEventListener.run();
        } else if (ui.getUserResponse().toLowerCase().equals(state.getCurrentCocktail().getName().toLowerCase())) {
            roundNumber = 0;
            state.increaseScore();
            state.restoreAttempts();
            state.nextCocktail();
            cocktailName = "_".repeat(state.getCurrentCocktail().getName().length());
//            ui.drawField(cocktailName, state.getCurrentCocktail().getInstructions(), state.getCurrentCocktail().getName());
        } else {
            roundNumber++;
            state.decreaseAttempts();
            revealLetter();
            if(state.getAttempts() > 1 && !ui.getUserResponse().equals("0")){
                showAdditionalInfo = true;
            }
//            ui.drawField(cocktailName, state.getCurrentCocktail().getInstructions(), state.getCurrentCocktail().getName());
        }
//        System.out.println("szdfghjsxzcdvfgbhnj"); //proverit skolko raz soobshenije visvetitsja v oboix sluchajax
    }

    public void revealLetter() {
        Random random = new Random();
        while (true) {
            int charPosition = random.nextInt(state.getCurrentCocktail().getName().length());
            char charOnPosition = state.getCurrentCocktail().getName().charAt(charPosition);
            if (cocktailName.charAt(charPosition) == '_') {
                cocktailName = cocktailName.substring(0, charPosition) + charOnPosition + cocktailName.substring(charPosition + 1);
                return;
            }
        }
    }
}
