package pl.lab.factory.manufacturing;

import pl.lab.factory.pcbboards.BaseBoard;
import pl.lab.factory.pcbboards.StandardBoard;

public class StandardBoardFactory extends BaseBoardFactory  {

    // Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
    // Wzorzec Factory 1 (Factory Method)
    // Konkretna fabryka nadpisująca metodę fabryczną createBoard() —
    // zwraca instancję StandardBoard, ukrywając szczegóły tworzenia przed klientem
    @Override
    public BaseBoard createBoard() {
        return new StandardBoard();
    }
    // Koniec, Tydzień 2
    // Wzorzec Factory 1
}
