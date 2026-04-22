package pl.lab.factory.manufacturing;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.OrderStatus;
import pl.lab.factory.orders.ProductionOrder;

import java.util.concurrent.TimeUnit;

// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada odwracania zależności (DIP) 1 - wersja z klasą abstrakcyjną (Klasa abstrakcyjna)
// Klasa abstrakcyjna OrderProcessor jest abstrakcją, od której zależy moduł wysokopoziomowy (ProductionDepartment), 
// a nie od konkretnej implementacji
public abstract class OrderProcessor {
    public void process(ProductionOrder order) throws InterruptedException {
        order.setStatus(OrderStatus.IN_PRODUCTION);
        SystemLogger.INSTANCE.logInfo("PRODUCTION", "Order started: " + order.getId()
                + " | qty: " + order.getQuantity()
                + " | spec: " + order.getSpecification());

        for (int i = 1; i <= order.getQuantity(); i++) {
            SystemLogger.INSTANCE.logInfo("PRODUCTION", "[" + i + "/" + order.getQuantity() + "] "
                    + order.getId() + " — producing unit...");
            produceUnit(order);
            TimeUnit.MILLISECONDS.sleep(100);
        }

        order.setStatus(OrderStatus.COMPLETED);
        SystemLogger.INSTANCE.logInfo("PRODUCTION", order.getId() + " -> " + order.getStatus());
    }

    protected abstract void produceUnit(ProductionOrder order) throws InterruptedException;
}
// Koniec, Tydzień 8
// Zasada odwracania zależności (DIP) 1 - wersja z klasą abstrakcyjną