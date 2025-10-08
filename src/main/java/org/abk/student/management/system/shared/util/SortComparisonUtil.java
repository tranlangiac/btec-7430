package org.abk.student.management.system.shared.util;

import org.abk.student.management.system.algorithms.sorting.SortStrategy;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Utility class for comparing sorting algorithm performance.
 * <p>
 * This class helps analyze and compare different sorting algorithms
 * by tracking their performance metrics and displaying results in
 * formatted tables.
 *
 * @author Soft Development ABK
 * @version 1.0
 */
public class SortComparisonUtil {
    private SortComparisonUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static class SortResult {
        private final String algorithmName;
        private final long comparisons;
        private final long swaps;
        private final long executionTime;
        private final String timeComplexity;
        private final String spaceComplexity;

        public SortResult(SortStrategy strategy) {
            this.algorithmName = strategy.getAlgorithmName();
            this.comparisons = strategy.getComparisonCount();
            this.swaps = strategy.getSwapCount();
            this.executionTime = strategy.getExecutionTime();
            this.timeComplexity = strategy.getTimeComplexity();
            this.spaceComplexity = strategy.getSpaceComplexity();
        }

        public String getAlgorithmName() { return algorithmName; }
        public long getComparisons() { return comparisons; }
        public long getSwaps() { return swaps; }
        public long getExecutionTime() { return executionTime; }
        public String getTimeComplexity() { return timeComplexity; }
        public String getSpaceComplexity() { return spaceComplexity; }
    }

    public static <T> List<SortResult> compareAlgorithms(
            List<T> originalData,
            Comparator<T> comparator,
            SortStrategy... strategies) {

        List<SortResult> results = new ArrayList<>();

        for (SortStrategy strategy : strategies) {
            List<T> dataCopy = new ArrayList<>(originalData);

            strategy.sort(dataCopy, comparator);

            results.add(new SortResult(strategy));
        }

        return results;
    }

    public static void displayComparison(List<SortResult> results) {
        if (results == null || results.isEmpty()) {
            ColorUtil.printWarning("No results to display");
            return;
        }

        AsciiTable table = new AsciiTable();

        table.addRule();
        table.addRow("Algorithm", "Time (ms)", "Comparisons", "Swaps/Moves",
                "Time Complexity", "Space Complexity");
        table.addRule();

        for (SortResult result : results) {
            table.addRow(
                    result.getAlgorithmName(),
                    result.getExecutionTime(),
                    result.getComparisons(),
                    result.getSwaps(),
                    result.getTimeComplexity(),
                    result.getSpaceComplexity()
            );
        }
        table.addRule();

        table.getRenderer().setCWC(new CWC_LongestLine());

        ColorUtil.printBlankLine();
        ColorUtil.printHeader("SORTING ALGORITHM COMPARISON");
        System.out.println(table.render());
    }

    public static void displayDetailedComparison(List<SortResult> results) {
        if (results == null || results.isEmpty()) {
            ColorUtil.printWarning("No results to display");
            return;
        }

        SortResult fastestTime = results.stream()
                .min(Comparator.comparingLong(SortResult::getExecutionTime))
                .orElse(null);

        SortResult fewestComparisons = results.stream()
                .min(Comparator.comparingLong(SortResult::getComparisons))
                .orElse(null);

        SortResult fewestSwaps = results.stream()
                .min(Comparator.comparingLong(SortResult::getSwaps))
                .orElse(null);

        ColorUtil.printBlankLine();
        ColorUtil.printHeader("DETAILED ALGORITHM COMPARISON");

        for (SortResult result : results) {
            System.out.println(ColorUtil.bold(result.getAlgorithmName()));
            System.out.println("─".repeat(50));

            String timeStr = result.getExecutionTime() + " ms";
            if (result == fastestTime) {
                timeStr = ColorUtil.success(timeStr + " ← FASTEST");
            }
            System.out.println("  Execution Time:    " + timeStr);

            String compStr = String.valueOf(result.getComparisons());
            if (result == fewestComparisons) {
                compStr = ColorUtil.success(compStr + " ← FEWEST COMPARISONS");
            }
            System.out.println("  Comparisons:       " + compStr);

            String swapStr = String.valueOf(result.getSwaps());
            if (result == fewestSwaps) {
                swapStr = ColorUtil.success(swapStr + " ← FEWEST SWAPS");
            }
            System.out.println("  Swaps/Moves:       " + swapStr);

            System.out.println("  Time Complexity:   " + result.getTimeComplexity());
            System.out.println("  Space Complexity:  " + result.getSpaceComplexity());
            ColorUtil.printBlankLine();
        }
    }

