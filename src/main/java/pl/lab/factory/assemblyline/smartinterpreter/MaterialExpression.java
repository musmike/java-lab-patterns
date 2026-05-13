package pl.lab.factory.assemblyline.smartinterpreter;

import pl.lab.factory.pcbboards.BaseBoard;
import pl.lab.factory.pcbboards.BoardMaterials;
// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Interpreter 3 (Wyrażenie terminalne - Typ materialu)
public class MaterialExpression implements BaseProducerExpression{
    private BoardMaterials expected;

    public MaterialExpression(BoardMaterials expected) {
        this.expected = expected;
    }

    @Override
    public boolean interpret(BaseBoard baseBoard) {
        return baseBoard.getBoardMaterial() == expected;
    }
}

//interpreter 3
//koniec tydzien 5