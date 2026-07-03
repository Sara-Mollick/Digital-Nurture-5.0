import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Exercise 3: Sorting Customer Orders
 * =====================================
 * Scenario: Sorting customer orders by totalPrice on an e-commerce platform
 * to prioritize high-value orders.
 *
 * UNDERSTANDING SORTING ALGORITHMS:
 * -----------------------------------
 * 1. Bubble Sort: Repeatedly swaps adjacent elements if they are in wrong order.
 *    - Simple but inefficient for large datasets.
 *
 * 2. Insertion Sort: Builds sorted array one element at a time by inserting
 *    each element into its correct position.
 *    - Efficient for small or nearly sorted datasets.
 *
 * 3. Quick Sort: Divide-and-conquer algorithm that picks a pivot and partitions
 *    elements around it.
 *    - Efficient for large datasets, widely used in practice.
 *
 * 4. Merge Sort: Divide-and-conquer algorithm that divides array in half,
 *    sorts each half, and merges them.
 *    - Guaranteed O(n log n) but uses extra space.
 */

class Order {
    private String orderId;
    private String customerName;
    private double totalPrice;

    public Order(String orderId, String customerName, double totalPrice) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
    }

    public String getOrderId() { return orderId; }
    public String getCustomerName() { return customerName; }
    public double getTotalPrice() { return totalPrice; }

    @Override
    public String toString() {
        return String.format("Order[ID=%s, Customer=%s, Total=$%.2f]",
                orderId, customerName, totalPrice);
    }
}

public class SortingCustomerOrders {

    // ===================== BUBBLE SORT =====================
    /**
     * Bubble Sort: Compares adjacent elements and swaps them if in wrong order.
     * Repeats until no more swaps are needed.
     *
     * Time Complexity:
     *   Best Case:    O(n)    - Already sorted (with optimization flag)
     *   Average Case: O(n²)   - Random order
     *   Worst Case:   O(n²)   - Reverse sorted
     *
     * Space Complexity: O(1) - In-place sorting
     * Stability: Stable (preserves relative order of equal elements)
     */
    public static void bubbleSort(List<Order> orders) {
        int n = orders.size();
        int swapCount = 0;
        int comparisons = 0;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                comparisons++;
                if (orders.get(j).getTotalPrice() > orders.get(j + 1).getTotalPrice()) {
                    // Swap
                    Order temp = orders.get(j);
                    orders.set(j, orders.get(j + 1));
                    orders.set(j + 1, temp);
                    swapped = true;
                    swapCount++;
                }
            }

