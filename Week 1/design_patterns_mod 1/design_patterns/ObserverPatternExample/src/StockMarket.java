import java.util.ArrayList;
import java.util.List;

public class StockMarket implements Stock {
    private final List<Observer> observers = new ArrayList<>();
    private String stockName;
    private double price;

    // Method to update stock price and trigger notification to all observers
    public void setStockPrice(String stockName, double price) {
        this.stockName = stockName;
        this.price = price;
        notifyObservers();
    }

    @Override
    public void register(Observer o) {
        observers.add(o);
        System.out.println("Observer registered successfully.");
    }

    @Override
    public void deregister(Observer o) {
        observers.remove(o);
        System.out.println("Observer deregistered successfully.");
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(stockName, price);
        }
    }
}
