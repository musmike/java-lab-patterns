package pl.lab.factory.reporting.interpreter;

import pl.lab.factory.orders.ProductionOrder;
import java.util.List;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Interpreter 1 (Wyrażenie terminalne — filtr po ilości)
// Interpretuje warunek QUANTITY >/</= <wartość>
// Operator przekazywany jako String do konstruktora.
public class QuantityExpression implements AbstractExpression {
    private final QuantityOperator operator;
    private final int value;

    public QuantityExpression(QuantityOperator operator, int value) {
        this.operator = operator;
        this.value = value;
    }

    @Override
    public List<ProductionOrder> interpret(ReportContext reportContext) {
        reportContext.setFilter(order -> {
            return switch (operator) {
                case GREATER_THAN -> order.getQuantity() > value;
                case LESS_THAN    -> order.getQuantity() < value;
                case EQUAL        -> order.getQuantity() == value;
            };
        });
        return reportContext.search();
    }
}
// Koniec, Tydzień 5
// Wzorzec Interpreter 1