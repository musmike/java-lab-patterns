package pl.lab.factory.electronicboards.firmware.programmers;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.firmware.FirmwareAdapter;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
//bridge 3 - abstrakcja
public abstract class FirmwareProgrammer {
    protected ProgrammingDevice device;

    public FirmwareProgrammer(ProgrammingDevice device) {
        this.device = device;
    }

    public abstract void program(BaseElectronicBoard board, FirmwareAdapter firmwareAdapter);
}
//koniec bridge 3
//koniec tydzien 3