import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Exercise 6: Library Management System
 * =======================================
 * Scenario: A library management system where users can search for books
 * by title or author.
 *
 * UNDERSTANDING SEARCH ALGORITHMS:
 * ----------------------------------
 * Linear Search:
 *   - Scans each element sequentially until a match is found.
 *   - Works on unsorted AND sorted data.
 *   - Time: O(n)
 *
 * Binary Search:
 *   - Divides the search space in half each step.
 *   - REQUIRES sorted data.
 *   - Time: O(log n)
 */

class Book {
    private String bookId;
    private String title;
    private String author;

    public Book(String bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
    }

    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }

    @Override
    public String toString() {
        return String.format("Book[ID=%s, Title=\"%s\", Author=%s]", bookId, title, author);
    }
}

public class LibraryManagementSystem {

    private List<Book> books;

    public LibraryManagementSystem() {
        this.books = new ArrayList<>();
    }

    // ===================== ADD BOOK =====================
    public void addBook(Book book) {
        books.add(book);
        System.out.println("✔ Added: " + book);
    }

    // ===================== LINEAR SEARCH BY TITLE =====================
    /**
     * Searches for a book by title using Linear Search.
     * Works on unsorted data.
     * Time Complexity: O(n)
     */
    public Book linearSearchByTitle(String title) {
        int comparisons = 0;
        for (Book book : books) {
            comparisons++;
            if (book.getTitle().equalsIgnoreCase(title)) {
                System.out.println("  [Linear Search] Found in " + comparisons + " comparison(s).");
                return book;
            }
        }
        System.out.println("  [Linear Search] Not found after " + comparisons + " comparison(s).");
        return null;
    }

    // ===================== LINEAR SEARCH BY AUTHOR =====================
    /**
     * Searches for all books by a given author using Linear Search.
     * Returns ALL matches (since an author can have multiple books).
     * Time Complexity: O(n) - must scan entire list for all matches
     */
    public List<Book> linearSearchByAuthor(String author) {
        List<Book> results = new ArrayList<>();
        int comparisons = 0;
        for (Book book : books) {
            comparisons++;
            if (book.getAuthor().equalsIgnoreCase(author)) {
                results.add(book);
            }
        }
        System.out.println("  [Linear Search by Author] " + results.size() +
                " book(s) found in " + comparisons + " comparison(s).");
        return results;
    }

