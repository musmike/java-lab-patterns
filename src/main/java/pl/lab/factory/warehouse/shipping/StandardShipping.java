package pl.lab.factory.warehouse.shipping;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.OrderStatus;
import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.warehouse.packing.PackageImplementor;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Bridge 2 (Uszczegółowiona Abstrakcja)

// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada odwracania zależności (DIP) (klasa pochodna)
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
// Koniec, Tydzień 8
// Zasada odwracania zależności (DIP)

// Koniec, Tydzień 3
// Wzorzec Bridge 2