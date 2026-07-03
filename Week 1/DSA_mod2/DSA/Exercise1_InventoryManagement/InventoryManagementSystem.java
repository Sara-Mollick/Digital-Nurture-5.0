import java.util.HashMap;
import java.util.Map;

/**
 * Exercise 1: Inventory Management System
 * =========================================
 * Uses a HashMap for O(1) average-case CRUD operations.
 */
public class InventoryManagementSystem {

    // HashMap chosen for O(1) average lookup/insert/delete by productId
    private Map<String, Product> inventory;

    public InventoryManagementSystem() {
        this.inventory = new HashMap<>();
    }

    // ===================== ADD PRODUCT =====================
    // Time Complexity: O(1) average, O(n) worst case (hash collision)
    public void addProduct(Product product) {
        if (inventory.containsKey(product.getProductId())) {
            System.out.println("⚠ Product with ID " + product.getProductId() + " already exists. Use update instead.");
            return;
        }
        inventory.put(product.getProductId(), product);
        System.out.println("✔ Added: " + product);
    }

    // ===================== UPDATE PRODUCT =====================
    // Time Complexity: O(1) average, O(n) worst case (hash collision)
    public void updateProduct(String productId, String newName, int newQuantity, double newPrice) {
        Product product = inventory.get(productId);
        if (product == null) {
            System.out.println("✘ Product with ID " + productId + " not found.");
            return;
        }
        product.setProductName(newName);
        product.setQuantity(newQuantity);
        product.setPrice(newPrice);
        System.out.println("✔ Updated: " + product);
    }

    // ===================== DELETE PRODUCT =====================
    // Time Complexity: O(1) average, O(n) worst case (hash collision)
    public void deleteProduct(String productId) {
        Product removed = inventory.remove(productId);
        if (removed == null) {
            System.out.println("✘ Product with ID " + productId + " not found.");
            return;
        }
        System.out.println("✔ Deleted: " + removed);
    }

    // ===================== SEARCH PRODUCT =====================
    // Time Complexity: O(1) average, O(n) worst case (hash collision)
    public Product searchProduct(String productId) {
        Product product = inventory.get(productId);
        if (product == null) {
            System.out.println("✘ Product with ID " + productId + " not found.");
        } else {
            System.out.println("✔ Found: " + product);
        }
        return product;
    }

    // ===================== DISPLAY ALL =====================
    // Time Complexity: O(n)
    public void displayAll() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        System.out.println("\n===== INVENTORY =====");
        for (Product product : inventory.values()) {
            System.out.println("  " + product);
        }
        System.out.println("Total products: " + inventory.size());
        System.out.println("=====================\n");
    }

    // ===================== MAIN METHOD =====================
    public static void main(String[] args) {
        InventoryManagementSystem ims = new InventoryManagementSystem();

        System.out.println("===== Exercise 1: Inventory Management System =====\n");

        // Adding products
        System.out.println("--- Adding Products ---");
        ims.addProduct(new Product("P001", "Laptop", 50, 999.99));
        ims.addProduct(new Product("P002", "Mouse", 200, 25.50));
        ims.addProduct(new Product("P003", "Keyboard", 150, 75.00));
        ims.addProduct(new Product("P004", "Monitor", 30, 450.00));
        ims.addProduct(new Product("P005", "Headphones", 100, 120.00));

        // Display all
        ims.displayAll();

        // Searching
        System.out.println("--- Searching Products ---");
        ims.searchProduct("P003");
        ims.searchProduct("P999"); // Not found

        // Updating
        System.out.println("\n--- Updating Products ---");
        ims.updateProduct("P002", "Wireless Mouse", 180, 35.99);

        // Deleting
        System.out.println("\n--- Deleting Products ---");
        ims.deleteProduct("P004");

        // Final display
        ims.displayAll();

        // ===================== ANALYSIS =====================
        System.out.println("===== TIME COMPLEXITY ANALYSIS =====");
        System.out.println("┌──────────────┬──────────────────┬──────────────────┐");
        System.out.println("│  Operation   │  Average Case    │  Worst Case      │");
        System.out.println("├──────────────┼──────────────────┼──────────────────┤");
        System.out.println("│  Add         │  O(1)            │  O(n)            │");
        System.out.println("│  Update      │  O(1)            │  O(n)            │");
        System.out.println("│  Delete      │  O(1)            │  O(n)            │");
        System.out.println("│  Search      │  O(1)            │  O(n)            │");
        System.out.println("└──────────────┴──────────────────┴──────────────────┘");
        System.out.println();
        System.out.println("OPTIMIZATION STRATEGIES:");
        System.out.println("  1. Use a good hash function to minimize collisions.");
        System.out.println("  2. Set initial capacity & load factor to reduce rehashing.");
        System.out.println("  3. For range queries, consider TreeMap (O(log n) ops).");
        System.out.println("  4. For multi-field search, maintain secondary indexes.");
    }
}
