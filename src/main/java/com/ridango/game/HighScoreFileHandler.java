package com.ridango.game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;

public class HighScoreFileHandler {

    private static final String FILE_PATH = getFilePath();

    public static int readNumberFromFile() {
        File file = new File(FILE_PATH);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            return Integer.parseInt(line.trim());
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + FILE_PATH);
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format in file: " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + FILE_PATH);
        }
        return 0;
    }

    public static void updateNumberInFile(int newNumber) {
        int currentNumber = readNumberFromFile();
        if (newNumber > currentNumber) {
            writeNumberToFile(FILE_PATH, newNumber);
        }
    }

    public static void writeNumberToFile(String filePath, int number) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(String.valueOf(number));
        } catch (Exception e) {
            System.out.println("Failed to write to file");
        }
    }

    private static String getFilePath() {
        return "src/main/java/com/ridango/game/fileHighScore.txt".replaceAll("/", File.separator);
    }
}
