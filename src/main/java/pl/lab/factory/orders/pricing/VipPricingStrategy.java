package pl.lab.factory.orders.pricing;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.ProductionOrder;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Strategy 1 (Concrete Strategy)
// Specjalna strategia dla klientów VIP: stały rabat + dodatkowe bonusy.
public class VipPricingStrategy implements PricingStrategy {

    private final PricingStrategy standardStrategy = new StandardPricingStrategy();
    private static final double VIP_DISCOUNT_PERCENTAGE = 15.0;

    @Override
    public double calculatePrice(ProductionOrder order) {
        double basePrice = standardStrategy.calculatePrice(order);
        double discountAmount = basePrice * (VIP_DISCOUNT_PERCENTAGE / 100.0);
        double finalPrice = basePrice - discountAmount;

        SystemLogger.INSTANCE.logInfo("PRICING", "Applied VIP discount of " + VIP_DISCOUNT_PERCENTAGE
                + "% for " + order.getId() + ". Final price: $" + String.format("%.2f", finalPrice));

        return finalPrice;
    }
}
// Koniec, Tydzień 6
// Wzorzec Strategy 1