package pl.lab.factory.warehouse.shipping;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.OrderStatus;
import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.warehouse.WarehouseManagementService;

// Tydzień 4 - Stosowanie strukturalnych wzorców projektowych cz. 2
// Wzorzec Facade 3
// Fasada ukrywa wywołania metod odpowiedzialnych za dostawę i aktualizację magazynu podczas wysyłki zamówienia.
public class ShippingFacade {
    private final WarehouseManagementService warehouseManagementService;

    public ShippingFacade(WarehouseManagementService warehouseManagementService){
        this.warehouseManagementService = warehouseManagementService;
    }

    public void ship(ShippingStrategy strategy, ProductionOrder order){
        strategy.shipOrder(order);
        warehouseManagementService.removeOrder(order);
    }

    public void cancelShipping(ProductionOrder order){
        SystemLogger.INSTANCE.logInfo("FACADE", "Cancelling shipping for order " + order.getId());
        order.setStatus(OrderStatus.COMPLETED);
        warehouseManagementService.storeOrder(order);
    }


}
// Koniec, Tydzień 4
// Wzorzec Facade 3