    public static void displayRecommendation(List<SortResult> results, int dataSize) {
        ColorUtil.printHeader("RECOMMENDATION");

        if (dataSize < 50) {
            ColorUtil.printInfo("For small datasets (< 50 elements):");
            System.out.println("  • " + ColorUtil.bold("Bubble Sort") +
                    " is acceptable due to simplicity");
            System.out.println("  • " + ColorUtil.bold("Quick Sort") +
                    " and " + ColorUtil.bold("Merge Sort") +
                    " may have overhead");
        } else if (dataSize < 1000) {
            ColorUtil.printInfo("For medium datasets (50-1000 elements):");
            System.out.println("  • " + ColorUtil.bold("Quick Sort") +
                    " is recommended for best average performance");
            System.out.println("  • " + ColorUtil.bold("Merge Sort") +
                    " if stable sorting is required");
        } else {
            ColorUtil.printInfo("For large datasets (> 1000 elements):");
            System.out.println("  • " + ColorUtil.bold("Quick Sort") +
                    " for fastest average case");
            System.out.println("  • " + ColorUtil.bold("Merge Sort") +
                    " for guaranteed O(n log n) performance");
            System.out.println("  • Avoid " + ColorUtil.bold("Bubble Sort") +
                    " - O(n²) is too slow");
        }

        ColorUtil.printBlankLine();

        results.stream()
                .min(Comparator.comparingLong(SortResult::getExecutionTime))
                .ifPresent(winner -> ColorUtil.printSuccess("Overall Winner: "
                        + winner.getAlgorithmName()
                        + " (" + winner.getExecutionTime() + "ms)"));
    }

    public static void displayAlgorithmCharacteristics() {
        ColorUtil.printHeader("ALGORITHM CHARACTERISTICS");

        System.out.println(ColorUtil.bold("Bubble Sort:"));
        System.out.println("  ✓ Simple to understand and implement");
        System.out.println("  ✓ Stable (maintains order of equal elements)");
        System.out.println("  ✓ In-place (O(1) space)");
        System.out.println("  ✗ Slow for large datasets (O(n²))");
        System.out.println("  Use: Educational purposes, small datasets");

        System.out.println("\n" + ColorUtil.bold("Quick Sort:"));
        System.out.println("  ✓ Fast average performance (O(n log n))");
        System.out.println("  ✓ In-place with minimal extra memory");
        System.out.println("  ✓ Good cache performance");
        System.out.println("  ✗ Not stable");
        System.out.println("  ✗ Worst case O(n²) (rare with good pivot)");
        System.out.println("  Use: General purpose, large datasets");

        System.out.println("\n" + ColorUtil.bold("Merge Sort:"));
        System.out.println("  ✓ Guaranteed O(n log n) all cases");
        System.out.println("  ✓ Stable (maintains order of equal elements)");
        System.out.println("  ✓ Predictable performance");
        System.out.println("  ✗ Requires O(n) extra space");
        System.out.println("  Use: When stability required, external sorting");

        ColorUtil.printBlankLine();
    }

    public static <T> void runComprehensiveBenchmark(
            List<T> data,
            Comparator<T> comparator,
            SortStrategy... strategies) {
        ColorUtil.clearScreen();
        ColorUtil.printBanner("SORTING ALGORITHM BENCHMARK");
        ColorUtil.printInfo("Dataset size: " + data.size() + " elements");
        ColorUtil.printBlankLine();

        displayAlgorithmCharacteristics();

        ColorUtil.printInfo("Running sorting algorithms...");
        List<SortResult> results = compareAlgorithms(data, comparator, strategies);

        displayComparison(results);
        displayDetailedComparison(results);
        displayRecommendation(results, data.size());
    }
}
