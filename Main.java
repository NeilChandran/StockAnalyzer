
public class Main {
    public static void main(String[] args) {
        StockAnalyzer analyzer = new StockAnalyzer();
        analyzer.loadStockData("AAPL", 30);
        System.out.println("Average Closing Price: $" + analyzer.calculateAveragePrice());
        System.out.println("Highest Price: $" + analyzer.getHighestPrice());
        System.out.println("Lowest Price: $" + analyzer.getLowestPrice());
    }
}
