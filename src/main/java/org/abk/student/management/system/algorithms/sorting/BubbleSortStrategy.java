package org.abk.student.management.system.algorithms.sorting;

import java.util.Comparator;
import java.util.List;

/**
 * Bubble Sort implementation using the Strategy pattern.
 * <p>
 * Algorithm Description: <br/>
 * Bubble Sort is a simple sorting algorithm that repeatedly steps through
 * the list, compares adjacent elements and swaps them if they are in the
 * wrong order. The pass through the list is repeated until the list is sorted.
 * <p>
 * Characteristics: <br/>
 * - Simple and intuitive <br/>
 * - Stable sort (maintains relative order of equal elements) <br/>
 * - In-place sorting (requires only O(1) extra space) <br/>
 * - Poor performance on large datasets
 * <p>
 * Time Complexity: <br/>
 * - Best Case: O(n) - when array is already sorted <br/>
 * - Average Case: O(n²) <br/>
 * - Worst Case: O(n²) - when array is reverse sorted
 * <p>
 * Space Complexity: O(1) - sorts in place
 * <p>
 * Use Cases: <br/>
 * - Small datasets (< 50 elements) <br/>
 * - Educational purposes <br/>
 * - Nearly sorted data <br/>
 * - When simplicity is more important than performance
 *
 * @author Soft Development ABK
 * @version 1.0
 */
public class BubbleSortStrategy implements SortStrategy {
    private long comparisonCount;
    private long swapCount;
    private long executionTime;

    public BubbleSortStrategy() {
        resetCounters();
    }

    public <T> void sort(List<T> list, Comparator<T> comparator) {
        if (list == null) {
            throw new IllegalArgumentException("List cannot be null");
        }

        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }

        resetCounters();
        long startTime = System.currentTimeMillis();

        int n = list.size();
        boolean swapped;

        // Outer loop: passes through the list
        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            // Inner loop: compare adjacent elements
            for (int j = 0; j < n - i - 1; j++) {
                comparisonCount++;

                // Compare adjacent elements
                if (comparator.compare(list.get(j), list.get(j + 1)) > 0) {
                    // Swap if they are in wrong order
                    swap(list, j, j + 1);
                    swapCount++;
                    swapped = true;
                }
            }

            // Optimization: if no swaps occurred, list is sorted
            if (!swapped) {
                break;
            }
        }

        // Calculate execution time
        executionTime = System.currentTimeMillis() - startTime;
    }

    private <T> void swap(List<T> list, int i, int j) {
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    @Override
    public String getAlgorithmName() {
        return "Bubble Sort";
    }

    @Override
    public String getTimeComplexity() {
        return "O(n²) avg/worst, O(n) best";
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
    public long getSwapCount() {
        return swapCount;
    }

    @Override
    public long getExecutionTime() {
        return executionTime;
    }

    @Override
    public void resetCounters() {
        this.comparisonCount = 0;
        this.swapCount = 0;
        this.executionTime = 0;
    }

    @Override
    public String toString() {
        return String.format("%s [Comparisons: %d, Swaps: %d, Time: %dms]",
                getAlgorithmName(), comparisonCount, swapCount, executionTime);
    }
}
