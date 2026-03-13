package pl.lab.factory.electronicboards.firmware.programmers;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.firmware.FirmwareAdapter;

public class STLinkDevice implements ProgrammingDevice{

    @Override
    public void start() {
        System.out.println("Connecting using ST-Link");
    }

    @Override
    public void writeFirmware(BaseElectronicBoard board, FirmwareAdapter firmwareAdapter) {
        System.out.println("Writing firmware onto flash...");
        board.setFirmware(firmwareAdapter);
    }

    @Override
    public void finish() {
        System.out.println("Disconnected!");
    }
}
