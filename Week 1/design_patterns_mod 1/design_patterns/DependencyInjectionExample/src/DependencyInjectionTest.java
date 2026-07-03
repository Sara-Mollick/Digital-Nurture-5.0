public class DependencyInjectionTest {
    public static void main(String[] args) {
        System.out.println("=== Testing Dependency Injection ===");

        // 1. Create concrete repository (the dependency)
        CustomerRepository repository = new CustomerRepositoryImpl();

        // 2. Inject repository dependency into the service via constructor injection
        CustomerService service = new CustomerService(repository);

        // 3. Use the service to query customer
        System.out.println("--- Scenario 1: Retrieve Customer #1 ---");
        Customer customer1 = service.getCustomer(1);
        System.out.println("Result: " + customer1);

        System.out.println();

        System.out.println("--- Scenario 2: Retrieve Customer #3 ---");
        Customer customer3 = service.getCustomer(3);
        System.out.println("Result: " + customer3);

        System.out.println();

        System.out.println("--- Scenario 3: Retrieve Non-existent Customer #5 ---");
        Customer customer5 = service.getCustomer(5);
        System.out.println("Result: " + customer5);
    }
}
