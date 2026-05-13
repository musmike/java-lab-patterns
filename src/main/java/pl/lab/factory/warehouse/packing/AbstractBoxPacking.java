package pl.lab.factory.warehouse.packing;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.OrderStatus;
import pl.lab.factory.orders.ProductionOrder;

public abstract class AbstractBoxPacking implements PackageImplementor {
    @Override
    public void packOrder(ProductionOrder order) {
        order.setStatus(OrderStatus.PACKED);
        SystemLogger.INSTANCE.logInfo(getBoxLogName(), "Packing order " + order.getId() + " in " + getBoxDescription() + ".");
    }

    protected abstract String getBoxLogName();
    protected abstract String getBoxDescription();
}