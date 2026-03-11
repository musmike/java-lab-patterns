package pl.lab.factory.reporting;

import pl.lab.factory.orders.OrderStatus;
import pl.lab.factory.orders.ProductionOrder;

import java.util.List;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Bridge 1 (Uszczegółowiona Abstrakcja — raport ukończonych)
// Filtruje zlecenia do statusu COMPLETED przed przekazaniem do formattera.
// Niezależna od formatu wyjściowego — działa z Console i CSV bez zmian.
public class CompletedOnlyProductionReport extends ProductionReport {

    public CompletedOnlyProductionReport(ReportFormatter formatter) {
        super(formatter);
    }

    @Override
    public void generate(List<ProductionOrder> orders) {
        formatter.writeHeader("Ukończone zlecenia");

        List<ProductionOrder> completed = orders.stream()
                .filter(o -> o.getStatus() == OrderStatus.COMPLETED)
                .toList();

        for (ProductionOrder order : completed) {
            formatter.writeOrder(order);
        }

        long totalUnits = completed.stream()
                .mapToLong(ProductionOrder::getQuantity)
                .sum();

        formatter.writeFooter(completed.size(), totalUnits);
    }
}
// Koniec, Tydzień 3
// Wzorzec Bridge 1