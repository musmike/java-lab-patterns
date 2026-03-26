package pl.lab.factory.warehouse.interpreter;

import pl.lab.factory.orders.ProductionOrder;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Interpreter 2 (Wyrażenie nie-terminalne - Operacja OR)
public class OrderOrExpression implements OrderExpression{
    private final OrderExpression left;
    private final OrderExpression right;

    public OrderOrExpression(OrderExpression left, OrderExpression right){
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean interpret(ProductionOrder order){
        return left.interpret(order) || right.interpret(order);
    }
}
// Koniec, Tydzień 5
// Wzorzec Interpreter 2