package pl.lab.factory.warehouse.interpreter;

import pl.lab.factory.orders.ProductionOrder;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Interpreter 2 (Wyrażenie terminalne - Minimalna ilość)
public class OrderMinQuantityExpression implements OrderExpression{
    private final int min;

    public OrderMinQuantityExpression(int min){
        this.min = min;
    }

    @Override
    public boolean interpret(ProductionOrder order){
        return order.getQuantity() >= min;
    }
}
// Koniec, Tydzień 5
// Wzorzec Interpreter 2