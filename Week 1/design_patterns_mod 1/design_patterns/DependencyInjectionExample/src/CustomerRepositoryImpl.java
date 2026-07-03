import java.util.HashMap;
import java.util.Map;

public class CustomerRepositoryImpl implements CustomerRepository {
    private final Map<Integer, Customer> database = new HashMap<>();

    public CustomerRepositoryImpl() {
        // Seed database with mock data
        database.put(1, new Customer(1, "Alice Johnson", "alice@example.com"));
        database.put(2, new Customer(2, "Bob Smith", "bob@example.com"));
        database.put(3, new Customer(3, "Charlie Brown", "charlie@example.com"));
    }

    @Override
    public Customer findCustomerById(int id) {
        System.out.println("[Repository]: Querying database for Customer ID: " + id);
        return database.get(id);
    }
}
