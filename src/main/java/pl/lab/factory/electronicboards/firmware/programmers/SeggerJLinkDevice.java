package pl.lab.factory.electronicboards.firmware.programmers;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.firmware.FirmwareAdapter;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Bridge 3
// Konkretny implementor
public class SeggerJLinkDevice implements ProgrammingDevice {

    @Override
    public void start() {
        System.out.println("Engaging with SeggerJLink!");
    }

    @Override
    public void writeFirmware(BaseElectronicBoard board, FirmwareAdapter firmwareAdapter) {
        System.out.println("Preparing code...");
        board.setFirmware(firmwareAdapter);
        System.out.println("Code written!");
    }

    @Override
    public void finish() {
        System.out.println("SeggerJLink disengaged!");
    }
}
// Koniec, Tydzień 3
// Wzorzec Bridge 3