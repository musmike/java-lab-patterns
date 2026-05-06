package pl.lab.factory.reporting;

import pl.lab.factory.orders.ProductionOrder;

import java.util.List;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Bridge 1 (Abstrakcja)
// Trzyma referencję do ReportFormatter zamiast dziedziczyć po nim.
// Podklasy definiują CO raportują; formatter decyduje JAK to wygląda

// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada podstawienia Liskov (LSP) 4
// Klasa abstrakcyjna ProductionReportm która jest bazowa 
// dla różnych typów raportów takich jak CompletedOnlyProductionReport czy SummaryProductionReport,
public abstract class ProductionReport {

    protected final ReportFormatter formatter;

    public ProductionReport(ReportFormatter formatter) {
        this.formatter = formatter;
    }

    public final void generate(List<ProductionOrder> orders) {
        writeReportHeader();
        List<ProductionOrder> filteredOrders = filterOrders(orders);
        writeOrderDetails(filteredOrders);
        writeReportFooter(filteredOrders);
    }

    protected abstract void writeReportHeader();

    protected List<ProductionOrder> filterOrders(List<ProductionOrder> orders) {
        return orders;
    }

    private void writeOrderDetails(List<ProductionOrder> orders) {
        for (ProductionOrder order : orders) {
            formatter.writeOrder(order);
        }
    }

    private void writeReportFooter(List<ProductionOrder> orders) {
        long totalUnits = orders.stream()
                .mapToLong(ProductionOrder::getQuantity)
                .sum();
        formatter.writeFooter(orders.size(), totalUnits);
    }
}
// Koniec, Tydzień 3
// Wzorzec Bridge 1

// Koniec, Tydzień 8
// Zasada podstawienia Liskov (LSP) 4