package database;

import backend.Question;
import backend.Level;
import java.io.*;
import java.nio.file.*;

/**
 * Utility class to import questions from CSV file
 */
public class ImportQuestions {

    /**
     * Import questions from CSV file
     * @param csvFilePath Path to the CSV file
     */
    public static void importFromCSV(String csvFilePath) {
        try {
            // Read all lines from CSV
            Path path = Paths.get(csvFilePath);
            java.util.List<String> lines = Files.readAllLines(path);

            int imported = 0;
            int skipped = 0;

            // Skip header line
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                
                if (line.isEmpty()) {
                    continue;
                }

                // Parse CSV line (handle commas in quotes)
                String[] parts = parseCSVLine(line);

                if (parts.length >= 7) {
                    try {
                        Question q = new Question();
                        q.setQuestionText(parts[0].trim());
                        q.setOptionA(parts[1].trim());
                        q.setOptionB(parts[2].trim());
                        q.setOptionC(parts[3].trim());
                        q.setOptionD(parts[4].trim());
                        q.setCorrectAnswer(parts[5].trim().toUpperCase());
                        q.setLevel(Level.fromString(parts[6].trim()));

                        if (DBinsert.insertQuestion(q)) {
                            imported++;
                        } else {
                            skipped++;
                        }
                    } catch (Exception e) {
                        System.err.println("Error parsing line " + (i + 1) + ": " + e.getMessage());
                        skipped++;
                    }
                } else {
                    System.err.println("Invalid line " + (i + 1) + ": insufficient columns");
                    skipped++;
                }
            }

            System.out.println("Import completed!");
            System.out.println("Questions imported: " + imported);
            System.out.println("Questions skipped: " + skipped);

        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }

    /**
     * Parse CSV line handling commas within quotes
     */
    private static String[] parseCSVLine(String line) {
        java.util.List<String> result = new java.util.ArrayList<>();
        boolean inQuotes = false;
        StringBuilder current = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }

        result.add(current.toString());

        return result.toArray(new String[0]);
    }

    /**
     * Main method to run import
     */
    public static void main(String[] args) {
        // First create tables
        DBCreate.createTables();

        // Then import questions
        String csvPath = "quiz_questions.csv"; // Update this path as needed
        
        System.out.println("Starting import from: " + csvPath);
        importFromCSV(csvPath);
        
        // Verify import
        System.out.println("\nVerifying import:");
        System.out.println("Beginner questions: " + DBOperation.getQuestionCount(Level.BEGINNER));
        System.out.println("Intermediate questions: " + DBOperation.getQuestionCount(Level.INTERMEDIATE));
        System.out.println("Advanced questions: " + DBOperation.getQuestionCount(Level.ADVANCED));

        DBConnection.closeConnection();
    }
}
