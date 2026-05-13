package pl.lab.factory.reporting;

import pl.lab.factory.orders.OrderStatus;
import pl.lab.factory.orders.ProductionOrder;
import java.util.List;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Bridge 1 (Uszczegółowiona Abstrakcja — raport ukończonych)
// Filtruje zlecenia do statusu COMPLETED przed przekazaniem do formattera.
// Niezależna od formatu wyjściowego — działa z Console i CSV bez zmian.

// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada podstawienia Liskov (LSP) 4
// Klasa pochodna CompletedOnlyProductionReport dziedzicząca po klasie ProductionReport (abstrakcyjnej)
public class CompletedOnlyProductionReport extends ProductionReport {

    public CompletedOnlyProductionReport(ReportFormatter formatter) {
        super(formatter);
    }

    @Override
    protected void writeReportHeader() {
        formatter.writeHeader("Completed orders");
    }

    @Override
    protected List<ProductionOrder> filterOrders(List<ProductionOrder> orders) {
        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.COMPLETED)
                .toList();
    }
}
// Koniec, Tydzień 3
// Wzorzec Bridge 1

// Koniec, Tydzień 8
// Zasada podstawienia Liskov (LSP) 4