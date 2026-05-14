package pl.lab.factory.functional;

import pl.lab.factory.orders.ProductionOrder;

// Tydzień 10 - Programowanie funkcyjne
// Interfejs funkcyjny 3
// Sprawdza, czy zamówienie produkcyjne spełnia podany warunek statusu.
@FunctionalInterface
public interface OrderStatusChecker {
    boolean check(ProductionOrder productionOrder);
}
// Koniec, Tydzień 10
// Interfejs funkcyjny 3
