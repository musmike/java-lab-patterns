package pl.lab.factory.electronicboards.firmware.programmers;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.firmware.FirmwareAdapter;

public abstract class FirmwareProgrammer {
    protected ProgrammingDevice device;

    public FirmwareProgrammer(ProgrammingDevice device) {
        this.device = device;
    }

    public abstract void program(BaseElectronicBoard board, FirmwareAdapter firmwareAdapter);
}
