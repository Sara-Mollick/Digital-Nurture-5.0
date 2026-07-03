/**
 * Exercise 5: Task Management System
 * =====================================
 * Scenario: A task management system where tasks need to be added, deleted,
 * and traversed efficiently.
 *
 * UNDERSTANDING LINKED LISTS:
 * ----------------------------
 * Types of Linked Lists:
 *
 * 1. Singly Linked List:
 *    - Each node has data and a pointer to the NEXT node.
 *    - Traversal: Forward only.
 *    - Memory: One pointer per node.
 *    [Head] → [A|→] → [B|→] → [C|→] → null
 *
 * 2. Doubly Linked List:
 *    - Each node has data, a pointer to NEXT, and a pointer to PREVIOUS.
 *    - Traversal: Both forward and backward.
 *    - Memory: Two pointers per node.
 *    null ← [←|A|→] ↔ [←|B|→] ↔ [←|C|→] → null
 *
 * 3. Circular Linked List:
 *    - Last node points back to the first node.
 *    - No null at the end; forms a cycle.
 *    [Head] → [A|→] → [B|→] → [C|→] ──┐
 *      ↑                                │
 *      └────────────────────────────────┘
 *
 * This exercise uses a SINGLY LINKED LIST.
 */

class Task {
    private String taskId;
    private String taskName;
    private String status;

    public Task(String taskId, String taskName, String status) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.status = status;
    }

    public String getTaskId() { return taskId; }
    public String getTaskName() { return taskName; }
    public String getStatus() { return status; }

    public void setTaskName(String taskName) { this.taskName = taskName; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("Task[ID=%s, Name=%s, Status=%s]", taskId, taskName, status);
    }
}

// Node class for the Singly Linked List
class TaskNode {
    Task data;
    TaskNode next;

    public TaskNode(Task data) {
        this.data = data;
        this.next = null;
    }
}

public class TaskManagementSystem {

    private TaskNode head;
    private int size;

    public TaskManagementSystem() {
        this.head = null;
        this.size = 0;
    }

    // ===================== ADD TASK =====================
    // Add at the end of the linked list
    // Time Complexity: O(n) - traverse to end; O(1) if adding at head
    public void addTask(Task task) {
        TaskNode newNode = new TaskNode(task);

        if (head == null) {
            head = newNode;
        } else {
            // Check for duplicate ID while traversing
            TaskNode current = head;
            while (current.next != null) {
                if (current.data.getTaskId().equals(task.getTaskId())) {
                    System.out.println("⚠ Task with ID " + task.getTaskId() + " already exists.");
                    return;
                }
                current = current.next;
            }
            if (current.data.getTaskId().equals(task.getTaskId())) {
                System.out.println("⚠ Task with ID " + task.getTaskId() + " already exists.");
                return;
            }
            current.next = newNode;
        }
        size++;
        System.out.println("✔ Added: " + task);
    }

    // ===================== ADD AT HEAD =====================
    // Time Complexity: O(1)
    public void addTaskAtHead(Task task) {
        TaskNode newNode = new TaskNode(task);
        newNode.next = head;
        head = newNode;
        size++;
        System.out.println("✔ Added at head: " + task);
    }

    // ===================== SEARCH TASK =====================
    // Time Complexity: O(n) - linear traversal
    public Task searchTask(String taskId) {
        TaskNode current = head;
        int position = 0;

        while (current != null) {
            if (current.data.getTaskId().equals(taskId)) {
                System.out.println("✔ Found at position " + position + ": " + current.data);
                return current.data;
            }
            current = current.next;
            position++;
        }
        System.out.println("✘ Task with ID " + taskId + " not found.");
        return null;
    }

    // ===================== TRAVERSE =====================
    // Time Complexity: O(n) - visits every node
    public void traverseTasks() {
        if (head == null) {
            System.out.println("Task list is empty.");
            return;
        }
        System.out.println("\n===== TASK LIST (Linked List Traversal) =====");
        TaskNode current = head;
        int index = 0;
        while (current != null) {
            System.out.println("  [" + index + "] " + current.data);
            current = current.next;
            index++;
        }
        System.out.println("Total tasks: " + size);
        System.out.println("=============================================\n");
    }

