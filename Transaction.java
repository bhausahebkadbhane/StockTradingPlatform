import java.time.LocalDateTime;

public class Transaction {
    private String type; // "BUY" or "SELL"
    private String symbol;
    private int quantity;
    private double price;
    private LocalDateTime timestamp;

    public Transaction(String type, String symbol, int quantity, double price) {
        this.type = type;
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.timestamp = LocalDateTime.now();
    }

    public String getType() {
        return type;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return timestamp + " - " + type + " " + quantity + " " + symbol + " @ $" + price;
    }
}