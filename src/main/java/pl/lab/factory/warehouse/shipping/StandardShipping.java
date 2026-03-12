package pl.lab.factory.warehouse.shipping;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.OrderStatus;
import pl.lab.factory.orders.ProductionOrder;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Bridge 2 (Uszczegółowiona Abstrakcja)
public class StandardShipping extends ShippingStrategy{
    public StandardShipping(PackageImplementor implementor){
        super(implementor);
    }

    @Override
    public void shipOrder(ProductionOrder order){
        SystemLogger.INSTANCE.logInfo("[STANDARD SHIPPING]", "Processing order " + order.getId());
        implementor.packOrder(order);
        order.setStatus(OrderStatus.SHIPPED);
        SystemLogger.INSTANCE.logInfo("[STANDARD SHIPPING]", "Order " + order.getId() + " sent.");
    }
}
// Koniec, Tydzień 3
// Wzorzec Bridge 2