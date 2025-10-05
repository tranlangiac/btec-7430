package org.abk.student.management.system.utils;

import org.abk.student.management.system.algorithms.searching.SearchStrategy;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Utility class for comparing searching algorithm performance.
 * <p>
 * This class helps analyze and compare different searching algorithms
 * by tracking their performance metrics and displaying results in
 * formatted tables.
 *
 * @author Soft Development ABK
 * @version 1.0
 */
public class SearchComparisonUtil {
    private SearchComparisonUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static class SearchResult {
        private final String algorithmName;
        private final long comparisons;
        private final long executionTime;
        private final String timeComplexity;
        private final String spaceComplexity;
        private final boolean requiresSorted;
        private final boolean found;
        private final int index;

        public SearchResult(SearchStrategy strategy, boolean found, int index) {
            this.algorithmName = strategy.getAlgorithmName();
            this.comparisons = strategy.getComparisonCount();
            this.executionTime = strategy.getExecutionTime();
            this.timeComplexity = strategy.getTimeComplexity();
            this.spaceComplexity = strategy.getSpaceComplexity();
            this.requiresSorted = strategy.requiresSortedList();
            this.found = found;
            this.index = index;
        }

        public String getAlgorithmName() { return algorithmName; }
        public long getComparisons() { return comparisons; }
        public long getExecutionTime() { return executionTime; }
        public String getTimeComplexity() { return timeComplexity; }
        public String getSpaceComplexity() { return spaceComplexity; }
        public boolean requiresSorted() { return requiresSorted; }
        public boolean isFound() { return found; }
        public int getIndex() { return index; }
    }

    public static <T> List<SearchResult> compareAlgorithms(
            List<T> data,
            T target,
            Comparator<T> comparator,
            SearchStrategy... strategies) {

        List<SearchResult> results = new ArrayList<>();

        for (SearchStrategy strategy : strategies) {
            int index = strategy.searchIndex(data, target, comparator);
            boolean found = index != -1;

            results.add(new SearchResult(strategy, found, index));
        }

        return results;
    }

    public static void displayComparison(List<SearchResult> results) {
        if (results == null || results.isEmpty()) {
            ColorUtil.printWarning("No results to display");
            return;
        }

        AsciiTable table = new AsciiTable();

        table.addRule();
        table.addRow("Algorithm", "Found", "Index", "Time (ms)", "Comparisons",
                "Requires Sorted", "Time Complexity");
        table.addRule();

        for (SearchResult result : results) {
            table.addRow(
                    result.getAlgorithmName(),
                    result.isFound() ? "✓" : "✗",
                    result.isFound() ? result.getIndex() : "N/A",
                    result.getExecutionTime(),
                    result.getComparisons(),
                    result.requiresSorted() ? "Yes" : "No",
                    result.getTimeComplexity()
            );
        }
        table.addRule();

        table.getRenderer().setCWC(new CWC_LongestLine());

        ColorUtil.printHeader("SEARCHING ALGORITHM COMPARISON");
        System.out.println(table.render());
    }

    public static void displayDetailedComparison(List<SearchResult> results) {
        if (results == null || results.isEmpty()) {
            ColorUtil.printWarning("No results to display");
            return;
        }

        SearchResult fastestTime = results.stream()
                .filter(SearchResult::isFound)
                .min(Comparator.comparingLong(SearchResult::getExecutionTime))
                .orElse(null);

        SearchResult fewestComparisons = results.stream()
                .filter(SearchResult::isFound)
                .min(Comparator.comparingLong(SearchResult::getComparisons))
                .orElse(null);

        ColorUtil.printHeader("DETAILED SEARCH COMPARISON");

        for (SearchResult result : results) {
            System.out.println(ColorUtil.bold(result.getAlgorithmName()));
            System.out.println("─".repeat(50));

            // Found status
            if (result.isFound()) {
                System.out.println("  Status:            " + ColorUtil.success("FOUND at index " + result.getIndex()));
            } else {
                System.out.println("  Status:            " + ColorUtil.error("NOT FOUND"));
            }

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

            System.out.println("  Requires Sorted:   " + (result.requiresSorted() ? "Yes" : "No"));
            System.out.println("  Time Complexity:   " + result.getTimeComplexity());
            System.out.println("  Space Complexity:  " + result.getSpaceComplexity());
            ColorUtil.printBlankLine();
        }
    }

