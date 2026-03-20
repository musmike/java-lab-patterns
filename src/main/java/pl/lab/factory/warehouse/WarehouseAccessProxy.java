package pl.lab.factory.warehouse;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.ProductionOrder;
import java.util.Set;

// Tydzień 4 - Stosowanie strukturalnych wzorców projektowych cz. 2
// Wzorzec Proxy 3 (Protection Proxy)
public class WarehouseAccessProxy implements WarehouseService{
    private final WarehouseService realWarehouse;
    private final Set<String> unapprovedOrderIds;
    public WarehouseAccessProxy(WarehouseService realWarehouse, Set<String> unapprovedOrderIds){
        this.realWarehouse = realWarehouse;
        this.unapprovedOrderIds = unapprovedOrderIds;
    }

    @Override
    public void storeOrder(ProductionOrder order){
        if(unapprovedOrderIds.contains(order.getId())){
            SystemLogger.INSTANCE.logError(
                    "PROXY WAREHOUSE",
                    "ACCESS DENIED (UNAPPROVED) for storing order " + order.getId()
            );
            return;
        }
        realWarehouse.storeOrder(order);
    }

    @Override
    public void removeOrder(ProductionOrder order){
        realWarehouse.removeOrder(order);
    }

    @Override
    public void printWarehouse(){
        realWarehouse.printWarehouse();
    }
}
// Koniec, Tydzień 4
// Wzorzec Proxy 3