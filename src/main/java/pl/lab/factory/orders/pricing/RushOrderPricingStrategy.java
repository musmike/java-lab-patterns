package pl.lab.factory.orders.pricing;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.ProductionOrder;

// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada podstawienia Liskov (LSP) 1
// Klasa pochodna RushOrderPricingStrategy dziedzicząca po StandardPricingStrategy (konketnej)
public class RushOrderPricingStrategy extends StandardPricingStrategy { 
    @Override
    public double calculatePrice(ProductionOrder order) {
        double basePrice = super.calculatePrice(order);
        double rushPrice = basePrice * 1.20;

        SystemLogger.INSTANCE.logInfo("PRICING",
                "Rush order fee applied (+20%). New price: $" + String.format("%.2f", rushPrice));

        return rushPrice;
    }
}
// Koniec, Tydzień 8
// Zasada podstawienia Liskov (LSP) 1