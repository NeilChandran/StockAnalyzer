// src/com/yourdomain/stockanalyzer/IndicatorCalculator.java
package com.yourdomain.stockanalyzer;

public class IndicatorCalculator {
    public static double simpleMovingAverage(double[] prices, int period) {
        if (prices.length < period) return Double.NaN;
        double sum = 0.0;
        for (int i = prices.length - period; i < prices.length; i++) {
            sum += prices[i];
        }
        return sum / period;
    }

    public static double calculateRSI(double[] prices, int period) {
        if (prices.length < period + 1) return Double.NaN;
        double gain = 0, loss = 0;
        for (int i = prices.length - period; i < prices.length; i++) {
            double diff = prices[i] - prices[i-1];
            if (diff > 0) gain += diff;
            else loss -= diff;
        }
        if (loss == 0) return 100;
        double rs = gain / loss;
        return 100 - (100 / (1 + rs));
    }
}
