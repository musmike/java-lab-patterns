package pl.lab.factory.reporting.interpreter;

import pl.lab.factory.orders.ProductionOrder;
import java.util.List;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Interpreter 1 (Interfejs wyrażenia)
// Wspólny kontrakt dla wyrażeń terminalnych i nieterminalnych; interpret() filtruje zamówienia przez kontekst.
public interface AbstractExpression {
    List<ProductionOrder> interpret(ReportContext ctx);
}
// Koniec, Tydzień 5
// Wzorzec Interpreter 1