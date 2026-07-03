import java.util.HashMap;
import java.util.Map;

/**
 * Exercise 7: Financial Forecasting
 * ====================================
 * Scenario: A financial forecasting tool that predicts future values
 * based on past data using recursive algorithms.
 *
 * UNDERSTANDING RECURSIVE ALGORITHMS:
 * -------------------------------------
 * Recursion is a technique where a method calls itself to solve smaller
 * instances of the same problem until it reaches a base case.
 *
 * Structure of a Recursive Algorithm:
 *   1. BASE CASE    → The simplest instance that can be solved directly (stops recursion).
 *   2. RECURSIVE CASE → Breaks the problem into a smaller sub-problem and calls itself.
 *
 * How Recursion Simplifies Problems:
 *   - Compound interest: FutureValue(n) = FutureValue(n-1) × (1 + growthRate)
 *     Instead of a complex formula, we express it as "this year's value is
 *     last year's value grown by one period." Recursion naturally models this.
 *   - Problems like tree traversal, factorial, Fibonacci, and divide-and-conquer
 *     algorithms (Merge Sort, Quick Sort) are elegantly expressed with recursion.
 *
 * Visual Example (Compound Growth over 3 years):
 *
 *   futureValue(1000, 0.10, 3)
 *     └─→ futureValue(1000, 0.10, 2) × 1.10
 *           └─→ futureValue(1000, 0.10, 1) × 1.10
 *                 └─→ futureValue(1000, 0.10, 0) × 1.10
 *                       └─→ BASE CASE: return 1000.0
 *                     = 1000.0 × 1.10 = 1100.0
 *               = 1100.0 × 1.10 = 1210.0
 *         = 1210.0 × 1.10 = 1331.0
 *
 * FORMULA:
 *   FutureValue = PresentValue × (1 + growthRate)^years
 *   Recursively: FV(pv, r, n) = FV(pv, r, n-1) × (1 + r)
 *   Base case:   FV(pv, r, 0) = pv
 */
public class FinancialForecasting {

    // Counter to track recursive calls (for analysis demonstration)
    private static int recursiveCallCount = 0;

    // =====================================================================
    // METHOD 1: SIMPLE RECURSION (No Optimization)
    // =====================================================================
    /**
     * Calculates future value using simple recursion.
     *
     * @param presentValue  The current/initial value (e.g., investment amount)
     * @param growthRate    The growth rate per period (e.g., 0.10 for 10%)
     * @param years         Number of periods to forecast into the future
     * @return              The predicted future value
     *
     * TIME COMPLEXITY:  O(n) — one recursive call per year
     * SPACE COMPLEXITY: O(n) — call stack depth equals the number of years
     */
    public static double futureValueRecursive(double presentValue, double growthRate, int years) {
        recursiveCallCount++;

        // BASE CASE: No more years to forecast
        if (years == 0) {
            return presentValue;
        }

        // RECURSIVE CASE: This year's value = last year's value × (1 + rate)
        return futureValueRecursive(presentValue, growthRate, years - 1) * (1 + growthRate);
    }

    // =====================================================================
    // METHOD 2: MEMOIZED RECURSION (Optimized — avoids recomputation)
    // =====================================================================
    /**
     * When the same recursive sub-problems are solved multiple times
     * (e.g., forecasting multiple scenarios from the same base),
     * memoization caches results to avoid redundant computation.
     *
     * TIME COMPLEXITY:  O(n) for first call, O(1) for cached lookups
     * SPACE COMPLEXITY: O(n) for cache + O(n) for call stack
     */
    private static Map<Integer, Double> memo = new HashMap<>();
    private static int memoCallCount = 0;

    public static double futureValueMemoized(double presentValue, double growthRate, int years) {
        memoCallCount++;

        // Check cache first
        if (memo.containsKey(years)) {
            return memo.get(years);
        }

        // BASE CASE
        if (years == 0) {
            memo.put(0, presentValue);
            return presentValue;
        }

        // RECURSIVE CASE with caching
        double result = futureValueMemoized(presentValue, growthRate, years - 1) * (1 + growthRate);
        memo.put(years, result);
        return result;
    }

    // =====================================================================
    // METHOD 3: TAIL-RECURSIVE / ITERATIVE (Most Optimized)
    // =====================================================================
    /**
     * Tail-recursive version where the recursive call is the last operation.
     * This can be optimized by the compiler into a loop (tail-call optimization).
     * Java doesn't natively support TCO, so we also provide the iterative version.
     *
     * TIME COMPLEXITY:  O(n)
     * SPACE COMPLEXITY: O(1) for iterative / O(n) for tail-recursive in Java
     */
    public static double futureValueTailRecursive(double currentValue, double growthRate, int yearsRemaining) {
        // BASE CASE
        if (yearsRemaining == 0) {
            return currentValue;
        }
        // TAIL RECURSIVE: result is passed forward as accumulator
        return futureValueTailRecursive(currentValue * (1 + growthRate), growthRate, yearsRemaining - 1);
    }

