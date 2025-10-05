package org.abk.student.management.system.algorithms.sorting;

import java.util.Comparator;
import java.util.List;

/**
 * Quick Sort implementation using the Strategy pattern.
 * <p>
 * Algorithm Description: <br/>
 * Quick Sort is a divide-and-conquer algorithm that picks a pivot element
 * and partitions the array around the pivot, placing smaller elements before
 * it and larger elements after it. It then recursively sorts the sub-arrays.
 * <p>
 * Characteristics: <br/>
 * - Divide and conquer approach <br/>
 * - Not stable (may change relative order of equal elements) <br/>
 * - In-place sorting with O(log n) stack space <br/>
 * - One of the fastest sorting algorithms in practice
 * <p>
 * Time Complexity: <br/>
 * - Best Case: O(n log n) - when pivot divides array evenly <br/>
 * - Average Case: O(n log n) <br/>
 * - Worst Case: O(n²) - when pivot is always smallest/largest (rare with good pivot selection)
 * <p>
 * Space Complexity: O(log n) - due to recursion stack
 * <p>
 * Use Cases: <br/>
 * - Large datasets <br/>
 * - When average-case performance is important <br/>
 * - When in-place sorting is required <br/>
 * - General-purpose sorting
 * <p>
 * Pivot Selection Strategy: <br/>
 * This implementation uses "median-of-three" pivot selection for better
 * average-case performance and to avoid worst-case scenarios.
 *
 * @author Soft Development ABK
 * @version 1.0
 */
public class QuickSortStrategy implements SortStrategy {
    private long comparisonCount;
    private long swapCount;
    private long executionTime;

    public QuickSortStrategy() {
        resetCounters();
    }

    @Override
    public <T> void sort(List<T> list, Comparator<T> comparator) {
        if (list == null) {
            throw new IllegalArgumentException("List cannot be null");
        }

        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }

        resetCounters();
        long startTime = System.currentTimeMillis();

        if (list.size() > 1) {
            quickSort(list, 0, list.size() - 1, comparator);
        }

        executionTime = System.currentTimeMillis() - startTime;
    }

    private <T> void quickSort(List<T> list, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int pivotIndex = partition(list, low, high, comparator);

            quickSort(list, low, pivotIndex - 1, comparator);
            quickSort(list, pivotIndex + 1, high, comparator);
        }
    }

    private <T> int partition(List<T> list, int low, int high, Comparator<T> comparator) {
        int mid = low + (high - low) / 2;

        if (comparator.compare(list.get(mid), list.get(low)) < 0) {
            swap(list, low, mid);
            swapCount++;
        }
        comparisonCount++;

        if (comparator.compare(list.get(high), list.get(low)) < 0) {
            swap(list, low, high);
            swapCount++;
        }
        comparisonCount++;

        if (comparator.compare(list.get(mid), list.get(high)) < 0) {
            swap(list, mid, high);
            swapCount++;
        }
        comparisonCount++;

        T pivot = list.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            comparisonCount++;

            if (comparator.compare(list.get(j), pivot) <= 0) {
                i++;
                swap(list, i, j);
                swapCount++;
            }
        }

        swap(list, i + 1, high);
        swapCount++;

        return i + 1;
    }

    private <T> void swap(List<T> list, int i, int j) {
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    @Override
    public String getAlgorithmName() {
        return "Quick Sort";
    }

    @Override
    public String getTimeComplexity() {
        return "O(n log n) avg, O(n²) worst";
    }

    @Override
    public String getSpaceComplexity() {
        return "O(log n)";
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
