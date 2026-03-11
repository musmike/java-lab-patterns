package pl.lab.factory.reporting;

import pl.lab.factory.orders.ProductionOrder;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Bridge 1 (Konkretny Implementor — konsola)
// Formatuje raport jako czytelny tekst na standardowe wyjście.
public class ConsoleReportFormatter implements ReportFormatter {

    @Override
    public void writeHeader(String title) {
        System.out.println("========================================");
        System.out.println("  RAPORT: " + title);
        System.out.println("========================================");
    }

    @Override
    public void writeOrder(ProductionOrder order) {
        System.out.printf("  [%-10s] %-45s qty: %3d  status: %s%n",
                order.getId(),
                order.getClass().getSimpleName(),
                order.getQuantity(),
                order.getStatus());
    }

    @Override
    public void writeFooter(int totalOrders, long totalUnits) {
        System.out.println("----------------------------------------");
        System.out.printf("  Łącznie zleceń: %d  |  Łącznie jednostek: %d%n", totalOrders, totalUnits);
        System.out.println("========================================");
    }
}
// Koniec, Tydzień 3
// Wzorzec Bridge 1