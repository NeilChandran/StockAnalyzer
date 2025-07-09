
import java.util.*;

public class StockAnalyzer {
    private List<Double> closingPrices;

    public StockAnalyzer() {
        this.closingPrices = new ArrayList<>();
    }

    public void loadStockData(String ticker, int days) {
        Random rand = new Random();
        for (int i = 0; i < days; i++) {
            double price = 100 + rand.nextDouble() * 50; // Simulate stock prices
            closingPrices.add(price);
        }
        System.out.println(days + " days of data loaded for " + ticker);
    }

    public double calculateAveragePrice() {
        double sum = 0.0;
        for (double price : closingPrices) {
            sum += price;
        }
        return closingPrices.isEmpty() ? 0.0 : sum / closingPrices.size();
    }

    public double getHighestPrice() {
        return Collections.max(closingPrices);
    }

    public double getLowestPrice() {
        return Collections.min(closingPrices);
    }
}