    // ===================== DELETE TASK =====================
    // Time Complexity: O(n) - need to find the node and update pointers
    public void deleteTask(String taskId) {
        if (head == null) {
            System.out.println("✘ List is empty.");
            return;
        }

        // If head node itself holds the target
        if (head.data.getTaskId().equals(taskId)) {
            System.out.println("✔ Deleted: " + head.data);
            head = head.next;
            size--;
            return;
        }

        // Search for the node to delete, keeping track of previous
        TaskNode previous = head;
        TaskNode current = head.next;

        while (current != null) {
            if (current.data.getTaskId().equals(taskId)) {
                System.out.println("✔ Deleted: " + current.data);
                previous.next = current.next; // Bypass the node
                size--;
                return;
            }
            previous = current;
            current = current.next;
        }

        System.out.println("✘ Task with ID " + taskId + " not found.");
    }

    // ===================== UPDATE TASK STATUS =====================
    // Time Complexity: O(n) - need to find the node first
    public void updateTaskStatus(String taskId, String newStatus) {
        Task task = searchTask(taskId);
        if (task != null) {
            task.setStatus(newStatus);
            System.out.println("✔ Status updated: " + task);
        }
    }

    // ===================== MAIN METHOD =====================
    public static void main(String[] args) {
        System.out.println("===== Exercise 5: Task Management System =====\n");

        TaskManagementSystem tms = new TaskManagementSystem();

        // Adding tasks
        System.out.println("--- Adding Tasks ---");
        tms.addTask(new Task("T001", "Design Database Schema", "Pending"));
        tms.addTask(new Task("T002", "Implement User Authentication", "In Progress"));
        tms.addTask(new Task("T003", "Write Unit Tests", "Pending"));
        tms.addTask(new Task("T004", "Deploy to Staging", "Pending"));
        tms.addTask(new Task("T005", "Code Review", "In Progress"));

        // Traverse
        tms.traverseTasks();

        // Search
        System.out.println("--- Searching Tasks ---");
        tms.searchTask("T003");
        tms.searchTask("T999");

        // Update
        System.out.println("\n--- Updating Task Status ---");
        tms.updateTaskStatus("T001", "Completed");

        // Delete
        System.out.println("\n--- Deleting Tasks ---");
        tms.deleteTask("T003");

        // Add at head
        System.out.println("\n--- Adding Urgent Task at Head ---");
        tms.addTaskAtHead(new Task("T000", "Fix Critical Bug", "Urgent"));

        // Final state
        tms.traverseTasks();

        // ===================== ANALYSIS =====================
        System.out.println("===== TIME COMPLEXITY ANALYSIS =====");
        System.out.println("┌──────────────────┬──────────────────┬──────────────────┐");
        System.out.println("│  Operation       │  Linked List     │  Array           │");
        System.out.println("├──────────────────┼──────────────────┼──────────────────┤");
        System.out.println("│  Add at Head     │  O(1) ✔          │  O(n)            │");
        System.out.println("│  Add at End      │  O(n)*           │  O(1)            │");
        System.out.println("│  Search          │  O(n)            │  O(n)            │");
        System.out.println("│  Delete (known)  │  O(1)†           │  O(n)            │");
        System.out.println("│  Delete (search) │  O(n)            │  O(n)            │");
        System.out.println("│  Traverse        │  O(n)            │  O(n)            │");
        System.out.println("│  Access by Index │  O(n)            │  O(1) ✔          │");
        System.out.println("│  Memory          │  Extra (ptrs)    │  Compact         │");
        System.out.println("└──────────────────┴──────────────────┴──────────────────┘");
        System.out.println("  * O(1) with tail pointer    † If previous node is known\n");
        System.out.println("ADVANTAGES OF LINKED LISTS OVER ARRAYS:");
        System.out.println("  1. Dynamic size — no need to declare max capacity upfront.");
        System.out.println("  2. Efficient insertion/deletion at known positions (no shifting).");
        System.out.println("  3. No wasted memory from pre-allocated empty slots.");
        System.out.println("  4. Ideal for data that changes frequently (task queues, playlists).");
        System.out.println("  5. Natural fit for implementing stacks, queues, and graphs.");
    }
}
