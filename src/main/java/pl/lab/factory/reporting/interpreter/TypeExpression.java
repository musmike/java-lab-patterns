package pl.lab.factory.reporting.interpreter;

import pl.lab.factory.orders.ProductionOrder;
import java.util.List;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Interpreter 1 (Wyrażenie terminalne — filtr po typie zamówienia)
// Interpretuje warunek TYPE = <NazwaKlasy>
// Porównuje getSimpleName() zamówienia z oczekiwaną nazwą.
public class TypeExpression implements AbstractExpression {
    private final String typeName;

    public TypeExpression(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public List<ProductionOrder> interpret(ReportContext ctx) {
        ctx.setFilter(order -> order.getClass().getSimpleName().equalsIgnoreCase(typeName));
        return ctx.search();
    }
}
// Koniec, Tydzień 5
// Wzorzec Interpreter 1