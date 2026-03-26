package pl.lab.factory.orders.iterator;


import pl.lab.factory.orders.OrderStatus;
import pl.lab.factory.orders.ProductionOrder;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Iterator 1 (Interfejs Agregatu/Kolekcji)
// Definiuje interfejs dla kolekcji, która potrafi tworzyć iteratory.
public interface OrderCollection {
    void addOrder(ProductionOrder order);
    void removeOrder(ProductionOrder order);

    // Metoda fabryczna, która tworzy i zwraca dedykowany iterator.
    OrderIterator statusIterator(OrderStatus status);
}
// Koniec, Tydzień 5
// Wzorzec Iterator 1