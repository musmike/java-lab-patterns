package pl.lab.factory.warehouse.shipping;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.OrderStatus;
import pl.lab.factory.orders.ProductionOrder;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Bridge 2 (Konkretny Implementor)
public class AntiStaticBagPacking implements PackageImplementor {
    @Override
    public void packOrder(ProductionOrder order){
        order.setStatus(OrderStatus.PACKED);
        SystemLogger.INSTANCE.logInfo("[ANTI-STATIC BAG]", "Packing order " + order.getId() + " in anti-static bag.");
    }
}
// Koniec, Tydzień 3
// Wzorzec Bridge 2