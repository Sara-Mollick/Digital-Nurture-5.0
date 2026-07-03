public class AdapterTest {
    public static void main(String[] args) {
        System.out.println("=== Testing Adapter Pattern ===");

        // Using PayPal via Adapter
        PayPalGateway payPalGateway = new PayPalGateway();
        PaymentProcessor payPalProcessor = new PayPalAdapter(payPalGateway);
        
        System.out.println("Using PayPal Adapter:");
        payPalProcessor.processPayment(150.00);

        System.out.println();

        // Using Stripe via Adapter
        StripeGateway stripeGateway = new StripeGateway();
        PaymentProcessor stripeProcessor = new StripeAdapter(stripeGateway);
        
        System.out.println("Using Stripe Adapter:");
        stripeProcessor.processPayment(275.50);
    }
}
