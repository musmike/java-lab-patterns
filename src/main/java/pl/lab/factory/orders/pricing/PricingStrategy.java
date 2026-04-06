package pl.lab.factory.orders.pricing;

import pl.lab.factory.orders.ProductionOrder;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Strategy 1 (Interface Strategy)
// Definiuje wspólny kontrakt dla wszystkich algorytmów wyceny zamówień.
public interface PricingStrategy {
    double calculatePrice(ProductionOrder order);
}
// Koniec, Tydzień 6
// Wzorzec Strategy 1