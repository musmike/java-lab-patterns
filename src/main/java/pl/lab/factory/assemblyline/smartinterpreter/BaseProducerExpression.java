package pl.lab.factory.assemblyline.smartinterpreter;

import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Interpreter 3 (Interfejs wyrażenia)
//
public interface BaseProducerExpression {
    boolean interpret(BaseBoard baseBoard);
}

//interpreter 3
//koniec tydzien 5