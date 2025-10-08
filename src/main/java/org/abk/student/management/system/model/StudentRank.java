package org.abk.student.management.system.model;

import org.abk.student.management.system.shared.constant.MarkConstant;

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
    FAIL(MarkConstant.FAIL_LABEL, MarkConstant.MIN_THRESHOLD, MarkConstant.MEDIUM_THRESHOLD),
    MEDIUM(MarkConstant.MEDIUM_LABEL, MarkConstant.MEDIUM_THRESHOLD, MarkConstant.GOOD_THRESHOLD),
    GOOD(MarkConstant.GOOD_LABEL, MarkConstant.GOOD_THRESHOLD, MarkConstant.VERY_GOOD_THRESHOLD),
    VERY_GOOD(MarkConstant.VERY_GOOD_LABEL, MarkConstant.VERY_GOOD_THRESHOLD, MarkConstant.EXCELLENT_THRESHOLD),
    EXCELLENT(MarkConstant.EXCELLENT_LABEL, MarkConstant.EXCELLENT_THRESHOLD, MarkConstant.MAX_THRESHOLD);

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
        if (mark < MarkConstant.MIN_THRESHOLD || mark > MarkConstant.MAX_THRESHOLD) {
            throw new IllegalArgumentException("Mark must be between " + MarkConstant.MIN_THRESHOLD + " and " + MarkConstant.MAX_THRESHOLD);
        }

        if (mark < MarkConstant.MEDIUM_THRESHOLD) {
            return FAIL;
        }

        if (mark < MarkConstant.GOOD_THRESHOLD) {
            return MEDIUM;
        }

        if (mark < MarkConstant.VERY_GOOD_THRESHOLD) {
            return GOOD;
        }

        if (mark < MarkConstant.EXCELLENT_THRESHOLD) {
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
