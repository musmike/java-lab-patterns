package pl.lab.factory.orders.iterator;

import pl.lab.factory.orders.ProductionOrder;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Iterator 1 (Interfejs Iteratora)
// Definiuje metody do przechodzenia po kolekcji.
// Zawiera dwa tryby iteracji: standardowy i z filtrowaniem po ilości.
public interface OrderIterator {
    boolean hasNext();
    boolean hasNextByQuantity(String operator, int value);
    ProductionOrder next();
    ProductionOrder nextByQuantity();
    void reset();
}
// Koniec, Tydzień 5
// Wzorzec Iterator 1