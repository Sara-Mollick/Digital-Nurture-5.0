/**
 * Exercise 1: Inventory Management System
 * =========================================
 * Scenario: Developing an inventory management system for a warehouse.
 *
 * UNDERSTANDING THE PROBLEM:
 * --------------------------
 * Why Data Structures & Algorithms are essential for large inventories:
 *   - Warehouses can contain millions of products. Without efficient data structures,
 *     searching for a product would require scanning every record (O(n)).
 *   - Proper data structures enable O(1) lookups, O(log n) searches, and efficient
 *     memory usage, which is critical for real-time inventory tracking.
 *   - Algorithms ensure operations like restocking, order fulfillment, and
 *     reporting are performed quickly even at scale.
 *
 * Suitable Data Structures:
 *   - HashMap: O(1) average for add/search/delete by productId (CHOSEN)
 *   - ArrayList: Good for ordered access, but O(n) search by productId
 *   - TreeMap: O(log n) operations with sorted keys
 *   - HashSet: O(1) lookup but no key-value mapping
 */

public class Product {
    private String productId;
    private String productName;
    private int quantity;
    private double price;

    // Constructor
    public Product(String productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Product[ID=%s, Name=%s, Qty=%d, Price=%.2f]",
                productId, productName, quantity, price);
    }
}
