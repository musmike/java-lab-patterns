package pl.lab.factory.orders;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.observer.OrderObserver;
import pl.lab.factory.orders.observer.OrderSubject;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Memento 1 (Originator)
// Źródło stanu. Automatycznie zapisuje migawkę przed każdą zmianą statusu.
// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Observer 1 (Konkretny podmiot)
// Powiadamia obserwatorów o zmianie statusu.
public abstract class ProductionOrder implements Originator, OrderSubject {
    protected String id;
    protected int quantity;
    protected OrderStatus status;
    protected OrderSpecification specification;
    private Memento orderStatusMemento;
    private OrderStatusMementoCaretaker orderStatusMementoCaretaker;

    // Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
    // Wzorzec Observer 1
    // Lista subskrybentów powiadomień
    private final List<OrderObserver> observers = new ArrayList<>();
    // Koniec, Tydzień 6
    // Wzorzec Observer 1


    public ProductionOrder(String id, int quantity, OrderStatus status, OrderSpecification specification) {
        this.id = id;
        this.quantity = quantity;
        this.status = status;
        this.specification = specification;
        this.orderStatusMementoCaretaker = new OrderStatusMementoCaretaker();
    }

    public String getId() { return id; }

    public int getQuantity() { return quantity; }

    public OrderStatus getStatus() { return status; }

    public OrderSpecification getSpecification() { return specification; }

    // Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
    // Wzorzec Memento 1
    // Każda zmiana statusu automatycznie zapisuje poprzedni stan przed nadpisaniem.
    public void setStatus(OrderStatus status) {
        if (this.status != status) {
            save();
            this.status = status;

            // Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
            // Wzorzec Observer 1
            // Powiadamiamy klientów / systemy o zmianie statusu
            notifyObservers();
            // Koniec, Tydzień 6
            // Wzorzec Observer 1
        }
    }

    // Prywatna metoda pomijająca save() — używana wyłącznie przez restore(),
    // żeby nie odkładać migawki podczas przywracania.
    private void setStatusFromMomento(OrderStatus status) {
        if (this.status != status) {
            this.status = status;

            // Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
            // Wzorzec Observer 1
            // Powiadamiamy klientów / systemy o zmianie statusu
            notifyObservers();
            // Koniec, Tydzień 6
            // Wzorzec Observer 1
        }
    }
    // Koniec, Tydzień 5
    // Wzorzec Memento 1

    // Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
    // Wzorzec Observer 1 (Metody z interfejsu OrderSubject)
    @Override
    public void addObserver(OrderObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (OrderObserver observer : observers) {
            observer.update(this);
        }
    }
    // Koniec, Tydzień 6
    // Wzorzec Observer 1

    // Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
    // Wzorzec Prototype 3
    public abstract ProductionOrder clone(String newId);
    // Koniec, Tydzień 2
    // Wzorzec Prototype 3

    // Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
    // Wzorzec Memento 1 (Originator)
    // Tworzy migawkę bieżącego statusu i przekazuje ją do Caretakera.
    @Override
    public void save() {
        orderStatusMemento = new OrderStatusMemento(this, status);
        orderStatusMementoCaretaker.add(orderStatusMemento);
        SystemLogger.INSTANCE.logInfo("Order Status Memento",
                "Saved status snapshot for " + id + ": " + status);
    }
    // Koniec, Tydzień 5
    // Wzorzec Memento 1

    // Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
    // Wzorzec Memento 1 (Originator)
    // Cofa status do poprzedniego. Caretaker zwraca migawkę, migawka przywraca stan.
    public void restoreStatus() {
        orderStatusMemento = orderStatusMementoCaretaker.undo();
        if (orderStatusMemento != null) {
            orderStatusMemento.restore();
            SystemLogger.INSTANCE.logInfo("Order Status Memento",
                    "Restored status for " + id + ": " + status);
        }
    }
    // Koniec, Tydzień 5
    // Wzorzec Memento 1

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + " | id: " + id
                + " | qty: " + quantity
                + " | status: " + status
                + (specification != null ? " | spec: " + specification : "");
    }

    // Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
    // Wzorzec Memento 1 (Konkretne Memento)
    // Prywatna klasa wewnętrzna. Przechowuje status i przywraca go przez restore().
    private class OrderStatusMemento implements Memento {

        private ProductionOrder order;
        private OrderStatus savedStatus;

        OrderStatusMemento(ProductionOrder order, OrderStatus savedStatus) {
            this.order = order;
            this.savedStatus = savedStatus;
        }

        @Override
        public void restore() {
            order.setStatusFromMomento(savedStatus);
        }
    }

    // Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
    // Wzorzec Memento 1 (Caretaker)
    // Prywatna klasa wewnętrzna. Zarządza stosem migawek. Nie zna ich zawartości.
    private class OrderStatusMementoCaretaker {

        Stack<Memento> orderStatusHistory;

        OrderStatusMementoCaretaker() {
            orderStatusHistory = new Stack<>();
        }

        void add(Memento orderStatus) {
            orderStatusHistory.push(orderStatus);
        }

        Memento undo() {
            if (orderStatusHistory.isEmpty()) {
                SystemLogger.INSTANCE.logInfo("Order Status Memento", "No history to undo.");
                return null;
            }
            return orderStatusHistory.pop();
        }
    }
    // Koniec, Tydzień 5
    // Wzorzec Memento 1
}
// Koniec, Tydzień 5
// Wzorzec Memento 1
// Koniec, Tydzień 6
// Wzorzec Observer 1