public class CreditCardPayment implements PaymentStrategy {
    private final String cardHolderName;
    private final String cardNumber;
    private final String cvv;
    private final String expiryDate;

    public CreditCardPayment(String cardHolderName, String cardNumber, String cvv, String expiryDate) {
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Processing Credit Card Payment of $" + amount + 
                " using Card Ending in " + cardNumber.substring(cardNumber.length() - 4) + 
                " for holder: " + cardHolderName);
    }
}
