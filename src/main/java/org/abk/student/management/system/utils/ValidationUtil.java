package org.abk.student.management.system.utils;

import java.util.regex.Pattern;

/**
 * Utility class for validation operations.
 * Provides additional validation methods beyond basic null/empty checks.
 *
 * @author Soft Development ABK
 * @version 1.0
 */
public class ValidationUtil {
    private static final Pattern ID_PATTERN = Pattern.compile("^[A-Za-z0-9]+$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z\\s]+$");

    public static final double MIN_MARK = 0.0;
    public static final double MAX_MARK = 10.0;
    public static final int MIN_ID_LENGTH = 2;
    public static final int MAX_ID_LENGTH = 20;
    public static final int MIN_NAME_LENGTH = 3;
    public static final int MAX_NAME_LENGTH = 100;

    private ValidationUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static ValidationResult validateId(String id) {
        if (id == null) {
            return ValidationResult.failure("Student ID cannot be null");
        }

        String trimmedId = id.trim();
        if (trimmedId.isEmpty()) {
            return ValidationResult.failure("Student ID cannot be empty");
        }
        if (trimmedId.length() < MIN_ID_LENGTH) {
            return ValidationResult.failure(String.format("Student ID must be at least %d characters", MIN_ID_LENGTH));
        }
        if (trimmedId.length() > MAX_ID_LENGTH) {
            return ValidationResult.failure(String.format("Student ID cannot exceed %d characters", MAX_ID_LENGTH));
        }
        if (!ID_PATTERN.matcher(trimmedId).matches()) {
            return ValidationResult.failure("Student ID must contain only letters and numbers");
        }

        return ValidationResult.success();
    }

    public static ValidationResult validateName(String name) {
        if (name == null) {
            return ValidationResult.failure("Student name cannot be null");
        }

        String trimmedName = name.trim();
        if (trimmedName.isEmpty()) {
            return ValidationResult.failure("Student name cannot be empty");
        }
        if (trimmedName.length() < MIN_NAME_LENGTH) {
            return ValidationResult.failure(String.format("Student name must be at least %d characters", MIN_NAME_LENGTH));
        }
        if (trimmedName.length() > MAX_NAME_LENGTH) {
            return ValidationResult.failure(String.format("Student name cannot exceed %d characters", MAX_NAME_LENGTH));
        }
        if (!NAME_PATTERN.matcher(trimmedName).matches()) {
            return ValidationResult.failure("Student name must contain only letters and spaces");
        }

        return ValidationResult.success();
    }

    public static ValidationResult validateMark(double mark) {
        if (Double.isNaN(mark)) {
            return ValidationResult.failure("Mark cannot be NaN");
        }
        if (Double.isInfinite(mark)) {
            return ValidationResult.failure("Mark cannot be infinite");
        }
        if (mark < MIN_MARK) {
            return ValidationResult.failure(String.format("Mark cannot be less than %.1f", MIN_MARK));
        }
        if (mark > MAX_MARK) {
            return ValidationResult.failure(String.format("Mark cannot be greater than %.1f", MAX_MARK));
        }

        return ValidationResult.success();
    }

    public record ValidationResult(boolean valid, String errorMessage) {
        public static ValidationResult success() {
            return new ValidationResult(true, null);
        }

        public static ValidationResult failure(String errorMessage) {
            return new ValidationResult(false, errorMessage);
        }

        @Override
        public String toString() {
            return valid ? "Valid" : "Invalid: " + errorMessage;
        }
    }
}
