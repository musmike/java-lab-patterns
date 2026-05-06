package pl.lab.factory.reporting.reportingfacade;

import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.reporting.*;
import pl.lab.factory.reporting.interpreter.AbstractExpression;
import java.util.List;

// Tydzień 4 - Stosowanie strukturalnych wzorców projektowych cz. 2
// Wzorzec Facade 1
// Upraszcza dostęp do systemu raportowania, ukrywając szczegóły klas i formatterów.
// Klient podaje tylko typ raportu i format, a fasada dobiera odpowiednie obiekty.
public class ReportingFacade {

    public static void generate(ReportType type, ReportFormat format, List<ProductionOrder> orders) {
        ReportFormatter formatter = createFormatter(format);

        ProductionReport report = switch (type) {
            case SUMMARY        -> new SummaryProductionReport(formatter);
            case COMPLETED_ONLY -> new CompletedOnlyProductionReport(formatter);
        };

        report.generate(orders);
    }

    // Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
    // Wzorzec Interpreter 1 (integracja z Facade)
    // Przyjmuje gotowe drzewo wyrażeń i ukrywa tworzenie DynamicProductionReport oraz formattera.
    public static void generateDynamic(AbstractExpression queryTree, ReportFormat format, List<ProductionOrder> orders) {
        ReportFormatter formatter = createFormatter(format);

        ProductionReport report = new DynamicProductionReport(formatter, queryTree);
        report.generate(orders);
    }

    private static ReportFormatter createFormatter(ReportFormat format) {
        return switch (format) {
            case CONSOLE -> new ConsoleReportFormatter();
            case CSV     -> new CsvReportFormatter();
        };
    }
    // Koniec, Tydzień 5
    // Wzorzec Interpreter 1
}
// Koniec, Tydzień 4
// Wzorzec Facade 1