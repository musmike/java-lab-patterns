package pl.lab.factory.warehouse;

import pl.lab.factory.orders.ProductionOrder;
// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Adapter 2 (Interfejs docelowy — Target)
public interface WarehouseService {
    void storeOrder(ProductionOrder order);
    void removeOrder(ProductionOrder order);
    void printWarehouse();
    void printLegacyWarehouse();

}
// Koniec, Tydzień 3
// Wzorzec Adapter 2