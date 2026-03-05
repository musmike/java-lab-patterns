package pl.lab.factory.manufacturing;

import pl.lab.factory.pcbboards.BaseBoard;

public interface ElectronicBoardBuilder {
    void buildBaseBoard(BaseBoard pcb);
    void solderComponents();
    void assembleBoard();
    void installFirmware();
    void build();
}
