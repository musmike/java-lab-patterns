package pl.lab.factory.warehouse.packing;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.OrderStatus;
import pl.lab.factory.orders.ProductionOrder;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Bridge 2 (Konkretny Implementor)

// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada odwracania zależności (DIP) (klasa pochodna)
public class PlacticBoxPacking implements PackageImplementor {
    @Override
    public void packOrder(ProductionOrder order){
        order.setStatus(OrderStatus.PACKED);
        SystemLogger.INSTANCE.logInfo("[PLASTIC BOX]", "Packing order " + order.getId() + " in plastic box.");
    }
}
// Koniec, Tydzień 8
// Zasada odwracania zależności (DIP)

// Koniec, Tydzień 3
// Wzorzec Bridge 2