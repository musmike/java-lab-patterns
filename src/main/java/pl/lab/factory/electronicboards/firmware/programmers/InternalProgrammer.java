package pl.lab.factory.electronicboards.firmware.programmers;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.firmware.FirmwareAdapter;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Bridge 3
// Uszczegółowiona abstrakcja
//Tydzien 8 - Odwrocenie zaleznosci
//implementacja klasy abstrakcyjnej możliwa do wywoływania przez odwołanie do klasy bazowej oraz wykonująca oczekiwane działania
public class InternalProgrammer extends FirmwareProgrammer {
    public InternalProgrammer(ProgrammingDevice device) {
        super(device);
    }

    @Override
    public void program(BaseElectronicBoard electronicBoard, FirmwareAdapter firmwareAdapter) {
        System.out.println("Internal programming device engaged...");
        device.start();
        device.writeFirmware(electronicBoard, firmwareAdapter);
        electronicBoard.programBoard();
        device.finish();
    }
}
// Koniec, Tydzień 3
// Wzorzec Bridge 3
//koniec DIP 3
//koniec tydzien 8