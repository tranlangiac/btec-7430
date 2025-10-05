package org.abk.student.management.system.algorithms.searching;

import java.util.Comparator;
import java.util.List;

/**
 * Linear Search (Sequential Search) implementation using the Strategy pattern.
 * <p>
 * Algorithm Description: <br/>
 * Linear Search is the simplest searching algorithm that checks every element
 * in the list sequentially until the target element is found or the end of
 * the list is reached.
 * <p>
 * Characteristics: <br/>
 * - Simple and straightforward <br/>
 * - Works on unsorted lists <br/>
 * - No preprocessing required <br/>
 * - Checks elements one by one from start to end
 * <p>
 * Time Complexity: <br/>
 * - Best Case: O(1) - element is at the first position <br/>
 * - Average Case: O(n) - element is somewhere in the middle <br/>
 * - Worst Case: O(n) - element is at the end or not present
 * <p>
 * Space Complexity: O(1) - uses constant extra space
 * <p>
 * Use Cases: <br/>
 * - Small datasets (< 100 elements) <br/>
 * - Unsorted data <br/>
 * - When simplicity is preferred over performance <br/>
 * - When data changes frequently (no need to maintain sort order) <br/>
 * - One-time searches
 * <p>
 * Advantages: <br/>
 * - Simple to implement and understand <br/>
 * - Works on any list (sorted or unsorted) <br/>
 * - No preprocessing required <br/>
 * - Best for small datasets
 * <p>
 * Disadvantages: <br/>
 * - Slow for large datasets <br/>
 * - Always checks every element in worst case <br/>
 * - Not suitable for frequent searches on large data
 *
 * @author Soft Development ABK
 * @version 1.0
 */
public class LinearSearchStrategy implements SearchStrategy {
    private long comparisonCount;
    private long executionTime;

    public LinearSearchStrategy() {
        resetCounters();
    }

    @Override
    public <T> int searchIndex(List<T> list, T target, Comparator<T> comparator) {
        validateInputs(list, target, comparator);

        resetCounters();
        long startTime = System.currentTimeMillis();

        int index = -1;

        for (int i = 0; i < list.size(); i++) {
            comparisonCount++;

            if (comparator.compare(list.get(i), target) == 0) {
                index = i;
                break;
            }
        }

        executionTime = System.currentTimeMillis() - startTime;

        return index;
    }

    private <T> void validateInputs(List<T> list, T target, Comparator<T> comparator) {
        if (list == null) {
            throw new IllegalArgumentException("List cannot be null");
        }
        if (target == null) {
            throw new IllegalArgumentException("Target cannot be null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
    }

    @Override
    public String getAlgorithmName() {
        return "Linear Search";
    }

    @Override
    public String getTimeComplexity() {
        return "O(n)";
    }

    @Override
    public String getSpaceComplexity() {
        return "O(1)";
    }

    @Override
    public long getComparisonCount() {
        return comparisonCount;
    }

    @Override
    public long getExecutionTime() {
        return executionTime;
    }

    @Override
    public boolean requiresSortedList() {
        return false;
    }

    @Override
    public void resetCounters() {
        this.comparisonCount = 0;
        this.executionTime = 0;
    }

    @Override
    public String toString() {
        return String.format("%s [Comparisons: %d, Time: %dms, Requires Sorted: %s]",
                getAlgorithmName(), comparisonCount, executionTime,
                requiresSortedList() ? "Yes" : "No");
    }
}
