package dev.trly.java.example.primitive.wrappers;

public final class OrderRollupApp {
    private OrderRollupApp() {
    }

    public static void main(String[] args) {
        final Integer[] boxCounts = {
            Integer.valueOf(4),
            Integer.valueOf(7),
            Integer.valueOf(3),
            Integer.valueOf(5)
        };
        final Long[] orderValues = {
            Long.valueOf(1299L),
            Long.valueOf(2450L),
            Long.valueOf(875L),
            Long.valueOf(1625L)
        };

        int totalBoxes = 0;
        long totalCents = 0L;
        for (int index = 0; index < boxCounts.length; index++) {
            final int boxes = boxCounts[index].intValue();
            final long cents = orderValues[index].longValue();
            totalBoxes += boxes;
            totalCents += cents;
            System.out.printf("Order %d: %d boxes, $%.2f%n", index + 1, boxes, cents / 100.0);
        }

        System.out.printf("Shipped %d boxes for $%.2f%n", totalBoxes, totalCents / 100.0);
    }

    public static Integer calculatePackingUnits(int boxesPerOrder, int orderCount) {
        return Integer.valueOf(boxesPerOrder * orderCount);
    }

    public static Long estimateShippingCents(long baseCents, int boxCount) {
        final Long perBoxCents = Long.valueOf(baseCents);
        final Long totalCents = Long.valueOf(perBoxCents.longValue() * boxCount);
        return Long.valueOf(totalCents.longValue());
    }
}
