package pl.lab.factory.manufacturing;

import pl.lab.factory.assemblyline.CircuitPrinter;
import pl.lab.factory.assemblyline.Solderer;
import pl.lab.factory.pcbboards.BaseBoard;

public abstract class BaseBoardFactory {
    public abstract BaseBoard createBoard();

    public BaseBoard produceBoard(){
        BaseBoard board = createBoard();

        CircuitPrinter circuitPrinter = CircuitPrinter.getInstance();
        circuitPrinter.printCircuits(board);

        // mozna mount component

        Solderer solderer = Solderer.getInstance();
        solderer.solderBoard(board);

        if (board.isReady()) {
            return board;
        }
        else {
            throw new RuntimeException("Board couldn't be manufactured!");
        }
    }
}
