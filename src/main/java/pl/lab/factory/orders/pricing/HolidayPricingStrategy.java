package pl.lab.factory.orders.pricing;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.ProductionOrder;

// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada podstawienia Liskov (LSP) 1
// Klasa pochodna HolidayPricingStrategy dziedzicząca po StandardPricingStrategy (konketnej)
public class HolidayPricingStrategy extends StandardPricingStrategy {
    private static final double HOLIDAY_DISCOUNT_MULTIPLIER = 0.90;

    @Override
    public double calculatePrice(ProductionOrder order) {
        double basePrice = super.calculatePrice(order);
        double holidayPrice = basePrice * HOLIDAY_DISCOUNT_MULTIPLIER;
        SystemLogger.INSTANCE.logInfo("PRICING", "Holiday discount applied! New price: $" + holidayPrice);
        return holidayPrice;
    }
}
// Koniec, Tydzień 8
// Zasada podstawienia Liskov (LSP) 1