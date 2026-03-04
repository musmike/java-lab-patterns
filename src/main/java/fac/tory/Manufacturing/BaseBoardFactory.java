package fac.tory.Manufacturing;

import fac.tory.AssemblyLine.Solderer;
import fac.tory.PCBBoards.BaseBoard;

public abstract class BaseBoardFactory {
    public abstract BaseBoard createBoard();

    public BaseBoard produceBoard(){
        BaseBoard board = createBoard();
        //mozna mount components
        board.printCircuits();
        Solderer solderer = Solderer.getInstance();
        solderer.solderBoard(board);
        //mozna printer

        if(board.isReady()){
            return board;
        }
        else {
            throw new RuntimeException("Board couldn't be manufactured!");
        }
    }
}
