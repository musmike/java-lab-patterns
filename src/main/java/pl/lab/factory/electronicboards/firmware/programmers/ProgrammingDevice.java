package pl.lab.factory.electronicboards.firmware.programmers;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.firmware.FirmwareAdapter;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Bridge 3
// Implementor
//Tydzien 8 - Odwrocenie zaleznosci
//interfejs bazowy pozwalający na pracę z wieloma urządzeniami z tym samym oczekiwanym efektem
public interface ProgrammingDevice {
    void start();
    void writeFirmware(BaseElectronicBoard board, FirmwareAdapter firmwareAdapter) throws RuntimeException;
    void finish();
}
// Koniec, Tydzień 3
// Wzorzec Bridge 3
//koniec DIP 3
//koniec tydzien 8