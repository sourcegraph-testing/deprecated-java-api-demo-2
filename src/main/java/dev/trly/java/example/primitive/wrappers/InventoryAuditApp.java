package dev.trly.java.example.primitive.wrappers;

public final class InventoryAuditApp {
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
            System.out.printf(
                "Item %d: $%.2f, grade %c, %s%n",
                index + 1,
                price,
                grade,
                available ? "in stock" : "out of stock");
        }

        System.out.printf("Available items: %d worth $%.2f%n", availableCount, totalValue);
    }

    public static Double calculateAverageValue(double totalValue, int itemCount) {
        return new Double(totalValue / itemCount);
    }

    public static Integer countAvailableItems(int totalItems, int outOfStockItems) {
        return new Integer(totalItems - outOfStockItems);
    }
}
