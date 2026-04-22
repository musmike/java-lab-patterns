package pl.lab.factory.reporting.reportingsystem;

import pl.lab.factory.orders.ProductionOrder;

// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada segregacji interfejsów (ISP) 1 (Interfejs wyspecjalizowany)
// Interfejs ReportGenerator został wydzielony, aby klasy, które nie potrzebują funkcji formatowania i wysyłki,
// nie musiały implementować tych metod. Teraz mogą implementować tylko te interfejsy, które są im potrzebne.
public interface ReportGenerator {
    void generateReport(ProductionOrder order);
}
// Koniec, Tydzień 8
// Zasada segregacji interfejsów (ISP) 1