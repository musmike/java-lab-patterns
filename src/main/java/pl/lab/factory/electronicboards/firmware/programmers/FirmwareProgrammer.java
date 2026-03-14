package pl.lab.factory.electronicboards.firmware.programmers;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.firmware.FirmwareAdapter;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Bridge 3
// Abstrakcja
public abstract class FirmwareProgrammer {
    protected ProgrammingDevice device;

    public FirmwareProgrammer(ProgrammingDevice device) {
        this.device = device;
    }

    public abstract void program(BaseElectronicBoard board, FirmwareAdapter firmwareAdapter);
}
// Koniec, Tydzień 3
// Wzorzec Bridge 3