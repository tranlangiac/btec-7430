package org.abk.student.management.system.models;

import org.abk.student.management.system.constants.AppConstant;

/**
 * Enumeration representing student performance ranks.
 * <p>
 * This enum categorizes student performance into five levels
 * based on their marks according to the following table:
 * <p>
 * [0.0 - 5.0) | FAIL <br/>
 * [5.0 - 6.5) | MEDIUM <br/>
 * [6.5 - 7.5) | GOOD <br/>
 * [7.5 - 9.0) | VERY_GOOD <br/>
 * [9.0 - 10.0] | EXCELLENT
 *
 * @author Soft Development ABK
 * @version 1.0
 */
public enum StudentRank {
    FAIL("Fail", AppConstant.FAIL_MARK_MIN, AppConstant.FAIL_MARK_MAX),
    MEDIUM("Medium", AppConstant.MEDIUM_MARK_MIN, AppConstant.MEDIUM_MARK_MAX),
    GOOD("Good", AppConstant.GOOD_MARK_MIN, AppConstant.GOOD_MARK_MAX),
    VERY_GOOD("Very Good", AppConstant.VERY_GOOD_MARK_MIN, AppConstant.VERY_GOOD_MARK_MAX),
    EXCELLENT("Excellent", AppConstant.EXCELLENT_MARK_MIN, AppConstant.EXCELLENT_MARK_MAX);

    private final String displayName;
    private final double minMark;
    private final double maxMark;

    StudentRank(String displayName, double minMark, double maxMark) {
        this.displayName = displayName;
        this.minMark = minMark;
        this.maxMark = maxMark;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static StudentRank fromMark(double mark) {
        if (mark < AppConstant.FAIL_MARK_MIN || mark > AppConstant.EXCELLENT_MARK_MAX) {
            throw new IllegalArgumentException("Mark must be between " + AppConstant.FAIL_MARK_MIN + " and " + AppConstant.EXCELLENT_MARK_MAX);
        }

        if (mark < AppConstant.MEDIUM_MARK_MIN) {
            return FAIL;
        }

        if (mark < AppConstant.GOOD_MARK_MIN) {
            return MEDIUM;
        }

        if (mark < AppConstant.VERY_GOOD_MARK_MIN) {
            return GOOD;
        }

        if (mark < AppConstant.EXCELLENT_MARK_MIN) {
            return VERY_GOOD;
        }

        return EXCELLENT;
    }

    public String getMarkRange() {
        if (this == EXCELLENT) {
            return String.format("[%.1f - %.1f]", minMark, maxMark);
        }

        return String.format("[%.1f - %.1f)", minMark, maxMark);
    }

    @Override
    public String toString() {
        return displayName;
    }
}