    // Iterative equivalent (O(n) time, O(1) space — truly optimal)
    public static double futureValueIterative(double presentValue, double growthRate, int years) {
        double value = presentValue;
        for (int i = 0; i < years; i++) {
            value *= (1 + growthRate);
        }
        return value;
    }

    // =====================================================================
    // BONUS: VARIABLE GROWTH RATES (Different rate each year)
    // =====================================================================
    /**
     * Real-world growth rates vary year to year. This recursive method
     * uses an array of historical growth rates to forecast.
     *
     * @param presentValue  Starting value
     * @param growthRates   Array of growth rates for each year
     * @param yearIndex     Current year index (starts at 0)
     * @return              Future value after applying all growth rates
     *
     * TIME COMPLEXITY: O(n) where n = growthRates.length
     */
    public static double futureValueVariableRates(double presentValue, double[] growthRates, int yearIndex) {
        // BASE CASE: All growth rates applied
        if (yearIndex >= growthRates.length) {
            return presentValue;
        }

        // RECURSIVE CASE: Apply this year's rate, then recurse for next year
        double grownValue = presentValue * (1 + growthRates[yearIndex]);
        return futureValueVariableRates(grownValue, growthRates, yearIndex + 1);
    }

    // =====================================================================
    // HELPER: Format currency
    // =====================================================================
    private static String formatCurrency(double amount) {
        return String.format("$%,.2f", amount);
    }

    // =====================================================================
    // MAIN METHOD
    // =====================================================================
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║       Exercise 7: Financial Forecasting                 ║");
        System.out.println("║       Using Recursive Algorithms                        ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");

        double presentValue = 10000.00;  // $10,000 initial investment
        double growthRate = 0.08;        // 8% annual growth rate
        int years = 10;                  // Forecast for 10 years

        System.out.println("INPUT PARAMETERS:");
        System.out.println("  Present Value : " + formatCurrency(presentValue));
        System.out.println("  Growth Rate   : " + (growthRate * 100) + "% per year");
        System.out.println("  Forecast Period: " + years + " years");
        System.out.println();

        // ===== METHOD 1: Simple Recursion =====
        System.out.println("═══════════════════════════════════════════");
        System.out.println("  METHOD 1: Simple Recursion");
        System.out.println("═══════════════════════════════════════════");
        recursiveCallCount = 0;
        double result1 = futureValueRecursive(presentValue, growthRate, years);
        System.out.println("  Future Value  : " + formatCurrency(result1));
        System.out.println("  Recursive Calls: " + recursiveCallCount);
        System.out.println();

        // Show year-by-year breakdown
        System.out.println("  Year-by-Year Breakdown:");
        System.out.println("  ┌────────┬───────────────────┬──────────────────┐");
        System.out.println("  │  Year  │  Value            │  Growth          │");
        System.out.println("  ├────────┼───────────────────┼──────────────────┤");
        for (int y = 0; y <= years; y++) {
            double val = futureValueRecursive(presentValue, growthRate, y);
            String growth = (y == 0) ? "    —" : formatCurrency(val - futureValueRecursive(presentValue, growthRate, y - 1));
            System.out.printf("  │  %-4d  │  %-15s  │  %-14s  │%n", y, formatCurrency(val), growth);
        }
        System.out.println("  └────────┴───────────────────┴──────────────────┘");
        System.out.println();

        // ===== METHOD 2: Memoized Recursion =====
        System.out.println("═══════════════════════════════════════════");
        System.out.println("  METHOD 2: Memoized Recursion");
        System.out.println("═══════════════════════════════════════════");
        memo.clear();
        memoCallCount = 0;
        double result2 = futureValueMemoized(presentValue, growthRate, years);
        System.out.println("  Future Value  : " + formatCurrency(result2));
        System.out.println("  Recursive Calls: " + memoCallCount);

        // Demonstrate cache benefit: query year 5 (already computed)
        memoCallCount = 0;
        double cachedResult = futureValueMemoized(presentValue, growthRate, 5);
        System.out.println("  Querying Year 5 (cached): " + formatCurrency(cachedResult));
        System.out.println("  Calls for cached query : " + memoCallCount + " (instant lookup!)");
        System.out.println();

        // ===== METHOD 3: Tail Recursive & Iterative =====
        System.out.println("═══════════════════════════════════════════");
        System.out.println("  METHOD 3: Tail Recursive & Iterative");
        System.out.println("═══════════════════════════════════════════");
        double result3 = futureValueTailRecursive(presentValue, growthRate, years);
        double result4 = futureValueIterative(presentValue, growthRate, years);
        System.out.println("  Tail Recursive: " + formatCurrency(result3));
        System.out.println("  Iterative     : " + formatCurrency(result4));
        System.out.println("  (Both produce the same result with O(1) effective space)");
        System.out.println();

