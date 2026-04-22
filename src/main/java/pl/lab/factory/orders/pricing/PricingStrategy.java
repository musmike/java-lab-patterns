package pl.lab.factory.orders.pricing;

import pl.lab.factory.orders.ProductionOrder;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Strategy 1 (Interface Strategy)
// Definiuje wspólny kontrakt dla wszystkich algorytmów wyceny zamówień.

// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada odwracania zależności (DIP) 1 - wersja z interfejsem (Interfejs) 
// Interfejs PricingStrategy jest abstrakcją, od której zależy moduł wysokopoziomowy (OrderInvoice),
// a nie od konkretnej implementacji.
public interface PricingStrategy {
    double calculatePrice(ProductionOrder order);
}
// Koniec, Tydzień 6
// Wzorzec Strategy 1

// Koniec, Tydzień 8
// Zasada odwracania zależności (DIP) 1 - wersja z interfejsem