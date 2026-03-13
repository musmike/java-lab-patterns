package pl.lab.factory.electronicboards.firmware.programmers;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.firmware.FirmwareAdapter;

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
