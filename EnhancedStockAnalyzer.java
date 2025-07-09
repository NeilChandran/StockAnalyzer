
import java.util.*;
import java.text.DecimalFormat;

public class StockAnalyzer {
    private List<Double> closingPrices;

    public StockAnalyzer() {
        this.closingPrices = new ArrayList<>();
    }

    public void loadStockData(String ticker, int days) {
        Random rand = new Random();
        closingPrices.clear(); // Reset data
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

    public double calculateVolatility() {
        double mean = calculateAveragePrice();
        double sumSquaredDiffs = 0.0;
        for (double price : closingPrices) {
            sumSquaredDiffs += Math.pow(price - mean, 2);
        }
        return closingPrices.size() > 1 ? Math.sqrt(sumSquaredDiffs / (closingPrices.size() - 1)) : 0.0;
    }

    public List<Double> calculateMovingAverage(int window) {
        List<Double> movingAverages = new ArrayList<>();
        for (int i = 0; i <= closingPrices.size() - window; i++) {
            double sum = 0.0;
            for (int j = i; j < i + window; j++) {
                sum += closingPrices.get(j);
            }
            movingAverages.add(sum / window);
        }
        return movingAverages;
    }

    public void printSummary(String ticker) {
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("Summary for " + ticker + ":");
        System.out.println("Average Price: $" + df.format(calculateAveragePrice()));
        System.out.println("Highest Price: $" + df.format(getHighestPrice()));
        System.out.println("Lowest Price: $" + df.format(getLowestPrice()));
        System.out.println("Volatility: $" + df.format(calculateVolatility()));

        List<Double> ma5 = calculateMovingAverage(5);
        System.out.println("5-Day Moving Averages:");
        for (int i = 0; i < ma5.size(); i++) {
            System.out.println("Day " + (i + 1) + ": $" + df.format(ma5.get(i)));
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StockAnalyzer analyzer = new StockAnalyzer();

        System.out.print("Enter stock ticker: ");
        String ticker = scanner.nextLine();

        System.out.print("Enter number of days to simulate: ");
        int days = scanner.nextInt();

        analyzer.loadStockData(ticker, days);
        analyzer.printSummary(ticker);

        scanner.close();
    }
}
