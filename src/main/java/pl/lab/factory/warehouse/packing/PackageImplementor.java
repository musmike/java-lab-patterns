package pl.lab.factory.warehouse.packing;

import pl.lab.factory.orders.ProductionOrder;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Bridge 2 (Implementor)
public interface PackageImplementor {
    void packOrder(ProductionOrder order);
}
// Koniec, Tydzień 3
// Wzorzec Bridge 2