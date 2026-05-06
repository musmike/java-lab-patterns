package pl.lab.factory.orders.pricing;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.ProductionOrder;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Strategy 1 (Concrete Strategy)
// Strategia dla zamówień hurtowych: jeśli ilość przekracza próg, stosuje rabat.
public class BulkPricingStrategy extends StandardPricingStrategy {
    private static final int BULK_THRESHOLD = 100;
    private static final double DISCOUNT_PERCENTAGE = 20.0;

    @Override
    public double calculatePrice(ProductionOrder order) {
        double basePrice = super.calculatePrice(order);

        if (order.getQuantity() > BULK_THRESHOLD) {
            double discountAmount = basePrice * (DISCOUNT_PERCENTAGE / PricingStrategy.PERCENTAGE_DIVISOR);
            double finalPrice = basePrice - discountAmount;

            SystemLogger.INSTANCE.logInfo("PRICING", "Applied bulk discount of " + DISCOUNT_PERCENTAGE
                    + "% ($" + CurrencyFormatter.format(discountAmount) + ") for order " + order.getId()
                    + ". Final price: $" + CurrencyFormatter.format(finalPrice));
            return finalPrice;
        }
        return basePrice;
    }
}
// Koniec, Tydzień 6
// Wzorzec Strategy 1