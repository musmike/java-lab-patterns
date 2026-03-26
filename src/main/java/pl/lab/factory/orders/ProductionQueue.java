package pl.lab.factory.orders;

import pl.lab.factory.SystemLogger;

import java.util.*;

public class ProductionQueue {
    private final Queue<ProductionOrder> queue = new LinkedList<>();

    // Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
    // Wzorzec Singleton 3 (Bill Pugh / Static Inner Class)
    // Klasa Holder jest ładowana dopiero przy pierwszym wywołaniu
    // getInstance() — leniwa inicjalizacja bez dodatkowej synchronizacji

    private ProductionQueue() {}

    private static class Holder {
        private static final ProductionQueue INSTANCE = new ProductionQueue();
    }

    public static ProductionQueue getInstance() {
        return Holder.INSTANCE;
    }

    // Koniec, Tydzień 2
    // Wzorzec Singleton 3

    public void addOrder(ProductionOrder order) {
        if (order == null)
            throw new IllegalArgumentException("Order cannot be null");
        queue.offer(order);
        SystemLogger.INSTANCE.logInfo("ProductionQueue", "Added: " + order.getId()
                + " | type: " + order.getClass().getSimpleName()
                + " | qty: " + order.getQuantity());
    }

    public ProductionOrder nextOrder() { return queue.poll(); }

    public boolean isEmpty() { return queue.isEmpty(); }

    public int size() { return queue.size(); }

    public List<ProductionOrder> peek() { return Collections.unmodifiableList(new ArrayList<>(queue)); }

    public void printQueue() {
        if (queue.isEmpty()) {
            SystemLogger.INSTANCE.logInfo("ProductionQueue", "Queue is empty.");
            return;
        }
        SystemLogger.INSTANCE.logInfo("ProductionQueue", "Orders in queue (" + queue.size() + "):");
        queue.forEach(o -> SystemLogger.INSTANCE.logInfo("ProductionQueue", "  -> " + o));
    }

    // Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
    // Wzorzec Command 1 (Receiver) — metody wywoływane przez komendy
    // removeOrder() i clear() umożliwiają undo().
    public void removeOrder(ProductionOrder order) {
        if (queue.remove(order)) {
            SystemLogger.INSTANCE.logInfo("ProductionQueue", "Removed manually: " + order.getId());
        }
    }

    public void clear() {
        queue.clear();
        SystemLogger.INSTANCE.logInfo("ProductionQueue", "Queue cleared!");
    }
    // Koniec, Tydzień 5
    // Wzorzec Command 1
}