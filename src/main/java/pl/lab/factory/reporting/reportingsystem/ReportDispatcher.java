package pl.lab.factory.reporting.reportingsystem;

import pl.lab.factory.orders.ProductionOrder;

// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada segregacji interfejsów (ISP) 1 (Interfejs wyspecjalizowany)
// Interfejs ReportDispatcher został wydzielony, aby klasy, które nie potrzebują funkcji generowania i formatowania, 
// nie musiały implementować tych metod. Teraz mogą implementować tylko te interfejsy, które są im potrzebne.
public interface ReportDispatcher {
    void archiveReport(ProductionOrder order);
    void sendReportByEmail(ProductionOrder order);
}
// Koniec, Tydzień 8
// Zasada segregacji interfejsów (ISP) 1
