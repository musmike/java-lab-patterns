package pl.lab.factory.reporting;

import pl.lab.factory.orders.ProductionOrder;

import java.util.List;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Bridge 1 (Abstrakcja)
// Trzyma referencję do ReportFormatter zamiast dziedziczyć po nim.
// Podklasy definiują CO raportują; formatter decyduje JAK to wygląda
public abstract class ProductionReport {

    protected final ReportFormatter formatter;

    public ProductionReport(ReportFormatter formatter) {
        this.formatter = formatter;
    }

    public abstract void generate(List<ProductionOrder> orders);
}
// Koniec, Tydzień 3
// Wzorzec Bridge 1