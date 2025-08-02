// src/com/yourdomain/stockanalyzer/StockDataLoader.java
package com.yourdomain.stockanalyzer;

import java.io.*;
import java.util.*;

public class StockDataLoader {
    public static List<Double> loadClosingPrices(String path) throws IOException {
        List<Double> prices = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split(",");
                double price = Double.parseDouble(parts[parts.length - 1]); // Assumes last column is closing price
                prices.add(price);
            }
        }
        return prices;
    }
}