        // ===== BONUS: Variable Growth Rates =====
        System.out.println("═══════════════════════════════════════════");
        System.out.println("  BONUS: Variable Growth Rates");
        System.out.println("═══════════════════════════════════════════");
        double[] historicalRates = {0.12, 0.08, -0.03, 0.15, 0.07, 0.10, -0.05, 0.09, 0.11, 0.06};
        System.out.println("  Historical growth rates (past 10 years):");
        System.out.print("  [");
        for (int i = 0; i < historicalRates.length; i++) {
            System.out.printf("%.0f%%", historicalRates[i] * 100);
            if (i < historicalRates.length - 1) System.out.print(", ");
        }
        System.out.println("]");
        double resultVariable = futureValueVariableRates(presentValue, historicalRates, 0);
        System.out.println("  Future Value with variable rates: " + formatCurrency(resultVariable));
        System.out.println();

        // ===== MULTIPLE SCENARIO COMPARISON =====
        System.out.println("═══════════════════════════════════════════");
        System.out.println("  SCENARIO COMPARISON (20-year forecast)");
        System.out.println("═══════════════════════════════════════════");
        double[] scenarios = {0.05, 0.08, 0.10, 0.12, 0.15};
        int forecastYears = 20;
        System.out.println("  ┌──────────────┬───────────────────┬───────────────────┐");
        System.out.println("  │  Growth Rate │  Future Value     │  Total Return     │");
        System.out.println("  ├──────────────┼───────────────────┼───────────────────┤");
        for (double rate : scenarios) {
            double fv = futureValueRecursive(presentValue, rate, forecastYears);
            double totalReturn = ((fv - presentValue) / presentValue) * 100;
            System.out.printf("  │  %5.1f%%      │  %-15s  │  %,10.1f%%       │%n",
                    rate * 100, formatCurrency(fv), totalReturn);
        }
        System.out.println("  └──────────────┴───────────────────┴───────────────────┘");
        System.out.println();

        // ===================== ANALYSIS =====================
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                TIME COMPLEXITY ANALYSIS                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("  ┌─────────────────────┬──────────┬──────────┐");
        System.out.println("  │  Method             │  Time    │  Space   │");
        System.out.println("  ├─────────────────────┼──────────┼──────────┤");
        System.out.println("  │  Simple Recursion   │  O(n)    │  O(n)    │");
        System.out.println("  │  Memoized Recursion │  O(n)*   │  O(n)    │");
        System.out.println("  │  Tail Recursion     │  O(n)    │  O(n)†   │");
        System.out.println("  │  Iterative          │  O(n)    │  O(1) ✔  │");
        System.out.println("  │  Direct Formula     │  O(1)‡   │  O(1)    │");
        System.out.println("  └─────────────────────┴──────────┴──────────┘");
        System.out.println("  * O(1) for repeated lookups (cached)");
        System.out.println("  † O(1) with Tail-Call Optimization (not in Java)");
        System.out.println("  ‡ Using Math.pow: PV × (1+r)^n");
        System.out.println();
        System.out.println("  OPTIMIZATION STRATEGIES TO AVOID EXCESSIVE COMPUTATION:");
        System.out.println("  ──────────────────────────────────────────────────────────");
        System.out.println("  1. MEMOIZATION (Top-Down DP):");
        System.out.println("     → Cache results of sub-problems in a HashMap/array.");
        System.out.println("     → Eliminates redundant calls when querying multiple years.");
        System.out.println("     → Especially useful when same forecasts are needed repeatedly.");
        System.out.println();
        System.out.println("  2. TABULATION (Bottom-Up DP):");
        System.out.println("     → Build results iteratively from year 0 to year n.");
        System.out.println("     → Avoids recursion overhead entirely (no stack overflow risk).");
        System.out.println();
        System.out.println("  3. TAIL RECURSION:");
        System.out.println("     → Restructure recursion so the recursive call is the LAST");
        System.out.println("       operation. Languages with TCO convert this to a loop.");
        System.out.println();
        System.out.println("  4. DIRECT FORMULA (Math.pow):");
        System.out.println("     → FV = PV × (1 + r)^n — computed in O(1) time.");
        System.out.printf("     → Example: %s × (1 + %.2f)^%d = %s%n",
                formatCurrency(presentValue), growthRate, years,
                formatCurrency(presentValue * Math.pow(1 + growthRate, years)));
        System.out.println("     → Best when only the final value is needed.");
        System.out.println();
        System.out.println("  5. AVOID DEEP RECURSION:");
        System.out.println("     → Java's default stack size limits recursion depth (~5,000-10,000).");
        System.out.println("     → For very long forecasts (100+ years), use iterative approach.");
    }
}
