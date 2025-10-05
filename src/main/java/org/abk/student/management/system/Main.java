package org.abk.student.management.system;

import org.abk.student.management.system.seeders.StudentSeeder;
import org.fusesource.jansi.AnsiConsole;

import org.abk.student.management.system.services.StudentService;
import org.abk.student.management.system.ui.ConsoleUI;
import org.abk.student.management.system.utils.ColorUtil;
import org.abk.student.management.system.utils.InputUtil;

/**
 * Main application entry point for the Student Management System.
 * <p>
 * This class initializes all components and starts the application.
 * <p>
 * Features: <br/>
 * - Student CRUD operations (Create, Read, Update, Delete) <br/>
 * - Sorting algorithms (Bubble, Quick, Merge Sort) <br/>
 * - Searching algorithms (Linear, Binary Search) <br/>
 * - Performance comparison tools <br/>
 * - Statistics and reporting <br/>
 * - Colorized console output
 *
 * @author Soft Development ABK
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        // Configurations
        System.setProperty("jansi.passthrough", "true");
        AnsiConsole.systemInstall();

        StudentService studentService = new StudentService();

        try {
            runApplication(studentService);
        } catch (Exception e) {
            ColorUtil.printError("An unexpected error occurred: " + e.getMessage());
        } finally {
            cleanup();
        }
    }

    private static void runApplication(StudentService studentService) {
        InputUtil.clearScreen();
        StudentSeeder studentSeeder = new StudentSeeder(studentService);

        boolean seedSampleData = studentSeeder.promptSeedSampleData();
        if (seedSampleData) {
            studentSeeder.promptSeedMethod();
        }

        InputUtil.pressEnterToContinue();
        ConsoleUI consoleUI = new ConsoleUI(studentService);
        consoleUI.start();
    }

    private static void cleanup() {
        InputUtil.closeScanner();

        AnsiConsole.systemUninstall();
    }
}