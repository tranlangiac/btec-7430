package org.abk.student.management.system.shared.util;

import java.util.Scanner;

import org.abk.student.management.system.shared.util.ValidationUtil.ValidationResult;

/**
 * Utility class for handling user input from console.
 * Provides safe input reading with validation and error handling.
 *
 * @author Soft Development ABK
 * @version 1.0
 */
public class InputUtil {
    private static final Scanner scanner = new Scanner(System.in);

    private InputUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static String readNonEmptyString(String prompt) {
        String input;
        do {
            input = readString(prompt);
            if (input.isEmpty()) {
                ColorUtil.printError("Input cannot be empty. Please try again.");
            }
        } while (input.isEmpty());
        return input;
    }

    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                ColorUtil.printError("Invalid number. Please enter a valid integer.");
            }
        }
    }

    public static int readInt(String prompt, int min, int max) {
        while (true) {
            int value = readInt(prompt);
            if (value >= min && value <= max) {
                return value;
            }
            ColorUtil.printError(String.format("Please enter a number between %d and %d.", min, max));
        }
    }

    public static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                ColorUtil.printError("Invalid number. Please enter a valid decimal number.");
            }
        }
    }

    public static boolean readConfirmation(String prompt) {
        while (true) {
            String input = readString(prompt + " (y/n): ").toLowerCase();
            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                return false;
            }
            ColorUtil.printError("Please enter 'y' for yes or 'n' for no.");
        }
    }

    public static int readMenuChoice(String prompt, int maxOption) {
        return readInt(prompt, 1, maxOption);
    }

    public static void pressEnterToContinue() {
        ColorUtil.printSeparator();
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // If clearing fails, just print newlines
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    public static String readStudentId(String prompt) {
        while (true) {
            String id = readNonEmptyString(prompt);
            ValidationResult result = ValidationUtil.validateId(id);
            if (result.valid()) {
                return id;
            }
            ColorUtil.printError(result.errorMessage());
        }
    }

    public static String readStudentName(String prompt) {
        while (true) {
            String name = readNonEmptyString(prompt);
            ValidationResult result = ValidationUtil.validateName(name);
            if (result.valid()) {
                return name;
            }
            ColorUtil.printError(result.errorMessage());
        }
    }

    public static double readStudentMark(String prompt) {
        while (true) {
            double mark = readDouble(prompt);
            ValidationResult result = ValidationUtil.validateMark(mark);
            if (result.valid()) {
                return mark;
            }
            ColorUtil.printError(result.errorMessage());
        }
    }

    public static void closeScanner() {
        scanner.close();
    }
}