            // Optimization: If no swaps occurred, array is already sorted
            if (!swapped) break;
        }

        System.out.println("  [Bubble Sort] Comparisons: " + comparisons + ", Swaps: " + swapCount);
    }

    // ===================== QUICK SORT =====================
    /**
     * Quick Sort: Picks a pivot element and partitions the array so that
     * elements less than pivot are on the left, greater on the right.
     * Then recursively sorts the sub-arrays.
     *
     * Time Complexity:
     *   Best Case:    O(n log n) - Pivot divides array evenly
     *   Average Case: O(n log n) - Random pivot selection
     *   Worst Case:   O(n²)      - Pivot is always min/max (sorted input)
     *
     * Space Complexity: O(log n) for recursive call stack
     * Stability: Not stable
     */
    private static int comparisonsQS = 0;
    private static int swapsQS = 0;

    public static void quickSort(List<Order> orders, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(orders, low, high);
            quickSort(orders, low, pivotIndex - 1);
            quickSort(orders, pivotIndex + 1, high);
        }
    }

    private static int partition(List<Order> orders, int low, int high) {
        // Choose last element as pivot
        double pivot = orders.get(high).getTotalPrice();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            comparisonsQS++;
            if (orders.get(j).getTotalPrice() <= pivot) {
                i++;
                // Swap
                Order temp = orders.get(i);
                orders.set(i, orders.get(j));
                orders.set(j, temp);
                swapsQS++;
            }
        }

        // Place pivot in correct position
        Order temp = orders.get(i + 1);
        orders.set(i + 1, orders.get(high));
        orders.set(high, temp);
        swapsQS++;

        return i + 1;
    }

    // ===================== HELPER: PRINT ORDERS =====================
    public static void printOrders(List<Order> orders) {
        for (Order order : orders) {
            System.out.println("  " + order);
        }
    }

    // ===================== MAIN METHOD =====================
    public static void main(String[] args) {
        System.out.println("===== Exercise 3: Sorting Customer Orders =====\n");

        // Original data
        List<Order> originalOrders = Arrays.asList(
            new Order("ORD001", "Alice Johnson", 250.00),
            new Order("ORD002", "Bob Smith", 89.99),
            new Order("ORD003", "Charlie Brown", 1500.00),
            new Order("ORD004", "Diana Prince", 45.50),
            new Order("ORD005", "Eve Wilson", 720.00),
            new Order("ORD006", "Frank Castle", 199.99),
            new Order("ORD007", "Grace Hopper", 3200.00),
            new Order("ORD008", "Henry Ford", 550.00)
        );

        System.out.println("--- Original Orders ---");
        printOrders(originalOrders);

        // ===== BUBBLE SORT =====
        System.out.println("\n--- Bubble Sort by Total Price ---");
        List<Order> bubbleSortList = new ArrayList<>(originalOrders);
        long startTime = System.nanoTime();
        bubbleSort(bubbleSortList);
        long bubbleTime = System.nanoTime() - startTime;
        printOrders(bubbleSortList);

        // ===== QUICK SORT =====
        System.out.println("\n--- Quick Sort by Total Price ---");
        List<Order> quickSortList = new ArrayList<>(originalOrders);
        comparisonsQS = 0;
        swapsQS = 0;
        startTime = System.nanoTime();
        quickSort(quickSortList, 0, quickSortList.size() - 1);
        long quickTime = System.nanoTime() - startTime;
        System.out.println("  [Quick Sort] Comparisons: " + comparisonsQS + ", Swaps: " + swapsQS);
        printOrders(quickSortList);

        // ===================== ANALYSIS =====================
        System.out.println("\n===== PERFORMANCE COMPARISON =====");
        System.out.println("┌──────────────┬─────────────┬──────────────┬──────────────┬──────────┐");
        System.out.println("│  Algorithm   │  Best Case  │  Average     │  Worst Case  │  Stable  │");
        System.out.println("├──────────────┼─────────────┼──────────────┼──────────────┼──────────┤");
        System.out.println("│  Bubble Sort │  O(n)       │  O(n²)       │  O(n²)       │  Yes     │");
        System.out.println("│  Quick Sort  │  O(n log n) │  O(n log n)  │  O(n²)       │  No      │");
        System.out.println("└──────────────┴─────────────┴──────────────┴──────────────┴──────────┘");
        System.out.println();
        System.out.printf("  Bubble Sort time: %,d ns%n", bubbleTime);
        System.out.printf("  Quick Sort time:  %,d ns%n", quickTime);
        System.out.println();
        System.out.println("WHY QUICK SORT IS PREFERRED OVER BUBBLE SORT:");
        System.out.println("  1. Average case O(n log n) vs O(n²) — exponentially faster.");
        System.out.println("  2. For 10,000 orders: Quick Sort ≈ 130K ops, Bubble Sort ≈ 100M ops.");
        System.out.println("  3. Quick Sort has excellent cache performance (in-place, sequential access).");
        System.out.println("  4. Widely used in practice (Java's Arrays.sort uses Dual-Pivot QuickSort).");
        System.out.println("  5. Bubble Sort's only advantage is simplicity and stability,");
        System.out.println("     but Merge Sort is a better stable alternative at O(n log n).");
    }
}
