package pl.lab.factory.electronicboards.firmware.commands;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.firmware.FirmwareAdapter;
import pl.lab.factory.electronicboards.firmware.memento.FirmwareMemento;
import pl.lab.factory.electronicboards.firmware.programmers.ProgrammingDevice;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Command 3 (Konkretne polecenie)
public class WriteFirmwareCommand implements FirmwareCommand{
    private ProgrammingDevice device;
    private BaseElectronicBoard electronicBoard;
    private FirmwareAdapter adapter;
    private FirmwareMemento backup;

    public WriteFirmwareCommand(ProgrammingDevice device, BaseElectronicBoard electronicBoard, FirmwareAdapter adapter) {
        this.device = device;
        this.electronicBoard = electronicBoard;
        this.adapter = adapter;
    }

    @Override
    public void execute() {
        backup = electronicBoard.saveFirmware();
        try{
            device.writeFirmware(electronicBoard, adapter);
            electronicBoard.programBoard();
        } catch (RuntimeException e){
            electronicBoard.restoreFirmware(backup);
            throw e;
        }

    }
}
//command 3
//koniec tydzien 5