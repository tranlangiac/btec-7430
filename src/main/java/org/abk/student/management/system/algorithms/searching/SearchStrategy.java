package org.abk.student.management.system.algorithms.searching;

import java.util.Comparator;
import java.util.List;

/**
 * Strategy interface for searching algorithms.
 * <p>
 * This interface defines the contract for all searching implementations,
 * allowing different algorithms to be used interchangeably.
 * <p>
 * Design Pattern: Strategy Pattern <br/>
 * - Encapsulates searching algorithms <br/>
 * - Makes them interchangeable <br/>
 * - Allows runtime algorithm selection
 *
 * @author Soft Development ABK
 * @version 1.0
 */
public interface SearchStrategy {
    <T> int searchIndex(List<T> list, T target, Comparator<T> comparator);
    String getAlgorithmName();
    String getTimeComplexity();
    String getSpaceComplexity();
    long getComparisonCount();
    long getExecutionTime();
    boolean requiresSortedList();
    void resetCounters();
}
