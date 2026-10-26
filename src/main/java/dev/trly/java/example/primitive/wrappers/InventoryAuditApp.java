package dev.trly.java.example.primitive.wrappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class InventoryAuditApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryAuditApp.class);

    private InventoryAuditApp() {
    }

    public static void main(String[] args) {
        final Double[] unitPrices = {
            Double.valueOf(19.99),
            Double.valueOf(4.50),
            Double.valueOf(120.00),
            Double.valueOf(7.25)
        };
        final Boolean[] inStock = {
            Boolean.valueOf(true),
            Boolean.valueOf(false),
            Boolean.valueOf(true),
            Boolean.valueOf(true)
        };
        final Character[] grades = {
            Character.valueOf('A'),
            Character.valueOf('C'),
            Character.valueOf('B'),
            Character.valueOf('A')
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
        return Double.valueOf(totalValue / itemCount);
    }

    public static Integer countAvailableItems(int totalItems, int outOfStockItems) {
        return Integer.valueOf(totalItems - outOfStockItems);
    }
}
