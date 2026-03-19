package pl.lab.factory.warehouse.packing;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.OrderStatus;
import pl.lab.factory.orders.ProductionOrder;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Bridge 2 (Konkretny Implementor)
public class CardboardBoxPacking implements PackageImplementor {
    @Override
    public void packOrder(ProductionOrder order){
        order.setStatus(OrderStatus.PACKED);
        SystemLogger.INSTANCE.logInfo("[CARDBOARD BOX]", "Packing order " + order.getId() + " in cardboard box.");
    }
}
// Koniec, Tydzień 3
// Wzorzec Bridge 2