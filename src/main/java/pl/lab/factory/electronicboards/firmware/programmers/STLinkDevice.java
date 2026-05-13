package pl.lab.factory.electronicboards.firmware.programmers;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.firmware.FirmwareAdapter;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Bridge 3
// Konkretny implementor
//Tydzien 8 - Odwrocenie zaleznosci
//konkretna klasa pozwalająca bedąca jedną z opcji dla programatorów
public class STLinkDevice implements ProgrammingDevice {


    @Override
    public void start() {
        System.out.println("Connecting using ST-Link");
    }

    @Override
    public void writeFirmware(BaseElectronicBoard electronicBoard, FirmwareAdapter firmwareAdapter) {
        System.out.println("Writing firmware onto flash...");
        electronicBoard.setFirmware(firmwareAdapter);
    }

    @Override
    public void finish() {
        System.out.println("Disconnected!");
    }
}
// Koniec, Tydzień 3
// Wzorzec Bridge 3
//koniec DIP 3
//koniec tydzien 8