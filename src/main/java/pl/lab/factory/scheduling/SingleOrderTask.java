package pl.lab.factory.scheduling;

import pl.lab.factory.manufacturing.OrderProcessor;
import pl.lab.factory.orders.ProductionOrder;

import java.util.concurrent.TimeUnit;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Composite 1 (Liść)
// Niepodzielna jednostka drzewa
public class SingleOrderTask implements ProductionTask {

    private final ProductionOrder order;
    private final OrderProcessor processor;

    public SingleOrderTask(ProductionOrder order, OrderProcessor processor) {
        this.order = order;
        this.processor = processor;
    }

    @Override
    public void execute() throws InterruptedException {
        processor.process(order);
        TimeUnit.MILLISECONDS.sleep(500);
    }

    @Override
    public String getDescription(String indent) {
        return indent + "- [ZLECENIE] " + order.getId()
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