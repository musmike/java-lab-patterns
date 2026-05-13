package pl.lab.factory.manufacturing;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.ElectronicType;
import pl.lab.factory.pcbboards.StandardBoard;

// Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
// Wzorzec Factory 3 (Simple Factory)
// ukrywa logikę tworzenia różnych typów elektronicznych płytek.
// Metoda createBoard() sprawdza typ płytki i ustawia firmware oraz bazę

public class ElectronicBoardFactory {
    public static BaseElectronicBoard createBoard(ElectronicType type){
        BaseElectronicBoard electronicBoard = new BaseElectronicBoard();
        electronicBoard.setBoardType(type);
        electronicBoard.setBaseBoard(new StandardBoard());

        return electronicBoard;
    }
}

// Koniec, Tydzień 2
// Wzorzec Factory 3