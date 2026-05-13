package pl.lab.factory.electronicboards.firmware.programmers;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.firmware.FirmwareAdapter;

//Tydzien 8 - Odwrocenie zaleznosci
//konkretna klasa pozwalająca bedąca jedną z opcji dla programatorów
public class FaultyDevice implements ProgrammingDevice{
    private boolean first = true;

    @Override
    public void start() {
        System.out.println("--->Device started<---");
    }

    @Override
    public void writeFirmware(BaseElectronicBoard electronicBoard, FirmwareAdapter firmwareAdapter) {
        if (first) {
            simulateFailure(electronicBoard);
        } else {
            performSuccessfulWrite(electronicBoard, firmwareAdapter);
        }
    }

    private void simulateFailure(BaseElectronicBoard electronicBoard) {
        System.out.println("Writing firmware...");
        first = false;
        applyCorruptedFirmware(electronicBoard);
        throw new RuntimeException("Failed to write");
    }

    private void applyCorruptedFirmware(BaseElectronicBoard electronicBoard) {
        electronicBoard.setFirmware(new FirmwareAdapter() {
            @Override
            public String getFirmwareString() {
                return "#^#@^";
            }
        });
        System.out.println("--->Write failed!<---");
    }

    private void performSuccessfulWrite(BaseElectronicBoard electronicBoard, FirmwareAdapter firmwareAdapter) {
        electronicBoard.setFirmware(firmwareAdapter);
        System.out.println("Writing firmware...\n" + firmwareAdapter.getFirmwareString() + "...");
    }

    @Override
    public void finish() {
        System.out.println("--->Device stopped<---");
    }
}
//koniec DIP 3
//koniec tydzien 8