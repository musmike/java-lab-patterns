package pl.lab.factory.orders.pricing;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.ProductionOrder;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Strategy 1 (Concrete Strategy)
// Strategia dla zamówień hurtowych: jeśli ilość przekracza próg, stosuje rabat.
public class BulkPricingStrategy implements PricingStrategy {

    private final PricingStrategy standardStrategy = new StandardPricingStrategy();
    private static final int BULK_THRESHOLD = 100;
    private static final double DISCOUNT_PERCENTAGE = 20.0;
    private static final double PERCENTAGE_DIVISOR = 100.0;

    @Override
    public double calculatePrice(ProductionOrder order) {
        double basePrice = standardStrategy.calculatePrice(order);

        if (order.getQuantity() > BULK_THRESHOLD) {
            double discountAmount = basePrice * (DISCOUNT_PERCENTAGE / PERCENTAGE_DIVISOR);
            double finalPrice = basePrice - discountAmount;

            SystemLogger.INSTANCE.logInfo("PRICING", "Applied bulk discount of " + DISCOUNT_PERCENTAGE
                    + "% (" + String.format("%.2f", discountAmount) + ") for order " + order.getId()
                    + ". Final price: $" + String.format("%.2f", finalPrice));

            return finalPrice;
        }

        return basePrice;
    }
}
// Koniec, Tydzień 6
// Wzorzec Strategy 1