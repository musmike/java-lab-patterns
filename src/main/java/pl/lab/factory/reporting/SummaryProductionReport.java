package pl.lab.factory.reporting;


// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Bridge 1 (Uszczegółowiona Abstrakcja — raport skrócony)
// Wypisuje każde zlecenie i stopkę z podsumowaniem.
// Może działać z dowolnym ReportFormatter bez żadnych zmian.

// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada podstawienia Liskov (LSP) 4
// Klasa pochodna SummaryProductionReport dziedzicząca po ProductionReport (abstrakcyjnej)
public class SummaryProductionReport extends ProductionReport {

    public SummaryProductionReport(ReportFormatter formatter) {
        super(formatter);
    }

    @Override
    protected void writeReportHeader() {
        formatter.writeHeader("Production summary");
    }
}
// Koniec, Tydzień 3
// Wzorzec Bridge 1

// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada podstawienia Liskov (LSP) 4