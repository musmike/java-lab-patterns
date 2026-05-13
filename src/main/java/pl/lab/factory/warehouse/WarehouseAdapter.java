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

    private final WarehouseSection baseBoardZone;
    private final WarehouseSection electronicBoardZone;

    public WarehouseAdapter(LegacyWarehouseSystem legacyWarehouse){
        this.legacyWarehouse = legacyWarehouse;

        mainWarehouse = new WarehouseSection("Main Warehouse");
        baseBoardZone = new WarehouseSection("Base Board Zone");
        electronicBoardZone = new WarehouseSection("Electronic Board Zone");

        mainWarehouse.add(baseBoardZone);
        mainWarehouse.add(electronicBoardZone);
    }

    public void removeOrder(ProductionOrder order){

        SystemLogger.INSTANCE.logInfo(
                "WAREHOUSE",
                "Removing order " + order.getId() + " from warehouse"
        );
        mainWarehouse.removeOrder(order.getId());
    }

    @Override
    public void storeOrder(ProductionOrder order){
        String itemName = order.getClass().getSimpleName();
        int quantity = order.getQuantity();

        legacyWarehouse.storeItem(itemName, quantity);

        WarehouseItem item = new WarehouseItem(itemName + " (" + order.getId() + ")", quantity, order.getId());

        WarehouseSection targetSection = determineTargetSection(order);

        SystemLogger.INSTANCE.logInfo(
                "WAREHOUSE",
                "Sending order " + order.getId() + " to "+targetSection.getName()
        );
        targetSection.add(item);
    }

    private WarehouseSection determineTargetSection(ProductionOrder order) {
        if(order instanceof BaseBoardProductionOrder){
            return baseBoardZone;
        } else if (order instanceof BaseElectronicBoardProductionOrder) {
            return electronicBoardZone;
        }
        return mainWarehouse;
    }

    public void printLegacyWarehouse(){
        SystemLogger.INSTANCE.logInfo("WAREHOUSE", "Current warehouse state:");

        // Pobieramy dane z klasy Legacy i logujemy je tutaj (Zachowanie SRP dla Legacy)
        legacyWarehouse.getStorage().forEach((item, qty) ->
                SystemLogger.INSTANCE.logInfo(
                        "[LEGACY WAREHOUSE]",
                        item + " -> " + qty
                ));
    }

    public void printWarehouse() {
        mainWarehouse.print("");
    }

    public WarehouseSection getBaseBoardZone() {
        return baseBoardZone;
    }

    public WarehouseSection getElectronicBoardZone() {
        return electronicBoardZone;
    }
}
// Koniec, Tydzień 3
// Wzorzec Adapter 2