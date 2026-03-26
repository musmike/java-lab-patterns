package pl.lab.factory.assemblyline.smartinterpreter;

import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Interpreter 3 (Wyrażenie terminalne - Wyrazenie OR)
public class OrExpression implements BaseProducerExpression{

    private BaseProducerExpression first, second;

    public OrExpression(BaseProducerExpression first, BaseProducerExpression second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean interpret(BaseBoard board) {
        return first.interpret(board) || second.interpret(board);
    }
}

//interpreter 3
//koniec tydzien 5