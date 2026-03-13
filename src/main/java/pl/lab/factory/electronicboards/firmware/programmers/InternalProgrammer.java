package pl.lab.factory.electronicboards.firmware.programmers;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.firmware.FirmwareAdapter;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
//bridge 3 - uszczegolowiona abstrakcja
public class InternalProgrammer extends FirmwareProgrammer{
    public InternalProgrammer(ProgrammingDevice device) {
        super(device);
    }

    @Override
    public void program(BaseElectronicBoard board, FirmwareAdapter firmwareAdapter) {
        System.out.println("Internal programming device engaged...");
        device.start();
        device.writeFirmware(board, firmwareAdapter);
        board._programBoard();
        device.finish();
    }
}
//koniec bridge 3
//koniec tydzien 3