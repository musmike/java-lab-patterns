package pl.lab.factory.electronicboards.firmware.programmers;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.firmware.FirmwareAdapter;

//Tydzien 8 - Odwrocenie zaleznosci
//konkretna klasa pozwalająca bedąca jedną z opcji dla programatorów
public class FaultyDevice implements ProgrammingDevice{
    private boolean first = true;

    @Override
    public void start() {
        SystemLogger.INSTANCE.log("", "--->Device started<---");
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
        SystemLogger.INSTANCE.log("", "Writing firmware...");
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
        SystemLogger.INSTANCE.log("", "--->Write failed!<---");
    }

    private void performSuccessfulWrite(BaseElectronicBoard electronicBoard, FirmwareAdapter firmwareAdapter) {
        electronicBoard.setFirmware(firmwareAdapter);
        SystemLogger.INSTANCE.log("", "Writing firmware...\n" + firmwareAdapter.getFirmwareString() + "...");
    }

    @Override
    public void finish() {
        SystemLogger.INSTANCE.log("", "--->Device stopped<---");
    }
}
//koniec DIP 3
//koniec tydzien 8