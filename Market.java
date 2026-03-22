import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Market {
    private List<Stock> stocks;
    private Random random = new Random();

    public Market() {
        stocks = new ArrayList<>();
        // Add some sample stocks
        stocks.add(new Stock("AAPL", "Apple Inc.", 150.0));
        stocks.add(new Stock("GOOGL", "Alphabet Inc.", 2800.0));
        stocks.add(new Stock("MSFT", "Microsoft Corp.", 300.0));
        stocks.add(new Stock("TSLA", "Tesla Inc.", 200.0));
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public Stock getStock(String symbol) {
        for (Stock stock : stocks) {
            if (stock.getSymbol().equals(symbol)) {
                return stock;
            }
        }
        return null;
    }

    public void displayMarketData() {
        System.out.println("Market Data:");
        for (Stock stock : stocks) {
            System.out.println(stock);
        }
    }

    public void simulatePriceChange() {
        for (Stock stock : stocks) {
            double change = (random.nextDouble() - 0.5) * 0.1; // -5% to +5%
            double newPrice = stock.getPrice() * (1 + change);
            stock.setPrice(Math.max(0.01, newPrice)); // Ensure positive price
        }
    }
}