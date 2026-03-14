package pl.lab.factory.electronicboards.firmware.programmers;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.firmware.FirmwareAdapter;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Bridge 3
// Implementor
public interface ProgrammingDevice {
    void start();
    void writeFirmware(BaseElectronicBoard board, FirmwareAdapter firmwareAdapter);
    void finish();
}
// Koniec, Tydzień 3
// Wzorzec Bridge 3