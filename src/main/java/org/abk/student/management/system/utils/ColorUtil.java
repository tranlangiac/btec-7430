package org.abk.student.management.system.utils;

import org.abk.student.management.system.constants.AppConstant;
import org.abk.student.management.system.models.StudentRank;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Color;

/**
 * Utility class for colorizing console output using Jansi.
 * Provides consistent color scheme for the Student Management System.
 * <p>
 * Color Scheme: <br/>
 * - Excellent: MAGENTA (bright) <br/>
 * - Very Good: GREEN <br/>
 * - Good: CYAN <br/>
 * - Medium: YELLOW <br/>
 * - Fail: RED <br/>
 * - Headers: BLUE (bold) <br/>
 * - Success: GREEN <br/>
 * - Error: RED (bold) <br/>
 * - Warning: YELLOW <br/>
 * - Info: CYAN
 *
 * @author Soft Development ABK
 * @version 1.0
 */
public class ColorUtil {
    private ColorUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static String colorizedRank(StudentRank rank) {
        return switch (rank) {
            case EXCELLENT -> Ansi.ansi().fg(Color.MAGENTA).bold().a(rank.getDisplayName()).reset().toString();
            case VERY_GOOD -> Ansi.ansi().fg(Color.GREEN).a(rank.getDisplayName()).reset().toString();
            case GOOD -> Ansi.ansi().fg(Color.CYAN).a(rank.getDisplayName()).reset().toString();
            case MEDIUM -> Ansi.ansi().fg(Color.YELLOW).a(rank.getDisplayName()).reset().toString();
            case FAIL -> Ansi.ansi().fg(Color.RED).a(rank.getDisplayName()).reset().toString();
        };
    }

    public static String bold(String text) {
        return Ansi.ansi().bold().a(text).reset().toString();
    }

    public static String success(String message) {
        return Ansi.ansi().fg(Color.GREEN).bold().a("✓ ").a(message).reset().toString();
    }

    public static String error(String message) {
        return Ansi.ansi().fg(Color.RED).bold().a("✗ ").a(message).reset().toString();
    }

    public static String warning(String message) {
        return Ansi.ansi().fg(Color.YELLOW).a("⚠ ").a(message).reset().toString();
    }

    public static String info(String message) {
        return Ansi.ansi().fg(Color.CYAN).a("ℹ ").a(message).reset().toString();
    }

    public static void printSuccess(String message) {
        System.out.println(success(message));
    }

    public static void printError(String message) {
        System.out.println(error(message));
    }

    public static void printWarning(String message) {
        System.out.println(warning(message));
    }

    public static void printInfo(String message) {
        System.out.println(info(message));
    }

    public static void printHeader(String header) {
        int length = (AppConstant.SEPARATOR_SPACE - header.length()) / 2;
        String line = "─".repeat(length);

        System.out.println(Ansi.ansi().fg(Color.BLUE).bold()
                .a(line).a(" " + header + " ").a(line).reset());
    }

    public static void printSeparator() {
        System.out.println(Ansi.ansi().fg(Color.BLUE).a("─".repeat(AppConstant.SEPARATOR_SPACE + 1)).reset());
    }

    public static void printBlankLine() {
        System.out.println();
    }

    public static void printBanner(String text) {
        int length = (AppConstant.SEPARATOR_SPACE - text.length()) / 2;
        String line = "═".repeat(length * 2 + text.length());
        String space = " ".repeat(length);

        System.out.println(Ansi.ansi().fg(Color.BLUE).bold()
                .a("╔").a(line).a("╗\n")
                .a("║").a(space + text + space).a("║\n")
                .a("╚").a(line).a("╝")
                .reset());
    }

    public static void clearScreen() {
        System.out.print(Ansi.ansi().eraseScreen().cursor(1, 1));
    }
}
