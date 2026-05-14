package pl.lab.factory.functional;

import pl.lab.factory.orders.ProductionOrder;

// Tydzień 10 - Programowanie funkcyjne
// Interfejs funkcyjny 2
// Formatuje czytelny opis tekstowy zamówienia produkcyjnego.
@FunctionalInterface
public interface OrderDescriptionFormatter {
    String format(ProductionOrder productionOrder);
}
// Koniec, Tydzień 10
// Interfejs funkcyjny 2
