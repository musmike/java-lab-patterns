package pl.lab.factory.reporting.interpreter;

import pl.lab.factory.orders.OrderStatus;
import pl.lab.factory.orders.ProductionOrder;
import java.util.List;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Interpreter 1 (Wyrażenie terminalne — filtr po statusie)
// Interpretuje warunek STATUS = <wartość>
// Ustawia predykat na kontekście i zwraca pasujące zamówienia.
public class StatusExpression implements AbstractExpression {
    private final OrderStatus expectedStatus;

    public StatusExpression(OrderStatus expectedStatus) {
        this.expectedStatus = expectedStatus;
    }

    @Override
    public List<ProductionOrder> interpret(ReportContext reportContext) {
        reportContext.setFilter(order -> order.getStatus() == expectedStatus);
        return reportContext.search();
    }
}
// Koniec, Tydzień 5
// Wzorzec Interpreter 1