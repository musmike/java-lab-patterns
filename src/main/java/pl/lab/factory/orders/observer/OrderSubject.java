package pl.lab.factory.orders.observer;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Observer 1 (Interfejs podmiotu)
// Definiuje kontrakt do zarządzania listą subskrybentów.
public interface OrderSubject {
    void addObserver(OrderObserver observer);
    void removeObserver(OrderObserver observer);
    void notifyObservers();
}
// Koniec, Tydzień 6
// Wzorzec Observer 1
