package pl.lab.factory.electronicboards.firmware.programmers;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.firmware.FirmwareAdapter;

//tydzien 3 - bridge - uszczegolowiona abstrakcja
public class BootloaderProgrammer extends FirmwareProgrammer{
    public BootloaderProgrammer(ProgrammingDevice device) {
        super(device);
    }

    @Override
    public void program(BaseElectronicBoard board, FirmwareAdapter firmwareAdapter) {
        device.start();
        device.writeFirmware(board, firmwareAdapter);
        System.out.println("Preparing bootloader...\n.\n.\n.\nDone!");
        board._programBoard();
        System.out.println("Programming finished!");
        device.finish();
    }
}
//koniec tydzien 3