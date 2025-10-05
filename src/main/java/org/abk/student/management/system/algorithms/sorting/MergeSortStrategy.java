package org.abk.student.management.system.algorithms.sorting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Merge Sort implementation using the Strategy pattern.
 * <p>
 * Algorithm Description: <br/>
 * Merge Sort is a divide-and-conquer algorithm that divides the input array
 * into two halves, recursively sorts them, and then merges the two sorted
 * halves back together.
 * <p>
 * Characteristics: <br/>
 * - Divide and conquer approach <br/>
 * - Stable sort (maintains relative order of equal elements) <br/>
 * - Not in-place (requires O(n) extra space) <br/>
 * - Consistent performance regardless of input
 * <p>
 * Time Complexity: <br/>
 * - Best Case: O(n log n) <br/>
 * - Average Case: O(n log n) <br/>
 * - Worst Case: O(n log n)
 * <p>
 * Space Complexity: O(n) - requires additional array for merging
 * <p>
 * Use Cases: <br/>
 * - When stable sorting is required <br/>
 * - When consistent O(n log n) performance is needed <br/>
 * - Large datasets where worst-case performance matters <br/>
 * - Linked lists (where it becomes O(1) space) <br/>
 * - External sorting (sorting data that doesn't fit in memory)
 * <p>
 * Advantages over Quick Sort: <br/>
 * - Guaranteed O(n log n) time complexity <br/>
 * - Stable sorting <br/>
 * - Better for linked lists
 *
 * @author Soft Development ABK
 * @version 1.0
 */
public class MergeSortStrategy implements SortStrategy {
    private long comparisonCount;
    private long swapCount;
    private long executionTime;

    public MergeSortStrategy() {
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
            mergeSort(list, 0, list.size() - 1, comparator);
        }

        executionTime = System.currentTimeMillis() - startTime;
    }

    private <T> void mergeSort(List<T> list, int left, int right, Comparator<T> comparator) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            mergeSort(list, left, mid, comparator);
            mergeSort(list, mid + 1, right, comparator);

            merge(list, left, mid, right, comparator);
        }
    }

    private <T> void merge(List<T> list, int left, int mid, int right, Comparator<T> comparator) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        List<T> leftArray = new ArrayList<>(n1);
        List<T> rightArray = new ArrayList<>(n2);

        for (int i = 0; i < n1; i++) {
            leftArray.add(list.get(left + i));
        }
        for (int j = 0; j < n2; j++) {
            rightArray.add(list.get(mid + 1 + j));
        }

        int i = 0;
        int j = 0;
        int k = left;

        while (i < n1 && j < n2) {
            comparisonCount++;

            if (comparator.compare(leftArray.get(i), rightArray.get(j)) <= 0) {
                list.set(k, leftArray.get(i));
                i++;
            } else {
                list.set(k, rightArray.get(j));
                j++;
            }
            swapCount++;
            k++;
        }

        while (i < n1) {
            list.set(k, leftArray.get(i));
            swapCount++;
            i++;
            k++;
        }

        while (j < n2) {
            list.set(k, rightArray.get(j));
            swapCount++;
            j++;
            k++;
        }
    }

    @Override
    public String getAlgorithmName() {
        return "Merge Sort";
    }

    @Override
    public String getTimeComplexity() {
        return "O(n log n) all cases";
    }

    @Override
    public String getSpaceComplexity() {
        return "O(n)";
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
        return String.format("%s [Comparisons: %d, Moves: %d, Time: %dms]",
                getAlgorithmName(), comparisonCount, swapCount, executionTime);
    }
}
