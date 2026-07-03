public class PayPalPayment implements PaymentStrategy {
    private final String email;
    private final String password;

    public PayPalPayment(String email, String password) {
        this.email = email;
        this.password = password; // In practice, use secure tokens instead of passwords!
    }

    @Override
    public void pay(double amount) {
        System.out.println("Processing PayPal Payment of $" + amount + " for account: " + email);
    }
}
