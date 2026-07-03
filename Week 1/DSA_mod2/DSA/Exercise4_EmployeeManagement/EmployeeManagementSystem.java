/**
 * Exercise 4: Employee Management System
 * ========================================
 * Scenario: Managing employee records efficiently using arrays.
 *
 * UNDERSTANDING ARRAY REPRESENTATION:
 * -------------------------------------
 * Arrays in Memory:
 *   - Stored in contiguous (consecutive) memory locations.
 *   - Each element occupies the same amount of memory.
 *   - Address of element at index i = base_address + (i × element_size).
 *   - This enables O(1) random access by index.
 *
 * Advantages of Arrays:
 *   1. O(1) access by index — fastest random access of any data structure.
 *   2. Cache-friendly — contiguous memory benefits CPU caching.
 *   3. Simple and low overhead — no extra pointers needed.
 *   4. Efficient iteration — sequential memory access is fast.
 *
 * Limitations of Arrays:
 *   1. Fixed size (in static arrays) — cannot grow dynamically.
 *   2. Insertion/Deletion in middle requires shifting → O(n).
 *   3. Wasted space if not fully utilized.
 *   4. No built-in key-based lookup.
 */

class Employee {
    private String employeeId;
    private String name;
    private String position;
    private double salary;

    public Employee(String employeeId, String name, String position, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    public String getEmployeeId() { return employeeId; }
    public String getName() { return name; }
    public String getPosition() { return position; }
    public double getSalary() { return salary; }

    public void setName(String name) { this.name = name; }
    public void setPosition(String position) { this.position = position; }
    public void setSalary(double salary) { this.salary = salary; }

    @Override
    public String toString() {
        return String.format("Employee[ID=%s, Name=%s, Position=%s, Salary=$%.2f]",
                employeeId, name, position, salary);
    }
}

public class EmployeeManagementSystem {

    private Employee[] employees; // Fixed-size array
    private int count;            // Current number of employees
    private static final int MAX_SIZE = 100;

    public EmployeeManagementSystem() {
        employees = new Employee[MAX_SIZE];
        count = 0;
    }

    // ===================== ADD EMPLOYEE =====================
    // Time Complexity: O(1) - adds at the end
    public void addEmployee(Employee employee) {
        if (count >= MAX_SIZE) {
            System.out.println("✘ Array is full. Cannot add more employees.");
            return;
        }
        // Check for duplicate ID
        for (int i = 0; i < count; i++) {
            if (employees[i].getEmployeeId().equals(employee.getEmployeeId())) {
                System.out.println("⚠ Employee with ID " + employee.getEmployeeId() + " already exists.");
                return;
            }
        }
        employees[count] = employee;
        count++;
        System.out.println("✔ Added: " + employee);
    }

    // ===================== SEARCH EMPLOYEE =====================
    // Time Complexity: O(n) - linear search through array
    public Employee searchEmployee(String employeeId) {
        for (int i = 0; i < count; i++) {
            if (employees[i].getEmployeeId().equals(employeeId)) {
                System.out.println("✔ Found at index " + i + ": " + employees[i]);
                return employees[i];
            }
        }
        System.out.println("✘ Employee with ID " + employeeId + " not found.");
        return null;
    }

    // ===================== TRAVERSE (Display All) =====================
    // Time Complexity: O(n) - visits every element
    public void traverseEmployees() {
        if (count == 0) {
            System.out.println("No employees in the system.");
            return;
        }
        System.out.println("\n===== EMPLOYEE RECORDS =====");
        for (int i = 0; i < count; i++) {
            System.out.println("  [" + i + "] " + employees[i]);
        }
        System.out.println("Total employees: " + count + " / " + MAX_SIZE);
        System.out.println("============================\n");
    }

