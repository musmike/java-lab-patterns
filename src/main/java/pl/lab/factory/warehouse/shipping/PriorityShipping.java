package pl.lab.factory.warehouse.shipping;

import pl.lab.factory.warehouse.packing.PackageImplementor;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Bridge 2 (Uszczegółowiona Abstrakcja)

// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada odwracania zależności (DIP) (klasa pochodna)
public class PriorityShipping extends ShippingStrategy {
    public PriorityShipping(PackageImplementor implementor) { super(implementor); }
    @Override protected String getShippingLogPrefix() { return "[PRIORITY SHIPPING]"; }
}
// Koniec, Tydzień 8
// Zasada odwracania zależności (DIP)

// Koniec, Tydzień 3
// Wzorzec Bridge 2