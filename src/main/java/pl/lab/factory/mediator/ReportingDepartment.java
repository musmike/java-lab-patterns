package pl.lab.factory.mediator;

import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.reporting.BoardReporter;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Mediator 1 (Konkretny Colleague — raportowanie)
// Działa jako odbiorca (sink). Wykonuje swoje zadanie na żądanie
// mediatora, ale samo nie zgłasza żadnych powiadomień.
public class ReportingDepartment extends Department {
    private final BoardReporter reporter;

    public ReportingDepartment(FactoryMediator mediator, BoardReporter reporter) {
        super(mediator);
        this.reporter = reporter;
    }

    public void report(ProductionOrder order) {
        reporter.report(order);
    }
}
// Koniec, Tydzień 5
// Wzorzec Mediator 1