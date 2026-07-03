import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Exercise 2: E-commerce Platform Search Function
 * ==================================================
 * Scenario: Search functionality for an e-commerce platform optimized for fast performance.
 *
 * UNDERSTANDING ASYMPTOTIC NOTATION (Big O):
 * -------------------------------------------
 * Big O notation describes the upper bound of an algorithm's growth rate.
 * It helps analyze how performance scales as input size (n) increases.
 *
 *   O(1)       - Constant time (HashMap lookup)
 *   O(log n)   - Logarithmic (Binary Search)
 *   O(n)       - Linear (Linear Search)
 *   O(n log n) - Linearithmic (Merge Sort, Quick Sort avg)
 *   O(n²)      - Quadratic (Bubble Sort, nested loops)
 *
 * For search operations:
 *   Best Case    → The element is found immediately (first position)
 *   Average Case → The element is found somewhere in the middle
 *   Worst Case   → The element is at the end or not present
 */

class Product2 {
    private String productId;
    private String productName;
    private String category;

    public Product2(String productId, String productName, String category) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
    }

    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getCategory() { return category; }

    @Override
    public String toString() {
        return String.format("Product[ID=%s, Name=%s, Category=%s]", productId, productName, category);
    }
}

public class EcommerceSearchSystem {

    // ===================== LINEAR SEARCH =====================
    /**
     * Linear Search: Sequentially checks each element until found.
     *
     * Time Complexity:
     *   Best Case:    O(1)   - Element is at the first position
     *   Average Case: O(n)   - Element is somewhere in the middle
     *   Worst Case:   O(n)   - Element is at the end or not present
     *
     * Space Complexity: O(1)
     */
    public static Product2 linearSearch(List<Product2> products, String targetId) {
        int comparisons = 0;
        for (Product2 product : products) {
            comparisons++;
            if (product.getProductId().equals(targetId)) {
                System.out.println("  [Linear Search] Found in " + comparisons + " comparison(s).");
                return product;
            }
        }
        System.out.println("  [Linear Search] Not found after " + comparisons + " comparison(s).");
        return null;
    }

    // ===================== BINARY SEARCH =====================
    /**
     * Binary Search: Requires a SORTED array. Repeatedly divides search interval in half.
     *
     * Time Complexity:
     *   Best Case:    O(1)     - Element is at the middle
     *   Average Case: O(log n) - Element is found after several halvings
     *   Worst Case:   O(log n) - Element is at extreme end or not present
     *
     * Space Complexity: O(1) for iterative version
     *
     * PREREQUISITE: The array MUST be sorted by productId.
     */
    public static Product2 binarySearch(List<Product2> sortedProducts, String targetId) {
        int low = 0;
        int high = sortedProducts.size() - 1;
        int comparisons = 0;

        while (low <= high) {
            comparisons++;
            int mid = low + (high - low) / 2;
            String midId = sortedProducts.get(mid).getProductId();
            int cmp = midId.compareTo(targetId);

            if (cmp == 0) {
                System.out.println("  [Binary Search] Found in " + comparisons + " comparison(s).");
                return sortedProducts.get(mid);
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        System.out.println("  [Binary Search] Not found after " + comparisons + " comparison(s).");
        return null;
    }

    // ===================== MAIN METHOD =====================
    public static void main(String[] args) {
        System.out.println("===== Exercise 2: E-commerce Platform Search Function =====\n");

        // Create product list (unsorted for linear search)
        List<Product2> products = new ArrayList<>(Arrays.asList(
            new Product2("P010", "Wireless Earbuds", "Electronics"),
            new Product2("P003", "Running Shoes", "Sports"),
            new Product2("P007", "Coffee Maker", "Kitchen"),
            new Product2("P001", "Laptop Stand", "Accessories"),
            new Product2("P015", "Yoga Mat", "Sports"),
            new Product2("P005", "Bluetooth Speaker", "Electronics"),
            new Product2("P012", "Water Bottle", "Kitchen"),
            new Product2("P008", "Desk Lamp", "Home"),
            new Product2("P002", "Phone Case", "Accessories"),
            new Product2("P020", "Backpack", "Travel")
        ));

        // === LINEAR SEARCH (works on unsorted data) ===
        System.out.println("--- Linear Search (Unsorted Array) ---");
        System.out.println("Searching for P005 (Bluetooth Speaker):");
        Product2 result1 = linearSearch(products, "P005");
        System.out.println("  Result: " + result1 + "\n");

        System.out.println("Searching for P020 (Backpack - last element):");
        Product2 result2 = linearSearch(products, "P020");
        System.out.println("  Result: " + result2 + "\n");

        System.out.println("Searching for P999 (Not in list):");
        Product2 result3 = linearSearch(products, "P999");
        System.out.println("  Result: " + result3 + "\n");

        // === BINARY SEARCH (requires sorted data) ===
        // First, sort the products by productId
        List<Product2> sortedProducts = new ArrayList<>(products);
        Collections.sort(sortedProducts, (a, b) -> a.getProductId().compareTo(b.getProductId()));

        System.out.println("--- Binary Search (Sorted Array) ---");
        System.out.println("Sorted products:");
        for (Product2 p : sortedProducts) {
            System.out.println("  " + p);
        }
        System.out.println();

        System.out.println("Searching for P005 (Bluetooth Speaker):");
        Product2 result4 = binarySearch(sortedProducts, "P005");
        System.out.println("  Result: " + result4 + "\n");

        System.out.println("Searching for P020 (Backpack):");
        Product2 result5 = binarySearch(sortedProducts, "P020");
        System.out.println("  Result: " + result5 + "\n");

        System.out.println("Searching for P999 (Not in list):");
        Product2 result6 = binarySearch(sortedProducts, "P999");
        System.out.println("  Result: " + result6 + "\n");

        // ===================== ANALYSIS =====================
        System.out.println("===== TIME COMPLEXITY COMPARISON =====");
        System.out.println("┌──────────────────┬─────────────┬──────────────┬──────────────┐");
        System.out.println("│  Algorithm       │  Best Case  │  Average     │  Worst Case  │");
        System.out.println("├──────────────────┼─────────────┼──────────────┼──────────────┤");
        System.out.println("│  Linear Search   │  O(1)       │  O(n)        │  O(n)        │");
        System.out.println("│  Binary Search   │  O(1)       │  O(log n)    │  O(log n)    │");
        System.out.println("└──────────────────┴─────────────┴──────────────┴──────────────┘");
        System.out.println();
        System.out.println("WHICH IS MORE SUITABLE?");
        System.out.println("  → Binary Search is preferred for the e-commerce platform because:");
        System.out.println("    1. Product catalogs are typically stored sorted (by ID/name).");
        System.out.println("    2. O(log n) scales much better: for 1M products, Binary Search");
        System.out.println("       needs ~20 comparisons vs 1,000,000 for Linear Search.");
        System.out.println("    3. For even faster lookups, a HashMap (O(1)) can be used.");
        System.out.println("  → Linear Search is suitable only for small, unsorted datasets");
        System.out.println("    or when the data changes too frequently to maintain sort order.");
    }
}
