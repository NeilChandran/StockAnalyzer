
import java.util.*;
import java.text.DecimalFormat;

public class StockAnalyzer {
    private List<Double> closingPrices;
    private String ticker;

    public StockAnalyzer() {
        this.closingPrices = new ArrayList<>();
    }

    public void loadStockData(String ticker, int days) {
        this.ticker = ticker;
        Random rand = new Random();
        closingPrices.clear();
        for (int i = 0; i < days; i++) {
            double price = 100 + rand.nextGaussian() * 10; // Simulated via Gaussian noise
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

    public double calculateExponentialMovingAverage(int window) {
        if (closingPrices.size() < window) return 0.0;
        double alpha = 2.0 / (window + 1);
        double ema = closingPrices.get(0);
        for (int i = 1; i < closingPrices.size(); i++) {
            ema = alpha * closingPrices.get(i) + (1 - alpha) * ema;
        }
        return ema;
    }

    public double calculateRSI(int period) {
        if (closingPrices.size() <= period) return 0.0;
        double gain = 0.0;
        double loss = 0.0;
        for (int i = 1; i <= period; i++) {
            double change = closingPrices.get(i) - closingPrices.get(i - 1);
            if (change > 0) gain += change;
            else loss -= change;
        }
        double rs = loss == 0 ? 100 : gain / loss;
        return 100 - (100 / (1 + rs));
    }

    public double calculateBeta(List<Double> marketReturns) {
        if (closingPrices.size() != marketReturns.size()) return 0.0;
        List<Double> assetReturns = new ArrayList<>();
        for (int i = 1; i < closingPrices.size(); i++) {
            assetReturns.add((closingPrices.get(i) - closingPrices.get(i - 1)) / closingPrices.get(i - 1));
        }
        double meanAsset = assetReturns.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double meanMarket = marketReturns.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double covariance = 0.0, variance = 0.0;

        for (int i = 0; i < assetReturns.size(); i++) {
            covariance += (assetReturns.get(i) - meanAsset) * (marketReturns.get(i) - meanMarket);
            variance += Math.pow(marketReturns.get(i) - meanMarket, 2);
        }
        return variance == 0 ? 0.0 : covariance / variance;
    }

    public void printSummary() {
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("==== SUMMARY FOR " + ticker + " ====");
        System.out.println("Average Price: $" + df.format(calculateAveragePrice()));
        System.out.println("Highest Price: $" + df.format(getHighestPrice()));
        System.out.println("Lowest Price: $" + df.format(getLowestPrice()));
        System.out.println("Volatility: $" + df.format(calculateVolatility()));
        System.out.println("EMA(10): $" + df.format(calculateExponentialMovingAverage(10)));
        System.out.println("RSI(14): " + df.format(calculateRSI(14)));

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
        analyzer.printSummary();

        scanner.close();
    }
}
