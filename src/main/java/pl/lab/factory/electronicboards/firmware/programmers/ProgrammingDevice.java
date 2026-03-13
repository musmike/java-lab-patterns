package pl.lab.factory.electronicboards.firmware.programmers;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.firmware.FirmwareAdapter;

public interface ProgrammingDevice {
    void start();
    void writeFirmware(BaseElectronicBoard board, FirmwareAdapter firmwareAdapter);
    void finish();
}
