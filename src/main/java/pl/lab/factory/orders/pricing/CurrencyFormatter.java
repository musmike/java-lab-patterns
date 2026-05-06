package pl.lab.factory.orders.pricing;

public class CurrencyFormatter {
    public static String format(double amount) {
        return String.format("%.2f", amount);
    }
}