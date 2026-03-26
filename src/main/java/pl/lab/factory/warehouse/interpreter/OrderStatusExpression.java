package pl.lab.factory.warehouse.interpreter;

import pl.lab.factory.orders.OrderStatus;
import pl.lab.factory.orders.ProductionOrder;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Interpreter 2 (Wyrażenie terminalne - Status)
public class OrderStatusExpression implements OrderExpression{
    private final OrderStatus status;

    public OrderStatusExpression(OrderStatus status){
        this.status = status;
    }

    @Override
    public boolean interpret(ProductionOrder order){
        return order.getStatus() == status;
    }
}
// Koniec, Tydzień 5
// Wzorzec Interpreter 2