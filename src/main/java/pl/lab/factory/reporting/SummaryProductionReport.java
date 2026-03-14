package pl.lab.factory.reporting;

import pl.lab.factory.orders.ProductionOrder;

import java.util.List;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Bridge 1 (Uszczegółowiona Abstrakcja — raport skrócony)
// Wypisuje każde zlecenie i stopkę z podsumowaniem.
// Może działać z dowolnym ReportFormatter bez żadnych zmian.
public class SummaryProductionReport extends ProductionReport {

    public SummaryProductionReport(ReportFormatter formatter) {
        super(formatter);
    }

    @Override
    public void generate(List<ProductionOrder> orders) {
        formatter.writeHeader("Production summary");

        for (ProductionOrder order : orders) {
            formatter.writeOrder(order);
        }

        long totalUnits = orders.stream()
                .mapToLong(ProductionOrder::getQuantity)
                .sum();

        formatter.writeFooter(orders.size(), totalUnits);
    }
}
// Koniec, Tydzień 3
// Wzorzec Bridge 1