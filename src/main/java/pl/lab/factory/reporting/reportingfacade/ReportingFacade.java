package pl.lab.factory.reporting.reportingfacade;

import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.reporting.*;

import java.util.List;

// Tydzień 4 - Stosowanie strukturalnych wzorców projektowych cz. 2
// Wzorzec Facade 1
// Upraszcza dostęp do systemu raportowania, ukrywając szczegóły klas i formatterów.
// Klient podaje tylko typ raportu i format, a fasada dobiera odpowiednie obiekty.
public class ReportingFacade {

    public static void generate(ReportType type, ReportFormat format, List<ProductionOrder> orders) {
        ReportFormatter formatter = switch (format) {
            case CONSOLE -> new ConsoleReportFormatter();
            case CSV     -> new CsvReportFormatter();
        };

        ProductionReport report = switch (type) {
            case SUMMARY        -> new SummaryProductionReport(formatter);
            case COMPLETED_ONLY -> new CompletedOnlyProductionReport(formatter);
        };

        report.generate(orders);
    }
}
// Koniec, Tydzień 4
// Wzorzec Facade 1
