package pl.lab.factory.warehouse.shipping;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.OrderStatus;
import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.warehouse.packing.PackageImplementor;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Bridge 2 (Abstrakcja)

// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada odwracania zależności (DIP) (Klasa abstrakcyjna)
public abstract class ShippingStrategy {
    protected PackageImplementor implementor;

    public ShippingStrategy(PackageImplementor implementor){
        this.implementor = implementor;
    }

    public void shipOrder(ProductionOrder order) {
        SystemLogger.INSTANCE.logInfo(getShippingLogPrefix(), "Processing order " + order.getId());
        implementor.packOrder(order);
        order.setStatus(OrderStatus.SHIPPED);
        SystemLogger.INSTANCE.logInfo(getShippingLogPrefix(), "Order " + order.getId() + " sent.");
    }

    protected abstract String getShippingLogPrefix();
}
// Koniec, Tydzień 8
// Zasada odwracania zależności (DIP)

// Koniec, Tydzień 3
// Wzorzec Bridge 2