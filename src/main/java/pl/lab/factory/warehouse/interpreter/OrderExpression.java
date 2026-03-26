package pl.lab.factory.warehouse.interpreter;

import pl.lab.factory.orders.ProductionOrder;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Interpreter 2 (Interfejs wyrażenia)
// Definiuje operację interpretacji dla wszystkich węzłów w drzewie gramatycznym.
public interface OrderExpression {
    boolean interpret(ProductionOrder order);
}
// Koniec, Tydzień 5
// Wzorzec Interpreter 2