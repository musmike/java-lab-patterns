package pl.lab.factory.warehouse;
import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.BaseBoardProductionOrder;
import pl.lab.factory.orders.BaseElectronicBoardProductionOrder;
import pl.lab.factory.orders.ProductionOrder;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Adapter 2 (Konkretny Adapter)
public class WarehouseAdapter implements WarehouseService {
    private final LegacyWarehouseSystem legacyWarehouse;

    private final WarehouseSection mainWarehouse;

    private final WarehouseSection zoneA; //BaseBoard
    private final WarehouseSection zoneB; //ElectronicBoard

    public WarehouseAdapter(LegacyWarehouseSystem legacyWarehouse){
        this.legacyWarehouse = legacyWarehouse;

        mainWarehouse = new WarehouseSection("Main Warehouse");
        zoneA = new WarehouseSection("Zone A");
        zoneB = new WarehouseSection("Zone B");

        mainWarehouse.add(zoneA);
        mainWarehouse.add(zoneB);
    }

    @Override
    public void storeOrder(ProductionOrder order){
        String itemName = order.getClass().getSimpleName();
        int quantity = order.getQuantity();

        legacyWarehouse.storeItem(itemName, quantity);

        WarehouseItem item = new WarehouseItem(itemName + " (" + order.getId() + ")", quantity);

        WarehouseSection targetSection;
        if(order instanceof BaseBoardProductionOrder){
            targetSection = zoneA;
        } else if (order instanceof BaseElectronicBoardProductionOrder) {
            targetSection = zoneB;
        }else {
            targetSection = mainWarehouse;
        }

        SystemLogger.INSTANCE.logInfo(
                "WAREHOUSE",
                "Sending order " + order.getId() + " to "+targetSection.getName()
        );
        targetSection.add(item);
    }

    public void printWarehouse() {
        mainWarehouse.print("");
    }
}
// Koniec, Tydzień 3
// Wzorzec Adapter 2