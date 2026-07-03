public class ObserverTest {
    public static void main(String[] args) {
        System.out.println("=== Testing Observer Pattern ===");

        // Create Stock Market (Subject)
        StockMarket stockMarket = new StockMarket();

        // Create Observers
        Observer mobileApp = new MobileApp("TradingPro");
        Observer webApp = new WebApp("YahooFinanceDashboard");

        // Register Observers
        System.out.println("Registering observers...");
        stockMarket.register(mobileApp);
        stockMarket.register(webApp);

        System.out.println("\n--- Update stock price: AAPL to $180.50 ---");
        stockMarket.setStockPrice("AAPL", 180.50);

        System.out.println("\n--- Update stock price: GOOGL to $172.10 ---");
        stockMarket.setStockPrice("GOOGL", 172.10);

        // Deregister WebApp
        System.out.println("\nDeregistering WebApp observer...");
        stockMarket.deregister(webApp);

        System.out.println("\n--- Update stock price: AAPL to $185.00 ---");
        stockMarket.setStockPrice("AAPL", 185.00); // only MobileApp should receive notification now
    }
}
