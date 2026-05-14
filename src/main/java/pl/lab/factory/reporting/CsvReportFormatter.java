package pl.lab.factory.reporting;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.ProductionOrder;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Bridge 1 (Konkretny Implementor — CSV)
// Formatuje raport jako linie CSV gotowe do zapisu do pliku lub dalszego przetwarzania.

// Tydzień 7 - Stosowanie zasad pojedynczej odpowiedzialności i otwarte-zamknięte
// Zasada pojedynczej odpowiedzalności (SRP) 4
// Klasa CsvReportFormatter jest odpowiedzialna tylko za formatowanie raportu w formacie CSV
public class CsvReportFormatter implements ReportFormatter {

    @Override
    public void writeHeader(String title) {
        SystemLogger.INSTANCE.log("", "\n# " + title);
        SystemLogger.INSTANCE.log("", "orderId,type,quantity,status");
    }

    @Override
    public void writeOrder(ProductionOrder order) {
        System.out.printf("%s,%s,%d,%s%n",
                order.getId(),
                order.getClass().getSimpleName(),
                order.getQuantity(),
                order.getStatus());
    }

    @Override
    public void writeFooter(int totalOrders, long totalUnits) {
        System.out.printf("# totalOrders=%d totalUnits=%d%n", totalOrders, totalUnits);
    }
}
// Koniec, Tydzień 3
// Wzorzec Bridge 1

// Koniec, Tydzień 7
// Zasada pojedynczej odpowiedzalności (SRP) 4