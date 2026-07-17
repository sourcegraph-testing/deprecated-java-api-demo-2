package dev.trly.java.example.primitive.wrappers;

public final class OrderRollupApp {
    private OrderRollupApp() {
    }

    public static void main(String[] args) {
        final Integer[] boxCounts = {
            new Integer(4),
            new Integer(7),
            new Integer(3),
            new Integer(5)
        };
        final Long[] orderValues = {
            new Long(1299L),
            new Long(2450L),
            new Long(875L),
            new Long(1625L)
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
        return new Integer(boxesPerOrder * orderCount);
    }

    public static Long estimateShippingCents(long baseCents, int boxCount) {
        final Long perBoxCents = new Long(baseCents);
        final Long totalCents = new Long(perBoxCents.longValue() * boxCount);
        return new Long(totalCents.longValue());
    }
}
