package pl.lab.factory.warehouse.packing;

import pl.lab.factory.orders.ProductionOrder;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Bridge 2 (Implementor)

// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada odwracania zależności (DIP) (interface)
public interface PackageImplementor {
    void packOrder(ProductionOrder order);
}
// Koniec, Tydzień 8
// Zasada odwracania zależności (DIP)

// Koniec, Tydzień 3
// Wzorzec Bridge 2