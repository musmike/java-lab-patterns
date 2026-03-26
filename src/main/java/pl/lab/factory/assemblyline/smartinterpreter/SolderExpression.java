package pl.lab.factory.assemblyline.smartinterpreter;

import pl.lab.factory.pcbboards.BaseBoard;
import pl.lab.factory.pcbboards.SolderMaterials;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Interpreter 3 (Wyrażenie terminalne - Typ lutu)
public class SolderExpression implements BaseProducerExpression{
    private SolderMaterials expected;

    public SolderExpression(SolderMaterials expected) {
        this.expected = expected;
    }

    @Override
    public boolean interpret(BaseBoard board) {
        return board.getSolderType() == expected;
    }
}

//interpreter 3
//koniec tydzien 5