package pl.lab.factory.warehouse;

import pl.lab.factory.orders.ProductionOrder;

// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada segregacji interfejsów (ISP) 1 (Interfejs segregowany)
public interface WarehouseManagementService {
    void storeOrder(ProductionOrder order);
    void removeOrder(ProductionOrder order);
}
// Koniec, Tydzień 8
// Zasada segregacji interfejsów (ISP) 1