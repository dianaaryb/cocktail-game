package com.ridango.game;

import lombok.Getter;

import java.util.Random;

public class Engine {

    private UI ui = new UI();
    private GameState state;
    private String cocktailName;
    @Getter
    private int roundNumber = 0;
    @Getter
    private boolean showAdditionalInfo = false;

    public void run() {
        state = new GameState(CocktailDatabaseCommunicator.getTenRandomCocktails(), HighScoreFileHandler.readNumberFromFile(HighScoreFileHandler.FILE_PATH));
        ui.addStartEventListener(() -> startGame());
        ui.addSkipEventListener(() -> skipRound());
        ui.addCocktailNameEntryEventListener(() -> checkCocktailName());
        ui.displayGameStart();
    }

    public void startGame() {
        if (ui.getUserResponse().equals("s")) {
            while(roundNumber < 5)
            {
                if(roundNumber == 0){
                    cocktailName = state.getCurrentCocktail().getName().replaceAll("\\S", "_");
                }
                if(ui.getUserResponse().equals("0")){
                    updateScore();
                    gameIsOver();
                }
                ui.drawField(cocktailName, state);
            }
            updateScore();
            gameIsOver();
        } else {
            gameIsOver();
        }
    }

    private void updateScore() {
        try{
            HighScoreFileHandler.updateNumberInFile(state.getScore());
        }catch (Exception e){
            System.out.println("Failed to check");
        }
    }

    private static void gameIsOver() {
        System.out.println("Game is over!");
        System.exit(1);
    }

    public void skipRound() {
        state.restoreAttempts();
        if (state.getCurrentCocktailIndex() == state.cocktailCount()) {
            state.addNewCocktails(CocktailDatabaseCommunicator.getTenRandomCocktails());
        }
        state.nextCocktail();
        cocktailName = "_".repeat(state.getCurrentCocktail().getName().length());
        if (roundNumber >= 5) {
            gameIsOver();
        } else {
            roundNumber = 0;
            ui.drawField(cocktailName, state);
        }
    }

    public void checkCocktailName() {
        if(ui.getUserResponse().equals("5")){
            ui.skipEventListener.run();
        } else if (ui.getUserResponse().toLowerCase().equals(state.getCurrentCocktail().getName().toLowerCase())) {
            ui.disableAdditionalInfo();
            roundNumber = 0;
            state.increaseScore();
            state.restoreAttempts();
            if (state.getCurrentCocktailIndex() == state.cocktailCount() - 1) {
                state.addNewCocktails(CocktailDatabaseCommunicator.getTenRandomCocktails());
            }
            state.nextCocktail();
            cocktailName = "_".repeat(state.getCurrentCocktail().getName().length());
        } else {
            roundNumber++;
            state.decreaseAttempts();
            revealLetter();
            if(state.getAttempts() > 1 && !ui.getUserResponse().equals("0")){
                ui.enableAdditionalInfo();
            }
        }
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