    // ===================== DELETE EMPLOYEE =====================
    // Time Complexity: O(n) - needs to find element and shift remaining elements
    public void deleteEmployee(String employeeId) {
        int index = -1;
        for (int i = 0; i < count; i++) {
            if (employees[i].getEmployeeId().equals(employeeId)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("✘ Employee with ID " + employeeId + " not found.");
            return;
        }

        System.out.println("✔ Deleted: " + employees[index]);

        // Shift all subsequent elements one position to the left
        for (int i = index; i < count - 1; i++) {
            employees[i] = employees[i + 1];
        }
        employees[count - 1] = null; // Clear the last slot
        count--;
    }

    // ===================== UPDATE EMPLOYEE =====================
    // Time Complexity: O(n) - needs to find the element first
    public void updateEmployee(String employeeId, String newName, String newPosition, double newSalary) {
        Employee emp = searchEmployee(employeeId);
        if (emp != null) {
            emp.setName(newName);
            emp.setPosition(newPosition);
            emp.setSalary(newSalary);
            System.out.println("✔ Updated: " + emp);
        }
    }

    // ===================== MAIN METHOD =====================
    public static void main(String[] args) {
        System.out.println("===== Exercise 4: Employee Management System =====\n");

        EmployeeManagementSystem ems = new EmployeeManagementSystem();

        // Adding employees
        System.out.println("--- Adding Employees ---");
        ems.addEmployee(new Employee("EMP001", "John Doe", "Software Engineer", 85000.00));
        ems.addEmployee(new Employee("EMP002", "Jane Smith", "Project Manager", 95000.00));
        ems.addEmployee(new Employee("EMP003", "Mike Johnson", "Data Analyst", 72000.00));
        ems.addEmployee(new Employee("EMP004", "Sarah Williams", "UX Designer", 78000.00));
        ems.addEmployee(new Employee("EMP005", "Tom Brown", "DevOps Engineer", 90000.00));

        // Traverse
        ems.traverseEmployees();

        // Search
        System.out.println("--- Searching Employees ---");
        ems.searchEmployee("EMP003");
        ems.searchEmployee("EMP999");

        // Update
        System.out.println("\n--- Updating Employee ---");
        ems.updateEmployee("EMP003", "Mike Johnson", "Senior Data Analyst", 82000.00);

        // Delete
        System.out.println("\n--- Deleting Employee ---");
        ems.deleteEmployee("EMP002");

        // Final state
        ems.traverseEmployees();

        // ===================== ANALYSIS =====================
        System.out.println("===== TIME COMPLEXITY ANALYSIS =====");
        System.out.println("┌──────────────┬──────────────────┬──────────────────┐");
        System.out.println("│  Operation   │  Time Complexity │  Explanation     │");
        System.out.println("├──────────────┼──────────────────┼──────────────────┤");
        System.out.println("│  Add (end)   │  O(1)*           │  Append at end   │");
        System.out.println("│  Search      │  O(n)            │  Linear scan     │");
        System.out.println("│  Traverse    │  O(n)            │  Visit all       │");
        System.out.println("│  Delete      │  O(n)            │  Find + shift    │");
        System.out.println("│  Update      │  O(n)            │  Find + modify   │");
        System.out.println("│  Access[i]   │  O(1)            │  Direct index    │");
        System.out.println("└──────────────┴──────────────────┴──────────────────┘");
        System.out.println("  * O(n) if checking for duplicates\n");
        System.out.println("LIMITATIONS & WHEN TO USE:");
        System.out.println("  → Arrays are ideal when:");
        System.out.println("    - The maximum size is known in advance.");
        System.out.println("    - Random access by index is frequently needed.");
        System.out.println("    - Memory efficiency is important (no pointer overhead).");
        System.out.println("  → Consider alternatives when:");
        System.out.println("    - Frequent insertions/deletions in the middle (use LinkedList).");
        System.out.println("    - The size is unknown or varies greatly (use ArrayList).");
        System.out.println("    - Fast key-based lookup is needed (use HashMap).");
    }
}
