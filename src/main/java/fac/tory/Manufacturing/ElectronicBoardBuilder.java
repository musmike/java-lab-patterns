package fac.tory.Manufacturing;

import fac.tory.PCBBoards.BaseBoard;

public interface ElectronicBoardBuilder {
    void buildBaseBoard(BaseBoard pcb);
    void solderComponents();
    void assembleBoard();
    void installFirmware();
    void build();
}