    public static void displayRecommendation(List<SearchResult> results, int dataSize, boolean isSorted) {
        ColorUtil.printHeader("RECOMMENDATION");

        if (!isSorted) {
            ColorUtil.printWarning("Data is NOT sorted:");
            System.out.println("  • You MUST use " + ColorUtil.bold("Linear Search"));
            System.out.println("  • Binary Search requires sorted data");
            System.out.println("  • Consider sorting if you'll search frequently");
        } else if (dataSize < 100) {
            ColorUtil.printInfo("For small sorted datasets (< 100 elements):");
            System.out.println("  • " + ColorUtil.bold("Linear Search") +
                    " is acceptable due to small size");
            System.out.println("  • " + ColorUtil.bold("Binary Search") +
                    " may have overhead for very small data");
        } else if (dataSize < 1000) {
            ColorUtil.printInfo("For medium sorted datasets (100-1000 elements):");
            System.out.println("  • " + ColorUtil.bold("Binary Search") +
                    " is recommended (much faster)");
            System.out.println("  • O(log n) is significantly better than O(n)");
        } else {
            ColorUtil.printInfo("For large sorted datasets (> 1000 elements):");
            System.out.println("  • " + ColorUtil.bold("Binary Search") +
                    " is strongly recommended");
            System.out.println("  • O(log n) is exponentially faster than O(n)");
            System.out.println("  • Example: 1000 elements → Binary needs ~10 comparisons vs Linear's 500");
        }

        ColorUtil.printBlankLine();

        results.stream()
                .filter(SearchResult::isFound)
                .min(Comparator.comparingLong(SearchResult::getComparisons))
                .ifPresent(winner -> ColorUtil.printSuccess("Most Efficient: "
                        + winner.getAlgorithmName()
                        + " (" + winner.getComparisons() + " comparisons)"));
    }

    public static void displayAlgorithmCharacteristics() {
        ColorUtil.printHeader("ALGORITHM CHARACTERISTICS");

        System.out.println(ColorUtil.bold("Linear Search:"));
        System.out.println("  ✓ Simple to understand and implement");
        System.out.println("  ✓ Works on unsorted lists");
        System.out.println("  ✓ No preprocessing required");
        System.out.println("  ✗ Slow for large datasets (O(n))");
        System.out.println("  ✗ Checks every element in worst case");
        System.out.println("  Use: Small/unsorted data, one-time searches");

        ColorUtil.printBlankLine();

        System.out.println(ColorUtil.bold("Binary Search:"));
        System.out.println("  ✓ Very fast (O(log n))");
        System.out.println("  ✓ Efficient for large datasets");
        System.out.println("  ✓ Eliminates half of data each step");
        System.out.println("  ✗ Requires sorted list");
        System.out.println("  ✗ More complex implementation");
        System.out.println("  Use: Large sorted data, frequent searches");

        ColorUtil.printBlankLine();
    }

    public static <T> void runComprehensiveBenchmark(
            List<T> data,
            T target,
            Comparator<T> comparator,
            boolean isSorted,
            SearchStrategy... strategies) {

        ColorUtil.printBanner("SEARCHING ALGORITHM BENCHMARK");
        ColorUtil.printInfo("Dataset size: " + data.size() + " elements");
        ColorUtil.printInfo("Data sorted: " + (isSorted ? "Yes" : "No"));
        ColorUtil.printBlankLine();

        displayAlgorithmCharacteristics();

        ColorUtil.printInfo("Running searching algorithms...");
        List<SearchResult> results = compareAlgorithms(data, target, comparator, strategies);

        displayComparison(results);
        displayDetailedComparison(results);
        displayRecommendation(results, data.size(), isSorted);
    }
}
