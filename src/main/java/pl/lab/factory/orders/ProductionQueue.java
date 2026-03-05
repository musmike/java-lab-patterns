package pl.lab.factory.orders;

import java.util.*;

public class ProductionQueue {
    private final Queue<ProductionOrder> queue = new LinkedList<>();

    // Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
    // Wzorzec Singleton 4 (Bill Pugh / Static Inner Class)
    // Klasa Holder jest ładowana dopiero przy pierwszym wywołaniu
    // getInstance() — leniwa inicjalizacja bez synchronizacji

    private ProductionQueue() {}

    private static class Holder {
        private static final ProductionQueue INSTANCE = new ProductionQueue();
    }

    public static ProductionQueue getInstance() {
        return Holder.INSTANCE;
    }

    // Koniec, Tydzień 2
    // Wzorzec Singleton 4

    public void addOrder(ProductionOrder order) {
        if (order == null)
            throw new IllegalArgumentException("Order cannot be null");
        queue.offer(order);
        System.out.println("[ProductionQueue] Dodano: " + order.getId()
                + " | typ: " + order.getClass().getSimpleName()
                + " | qty: " + order.getQuantity());
    }

    public ProductionOrder nextOrder() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }

    public List<ProductionOrder> peek() {
        return Collections.unmodifiableList(new ArrayList<>(queue));
    }

    public void printQueue() {
        if (queue.isEmpty()) {
            System.out.println("[ProductionQueue] Kolejka jest pusta.");
            return;
        }
        System.out.println("[ProductionQueue] Zlecenia w kolejce (" + queue.size() + "):");
        queue.forEach(o -> System.out.println("  -> " + o));
    }
}
