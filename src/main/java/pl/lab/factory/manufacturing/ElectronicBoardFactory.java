package pl.lab.factory.manufacturing;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.ElectronicType;
import pl.lab.factory.electronicboards.firmware.FirmwareAdapter;
import pl.lab.factory.pcbboards.StandardBoard;

// Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
// Wzorzec Factory 3 (Simple Factory)
// ukrywa logikę tworzenia różnych typów elektronicznych płytek.
// Metoda createBoard() sprawdza typ płytki i ustawia firmware oraz bazę

public class ElectronicBoardFactory {
    public static BaseElectronicBoard createBoard(ElectronicType type, FirmwareAdapter adapter){
        BaseElectronicBoard board = new BaseElectronicBoard();
        board.setBoardType(type);
        board.setFirmware(adapter);
        board.setBaseBoard(new StandardBoard());

        return board;
    }
}

// Koniec, Tydzień 2
// Wzorzec Factory 3