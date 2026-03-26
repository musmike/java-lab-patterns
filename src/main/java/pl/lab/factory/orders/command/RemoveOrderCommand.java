package pl.lab.factory.orders.command;

import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.orders.ProductionQueue;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Command 1 (Konkretna komenda — usunięcie zamówienia)
// execute() usuwa zamówienie z kolejki
// undo() je przywraca.
public class RemoveOrderCommand implements OrderQueueCommand {
    private final ProductionOrder order;
    private final ProductionQueue queue = ProductionQueue.getInstance();

    public RemoveOrderCommand(ProductionOrder order) {
        this.order = order;
    }

    @Override
    public void execute() {
        queue.removeOrder(order);
    }

    @Override
    public void undo() {
        queue.addOrder(order);
    }
}
// Koniec, Tydzień 5
// Wzorzec Command 1