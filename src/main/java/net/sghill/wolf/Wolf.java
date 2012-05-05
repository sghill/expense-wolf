package net.sghill.wolf;

public final class Wolf {
    public static void main(String[] args) {
        System.out.println(new Reader(args[1]).getAllExpenseReports().getMostRecentPaidReportsFor(Long.valueOf(args[0])).toString());
    }
}
