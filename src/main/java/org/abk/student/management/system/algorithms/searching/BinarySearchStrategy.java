package org.abk.student.management.system.algorithms.searching;

import java.util.Comparator;
import java.util.List;

/**
 * Binary Search implementation using the Strategy pattern.
 * <p>
 * Algorithm Description: <br/>
 * Binary Search is an efficient searching algorithm that works on sorted lists.
 * It repeatedly divides the search interval in half, eliminating half of the
 * remaining elements in each step.
 * <p>
 * Characteristics: <br/>
 * - Efficient for large datasets <br/>
 * - Requires sorted list <br/>
 * - Divide and conquer approach <br/>
 * - Eliminates half of elements in each step
 * <p>
 * Time Complexity: <br/>
 * - Best Case: O(1) - element is at the middle position <br/>
 * - Average Case: O(log n) <br/>
 * - Worst Case: O(log n) - element is at the end or not present
 * <p>
 * Space Complexity: <br/>
 * - Iterative: O(1) - uses constant extra space <br/>
 * - Recursive: O(log n) - due to call stack
 * <p>
 * This implementation uses the iterative approach for O(1) space complexity.
 * <p>
 * Use Cases: <br/>
 * - Large sorted datasets (> 100 elements) <br/>
 * - Frequent searches on the same data <br/>
 * - When search performance is critical <br/>
 * - Database indexes <br/>
 * - Dictionary/phonebook lookups
 * <p>
 * Advantages: <br/>
 * - Very fast for large datasets (O(log n)) <br/>
 * - Efficient for repeated searches <br/>
 * - Predictable performance
 * <p>
 * Disadvantages: <br/>
 * - Requires sorted list <br/>
 * - Extra cost if data needs to be sorted first <br/>
 * - More complex than linear search
 * <p>
 * Prerequisites: <br/>
 * - List MUST be sorted according to the comparator <br/>
 * - Comparator must be consistent with the sort order
 *
 * @author Soft Development ABK
 * @version 1.0
 */
public class BinarySearchStrategy implements SearchStrategy {
    private long comparisonCount;
    private long executionTime;

    public BinarySearchStrategy() {
        resetCounters();
    }

    @Override
    public <T> int searchIndex(List<T> list, T target, Comparator<T> comparator) {
        validateInputs(list, target, comparator);

        resetCounters();
        long startTime = System.currentTimeMillis();

        int index = binarySearchIterative(list, target, comparator);

        executionTime = System.currentTimeMillis() - startTime;

        return index;
    }

    private <T> int binarySearchIterative(List<T> list, T target, Comparator<T> comparator) {
        int left = 0;
        int right = list.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            comparisonCount++;
            int comparison = comparator.compare(list.get(mid), target);

            if (comparison == 0) {
                return mid;
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }

    @SuppressWarnings("unused")
    private <T> int binarySearchRecursive(List<T> list, T target,
                                          Comparator<T> comparator, int left, int right) {
        if (left > right) {
            return -1;
        }

        int mid = left + (right - left) / 2;

        comparisonCount++;
        int comparison = comparator.compare(list.get(mid), target);

        if (comparison == 0) {
            return mid;
        } else if (comparison < 0) {
            return binarySearchRecursive(list, target, comparator, mid + 1, right);
        } else {
            return binarySearchRecursive(list, target, comparator, left, mid - 1);
        }
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
        return "Binary Search";
    }

    @Override
    public String getTimeComplexity() {
        return "O(log n)";
    }

    @Override
    public String getSpaceComplexity() {
        return "O(1) iterative";
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
        return true;
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
