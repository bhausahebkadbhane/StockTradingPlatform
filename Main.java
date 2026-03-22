import java.io.*;
import java.util.*;

public class Main {
    private static User user;
    private static Market market;
    private static List<Transaction> transactions;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        user = new User("Trader", 10000.0); // Starting balance
        market = new Market();
        transactions = new ArrayList<>();

        loadData(); // Load from file if exists

        while (true) {
            System.out.println("\nStock Trading Platform");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Transaction History");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    market.displayMarketData();
                    break;
                case 2:
                    buyStock();
                    break;
                case 3:
                    sellStock();
                    break;
                case 4:
                    viewPortfolio();
                    break;
                case 5:
                    viewTransactions();
                    break;
                case 6:
                    saveData();
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option.");
            }

            // Simulate market change after each action
            market.simulatePriceChange();
        }
    }

    private static void buyStock() {
        System.out.print("Enter stock symbol: ");
        String symbol = scanner.nextLine().toUpperCase();
        Stock stock = market.getStock(symbol);
        if (stock == null) {
            System.out.println("Stock not found.");
            return;
        }
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        double cost = quantity * stock.getPrice();
        if (cost > user.getBalance()) {
            System.out.println("Insufficient balance.");
            return;
        }
        user.setBalance(user.getBalance() - cost);
        user.addToPortfolio(symbol, quantity);
        transactions.add(new Transaction("BUY", symbol, quantity, stock.getPrice()));
        System.out.println("Bought " + quantity + " shares of " + symbol);
    }

    private static void sellStock() {
        System.out.print("Enter stock symbol: ");
        String symbol = scanner.nextLine().toUpperCase();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        if (user.getHolding(symbol) < quantity) {
            System.out.println("Insufficient holdings.");
            return;
        }
        Stock stock = market.getStock(symbol);
        double revenue = quantity * stock.getPrice();
        user.setBalance(user.getBalance() + revenue);
        user.removeFromPortfolio(symbol, quantity);
        transactions.add(new Transaction("SELL", symbol, quantity, stock.getPrice()));
        System.out.println("Sold " + quantity + " shares of " + symbol);
    }

    private static void viewPortfolio() {
        System.out.println("Portfolio:");
        System.out.println("Balance: $" + user.getBalance());
        double totalValue = user.getBalance();
        for (Map.Entry<String, Integer> entry : user.getPortfolio().entrySet()) {
            Stock stock = market.getStock(entry.getKey());
            double value = entry.getValue() * stock.getPrice();
            totalValue += value;
            System.out.println(entry.getKey() + ": " + entry.getValue() + " shares @ $" + stock.getPrice() + " = $" + value);
        }
        System.out.println("Total Portfolio Value: $" + totalValue);
    }

    private static void viewTransactions() {
        System.out.println("Transaction History:");
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    private static void saveData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("user_data.txt"))) {
            writer.println(user.getBalance());
            for (Map.Entry<String, Integer> entry : user.getPortfolio().entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("transactions.txt"))) {
            for (Transaction t : transactions) {
                writer.println(t.getType() + "," + t.getSymbol() + "," + t.getQuantity() + "," + t.getPrice() + "," + t.getTimestamp());
            }
        } catch (IOException e) {
            System.out.println("Error saving transactions.");
        }
    }

    private static void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("user_data.txt"))) {
            String line = reader.readLine();
            if (line != null) {
                user.setBalance(Double.parseDouble(line));
            }
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                user.addToPortfolio(parts[0], Integer.parseInt(parts[1]));
            }
        } catch (IOException e) {
            // File not found or error, start fresh
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Assuming timestamp is not loaded for simplicity
                transactions.add(new Transaction(parts[0], parts[1], Integer.parseInt(parts[2]), Double.parseDouble(parts[3])));
            }
        } catch (IOException e) {
            // File not found or error
        }
    }
}