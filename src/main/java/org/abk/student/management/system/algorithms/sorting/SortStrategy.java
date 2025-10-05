package org.abk.student.management.system.algorithms.sorting;

import java.util.Comparator;
import java.util.List;

/**
 * Strategy interface for sorting algorithms.
 * <p>
 * This interface defines the contract for all sorting implementations,
 * allowing different algorithms to be used interchangeably.
 * <p>
 * Design Pattern: Strategy Pattern <br/>
 * - Encapsulates sorting algorithms <br/>
 * - Makes them interchangeable <br/>
 * - Allows runtime algorithm selection
 *
 * @author Soft Development ABK
 * @version 1.0
 */
public interface SortStrategy {
    <T> void sort(List<T> list, Comparator<T> comparator);
    String getAlgorithmName();
    String getTimeComplexity();
    String getSpaceComplexity();
    long getComparisonCount();
    long getSwapCount();
    long getExecutionTime();
    void resetCounters();
}
