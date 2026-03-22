import java.util.HashMap;
import java.util.Map;

public class User {
    private String name;
    private double balance;
    private Map<String, Integer> portfolio; // symbol -> quantity

    public User(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.portfolio = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Map<String, Integer> getPortfolio() {
        return portfolio;
    }

    public void addToPortfolio(String symbol, int quantity) {
        portfolio.put(symbol, portfolio.getOrDefault(symbol, 0) + quantity);
    }

    public boolean removeFromPortfolio(String symbol, int quantity) {
        if (portfolio.containsKey(symbol) && portfolio.get(symbol) >= quantity) {
            portfolio.put(symbol, portfolio.get(symbol) - quantity);
            if (portfolio.get(symbol) == 0) {
                portfolio.remove(symbol);
            }
            return true;
        }
        return false;
    }

    public int getHolding(String symbol) {
        return portfolio.getOrDefault(symbol, 0);
    }
}