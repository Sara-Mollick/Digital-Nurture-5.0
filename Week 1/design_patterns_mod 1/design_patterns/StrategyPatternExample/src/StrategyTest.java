public class StrategyTest {
    public static void main(String[] args) {
        System.out.println("=== Testing Strategy Pattern ===");

        PaymentContext context = new PaymentContext();

        // 1. Pay using Credit Card
        PaymentStrategy creditCard = new CreditCardPayment(
                "John Doe", 
                "1234-5678-9876-5432", 
                "123", 
                "12/28"
        );
        context.setPaymentStrategy(creditCard);
        System.out.println("Paying for checkout item 1:");
        context.executePayment(99.99);

        System.out.println();

        // 2. Pay using PayPal
        PaymentStrategy payPal = new PayPalPayment(
                "john.doe@example.com", 
                "securePassword123"
        );
        context.setPaymentStrategy(payPal);
        System.out.println("Paying for checkout item 2:");
        context.executePayment(45.50);
    }
}
