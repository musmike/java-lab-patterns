package pl.lab.factory.electronicboards.firmware.commands;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.firmware.FirmwareAdapter;
import pl.lab.factory.electronicboards.firmware.memento.FirmwareMemento;
import pl.lab.factory.electronicboards.firmware.programmers.ProgrammingDevice;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Command 3 (Konkretne polecenie)
public class WriteFirmwareCommand implements FirmwareCommand{
    private ProgrammingDevice device;
    private BaseElectronicBoard board;
    private FirmwareAdapter adapter;
    private FirmwareMemento backup;

    public WriteFirmwareCommand(ProgrammingDevice device, BaseElectronicBoard board, FirmwareAdapter adapter) {
        this.device = device;
        this.board = board;
        this.adapter = adapter;
    }

    @Override
    public void execute() {
        backup = board.saveFirmware();
        try{
            device.writeFirmware(board, adapter);
            board.programBoard();
        } catch (RuntimeException e){
            board.restoreFirmware(backup);
            throw e;
        }

    }
}
//command 3
//koniec tydzien 5