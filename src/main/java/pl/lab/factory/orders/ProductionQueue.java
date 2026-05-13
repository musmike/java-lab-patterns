package pl.lab.factory.orders;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.iterator.OrderCollection;
import pl.lab.factory.orders.iterator.OrderIterator;
import pl.lab.factory.reporting.interpreter.QuantityOperator;

import java.util.*;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Iterator 1 (Konkretny Agregat/Kolekcja)
public class ProductionQueue implements OrderCollection {
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

    @Override
    public void addOrder(ProductionOrder order) {
        if (order == null)
            throw new IllegalArgumentException("Order cannot be null");
        queue.offer(order);
        SystemLogger.INSTANCE.logInfo("ProductionQueue", "Added: " + order.getId()
                + " | type: " + order.getClass().getSimpleName()
                + " | qty: " + order.getQuantity());
    }

    // Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
    // Wzorzec Iterator 1 (Metoda fabryczna tworząca iterator)
    // Klient prosi kolekcję o iterator, nie tworzy go sam.
    @Override
    public OrderIterator statusIterator(OrderStatus status) {
        // Zwracamy instancję naszej prywatnej, wewnętrznej implementacji iteratora.
        // Przekazujemy mu "migawkę" stanu kolejki w momencie tworzenia.
        return new StatusIteratorImpl(status, new ArrayList<>(queue));
    }
    // Koniec, Tydzień 5
    // Wzorzec Iterator 1

    public ProductionOrder nextOrder() { return queue.poll(); }

    public boolean isEmpty() { return queue.isEmpty(); }

    public int size() { return queue.size(); }

    public List<ProductionOrder> getAllOrders() { return Collections.unmodifiableList(new ArrayList<>(queue)); }

    public void printQueue() {
        if (queue.isEmpty()) {
            SystemLogger.INSTANCE.logInfo("ProductionQueue", "Queue is empty.");
            return;
        }
        SystemLogger.INSTANCE.logInfo("ProductionQueue", "Orders in queue (" + queue.size() + "):");
        queue.forEach(order -> SystemLogger.INSTANCE.logInfo("ProductionQueue", "  -> " + order));
    }

    // Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
    // Wzorzec Command 1 (Receiver) — metody wywoływane przez komendy
    // removeOrder() i clear() umożliwiają undo().
    @Override
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


    // Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
    // Wzorzec Iterator 1 (Konkretny Iterator jako klasa wewnętrzna)
    // Implementacja iteratora jest ukryta wewnątrz kolekcji.
    // Zapewnia to hermetyzację logiki przechodzenia po danych.
    private static class StatusIteratorImpl implements OrderIterator {
        private final OrderStatus statusToFilter;
        private final List<ProductionOrder> orderList;

        private int statusPosition = 0;
        private int quantityPosition = 0;

        public StatusIteratorImpl(OrderStatus status, List<ProductionOrder> orders) {
            this.statusToFilter = status;
            this.orderList = orders;
        }

        // --- Tryb 1: Iteracja po statusie ---
        @Override
        public boolean hasNext() {
            while (statusPosition < orderList.size()) {
                if (matchesStatus(orderList.get(statusPosition))) {
                    return true;
                }
                statusPosition++;
            }
            return false;
        }

        private boolean matchesStatus(ProductionOrder order) {
            return order.getStatus() == statusToFilter;
        }

        @Override
        public ProductionOrder next() {
            while (statusPosition < orderList.size()) {
                ProductionOrder currentOrder = orderList.get(statusPosition);
                statusPosition++;
                if (currentOrder.getStatus() == statusToFilter) {
                    return currentOrder;
                }
            }
            throw new NoSuchElementException();
        }

        // --- Tryb 2: Iteracja po statusie ORAZ ilości ---
        @Override
        public boolean hasNextByQuantity(QuantityOperator operator, int value) {
            while (quantityPosition < orderList.size()) {
                if (matchesStatusAndQuantity(orderList.get(quantityPosition), operator, value)) {
                    return true;
                }
                quantityPosition++;
            }
            return false;
        }

        private boolean matchesStatusAndQuantity(ProductionOrder order, QuantityOperator operator, int value) {
            return matchesStatus(order) && isQuantityMatch(order.getQuantity(), operator, value);
        }

        private boolean isQuantityMatch(int orderQuantity, QuantityOperator operator, int value) {
            return switch (operator) {
                case GREATER_THAN -> orderQuantity > value;
                case LESS_THAN    -> orderQuantity < value;
                case EQUAL        -> orderQuantity == value;
            };
        }

        @Override
        public ProductionOrder nextByQuantity() {
            ProductionOrder order = orderList.get(quantityPosition);
            quantityPosition++;
            return order;
        }

        @Override
        public void reset() {
            this.statusPosition = 0;
            this.quantityPosition = 0;
        }
    }
    // Koniec, Tydzień 5
    // Wzorzec Iterator 1
}
// Koniec, Tydzień 5
// Wzorzec Iterator 1