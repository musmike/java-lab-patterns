package pl.lab.factory.warehouse;
import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.ProductionOrder;

public class WarehouseAdapter implements WarehouseService {
    private final LegacyWarehouseSystem legacyWarehouse;

    public WarehouseAdapter(LegacyWarehouseSystem legacyWarehouse){
        this.legacyWarehouse = legacyWarehouse;
    }

    @Override
    public void storeOrder(ProductionOrder order){
        String itemName = order.getClass().getSimpleName();
        int quantity = order.getQuantity();

        legacyWarehouse.storeItem(itemName, quantity);
    }
}
