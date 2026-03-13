package pl.lab.factory.electronicboards.firmware.programmers;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.firmware.FirmwareAdapter;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
//bridge 3 - implementor
public interface ProgrammingDevice {
    void start();
    void writeFirmware(BaseElectronicBoard board, FirmwareAdapter firmwareAdapter);
    void finish();
}
//koniec bridge 3
//koniec tydzien 3