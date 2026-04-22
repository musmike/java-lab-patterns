package pl.lab.factory.reporting.reportingsystem;

import pl.lab.factory.orders.ProductionOrder;

// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada segregacji interfejsów (ISP) 1 ("Gruby" interfejs)
// Interfejs ReportingSystem został podzielony na mniejsze interfejsy (ReportGenerator, ReportFormatter, ReportDispatcher),
// aby klasy implementujące mogły zależeć tylko od tych metod, które są im potrzebne.
public interface ReportingSystem {
    void generateReport(ProductionOrder order);   // generowanie
    String formatAsHtml(ProductionOrder order);   // formatowanie HTML
    String formatAsCsv(ProductionOrder order);    // formatowanie CSV
    void archiveReport(ProductionOrder order);    // archiwizacja
    void sendReportByEmail(ProductionOrder order); // wysyłka
}
// Koniec, Tydzień 8
// Zasada segregacji interfejsów (ISP) 1
