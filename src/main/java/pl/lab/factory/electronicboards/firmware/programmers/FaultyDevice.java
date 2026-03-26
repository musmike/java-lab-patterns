package pl.lab.factory.electronicboards.firmware.programmers;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.firmware.FirmwareAdapter;

public class FaultyDevice implements ProgrammingDevice{
    private boolean first = true;

    @Override
    public void start() {
        System.out.println("--->Device started<---");
    }

    @Override
    public void writeFirmware(BaseElectronicBoard board, FirmwareAdapter firmwareAdapter) {
        if(first){
            System.out.println("Writing firmware...");
            first= false;
            board.setFirmware(new FirmwareAdapter() {
                @Override
                public String getFirmwareString() {
                    return "#^#@^";
                }
            });
            System.out.println("--->Write failed!<---");
            throw new RuntimeException("Falied to write");
        }
        board.setFirmware(firmwareAdapter);
        System.out.println("Writing firmware...\n" + firmwareAdapter.getFirmwareString() + "...");
    }

    @Override
    public void finish() {
        System.out.println("--->Device stopped<---");
    }
}
