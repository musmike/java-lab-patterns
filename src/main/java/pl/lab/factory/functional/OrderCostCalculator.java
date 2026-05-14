package pl.lab.factory.functional;

import pl.lab.factory.orders.ProductionOrder;

// Tydzień 10 - Programowanie funkcyjne
// Interfejs funkcyjny 1
// Oblicza całkowity koszt zamówienia produkcyjnego.
@FunctionalInterface
public interface OrderCostCalculator {
    double calculate(ProductionOrder productionOrder);
}
// Koniec, Tydzień 10
// Interfejs funkcyjny 1
