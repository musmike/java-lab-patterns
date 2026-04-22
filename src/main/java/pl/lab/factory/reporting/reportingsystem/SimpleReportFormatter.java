package pl.lab.factory.reporting.reportingsystem;

import pl.lab.factory.orders.ProductionOrder;

// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada segregacji interfejsów (ISP) 1 (Konkretna klasa)
public class SimpleReportFormatter implements ReportFormatter {
    @Override
    public String formatAsHtml(ProductionOrder order) {
        return "<html><body>Order: " + order.getId() + "</body></html>";
    }

    @Override
    public String formatAsCsv(ProductionOrder order) {
        return order.getId() + "," + order.getQuantity() + "," + order.getStatus();
    }
}
// Koniec, Tydzień 8
// Zasada segregacji interfejsów (ISP) 1