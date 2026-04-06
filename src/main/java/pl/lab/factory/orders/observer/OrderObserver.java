package pl.lab.factory.orders.observer;

import pl.lab.factory.orders.ProductionOrder;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Observer 1 (Interfejs obserwatora)
// Wspólny interfejs dla wszystkich komponentów nasłuchujących na zmiany w zamówieniu.
public interface OrderObserver {
    void update(ProductionOrder order);
}
// Koniec, Tydzień 6
// Wzorzec Observer 1
