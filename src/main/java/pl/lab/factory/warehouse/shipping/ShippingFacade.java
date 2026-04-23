package pl.lab.factory.warehouse.shipping;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.OrderStatus;
import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.warehouse.WarehouseAdapter;
import pl.lab.factory.warehouse.WarehouseManagementService;

// Tydzień 4 - Stosowanie strukturalnych wzorców projektowych cz. 2
// Wzorzec Facade 3
// Fasada ukrywa wywołania metod odpowiedzialnych za dostawę i aktualizację magazynu podczas wysyłki zamówienia.
public class ShippingFacade {
    private final WarehouseManagementService warehouse;

    public ShippingFacade(WarehouseManagementService warehouse){
        this.warehouse = warehouse;
    }

    public void ship(ShippingStrategy strategy, ProductionOrder order){
        strategy.shipOrder(order);
        warehouse.removeOrder(order);
    }

    public void cancelShipping(ProductionOrder order){
        SystemLogger.INSTANCE.logInfo("FACADE", "Cancelling shipping for order " + order.getId());
        order.setStatus(OrderStatus.COMPLETED);
        warehouse.storeOrder(order);
    }


}
// Koniec, Tydzień 4
// Wzorzec Facade 3