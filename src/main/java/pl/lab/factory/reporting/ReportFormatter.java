package pl.lab.factory.reporting;

import pl.lab.factory.orders.ProductionOrder;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Bridge 1 (Implementor)
// Definiuje kontrakt dla wszystkich formatów wyjściowych raportu.
// Abstraction (ProductionReport) trzyma referencję do tego interfejsu —
// obie hierarchie (raporty i formaty) mogą się rozrastać niezależnie.
public interface ReportFormatter {
    void writeHeader(String title);
    void writeOrder(ProductionOrder order);
    void writeFooter(int totalOrders, long totalUnits);
}
// Koniec, Tydzień 3
// Wzorzec Bridge 1
