public class CustomerService {
    private final CustomerRepository customerRepository;

    // Constructor injection: Injecting CustomerRepository dependency
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomer(int id) {
        System.out.println("[Service]: Processing request for Customer ID: " + id);
        return customerRepository.findCustomerById(id);
    }
}
