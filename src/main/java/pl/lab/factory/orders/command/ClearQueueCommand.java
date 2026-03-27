package pl.lab.factory.orders.command;

import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.orders.ProductionQueue;
import java.util.ArrayList;
import java.util.List;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Command 1 (Konkretna komenda — wyczyszczenie kolejki)
// execute() zapisuje backup całej kolejki przed czyszczeniem
// undo() przywraca wszystkie zamówienia ze snapshotu.
public class ClearQueueCommand implements OrderQueueCommand {
    private final ProductionQueue queue = ProductionQueue.getInstance();
    private List<ProductionOrder> backup;

    @Override
    public void execute() {
        backup = new ArrayList<>(queue.getAllOrders());
        queue.clear();
    }

    @Override
    public void undo() {
        if (backup != null) {
            for (ProductionOrder order : backup) {
                queue.addOrder(order);
            }
        }
    }
}
// Koniec, Tydzień 5
// Wzorzec Command 1