package com.ridango.game;

public class Engine {

    private final UI ui = new UI();
    private GameState state;

    public void run() {
        state = new GameState(CocktailDatabaseCommunicator.getTenRandomCocktails(), HighScoreFileHandler.readNumberFromFile());
        ui.addStartEventListener(() -> startGame());
        ui.addCocktailNameEntryEventListener(() -> checkCocktailName());
        ui.displayGameStart();
    }

    public void startGame() {
        if (ui.getUserResponse().equalsIgnoreCase("s")) {
            while(state.getRoundNumber() < 5)
            {
                if(ui.getUserResponse().equals("0")){
                    break;
                }
                ui.drawField(state);
            }
        }
        updateScore();
        gameOver();
    }

    private void updateScore() {
        try {
            HighScoreFileHandler.updateNumberInFile(state.getScore());
        } catch (Exception e) {
            System.out.println("Failed to update the score in the file");
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
        ui.disableAdditionalInfo();
        if (state.getRoundNumber() >= 5) {
            gameOver();
        } else {
            state.setRoundNumber(0);
        }
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
        } else {
            state.increaseRoundNumber();
            state.decreaseAttempts();
            state.revealLetter();
            if(state.getAttempts() > 1 && !ui.getUserResponse().equals("0")){
                ui.enableAdditionalInfo();
            }
        }
    }
}
