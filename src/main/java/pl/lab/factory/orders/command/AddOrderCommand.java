package pl.lab.factory.orders.command;

import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.orders.ProductionQueue;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Command 1 (Konkretna komenda — dodanie zamówienia)
// execute() dodaje zamówienie do kolejki; undo() je usuwa.
public class AddOrderCommand implements OrderQueueCommand {
    private final ProductionOrder order;
    private final ProductionQueue queue = ProductionQueue.getInstance();

    public AddOrderCommand(ProductionOrder order) {
        this.order = order;
    }

    @Override
    public void execute() {
        queue.addOrder(order);
    }

    @Override
    public void undo() {
        queue.removeOrder(order);
    }
}
// Koniec, Tydzień 5
// Wzorzec Command 1