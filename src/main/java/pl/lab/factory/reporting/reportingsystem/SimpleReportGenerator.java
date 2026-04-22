package pl.lab.factory.reporting.reportingsystem;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.ProductionOrder;

// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada segregacji interfejsów (ISP) 1 (Konkretna klasa)
// Klasa zależy tylko od tego, czego potrzebuje. Nie musi implementować pustych metod wysyłki.
public class SimpleReportGenerator implements ReportGenerator {
    @Override
    public void generateReport(ProductionOrder order) {
        SystemLogger.INSTANCE.logInfo("ISP-GENERATOR", "Generating raw data for order: " + order.getId());
    }
}
// Koniec, Tydzień 8
// Zasada segregacji interfejsów (ISP) 1
