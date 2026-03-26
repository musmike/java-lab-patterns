package pl.lab.factory.orders.command;

import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.orders.ProductionQueue;
import java.util.ArrayList;
import java.util.List;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Command 1 (Konkretna komenda — dodanie wielu zamówień naraz)
// execute() dodaje całą listę do kolejki
// undo() usuwa każde zamówienie z listy.
public class AddMultipleOrdersCommand implements OrderQueueCommand {
    private final List<ProductionOrder> orders;
    private final ProductionQueue queue = ProductionQueue.getInstance();

    public AddMultipleOrdersCommand(List<ProductionOrder> orders) {
        this.orders = new ArrayList<>(orders);
    }

    @Override
    public void execute() {
        orders.forEach(queue::addOrder);
    }

    @Override
    public void undo() {
        orders.forEach(queue::removeOrder);
    }
}
// Koniec, Tydzień 5
// Wzorzec Command 1