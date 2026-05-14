package pl.lab.factory.warehouse;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.BaseBoardProductionOrder;
import pl.lab.factory.orders.BaseElectronicBoardProductionOrder;
import pl.lab.factory.orders.ProductionOrder;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Adapter 2 (Konkretny Adapter)
public class WarehouseAdapter implements WarehouseService {

    private static final String LOG_CONTEXT = WarehouseAdapter.class.getSimpleName();

    private final LegacyWarehouseSystem legacyWarehouseSystem;

    private final WarehouseSection mainWarehouse;

    private final WarehouseSection baseBoardZone;
    private final WarehouseSection electronicBoardZone;

    public WarehouseAdapter(LegacyWarehouseSystem legacyWarehouseSystem){
        this.legacyWarehouseSystem = legacyWarehouseSystem;

        mainWarehouse = new WarehouseSection("Main Warehouse");
        baseBoardZone = new WarehouseSection("Base Board Zone");
        electronicBoardZone = new WarehouseSection("Electronic Board Zone");

        mainWarehouse.add(baseBoardZone);
        mainWarehouse.add(electronicBoardZone);
    }

    public void removeOrder(ProductionOrder order){
        SystemLogger.INSTANCE.logInfo("WAREHOUSE", "Removing order " + order.getId() + " from warehouse");
        mainWarehouse.removeOrder(order.getId());
    }

    @Override
    public void storeOrder(ProductionOrder order) {
        updateLegacySystem(order);
        WarehouseItem item = createWarehouseItem(order);
        storeInTargetSection(order, item);
    }

    private void updateLegacySystem(ProductionOrder order) {
        String itemName = order.getClass().getSimpleName();
        legacyWarehouseSystem.storeItem(itemName, order.getQuantity());
    }

    private WarehouseItem createWarehouseItem(ProductionOrder order) {
        String itemName = order.getClass().getSimpleName();
        String formattedName = String.format("%s (%s)", itemName, order.getId());
        
        return new WarehouseItem(formattedName, order.getQuantity(), order.getId());
    }

    private void storeInTargetSection(ProductionOrder order, WarehouseItem item) {
        WarehouseSection targetSection = determineTargetSection(order);
        SystemLogger.INSTANCE.logInfo(LOG_CONTEXT, "Sending order " + order.getId() + " to " + targetSection.getName());
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
        SystemLogger.INSTANCE.logInfo(LOG_CONTEXT, "Current warehouse state:");

        // Pobieramy dane z klasy Legacy i logujemy je tutaj (Zachowanie SRP dla Legacy)
        legacyWarehouseSystem.getStorage().forEach((item, quantity) ->
            SystemLogger.INSTANCE.logInfo("LEGACY WAREHOUSE", item + " -> " + quantity));
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