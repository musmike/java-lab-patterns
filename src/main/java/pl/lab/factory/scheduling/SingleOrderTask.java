package pl.lab.factory.scheduling;

import pl.lab.factory.mediator.ProductionDepartment;
import pl.lab.factory.orders.ProductionOrder;
import java.util.concurrent.TimeUnit;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Composite 1 (Liść)
// Niepodzielna jednostka drzewa
public class SingleOrderTask implements ProductionTask {

    private final ProductionOrder order;
    private final ProductionDepartment productionDepartment;

    public SingleOrderTask(ProductionOrder order, ProductionDepartment productionDepartment) {
        this.order = order;
        this.productionDepartment = productionDepartment;
    }

    @Override
    public void execute() throws InterruptedException {
        productionDepartment.manufacture(order);
        TimeUnit.MILLISECONDS.sleep(500);
    }

    @Override
    public String getDescription(String indent) {
        return indent + "- [ORDER] " + order.getId()
                + " | qty: " + order.getQuantity()
                + " | status: " + order.getStatus();
    }

    @Override
    public int totalUnits() {
        return order.getQuantity();
    }
}
// Koniec, Tydzień 3
// Wzorzec Composite 1