    // ===================== BINARY SEARCH BY TITLE =====================
    /**
     * Searches for a book by title using Binary Search.
     * REQUIRES the books list to be sorted by title.
     * Time Complexity: O(log n)
     */
    public Book binarySearchByTitle(List<Book> sortedBooks, String title) {
        int low = 0;
        int high = sortedBooks.size() - 1;
        int comparisons = 0;

        while (low <= high) {
            comparisons++;
            int mid = low + (high - low) / 2;
            int cmp = sortedBooks.get(mid).getTitle().compareToIgnoreCase(title);

            if (cmp == 0) {
                System.out.println("  [Binary Search] Found in " + comparisons + " comparison(s).");
                return sortedBooks.get(mid);
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        System.out.println("  [Binary Search] Not found after " + comparisons + " comparison(s).");
        return null;
    }

    // ===================== DISPLAY ALL BOOKS =====================
    public void displayAllBooks() {
        System.out.println("\n===== LIBRARY CATALOG =====");
        for (int i = 0; i < books.size(); i++) {
            System.out.println("  [" + i + "] " + books.get(i));
        }
        System.out.println("Total books: " + books.size());
        System.out.println("===========================\n");
    }

    // ===================== GET SORTED BY TITLE =====================
    public List<Book> getSortedByTitle() {
        List<Book> sorted = new ArrayList<>(books);
        Collections.sort(sorted, (a, b) -> a.getTitle().compareToIgnoreCase(b.getTitle()));
        return sorted;
    }

    // ===================== MAIN METHOD =====================
    public static void main(String[] args) {
        System.out.println("===== Exercise 6: Library Management System =====\n");

        LibraryManagementSystem library = new LibraryManagementSystem();

        // Adding books
        System.out.println("--- Adding Books ---");
        library.addBook(new Book("B001", "The Great Gatsby", "F. Scott Fitzgerald"));
        library.addBook(new Book("B002", "To Kill a Mockingbird", "Harper Lee"));
        library.addBook(new Book("B003", "1984", "George Orwell"));
        library.addBook(new Book("B004", "Pride and Prejudice", "Jane Austen"));
        library.addBook(new Book("B005", "The Catcher in the Rye", "J.D. Salinger"));
        library.addBook(new Book("B006", "Animal Farm", "George Orwell"));
        library.addBook(new Book("B007", "Brave New World", "Aldous Huxley"));
        library.addBook(new Book("B008", "Lord of the Flies", "William Golding"));
        library.addBook(new Book("B009", "The Hobbit", "J.R.R. Tolkien"));
        library.addBook(new Book("B010", "Fahrenheit 451", "Ray Bradbury"));

        // Display all books
        library.displayAllBooks();

        // ===== LINEAR SEARCH BY TITLE =====
        System.out.println("--- Linear Search by Title ---");
        System.out.println("Searching for '1984':");
        Book result1 = library.linearSearchByTitle("1984");
        System.out.println("  Result: " + result1 + "\n");

        System.out.println("Searching for 'The Hobbit':");
        Book result2 = library.linearSearchByTitle("The Hobbit");
        System.out.println("  Result: " + result2 + "\n");

        System.out.println("Searching for 'War and Peace' (not in library):");
        Book result3 = library.linearSearchByTitle("War and Peace");
        System.out.println("  Result: " + result3 + "\n");

        // ===== LINEAR SEARCH BY AUTHOR =====
        System.out.println("--- Linear Search by Author ---");
        System.out.println("Searching for books by 'George Orwell':");
        List<Book> orwellBooks = library.linearSearchByAuthor("George Orwell");
        for (Book b : orwellBooks) {
            System.out.println("  → " + b);
        }
        System.out.println();

        // ===== BINARY SEARCH BY TITLE =====
        System.out.println("--- Binary Search by Title (Sorted List) ---");
        List<Book> sortedBooks = library.getSortedByTitle();
        System.out.println("Sorted catalog:");
        for (int i = 0; i < sortedBooks.size(); i++) {
            System.out.println("  [" + i + "] " + sortedBooks.get(i).getTitle());
        }
        System.out.println();

        System.out.println("Searching for '1984':");
        Book result4 = library.binarySearchByTitle(sortedBooks, "1984");
        System.out.println("  Result: " + result4 + "\n");

        System.out.println("Searching for 'The Hobbit':");
        Book result5 = library.binarySearchByTitle(sortedBooks, "The Hobbit");
        System.out.println("  Result: " + result5 + "\n");

        System.out.println("Searching for 'War and Peace' (not in library):");
        Book result6 = library.binarySearchByTitle(sortedBooks, "War and Peace");
        System.out.println("  Result: " + result6 + "\n");

        // ===================== ANALYSIS =====================
        System.out.println("===== ANALYSIS & COMPARISON =====");
        System.out.println("┌──────────────────────┬─────────────┬──────────────┬──────────────┐");
        System.out.println("│  Search Method       │  Best Case  │  Average     │  Worst Case  │");
        System.out.println("├──────────────────────┼─────────────┼──────────────┼──────────────┤");
        System.out.println("│  Linear by Title     │  O(1)       │  O(n)        │  O(n)        │");
        System.out.println("│  Linear by Author    │  O(n)*      │  O(n)        │  O(n)        │");
        System.out.println("│  Binary by Title     │  O(1)       │  O(log n)    │  O(log n)    │");
        System.out.println("└──────────────────────┴─────────────┴──────────────┴──────────────┘");
        System.out.println("  * Always O(n) for author search since we need ALL matches.\n");
        System.out.println("PRACTICAL RECOMMENDATIONS:");
        System.out.println("  1. For title lookup → Binary Search on a sorted catalog.");
        System.out.println("  2. For author lookup → HashMap<Author, List<Book>> for O(1) access.");
        System.out.println("  3. For keyword search → Use an inverted index or trie.");
        System.out.println("  4. For a real library system → Combine sorted storage with HashMap");
        System.out.println("     indexes on multiple fields (title, author, ISBN).");
    }
}
