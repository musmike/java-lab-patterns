package pl.lab.factory.reporting.reportingsystem;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.ProductionOrder;

// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada segregacji interfejsów (ISP) 1 (Konkretna klasa)
public class SimpleReportDispatcher implements ReportDispatcher {
    @Override
    public void archiveReport(ProductionOrder order) {
        SystemLogger.INSTANCE.logInfo("ISP-DISPATCHER", "Order " + order.getId() + " safely archived to database.");
    }

    @Override
    public void sendReportByEmail(ProductionOrder order) {
        SystemLogger.INSTANCE.logInfo("ISP-DISPATCHER", "Email sent to manager regarding order: " + order.getId());
    }
}
// Koniec, Tydzień 8
// Zasada segregacji interfejsów (ISP) 1