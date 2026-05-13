package pl.lab.factory.reporting.interpreter;

import pl.lab.factory.orders.ProductionOrder;
import java.util.List;
import java.util.stream.Stream;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Interpreter 1 (Wyrażenie nieterminalne — OR)
// Łączy wyniki lewego i prawego wyrażenia.
// Duplikaty usuwane przez distinct().
public class OrExpression implements AbstractExpression {
    private final AbstractExpression left;
    private final AbstractExpression right;

    public OrExpression(AbstractExpression left, AbstractExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public List<ProductionOrder> interpret(ReportContext reportContext) {
        List<ProductionOrder> leftResult = left.interpret(reportContext);
        List<ProductionOrder> rightResult = right.interpret(reportContext);

        return Stream.concat(leftResult.stream(), rightResult.stream())
                .distinct()
                .toList();
    }
}
// Koniec, Tydzień 5
// Wzorzec Interpreter 1