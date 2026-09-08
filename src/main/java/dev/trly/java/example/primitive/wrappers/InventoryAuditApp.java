package dev.trly.java.example.primitive.wrappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class InventoryAuditApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryAuditApp.class);

    private InventoryAuditApp() {
    }

    public static void main(String[] args) {
        final Double[] unitPrices = {
            new Double(19.99),
            new Double(4.50),
            new Double(120.00),
            new Double(7.25)
        };
        final Boolean[] inStock = {
            new Boolean(true),
            new Boolean(false),
            new Boolean(true),
            new Boolean(true)
        };
        final Character[] grades = {
            new Character('A'),
            new Character('C'),
            new Character('B'),
            new Character('A')
        };

        double totalValue = 0.0;
        int availableCount = 0;
        for (int index = 0; index < unitPrices.length; index++) {
            final double price = unitPrices[index].doubleValue();
            final boolean available = inStock[index].booleanValue();
            final char grade = grades[index].charValue();
            if (available) {
                totalValue += price;
                availableCount++;
            }
            LOGGER.info(
                "Item {}: ${}, grade {}, {}",
                index + 1,
                String.format("%.2f", price),
                String.format("%c", grade),
                available ? "in stock" : "out of stock");
        }

        LOGGER.info("Available items: {} worth ${}", availableCount, String.format("%.2f", totalValue));
    }

    public static Double calculateAverageValue(double totalValue, int itemCount) {
        return new Double(totalValue / itemCount);
    }

    public static Integer countAvailableItems(int totalItems, int outOfStockItems) {
        return new Integer(totalItems - outOfStockItems);
    }
}
