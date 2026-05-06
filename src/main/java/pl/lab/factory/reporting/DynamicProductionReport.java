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
    protected void writeReportHeader() {
        formatter.writeHeader("Custom Query Report");
    }

    @Override
    protected List<ProductionOrder> filterOrders(List<ProductionOrder> orders) {
        // Kontekst przekazywany do drzewa. Wynik to przefiltrowana lista zamówień.
        ReportContext context = new ReportContext(orders);
        return queryTree.interpret(context);
    }
}
// Koniec, Tydzień 5
// Wzorzec Interpreter 1