package pl.lab.factory.warehouse.shipping;

import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.warehouse.WarehouseAdapter;
import pl.lab.factory.warehouse.WarehouseService;

// Tydzień 4 - Stosowanie strukturalnych wzorców projektowych cz. 2
// Wzorzec Facade 3
// Fasada ukrywa wywołania metod odpowiedzialnych za dostawę i aktualizację magazynu podczas wysyłki zamówienia.
public class ShippingFacade {
    private final WarehouseService warehouse;

    public ShippingFacade(WarehouseService warehouse){
        this.warehouse = warehouse;
    }

    public void ship(ShippingStrategy strategy, ProductionOrder order){
        strategy.shipOrder(order);
        warehouse.removeOrder(order);
    }


}
// Koniec, Tydzień 4
// Wzorzec Facade 3