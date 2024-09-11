package com.ridango.game;

import java.io.*;

public class HighScoreFileHandler {

    static final String FILE_PATH = "/Users/di/IdeaProjects/coctail-game/src/main/java/com/ridango/game/fileHighScore.txt";

    public static int readNumberFromFile(String filePath) throws IOException {
        File file = new File(filePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            return Integer.parseInt(line.trim());
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format in file: " + filePath);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + filePath);
        }
        return 0;
    }

    public static void updateNumberInFile(int newNumber) throws IOException {
        int currentNumber = readNumberFromFile(FILE_PATH);
        if (newNumber > currentNumber) {
            writeNumberToFile(FILE_PATH, newNumber);
        }
    }

    public static void writeNumberToFile(String filePath, int number) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(String.valueOf(number));
        } catch (Exception e){
            System.out.println("Failed to write to file");
        }
    }

}
