package pl.lab.factory.mediator;

import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.warehouse.WarehouseService;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Mediator 1 (Konkretny Colleague — magazyn)
// Wywołuje logikę magazynu, a następnie powiadamia mediatora
// o zapisaniu zamówienia eventem ORDER_STORED.
public class WarehouseDepartment extends Department {
    private final WarehouseService warehouseService;

    public WarehouseDepartment(FactoryMediator mediator, WarehouseService warehouseService) {
        super(mediator);
        this.warehouseService = warehouseService;
    }

    public void store(ProductionOrder order) {
        warehouseService.storeOrder(order);

        if (mediator != null) {
            mediator.notify(this, "ORDER_STORED", order);
        }
    }
}
// Koniec, Tydzień 5
// Wzorzec Mediator 1
