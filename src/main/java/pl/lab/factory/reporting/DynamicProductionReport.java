package pl.lab.factory.reporting;

import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.reporting.interpreter.AbstractExpression;
import pl.lab.factory.reporting.interpreter.ReportContext;

import java.util.List;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Interpreter 1 (integracja z Bridge)
// Rozszerza ProductionReport (wzorzec Bridge) — filtrowanie przez drzewo wyrażeń Interpretera.
// Formatowanie przez istniejące formattery (Console/CSV) bez żadnych zmian.
public class DynamicProductionReport extends ProductionReport {
    private final AbstractExpression queryTree;

    public DynamicProductionReport(ReportFormatter formatter, AbstractExpression queryTree) {
        super(formatter);
        this.queryTree = queryTree;
    }

    @Override
    public void generate(List<ProductionOrder> orders) {
        formatter.writeHeader("Custom Query Report");

        // Kontekst przekazywany do drzewa. Wynik to przefiltrowana lista zamówień.
        ReportContext ctx = new ReportContext(orders);
        List<ProductionOrder> filteredOrders = queryTree.interpret(ctx);

        for (ProductionOrder order : filteredOrders) {
            formatter.writeOrder(order);
        }

        long totalUnits = filteredOrders.stream().mapToLong(ProductionOrder::getQuantity).sum();
        formatter.writeFooter(filteredOrders.size(), totalUnits);
    }
}
// Koniec, Tydzień 5
// Wzorzec Interpreter 1