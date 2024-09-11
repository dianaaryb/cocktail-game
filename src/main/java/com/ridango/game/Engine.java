package com.ridango.game;

import lombok.Getter;

import java.util.Random;

public class Engine {

    private final UI ui = new UI();
    private GameState state;
    private String cocktailNameToDisplay;
    @Getter
    private boolean showAdditionalInfo = false;

    public void run() {
        state = new GameState(CocktailDatabaseCommunicator.getTenRandomCocktails(), HighScoreFileHandler.readNumberFromFile(HighScoreFileHandler.FILE_PATH));
        ui.addStartEventListener(() -> startGame());
        ui.addCocktailNameEntryEventListener(() -> checkCocktailName());
        ui.displayGameStart();
    }

    public void startGame() {
        if (ui.getUserResponse().equals("s")) {
            while(state.getRoundNumber() < 5)
            {
                if(state.getRoundNumber()  == 0){
                    cocktailNameToDisplay = state.getCurrentCocktail().getName().replaceAll("\\S", "_");
                }
                if(ui.getUserResponse().equals("0")){
                    break;
                }
                ui.drawField(cocktailNameToDisplay, state);
            }
        }
        updateScore();
        gameOver();
    }

    private void updateScore() {
        try {
            HighScoreFileHandler.updateNumberInFile(state.getScore());
        } catch (Exception e) {
            System.out.println("Failed to check");
        }
    }

    private void gameOver() {
        ui.printGameOverMessage();
        System.exit(1);
    }

    public void skipRound() {
        state.restoreAttempts();
        if (hasPlayedAllCocktails()) {
            state.addNewCocktails(CocktailDatabaseCommunicator.getTenRandomCocktails());
        }
        state.nextCocktail();
        cocktailNameToDisplay = getHiddenCocktailName();
        if (state.getRoundNumber() >= 5) {
            gameOver();
        } else {
            state.setRoundNumber(0);
        }
    }

    private String getHiddenCocktailName() {
        return state.getCurrentCocktail().getName().replaceAll("\\S", "_");
    }

    private boolean hasPlayedAllCocktails() {
        return state.getCurrentCocktailIndex() == state.cocktailCount() - 1;
    }

    public void checkCocktailName() {
        if(ui.getUserResponse().equals("5")){
            skipRound();
        } else if (ui.getUserResponse().equalsIgnoreCase(state.getCurrentCocktail().getName())) {
            ui.disableAdditionalInfo();
            state.setRoundNumber(0);
            state.increaseScore();
            state.restoreAttempts();
            if (hasPlayedAllCocktails()) {
                state.addNewCocktails(CocktailDatabaseCommunicator.getTenRandomCocktails());
            }
            state.nextCocktail();
            cocktailNameToDisplay = "_".repeat(state.getCurrentCocktail().getName().length());
        } else {
            state.increaseRoundNumber();
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
            if (cocktailNameToDisplay.charAt(charPosition) == '_') {
                cocktailNameToDisplay = cocktailNameToDisplay.substring(0, charPosition) + charOnPosition + cocktailNameToDisplay.substring(charPosition + 1);
                return;
            }
        }
    }
}
