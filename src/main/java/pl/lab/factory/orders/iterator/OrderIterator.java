package pl.lab.factory.orders.iterator;

import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.reporting.interpreter.QuantityOperator;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Iterator 1 (Interfejs Iteratora)
// Definiuje metody do przechodzenia po kolekcji.
// Zawiera dwa tryby iteracji: standardowy i z filtrowaniem po ilości.
public interface OrderIterator {
    boolean hasNext();
    boolean hasNextByQuantity(QuantityOperator operator, int value);
    ProductionOrder next();
    ProductionOrder nextByQuantity();
    void reset();
}
// Koniec, Tydzień 5
// Wzorzec Iterator 1