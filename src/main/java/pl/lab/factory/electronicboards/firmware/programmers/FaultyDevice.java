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
    public void writeFirmware(BaseElectronicBoard board, FirmwareAdapter firmwareAdapter) {
        if (first) {
            simulateFailure(board);
        } else {
            performSuccessfulWrite(board, firmwareAdapter);
        }
    }

    private void simulateFailure(BaseElectronicBoard board) {
        System.out.println("Writing firmware...");
        first = false;
        applyCorruptedFirmware(board);
        throw new RuntimeException("Failed to write");
    }

    private void applyCorruptedFirmware(BaseElectronicBoard board) {
        board.setFirmware(new FirmwareAdapter() {
            @Override
            public String getFirmwareString() {
                return "#^#@^";
            }
        });
        System.out.println("--->Write failed!<---");
    }

    private void performSuccessfulWrite(BaseElectronicBoard board, FirmwareAdapter firmwareAdapter) {
        board.setFirmware(firmwareAdapter);
        System.out.println("Writing firmware...\n" + firmwareAdapter.getFirmwareString() + "...");
    }

    @Override
    public void finish() {
        System.out.println("--->Device stopped<---");
    }
}
//koniec DIP 3
//koniec tydzien 8