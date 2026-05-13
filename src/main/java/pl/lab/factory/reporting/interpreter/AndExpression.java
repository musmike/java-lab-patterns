package pl.lab.factory.reporting.interpreter;

import pl.lab.factory.orders.ProductionOrder;
import java.util.List;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Interpreter 1 (Wyrażenie nieterminalne — AND)
// Zawęża kontekst: wynik lewego wyrażenia staje się nowym kontekstem dla prawego.
public class AndExpression implements AbstractExpression {
    private final AbstractExpression left;
    private final AbstractExpression right;

    public AndExpression(AbstractExpression left, AbstractExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public List<ProductionOrder> interpret(ReportContext reportContext) {
        List<ProductionOrder> leftResult = left.interpret(reportContext);
        ReportContext narrowedContext = new ReportContext(leftResult);
        return right.interpret(narrowedContext);
    }
}
// Koniec, Tydzień 5
// Wzorzec Interpreter 1